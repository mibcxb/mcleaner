package com.mibcxb.cleaner.cli;

import com.mibcxb.cleaner.Cleaner;
import com.mibcxb.cleaner.gradle.GradleCleaner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CleanerCli {
    private final Map<String, Cleaner> cleaners = new HashMap<>();

    public CleanerCli() {
        cleaners.put("GradleCleaner", new GradleCleaner());
    }

    public static void main(String[] args) {
        CleanerCli cli = new CleanerCli();
        Options options = cli.createOptions();

        CommandLine commands = null;
        try {
            CommandLineParser parser = new DefaultParser();
            commands = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (commands == null || commands.getOptions().length == 0) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CleanerCli.jar -cc <工具名称> [参数]", options);
        } else {
            cli.processCommands(commands);
        }
    }

    private void processCommands(CommandLine commands) {
        String cleanerName = commands.getOptionValue("cleaner");
        if (StringUtils.isBlank(cleanerName)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CleanerCli.jar -cc <工具名称> [参数]", createOptions());
        } else {
            Cleaner cleaner = null;
            switch (cleanerName) {
                case "GradleCleaner":
                    cleaner = new GradleCleaner();
                default:
                    break;
            }
            if (cleaner != null) {
                cleaner.clean(commands);
            } else {
                System.out.println("不支持的清理工具： " + cleanerName);
            }
        }
    }

    private Options createOptions() {
        Options options = new Options()
                .addOption(Option.builder("h").longOpt("help")
                        .hasArg(false)
                        .desc("获取帮助信息")
                        .build())
                .addOption(Option.builder("v").longOpt("version")
                        .hasArg(false)
                        .desc("程序版本")
                        .build())
                .addOption(Option.builder("cc").longOpt("cleaner")
                        .hasArg(true).numberOfArgs(1)
                        .desc("选择清理工具")
                        .build());

        for (Cleaner cleaner : cleaners.values()) {
            for (Option option : cleaner.options()) {
                options.addOption(option);
            }
        }
        return options;
    }
}

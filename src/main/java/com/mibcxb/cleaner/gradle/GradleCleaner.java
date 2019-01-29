package com.mibcxb.cleaner.gradle;

import com.mibcxb.cleaner.Cleaner;
import com.mibcxb.cleaner.CleanerAdapter;
import com.mibcxb.cleaner.Printer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class GradleCleaner extends CleanerAdapter {

    @Override
    public Option[] options() {
        return new Option[]{
                Option.builder("g").longOpt("group")
                        .hasArg(true).numberOfArgs(1)
                        .desc("开发组名称")
                        .build(),
                Option.builder("m").longOpt("module")
                        .hasArg(true).numberOfArgs(1)
                        .desc("模块名称")
                        .build(),
                Option.builder("vn").longOpt("versionName")
                        .hasArg(true).numberOfArgs(1)
                        .desc("版本名称")
                        .build()};
    }

    @Override
    public void clean(CommandLine commands) {
        String groupId = null;
        String module = null;
        String version = null;
        if (commands.hasOption("group")) {
            groupId = commands.getOptionValue("group");
        }
        if (commands.hasOption("module")) {
            module = commands.getOptionValue("module");
        }
        if (commands.hasOption("versionName")) {
            version = commands.getOptionValue("versionName");
        }
        cleanCache(groupId, module, version);
    }

    public void cleanCache(String groupId, String module, String version) {
        if (StringUtils.isAllBlank(groupId, module, version)) {
            print("GradleCleaner缺少参数！");
            return;
        }
        String userHome = System.getProperty("user.home");
        File root = new File(userHome);
        File gradle = new File(root, ".gradle");
        File cacheModules = new File(gradle, "caches/modules-2");

        String path = groupId;
        if (StringUtils.isNotBlank(module)) {
            path = path + File.separator + module;
        }
        if (StringUtils.isNotBlank(version)) {
            path = path + File.separator + version;
        }

        File[] folders = cacheModules.listFiles(File::isDirectory);
        if (folders != null) {
            for (File parent : folders) {
                File folder = null;
                if (parent.getName().startsWith("file")) {
                    folder = new File(parent, path);
                } else if (parent.getName().startsWith("metadata")) {
                    folder = new File(parent, "descriptors" + File.separator + path);
                }
                if (folder != null) {
                    delete(folder);
                }
            }
        }
    }

    private void delete(File file) {
        if (file == null) {
            return;
        }
        if (file.isFile()) {
            if (file.delete()) {
                print("删除： " + file.getAbsolutePath());
            }
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    delete(child);
                }
            }
            if (file.delete()) {
                print("删除： " + file.getAbsolutePath());
            }
        }
    }
}

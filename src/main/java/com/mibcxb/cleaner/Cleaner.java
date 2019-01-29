package com.mibcxb.cleaner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

public interface Cleaner {
    Option[] options();

    void clean(CommandLine commands);

    void setPrinter(Printer printer);
}

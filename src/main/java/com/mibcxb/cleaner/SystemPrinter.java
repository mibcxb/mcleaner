package com.mibcxb.cleaner;

public class SystemPrinter implements Printer {
    @Override
    public void print(String message) {
        System.out.println(message);
    }
}

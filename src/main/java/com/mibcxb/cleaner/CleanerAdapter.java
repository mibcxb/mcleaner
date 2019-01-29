package com.mibcxb.cleaner;

public abstract class CleanerAdapter implements Cleaner {
    private Printer printer;

    public CleanerAdapter() {
        this(new SystemPrinter());
    }

    public CleanerAdapter(Printer printer) {
        this.printer = printer;
    }

    @Override
    public void setPrinter(Printer printer) {
        this.printer = printer;
    }

    protected void print(String message) {
        if (printer != null) {
            printer.print(message);
        }
    }
}

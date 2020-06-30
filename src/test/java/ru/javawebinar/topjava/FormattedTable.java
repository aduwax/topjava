package ru.javawebinar.topjava;


import java.util.ArrayList;
import java.util.List;

public class FormattedTable {
    private final List<String> lines = new ArrayList<>();
    private final String format;
    private final Integer tableSize;

    public FormattedTable(String... columnNames) {
        this(30, columnNames);
    }

    public FormattedTable(int columnLength, String... columnNames) {
        tableSize = columnNames.length;
        format = repeat("| %-" + columnLength + "s ", tableSize) + "|";
        add(columnNames);
    }

    public void add(Object... data) {
        lines.add(String.format(format, data));
    }

    private String repeat(String string, int count) {
        StringBuilder repeated = new StringBuilder(string);
        for (int i = 1; i < count; i++) {
            repeated.append(string);
        }
        return repeated.toString();
    }

    @Override
    public String toString() {
        return "\n" + String.join("\n", lines);
    }
}

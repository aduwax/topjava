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
        format = repeat("| %" + columnLength + "s ", tableSize) + "|";
        String columnFormat = "\033[1;94m" + format + "\u001B[0m";
        lines.add(String.format(columnFormat, columnNames));
    }

    public void add(Object... data) {
        lines.add(String.format(format, data));
    }

    private String repeat(String string, int count) {
        for (int i = 1; i < count; i++) {
            string += string;
        }
        return string;
    }

    @Override
    public String toString() {
        return "\n" + String.join("\n", lines);
    }
}

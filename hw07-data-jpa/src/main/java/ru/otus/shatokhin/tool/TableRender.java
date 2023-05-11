package ru.otus.shatokhin.tool;

import java.util.Collections;
import java.util.List;

public interface TableRender {

    <T> String render(String title, List<String> headers, RowRenderer<T> rowRenderer, List<T> rows);

    default <T> String singleRowRender(String title, List<String> headers, RowRenderer<T> rowRenderer, T row) {
        return render(title, headers, rowRenderer, Collections.singletonList(row));
    }

    @FunctionalInterface
    interface RowRenderer<T> {
        List<String> renderRow(T row);
    }
}

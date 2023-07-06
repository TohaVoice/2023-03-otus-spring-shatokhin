package ru.otus.shatokhin.tool;

import de.vandermeer.asciitable.AT_Row;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TableRenderImpl implements TableRender {

    @Override
    public <T> String render(String title, List<String> headers, RowRenderer<T> rowRenderer, List<T> rows) {
        AsciiTable table = new AsciiTable();
        table.addRule();
        List<String> titleRow = new ArrayList<>(headers.size());
        titleRow.addAll(Collections.nCopies(headers.size() - 1, null));
        titleRow.add(title);
        table.addRow(titleRow);
        table.addRule();
        table.addRow(headers);
        table.addRule();
        for (T row : rows) {
            AT_Row tableRow = table.addRow(rowRenderer.renderRow(row));
            tableRow.getCells().get(0).getContext().setTextAlignment(TextAlignment.RIGHT);
            table.addRule();
        }
        table.getRenderer().setCWC(new CWC_LongestLine());
        table.setPaddingLeftRight(1);
        return table.render();
    }
}

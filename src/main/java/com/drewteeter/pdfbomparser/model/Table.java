package com.drewteeter.pdfbomparser.model;

import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;

import java.util.List;

public class Table {
    private List<Row> rows;

    public Table(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }

    public static final class Row {
        private List<NormalizedTextEntry> columns;

        public Row(List<NormalizedTextEntry> columns) {
            this.columns = columns;
        }

        public List<NormalizedTextEntry> getColumns() {
            return columns;
        }
    }
}

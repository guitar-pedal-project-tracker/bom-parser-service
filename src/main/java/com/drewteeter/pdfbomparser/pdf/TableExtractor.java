package com.drewteeter.pdfbomparser.pdf;

import com.drewteeter.pdfbomparser.model.Table;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;

import java.util.List;

public interface TableExtractor {
    List<Table> tablesInEntries(List<NormalizedTextEntry> textEntries);
}

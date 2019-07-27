package com.drewteeter.pdfbomparser.pdf;

import com.drewteeter.pdfbomparser.hint.Hint;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;

import java.util.List;

public interface SectionExtractor {
    List<NormalizedTextEntry> entriesInSection(List<Hint> sectionHints, List<NormalizedTextEntry> textEntries);
}

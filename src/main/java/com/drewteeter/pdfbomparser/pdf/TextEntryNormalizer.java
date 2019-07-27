package com.drewteeter.pdfbomparser.pdf;

import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;
import com.drewteeter.pdfbomparser.pdf.model.RawTextEntry;

import java.util.List;

public interface TextEntryNormalizer {
    List<NormalizedTextEntry> normalize(List<RawTextEntry> rawTextEntries);
}

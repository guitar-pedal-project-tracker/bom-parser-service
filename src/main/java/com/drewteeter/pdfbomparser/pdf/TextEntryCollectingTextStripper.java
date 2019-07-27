package com.drewteeter.pdfbomparser.pdf;

import com.drewteeter.pdfbomparser.pdf.model.RawTextEntry;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

public interface TextEntryCollectingTextStripper {
    List<RawTextEntry> collectEntries(PDDocument document) throws IOException;
}

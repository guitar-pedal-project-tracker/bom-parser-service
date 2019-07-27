package com.drewteeter.pdfbomparser.pdf.impl;

import com.drewteeter.pdfbomparser.pdf.model.RawTextEntry;
import com.drewteeter.pdfbomparser.pdf.TextEntryCollectingTextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultTextEntryCollectingTextStripper extends PDFTextStripper implements TextEntryCollectingTextStripper  {
    private List<RawTextEntry> textEntries = new ArrayList<>();
    private int currentPage = 0;

    public DefaultTextEntryCollectingTextStripper() throws IOException {
        super();
    }

    public List<RawTextEntry> collectEntries(PDDocument document) throws IOException {
        DefaultTextEntryCollectingTextStripper stripper = new DefaultTextEntryCollectingTextStripper();
        stripper.getText(document);
        return stripper.getTextEntries();
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        boolean allTextRotated = textPositions.stream().allMatch(this::isTextRotated);
        // For now, we are going to ignore rotated text, as it makes it much more difficult to process
        if (!allTextRotated) {
            textEntries.add(new RawTextEntry(text, currentPage, textPositions));
        }
        super.writeString(text, textPositions);
    }

    protected boolean isTextRotated(TextPosition textPosition) {
        return !Double.isNaN(Math.acos(textPosition.getTextMatrix().createAffineTransform().getScaleX() % Math.PI));
    }

    @Override
    protected void startPage(PDPage page) throws IOException {
        ++currentPage;
        super.startPage(page);
    }

    protected List<RawTextEntry> getTextEntries() {
        return textEntries;
    }
}

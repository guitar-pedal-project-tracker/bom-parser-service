package com.drewteeter.pdfbomparser.pdf.impl;

import com.drewteeter.pdfbomparser.pdf.TextEntryNormalizer;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedPosition;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;
import com.drewteeter.pdfbomparser.pdf.model.RawTextEntry;
import org.apache.pdfbox.text.TextPosition;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultTextEntryNormalizer implements TextEntryNormalizer {
    @Override
    public List<NormalizedTextEntry> normalize(List<RawTextEntry> rawTextEntries) {
        return rawTextEntries.stream().map(rawTextEntry ->
                new NormalizedTextEntry(rawTextEntry.getText(), rawTextEntry.getPage(),
                        new NormalizedPosition(
                                textPositionMin(rawTextEntry, TextPosition::getX),
                                textPositionMin(rawTextEntry, TextPosition::getY),
                                textPositionMax(rawTextEntry, TextPosition::getEndX),
                                // endY seems quite unreliable, so use height
                                textPositionMax(rawTextEntry, TextPosition::getHeight)
                        )
                ))
                // Sorted by page
                .sorted(Comparator.comparing(NormalizedTextEntry::getPage)
                        // and then y-position on that page
                        .thenComparing(textEntry -> textEntry.getPosition().getY()))
                .collect(Collectors.toList());
    }

    private Stream<Float> nonEmptyMappedTextPositions(RawTextEntry rawTextEntry, Function<TextPosition, Float> mapper) {
        return rawTextEntry.getTextPositions().stream()
                .filter((TextPosition textPosition) -> textPosition.getUnicode() != null && !textPosition.getUnicode().trim().isEmpty())
                .map(mapper);
    }

    private Float textPositionMin(RawTextEntry rawTextEntry, Function<TextPosition, Float> mapper) {
        return nonEmptyMappedTextPositions(rawTextEntry, mapper).min(Comparator.naturalOrder()).get();
    }

    private Float textPositionMax(RawTextEntry rawTextEntry, Function<TextPosition, Float> mapper) {
        return nonEmptyMappedTextPositions(rawTextEntry, mapper).max(Comparator.naturalOrder()).get();
    }
}

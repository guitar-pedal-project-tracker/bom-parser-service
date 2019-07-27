package com.drewteeter.pdfbomparser.pdf.impl;

import com.drewteeter.pdfbomparser.hint.Hint;
import com.drewteeter.pdfbomparser.pdf.SectionExtractor;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultSectionExtractor implements SectionExtractor {
    @Override
    public List<NormalizedTextEntry> entriesInSection(List<Hint> sectionHints, List<NormalizedTextEntry> textEntries) {
        List<NormalizedTextEntry> trimmedNonEmptyEntries = textEntries.stream().filter(
                (NormalizedTextEntry entry) -> entry.getText() != null & !entry.getText().trim().equals(""))
                .map((NormalizedTextEntry entry) -> new NormalizedTextEntry(
                        entry.getText().trim(),
                        entry.getPage(),
                        entry.getPosition())
                ).collect(Collectors.toList());
        List<NormalizedTextEntry> entriesForSection = Lists.newArrayList();
        boolean inSection = false;
        boolean enteringSection = false;
        float inSectionHeadingSize = 0f;

        for (NormalizedTextEntry textEntry : trimmedNonEmptyEntries) {
            if (inSection && textEntry.getPosition().getHeight() >= inSectionHeadingSize && entriesForSection.size() > 5) {
                // We've hit something that has text size to indicate a new section, so assume we are exiting the section.
                // We're not short circuiting to allow for potential follow-on sections (e.g., Aion has a heading per page
                // of the parts list).
                inSection = false;
            }

            for (Hint sectionHint : sectionHints) {
                if (sectionHint.matches(textEntry.getText())) {
                    enteringSection = true;
                }
            }

            if (inSection) {
                entriesForSection.add(textEntry);
            }

            if (enteringSection) {
                inSection = true;
                inSectionHeadingSize = textEntry.getPosition().getHeight();
                enteringSection = false;
            }
        }

        return entriesForSection;
    }

    private boolean within(float height, float targetHeight, float allowedRange) {
        float minHeight = targetHeight - targetHeight * allowedRange;
        float maxHeight = targetHeight + targetHeight * allowedRange;

        return height >= minHeight && height <= maxHeight;
    }
}

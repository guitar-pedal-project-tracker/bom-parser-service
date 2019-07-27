package com.drewteeter.pdfbomparser.hint.impl;

import com.drewteeter.pdfbomparser.document.DocumentSection;
import com.drewteeter.pdfbomparser.hint.Hint;
import com.drewteeter.pdfbomparser.hint.HintService;
import com.drewteeter.pdfbomparser.hint.RegexHint;
import com.drewteeter.pdfbomparser.hint.StaticTextHint;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class StaticHintService implements HintService {
    @Override
    public List<Hint> getHintsForDocumentSection(DocumentSection section, Optional<String> knownDesigner) {
        if (section == DocumentSection.BILL_OF_MATERIALS) {
            return Lists.newArrayList(
                    new StaticTextHint("Bill of Materials"), new StaticTextHint("BoM"),
                    new StaticTextHint("Parts"),
                    new RegexHint(Pattern.compile("Parts\\s+List\\s*(?:,\\s*Cont\\.)?", Pattern.CASE_INSENSITIVE))
            );
        } else {
            return Lists.newArrayList();
        }
    }
}

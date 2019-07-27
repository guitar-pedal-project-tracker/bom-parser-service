package com.drewteeter.pdfbomparser.hint;

import java.util.regex.Pattern;

public class RegexHint implements Hint {
    private Pattern pattern;

    public RegexHint(Pattern pattern) {
        this.pattern = pattern;
    }

    public RegexHint(String regex) {
        this(Pattern.compile(regex));
    }


    @Override
    public boolean matches(String textBlock) {
        return pattern.matcher(textBlock).matches();
    }
}

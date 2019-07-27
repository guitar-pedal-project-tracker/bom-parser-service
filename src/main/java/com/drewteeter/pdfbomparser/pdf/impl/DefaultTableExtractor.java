package com.drewteeter.pdfbomparser.pdf.impl;

import com.drewteeter.pdfbomparser.model.Table;
import com.drewteeter.pdfbomparser.pdf.TableExtractor;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultTableExtractor implements TableExtractor {
    protected static final Function<NormalizedTextEntry, Float> Y_START_PROVIDER = (NormalizedTextEntry textEntry) -> textEntry.getPosition().getY();
    protected static final Function<NormalizedTextEntry, Float> Y_END_PROVIDER = (NormalizedTextEntry textEntry) -> textEntry.getPosition().getY() + textEntry.getPosition().getHeight();
    protected static final Function<NormalizedTextEntry, Float> X_START_PROVIDER = (NormalizedTextEntry textEntry) -> textEntry.getPosition().getX();
    protected static final Function<NormalizedTextEntry, Float> X_END_PROVIDER = (NormalizedTextEntry textEntry) -> textEntry.getPosition().getEndX();

    @Override
    public List<Table> tablesInEntries(List<NormalizedTextEntry> allTextEntries) {
        List<Table> tables = Lists.newArrayList();

        allTextEntries.stream().collect(Collectors.groupingBy(NormalizedTextEntry::getPage))
                .values()
                .forEach((List<NormalizedTextEntry> textEntries) -> {
                    LinkedHashSet<Float> yIndexes =
                            textEntries.stream()
                                    .map((NormalizedTextEntry entry) -> entry.getPosition().getY())
                                    .sorted().collect(Collectors.toCollection(LinkedHashSet::new));

                    List<NormalizedTextEntry> validEntriesByY = getValidEntriesForEachStartPosition(textEntries,
                            Y_START_PROVIDER);
                    List<NormalizedTextEntry> validEntriesByX = getValidEntriesForEachStartPosition(textEntries,
                            X_END_PROVIDER);
                    List<Float> yGaps = calculateGaps(getLongestEntryForEachValidStartIndex(validEntriesByY, Y_START_PROVIDER, Y_END_PROVIDER).values(),
                            Y_START_PROVIDER, Y_END_PROVIDER);
                    List<Float> xGaps = calculateGaps(getLongestEntryForEachValidStartIndex(validEntriesByX, X_START_PROVIDER, X_END_PROVIDER).values(),
                            X_START_PROVIDER, X_END_PROVIDER);

                    List<Table.Row> rows = Lists.newArrayList();

                    for (Float yIndex : yIndexes) {
                        List<NormalizedTextEntry> columns = Lists.newArrayList();
                        columns.addAll(textEntries.stream()
                                .filter((NormalizedTextEntry entry) -> entry.getPosition().getY() == yIndex)
                                .sorted(Comparator.comparing((NormalizedTextEntry entry) -> entry.getPosition().getX()))
                                .collect(Collectors.toList())
                        );
                        rows.add(new Table.Row(columns));
                    }

                    List<String> rowStrings = rows.stream()
                            .map((Table.Row row) ->
                                    row.getColumns().stream()
                                            .map(NormalizedTextEntry::getText).collect(Collectors.joining(" "))
                            ).collect(Collectors.toList());
                });

        return tables;
    }

    protected List<Float> calculateGaps(Collection<NormalizedTextEntry> longestEntriesForPositions,
                                        Function<NormalizedTextEntry, Float> startPositionProvider,
                                        Function<NormalizedTextEntry, Float> endPositionProvider) {
        List<Float> diffToPrev = Lists.newArrayList();
        Float prevEnd = null;
        for (NormalizedTextEntry textEntry : longestEntriesForPositions) {
            if (prevEnd != null) {
                diffToPrev.add(startPositionProvider.apply(textEntry) - prevEnd);
            }
            prevEnd = endPositionProvider.apply(textEntry);
        }

        return diffToPrev;
    }

    protected TreeMap<Float, NormalizedTextEntry> getLongestEntryForEachValidStartIndex(List<NormalizedTextEntry> textEntries,
                                                                                        Function<NormalizedTextEntry, Float> startPositionProvider,
                                                                                        Function<NormalizedTextEntry, Float> endPositionProvider) {
        return textEntries.stream()
                .collect(
                        Collectors.toMap(
                                startPositionProvider,
                                Function.identity(),
                                BinaryOperator.maxBy(
                                        Comparator.comparing(endPositionProvider)
                                ),
                                TreeMap::new
                        )
                );
    }

    protected List<NormalizedTextEntry> getValidEntriesForEachStartPosition(List<NormalizedTextEntry> textEntries,
                                                                            Function<NormalizedTextEntry, Float> startPositionProvider) {
        Map<Float, Long> countByStartIndex = new TreeMap<>(textEntries.stream()
                .collect(
                        Collectors.groupingBy(
                                startPositionProvider,
                                Collectors.counting()
                        )
                )
        );

        return textEntries.stream()
                .filter(
                        (NormalizedTextEntry textEntry) -> countByStartIndex.get(startPositionProvider.apply(textEntry)) > 1
                )
                .collect(Collectors.toList());
    }
}

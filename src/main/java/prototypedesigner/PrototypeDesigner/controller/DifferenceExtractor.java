package prototypedesigner.PrototypeDesigner.controller;

import javafx.util.Pair;
import prototypedesigner.PrototypeDesigner.components.NonPolarizedCapacitor;
import prototypedesigner.PrototypeDesigner.components.Resistor;
import prototypedesigner.PrototypeDesigner.components.Terminal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DifferenceExtractor {

    public static List<Pair<Set<Terminal>, Set<Terminal>>> extractDifferences(List<Set<Terminal>> schNetwork, List<Set<Terminal>> traversedTerminals) {
        List<Pair<Integer, Integer>> equalOnSchAndBoardIdx = new ArrayList<>();
        for (int i = 0; i < schNetwork.size(); i++) {
            for (int j = 0; j < traversedTerminals.size(); j++) {
                final int ii = i;
                final int jj = j;
                if (equalOnSchAndBoardIdx.stream().noneMatch(p -> p.getKey() == ii && p.getValue() == jj)) {
                    if (schNetwork.get(i).containsAll(traversedTerminals.get(j))
                            && traversedTerminals.get(j).containsAll(schNetwork.get(i)))
                        equalOnSchAndBoardIdx.add(new Pair<>(i, j));
                    else {
                        Set<String> schSet = new HashSet<>();
                        Set<String> boardSet = new HashSet<>();
                        for (Terminal t: schNetwork.get(i)) {
                            if (t.getComponent() instanceof Resistor || t.getComponent() instanceof NonPolarizedCapacitor)
                                schSet.add(t.getComponent().getIdentifier());
                            else schSet.add(t.getIdentifier());
                        }
                        for (Terminal t: traversedTerminals.get(i)) {
                            if (t.getComponent() instanceof Resistor || t.getComponent() instanceof NonPolarizedCapacitor)
                                boardSet.add(t.getComponent().getIdentifier());
                            else boardSet.add(t.getIdentifier());
                        }
                        if (schSet.containsAll(boardSet) && boardSet.containsAll(schSet))
                            equalOnSchAndBoardIdx.add(new Pair<>(i, j));
                    }
                }
            }
        }
        List<Pair<Set<Terminal>, Set<Terminal>>> equalOnSchAndBoardSets = equalOnSchAndBoardIdx.stream().map(pair ->
                new Pair<>(schNetwork.get(pair.getKey()), traversedTerminals.get(pair.getValue()))).collect(Collectors.toList());
        for (Pair<Set<Terminal>, Set<Terminal>> pair: equalOnSchAndBoardSets) {
            schNetwork.remove(pair.getKey());
            traversedTerminals.remove(pair.getValue());
        }
        return schNetwork.stream().map(schSet ->
                        new Pair<>(schSet, traversedTerminals.stream()
                                .filter(boardSet -> boardSet.stream().anyMatch(schSet::contains))
                                .findFirst().orElse(new HashSet<>())))
                .collect(Collectors.toList());
    }
}

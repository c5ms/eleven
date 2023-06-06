package com.eleven.eng.dto;

import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class TokenResult {
    private final Set<String> simples = new LinkedHashSet<>();
    private final Set<String> words = new LinkedHashSet<>();
    private final Set<String> unknowns = new LinkedHashSet<>();

    public void addWord(String word) {
        if (word.length() <= 3) {
            this.simples.add(word);
        }
        this.words.add(word);
    }


}

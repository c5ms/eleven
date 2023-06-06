package com.eleven.eng.domain;

import cn.hutool.core.io.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileUrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class WordsLibrary {

    private final Set<String> words = new HashSet<>();

    /**
     * load all word from word resource
     *
     * @return the word set.
     */
    public Set<String> loadAllWords() {
        if (this.words.size() == 0) {
            synchronized (words) {
                if (this.words.size() == 0) {
                    try {
                        FileUrlResource wllWordsResource = new FileUrlResource("./library/all_words.lib");
                        var words = FileUtil.readLines(wllWordsResource.getURL(), StandardCharsets.UTF_8);
                        this.words.addAll(words);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        return words;
    }

    /**
     * check whether the word exist or not from word library
     *
     * @param word is the check target
     * @return true means the word exists in the library
     */
    public boolean isWord(String word) {
        return loadAllWords().contains(word);
    }


    public Set<String> removeKnownWords(Set<String> words) {
        var result = new HashSet<String>();
        var file = new File("./library/known_words.lib");
        var libWords = FileUtil.readLines(file, StandardCharsets.UTF_8);
        for (String word : words) {
            if (!libWords.contains(word)) {
                result.add(word);
            }
        }
        return result;
    }

    public Set<String> extractNewWords(String article) {
        var words = this.extractWords(article);
        return removeKnownWords(words);
    }


    /**
     * extract word from article
     *
     * @param article the article needs to analyze
     * @return all word can be found from this article
     */
    public Set<String> extractWords(String article) {
        var words = new LinkedHashSet<String>();
        for (String word : article.split(" |\\\\.|\"")) {
            word= StringUtils.trim(word);
            if (word.length() > 2 && isWord(word)) {
                words.add(word);
            }
        }
        return words;
    }


    /**
     * update known words library
     *
     * @param words the words of your acquisition
     */
    public void masteredWords(Set<String> words) {
        var file = new File("./library/known_words.lib");
        var libWords = FileUtil.readLines(file, StandardCharsets.UTF_8);
        words.removeIf(s -> s.length() <= 1);
//        words.removeIf(s -> !isWord(s));
        words.removeIf(libWords::contains);
        FileUtil.appendLines(words, file, StandardCharsets.UTF_8);
    }



    public void createBook(String bookName, Set<String> words) {
        var file = new File("./library/book/" + bookName + ".txt");
        FileUtil.writeLines(words, file, StandardCharsets.UTF_8);
    }
}

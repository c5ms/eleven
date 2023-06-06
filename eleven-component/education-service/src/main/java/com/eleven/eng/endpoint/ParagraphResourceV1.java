package com.eleven.eng.endpoint;

import com.eleven.core.rest.annonation.RestResource;
import com.eleven.eng.domain.WordsLibrary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Slf4j
@Tag(name = "单词")
@RequestMapping("/edu/paragraphs")
@RestResource
@RequiredArgsConstructor
public class ParagraphResourceV1 {

    private final WordsLibrary wordsLibrary;

    @Operation(summary = "习得文章")
    @PostMapping("/_mastered")
    public void _mastered(@RequestBody String article) {
        var words = wordsLibrary.extractWords(article);
        wordsLibrary.masteredWords(words);
    }

    @Operation(summary = "创建单词本")
    @PostMapping("/_words_book/{name}")
    public void _words_book(@PathVariable("name") String bookName, @RequestBody String article) {
        var words = wordsLibrary.extractWords(article);
        wordsLibrary.createBook(bookName,words);
    }

    @Operation(summary = "提取单词表")
    @PostMapping("/_token_words")
    public Set<String> _token_words(@RequestBody String content) {
        return wordsLibrary.extractNewWords(content);
    }


}

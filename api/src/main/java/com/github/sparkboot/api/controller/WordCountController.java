package com.github.sparkboot.api.controller;

import com.github.sparkboot.driver.service.WordCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCountController {
    private final WordCountService wordCountService;

    @Autowired
    public WordCountController(WordCountService wordCountService) {
        this.wordCountService = wordCountService;
    }

    @GetMapping("/")
    public long wordCount() {
        return wordCountService.countWords();
    }
}

package com.sai.springaiintro.controllers;

import com.sai.springaiintro.model.GetCapitalRequest;
import com.sai.springaiintro.model.Question;
import com.sai.springaiintro.model.Answer;

import com.sai.springaiintro.services.OpenAIService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 * Create by jared
 */
@RestController
public class QuestionController {

    private final OpenAIService openAIService;

    public QuestionController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/capital")
    public Answer askQuestion(@RequestBody GetCapitalRequest getCapitalRequest){
        //return new Answer("This is the answer to your question");
        return openAIService.getCapital(getCapitalRequest);
    }

    @PostMapping("/ask")
    public Answer askQuestion(@RequestBody Question question){
        //return new Answer("This is the answer to your question");
        return openAIService.getAnswer(question);
    }
}

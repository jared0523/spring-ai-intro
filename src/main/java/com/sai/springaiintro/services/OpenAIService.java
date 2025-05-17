package com.sai.springaiintro.services;

import com.sai.springaiintro.model.GetCapitalRequest;
import com.sai.springaiintro.model.Question;
import com.sai.springaiintro.model.Answer;

public interface OpenAIService {

    Answer getCapital(GetCapitalRequest getCapitalRequest);

    String getAnswer(String question);

    Answer getAnswer(Question question);
}

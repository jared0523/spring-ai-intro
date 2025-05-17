package com.sai.springaiintro.services;

import com.sai.springaiintro.model.Answer;
import com.sai.springaiintro.model.GetCapitalRequest;
import com.sai.springaiintro.model.Question;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    private final ChatModel chatModel;
    private static final Logger logger = LoggerFactory.getLogger(OpenAIServiceImpl.class);

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Value("classpath:templates/get-capital-prompt.st")
    private Resource getCaptitalPrompt;

    @Value("classpath:templates/get-capital-with-info.st")
    private Resource getCaptitalPromptWithInfo;

    @Override
    public Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest) {

        Instant startTime = Instant.now();  // 記錄開始時間

        // 請求內容準備
        //替換關鍵字
        //String inputPrompt = "what is the capital of " + getCapitalRequest.stateOrCountry() + "?";
        //使用Template檔
        //String inputPrompt = "what is the capital of " + getCapitalRequest.stateOrCountry() + "?";
        PromptTemplate promptTemplate = new PromptTemplate(getCaptitalPromptWithInfo);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry()));
        logger.info("OpenAI 請求開始 - Prompt: {}", prompt.getContents().toString());

        //PromptTemplate promptTemplate = new PromptTemplate(inputPrompt);
        //Prompt prompt = promptTemplate.create();

        // 呼叫 OpenAI
        ChatResponse response = chatModel.call(prompt);

        // 結果與時間記錄
        Instant endTime = Instant.now();  // 記錄結束時間
        long durationMs = Duration.between(startTime, endTime).toMillis();

        String outputContent = response.getResult().getOutput().getContent();
        logger.info("OpenAI 回覆完成 - Response: {}", outputContent);
        logger.info("OpenAI 請求耗時: {} ms", durationMs);

        return new Answer(outputContent);
    }

    @Override
    public Answer getCapital(GetCapitalRequest getCapitalRequest) {

        Instant startTime = Instant.now();  // 記錄開始時間

        // 請求內容準備
        //替換關鍵字
        //String inputPrompt = "what is the capital of " + getCapitalRequest.stateOrCountry() + "?";
        //使用Template檔
        //String inputPrompt = "what is the capital of " + getCapitalRequest.stateOrCountry() + "?";
        PromptTemplate promptTemplate = new PromptTemplate(getCaptitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",getCapitalRequest.stateOrCountry()));
        logger.info("OpenAI 請求開始 - Prompt: {}", prompt.getContents().toString());

        //PromptTemplate promptTemplate = new PromptTemplate(inputPrompt);
        //Prompt prompt = promptTemplate.create();

        // 呼叫 OpenAI
        ChatResponse response = chatModel.call(prompt);

        // 結果與時間記錄
        Instant endTime = Instant.now();  // 記錄結束時間
        long durationMs = Duration.between(startTime, endTime).toMillis();

        String outputContent = response.getResult().getOutput().getContent();
        logger.info("OpenAI 回覆完成 - Response: {}", outputContent);
        logger.info("OpenAI 請求耗時: {} ms", durationMs);

        return new Answer(outputContent);
    }

    @Override
    public Answer getAnswer(Question question) {

        Instant startTime = Instant.now();  // 記錄開始時間

        // 請求內容準備
        String inputPrompt = question.question();
        logger.info("OpenAI 請求開始 - Prompt: {}", inputPrompt);

        PromptTemplate promptTemplate = new PromptTemplate(inputPrompt);
        Prompt prompt = promptTemplate.create();

        // 呼叫 OpenAI
        ChatResponse response = chatModel.call(prompt);

        // 結果與時間記錄
        Instant endTime = Instant.now();  // 記錄結束時間
        long durationMs = Duration.between(startTime, endTime).toMillis();

        String outputContent = response.getResult().getOutput().getContent();
        logger.info("OpenAI 回覆完成 - Response: {}", outputContent);
        logger.info("OpenAI 請求耗時: {} ms", durationMs);

        return new Answer(outputContent);
        /*
        PromptTemplate promptTemplate = new PromptTemplate(question.question());
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        return new Answer(response.getResult().getOutput().getContent());

         */
    }

    @Override
    public String getAnswer(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(question);
        Prompt prompt = promptTemplate.create();

        ChatResponse response = chatModel.call(prompt);

        //return response.getResult().getOutput().getText();
        String content = response.getResult().getOutput().getContent();
        return content;

    }
}

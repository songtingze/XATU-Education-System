package com.xatu.chat.controller;

import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.xatu.chat.constants.ChatConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Arrays;

/**
 * ChatGPT接口
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    /**
     * 调用ChatGPT接口，以SSE长连接形式返回流式输出
     */
    @GetMapping("/sse")
    @CrossOrigin
    public SseEmitter sseEmitter(String prompt) {
        ChatGPTStream chatGPTStream = ChatGPTStream.builder()
                .timeout(600)
                .apiKey(ChatConstants.API_KEY)
                .apiHost(ChatConstants.CHAT_GPT_HOST)
                .build()
                .init();

        SseEmitter sseEmitter = new SseEmitter(-1L);

        SseStreamListener listener = new SseStreamListener(sseEmitter);
        Message message = Message.of(prompt);

        listener.setOnComplate(msg -> {
            // 回答完成，可以做一些事情
            logger.info("生成完成");
            logger.info(msg);
        });
        chatGPTStream.streamChatCompletion(Arrays.asList(message), listener);

        return sseEmitter;
    }
}

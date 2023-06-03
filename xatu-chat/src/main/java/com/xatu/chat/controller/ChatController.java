package com.xatu.chat.controller;

import com.plexpt.chatgpt.ChatGPTStream;
import com.plexpt.chatgpt.entity.chat.Message;
import com.plexpt.chatgpt.listener.SseStreamListener;
import com.xatu.chat.constants.ChatConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "解答Controller", tags = {"解答Controller"})
@RestController
@RequestMapping("/chat")
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    /**
     * 调用ChatGPT接口，以SSE长连接形式返回流式输出
     */
    @ApiOperation(value = "发送消息获取结果")
    @GetMapping("/sse")
    @CrossOrigin
    public SseEmitter sseEmitter(@ApiParam(value = "输入") String prompt) {
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

package org.bluebridge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    /**
     * 基础流式响应示例
     */
    @GetMapping(value = "/basic", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter basicStream() {
        // 30秒超时
        //ResponseBodyEmitter emitter = new ResponseBodyEmitter(30 * 1000L);
        // 不超时，直接返回
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        CompletableFuture.runAsync(() -> {
            try {
                int i = 0;
                while (true) {
                    emitter.send("Message " + ++i + "\n");
                    Thread.sleep(1000);
                }
                //emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    /**
     * SSE (Server-Sent Events) 示例
     */
    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter sseStream() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        CompletableFuture.runAsync(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    String data = "data: Current time: " + LocalDateTime.now() +
                                 ", count: " + i + "\n\n";
                    emitter.send(data);
                    Thread.sleep(100);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    /**
     * JSON流式响应示例
     */
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public ResponseBodyEmitter jsonStream() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        CompletableFuture.runAsync(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id", i);
                    data.put("timestamp", System.currentTimeMillis());
                    data.put("message", "JSON message " + i);

                    ObjectMapper mapper = new ObjectMapper();
                    emitter.send(mapper.writeValueAsString(data) + "\n");
                    Thread.sleep(1500);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    /**
     * 带错误处理的流式响应
     */
    @GetMapping("/error-handling")
    public ResponseBodyEmitter errorHandlingStream() {
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // 注册回调事件
        emitter.onCompletion(() -> System.out.println("Stream completed"));
        emitter.onTimeout(() -> {
            System.out.println("Stream timeout");
            emitter.complete();
        });
        emitter.onError(throwable -> 
            System.err.println("Stream error: " + throwable.getMessage()));

        CompletableFuture.runAsync(() -> {
            try {
                emitter.send("Starting stream...\n");
                Thread.sleep(1000);

                emitter.send("Processing step 1...\n");
                Thread.sleep(1000);

                emitter.send("Processing step 2...\n");
                Thread.sleep(1000);

                // 模拟随机错误
                if (Math.random() > 0.7) {
                    throw new RuntimeException("Random error occurred!");
                }

                emitter.send("Stream completed successfully!\n");
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}

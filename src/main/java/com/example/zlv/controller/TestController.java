package com.example.zlv.controller;

import com.alibaba.fastjson2.JSON;
import com.example.zlv.service.TestService;
import com.example.zlv.vo.*;
import com.example.zlv.vo.ResponseEntity;
import io.github.lnyocly.ai4j.platform.openai.chat.entity.ChatCompletion;
import io.github.lnyocly.ai4j.platform.openai.chat.entity.ChatCompletionResponse;
import io.github.lnyocly.ai4j.platform.openai.chat.entity.ChatMessage;
import io.github.lnyocly.ai4j.platform.openai.chat.entity.Choice;
import io.github.lnyocly.ai4j.service.IChatService;
import io.github.lnyocly.ai4j.service.PlatformType;
import io.github.lnyocly.ai4j.service.factor.AiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.OpenAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@Tag(name = "test参数")
@Slf4j
public class TestController {

    @Autowired
    private  TestService testService;
    @Autowired
    private  RestTemplate restTemplate;



    @Value("${ollama.generate.url}")
    private String generateUrl;

    @Value("${ollama.chat.url}")
    private String chatUrl;

    @Value("${search.url}")
    private String searchUrl;

    @Autowired
    private AiService aiService;
    @PostMapping("/generate")
    public String generate(@RequestBody GenerateRequestVo chatRequestVo) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeader.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zhl;q=0.9,en;q=0.8");
        HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(chatRequestVo),httpHeader);
        org.springframework.http.ResponseEntity<byte[]> result = restTemplate.postForEntity(generateUrl, httpEntity, byte[].class);
        if (result.getStatusCode() == HttpStatus.OK) {
            byte[] resultRes = result.getBody();
            String body = new String(resultRes,StandardCharsets.UTF_8);
            log.info(body);
            String[] split = body.split("\\n");
            return Arrays.stream(split).map(json -> JSON.parseObject(json, GenerateResponseVo.class)
                    .getResponse()).collect(Collectors.joining());
        }
        return "fail";
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequestVo chatRequestVo) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        httpHeader.add(HttpHeaders.ACCEPT_LANGUAGE, "zh-CN,zhl;q=0.9,en;q=0.8");
        log.info( JSON.toJSONString(chatRequestVo));

        HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(chatRequestVo),httpHeader);
        org.springframework.http.ResponseEntity<byte[]> result = restTemplate.postForEntity(chatUrl, httpEntity, byte[].class);
        if (result.getStatusCode() == HttpStatus.OK) {
            byte[] resultRes = result.getBody();
            String body = new String(resultRes,StandardCharsets.UTF_8);
            log.info(body);
            String[] split = body.split("\\n");
            return Arrays.stream(split).map(json -> JSON.parseObject(json, ChatResponseVo.class).getMessage().getContent()).collect(Collectors.joining());
        }
        return "fail";
    }





    @GetMapping("/stream")
    public SseEmitter stream() {
        System.out.printf("11");
        SseEmitter sseEmitter = new SseEmitter();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {

                try {
                    sseEmitter.send(SseEmitter.event().name("message").data("Data #" + i));
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
            sseEmitter.complete();
        }).start();
        return sseEmitter;
    }
    @GetMapping("/ok")
    @Operation(summary = "普通test请求")
    public String test(@RequestParam("q") String q)  {
        String[] split = q.split(",");
        var search = split[1];
        String url = searchUrl + "?format=json&q=" + search ;
        log.info("request:start ：{}", url);

        org.springframework.http.ResponseEntity<SearXNGResponseVo> forEntity = restTemplate.getForEntity(url, SearXNGResponseVo.class);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            log.info("成功");
            SearXNGResponseVo body = forEntity.getBody();
            List<SearXNGResponseVo.Result> results = body.getResults();
            String chatString = toChatString(results);
            log.info(chatString);
            return chatString;
        }
        log.info("失败");
        return "你好";
    }
    @GetMapping("/url")
    @Operation(summary = "普通test请求")
    public String getChatUrl(@RequestParam("q") String q)  {
        String[] split = q.split(",");
        var search = split[1];
        String url = searchUrl + "?format=json&q=" + search ;
        log.info("request:start ：{}", url);

        org.springframework.http.ResponseEntity<SearXNGResponseVo> forEntity = restTemplate.getForEntity(url, SearXNGResponseVo.class);
        if (forEntity.getStatusCode() == HttpStatus.OK) {
            log.info("成功");
            SearXNGResponseVo body = forEntity.getBody();
            List<SearXNGResponseVo.Result> results = body.getResults();
            String chatString = toChatUrlString(results);
            log.info(chatString);
            return chatString;
        }
        log.info("失败");
        return "你好";
    }

    private String toChatString(List<SearXNGResponseVo.Result> results  ) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < results.size(); i++) {
            SearXNGResponseVo.Result result = results.get(i);
            stringBuilder.append("第").append(i+1).append("消息;\n");
            stringBuilder.append("消息标题：").append(result.getTitle()).append(";\n");
            stringBuilder.append("内容：").append(result.getContent()).append(";\n");
            stringBuilder.append("消息来源").append(result.getUrl()).append(";\n");
            stringBuilder.append("搜索引擎").append(result.getEngine()).append(";\n");
        }
        return stringBuilder.toString();
    }

   private String toChatUrlString(List<SearXNGResponseVo.Result> results  ) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < results.size(); i++) {
            SearXNGResponseVo.Result result = results.get(i);
            stringBuilder.append(result.getUrl()).append(",");
        }
        return stringBuilder.toString();
    }




    @Operation(summary = "普通body请求")
    @PostMapping("/body")
    public ResponseEntity<FileResp> body(@RequestBody FileResp fileResp) {
        return ResponseEntity.ok(fileResp);
    }

    @Operation(summary = "普通body请求+Param+Header+Path")
    @Parameters({@Parameter(name = "id", description = "文件id", in = ParameterIn.PATH),
            @Parameter(name = "token", description = "请求token", required = true, in = ParameterIn.HEADER),
            @Parameter(name = "name", description = "文件名称", required = true, in = ParameterIn.QUERY)})
    @PostMapping("/bodyParamHeaderPath/{id}")
    public ResponseEntity<FileResp> bodyParamHeaderPath(@PathVariable("id") String id,
                                                        @RequestHeader("token") String token,
                                                        @RequestParam("name") String name,
                                                        @RequestBody FileResp fileResp) {
        String name1 = fileResp.getName()+ ",receiveName:" + name + ",token:" + token + ",pathID:" + id;
        fileResp.setName(name1);
        return ResponseEntity.ok(fileResp);
    }

    @SneakyThrows
    @GetMapping("/getChatMessage")
    public String getChatMessage(@RequestParam String question) {
        IChatService chatService = aiService.getChatService(PlatformType.OLLAMA);
        chatService = aiService.webSearchEnhance(chatService);
        ChatCompletion chatCompletion = ChatCompletion.builder().model("deepseek-r1:7b").message(ChatMessage.withUser(question)).build();
        log.info(JSON.toJSONString(chatCompletion));
        ChatCompletionResponse chatCompletionResponse = chatService.chatCompletion(chatCompletion);
        List<Choice> choices = chatCompletionResponse.getChoices();
        Optional<Choice> optionalChoice = choices.stream().findFirst();
        String content = "未搜索到";
        if (optionalChoice.isPresent()) {
            content = choices.stream().findFirst().get().getMessage().getContent();
            long totalTokens = chatCompletionResponse.getUsage().getTotalTokens();
            log.info("消耗的tokens :{}",totalTokens);
        }
        return content;
    }

}

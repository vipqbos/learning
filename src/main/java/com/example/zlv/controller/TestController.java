package com.example.zlv.controller;

import com.alibaba.fastjson2.JSON;
import com.example.zlv.service.TestService;
import com.example.zlv.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@Tag(name = "test参数")
public class TestController {

    @Autowired
    private  TestService testService;
    @Autowired
    private  RestTemplate restTemplate;



    @Value("${ollama.generate.url}")
    private String generateUrl;

    @Value("${ollama.chat.url}")
    private String chatUrl;

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
            System.out.println(body);
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
        System.out.print("re:");
        System.out.println( JSON.toJSONString(chatRequestVo));

        HttpEntity httpEntity = new HttpEntity(JSON.toJSONString(chatRequestVo),httpHeader);
        org.springframework.http.ResponseEntity<byte[]> result = restTemplate.postForEntity(chatUrl, httpEntity, byte[].class);
        if (result.getStatusCode() == HttpStatus.OK) {
            byte[] resultRes = result.getBody();
            String body = new String(resultRes,StandardCharsets.UTF_8);
            System.out.println(body);
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
                    System.out.println(i);
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
    public String test() throws InterruptedException {
        testService.tesStAsync();
        return "OK";
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


}

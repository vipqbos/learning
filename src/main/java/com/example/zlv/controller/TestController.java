package com.example.zlv.controller;

import com.example.zlv.service.TestService;
import com.example.zlv.vo.FileResp;
import com.example.zlv.vo.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Tag(name = "test参数")
public class TestController {


    @Autowired
    private TestService testService;

    @GetMapping("/ok")
    @Operation(summary = "普通test请求")
    public String test() throws InterruptedException {
        testService.testAsync();
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

package com.example.zlv.tools;

import com.example.zlv.service.GeneraTargetService;
import com.example.zlv.service.MoveTargetService;
import com.example.zlv.service.impl.ZBSerice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class ZobTools {
    private final GeneraTargetService generaTargetService;
    private final MoveTargetService moveTargetService;
    private final ZBSerice zbSerice;

    @Tool(description = "查询玩家的当前坐标")
    public String queryPlayerLocation(){
       return generaTargetService.playerLocation();
    }
    @Tool(description = "查询随机移动的点")
    public String queryRandomLocation(){
       return moveTargetService.randomLocation();
    }

    @Tool(description = "移动到坐标")
    public void moveToTarget(@ToolParam(description = "目标坐标")String targetLocation) {
        moveTargetService.moveToTarget(targetLocation);
    }
    @Tool(description = "不同的动作")
    public void doMo() {
        boolean b = zbSerice.doMo();
        log.info("移动是否成功：{}", b);
    }

    @Tool(description = "发出丧尸叫声")
    public void doSound() {
        String b = zbSerice.doSound();
        log.info("发出的声音名称：{}", b);
    }


}

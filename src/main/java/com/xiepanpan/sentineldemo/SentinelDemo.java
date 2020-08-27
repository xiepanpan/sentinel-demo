package com.xiepanpan.sentineldemo;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiepanpan
 * @Date: 2020/8/21
 * @Description:
 */
public class SentinelDemo {

    public static void main(String[] args) {
        initFlowRules();

        while (true) {
            Entry entry = null;
            try {
                entry = SphU.entry("doTest");
                System.out.println("hello world");
            } catch (BlockException e) {
                e.printStackTrace();
            } finally {
                if (entry!=null) {
                    entry.exit();
                }
            }
        }
    }

    private static void initFlowRules() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("doTest");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(10);
        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);
    }
}

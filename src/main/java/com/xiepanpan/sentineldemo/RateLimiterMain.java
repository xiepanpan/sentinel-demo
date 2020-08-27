package com.xiepanpan.sentineldemo;


import com.google.common.util.concurrent.RateLimiter;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author: xiepanpan
 * @Date: 2020/8/21
 * @Description: guava 令牌桶
 */
public class RateLimiterMain {

    RateLimiter rateLimiter = RateLimiter.create(10);

    public void doTest() {
        if (rateLimiter.tryAcquire()){
            //成功获得一个令牌
            System.out.println("允许通过进行访问");
        }else {
            System.out.println("被限流了");
        }

    }

    public static void main(String[] args) throws IOException {
        RateLimiterMain rateLimiterMain = new RateLimiterMain();
        //当计数器数值减为0时，所有受其影响而等待的线程将会被激活，这样保证模拟并发请求的真实性。
        CountDownLatch countDownLatch =new CountDownLatch(1);
        Random random = new Random();
        for (int i=0;i<20;i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                    Thread.sleep(random.nextInt(1000));
                    rateLimiterMain.doTest();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.countDown();
        System.in.read();
    }
}

package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HeQi
 * @Date:Create in 14:00 2018/8/10
 */
public class CreateMobileThread implements Runnable {



    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMobileThread.class);


    private static byte[] lock = new byte[0];


    public static void main(String[] args) throws Exception {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(new CreateMobileThread());
        }
        fixedThreadPool.shutdown();
        LOGGER.info("======================start==================");
        while (!fixedThreadPool.awaitTermination(2, TimeUnit.SECONDS)) {
        }
        LOGGER.info("=================end================");
        FilePrintUtil.getInstance().write("D:\\hq\\files/mobile.txt", Cache.outs, "utf-8");
    }


    @Override
    public void run() {
        Random random = new Random();
        while (true) {
            //synchronized (lock) {
                if (Cache.count >= 300000) {
                    break;
                }
                int l = RangeRandom.getInstance().getRangeRandom(300000000, 400000000 - 1);
                if (!Cache.mobiles.contains(l)) {
                    Cache.mobiles.add(l);
                } else {
                    continue;
                }
                String s = "1" + String.valueOf(l) + random.nextInt(10);
                Cache.outs.add(s);
                Cache.count++;
       //     }
        }

    }
}

package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Author: HeQi
 * @Date:Create in 14:00 2018/8/10
 */
public class CreateMobileThread implements Runnable {


    private static final Logger LOGGER = LoggerFactory.getLogger(CreateMobileThread.class);


    public static void main(String[] args) throws Exception {
        LOGGER.info("======================start==================");
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.submit(new CreateMobileThread()).get();
        }

        FilePrintUtil.getInstance().write("D:\\hq\\files/mobile.txt", Cache.mobiles, "utf-8");
        fixedThreadPool.shutdown();
        LOGGER.info("=================end================");
    }


    @Override
    public void run() {
        while (true) {
            if (Cache.mobiles.size() >= 10000000) {
                break;
            }
            int i = ThreadLocalRandom.current().nextInt(3);
            int l = 0;
            if (i == 0) {
                l = RangeRandom.getInstance().getRangeRandom(300000000, 400000000 - 1);
            } else if (i == 1) {
                l = RangeRandom.getInstance().getRangeRandom(500000000, 600000000 - 1);
            } else if (i == 2) {
                l = RangeRandom.getInstance().getRangeRandom(800000000, 900000000 - 1);
            }
            String s = "1" + String.valueOf(l) + RangeRandom.getInstance().getRangeRandom(0, 9);
            Cache.mobiles.add(s);
        }

    }
}

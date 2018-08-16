package com.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author: HeQi
 * @Date:Create in 15:04 2018/8/9
 */
public class RangeRandom {

   // private static ThreadLocalRandom random = ThreadLocalRandom.current();
   // private static Random random = new Random();


    public static class RangeRandomHolder {
        public static RangeRandom rangeRandom = new RangeRandom();
    }

    public static RangeRandom getInstance() {
        return RangeRandomHolder.rangeRandom;
    }

    public int getRangeRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(max) % (max - min + 1) + min;
    }
}

package com.util;

import java.util.Random;

/**
 * @Author: HeQi
 * @Date:Create in 15:04 2018/8/9
 */
public class RangeRandom {


    public static class RangeRandomHolder {
        public static RangeRandom rangeRandom = new RangeRandom();
    }

    public static RangeRandom getInstance() {
        return RangeRandomHolder.rangeRandom;
    }

    public int getRangeRandom(int min, int max) {
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }
}

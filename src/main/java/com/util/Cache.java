package com.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: HeQi
 * @Date:Create in 14:02 2018/8/10
 */
public class Cache {

   public static List<Integer> mobiles = new CopyOnWriteArrayList<>();
   public static List<String> outs = new CopyOnWriteArrayList<>();
   public static  AtomicInteger  num = new AtomicInteger(0);

    public static int count = 0;
}

package com.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: HeQi
 * @Date:Create in 14:02 2018/8/10
 */
public class Cache {

   public static Set<String> mobiles =  Collections.synchronizedSet(new HashSet<>(300000));

}

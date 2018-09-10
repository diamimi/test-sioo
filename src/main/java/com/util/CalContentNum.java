package com.util;

/**
 * @Author: HeQi
 * @Date:Create in 15:33 2018/9/7
 */
public class CalContentNum {

    public static int calcContentNum(String content) {
        // 重新计算条数
        int contentLength = content.length();
        int cCount = contentLength > 70 ? 67 : 70;
        if (contentLength % cCount != 0) {
            cCount = (contentLength / cCount) + 1;
        } else {
            cCount = contentLength / cCount;
        }
        return cCount;
    }

}

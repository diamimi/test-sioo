package com;

import org.junit.Test;

/**
 * @Author: HeQi
 * @Date:Create in 14:36 2018/9/7
 */
public class TempTest {

    @Test
    public void ss(){
        String content="【标榜造型】标榜造型周年店庆 剪发 染发 烫发 护发一律折上折 豪礼相送 多种礼品等你拿 快来参加吧 13889236739回T退订";
        String content1="【标榜造型】标榜造型周年店庆 剪发  染发  烫发  护发一律折上折 豪礼相送  多种礼品等你拿  快来参加吧   13889236739回T退订";
        System.out.println(content.length());
        System.out.println(content1.length());
    }
}

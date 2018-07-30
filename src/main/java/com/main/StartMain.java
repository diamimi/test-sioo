package com.main;

import com.pojo.SendingVo;
import com.service.SendHistory35Service;
import com.service.SendHistoryService;
import com.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);


    @Autowired
    private SendHistoryService sendHistoryService;

    @Autowired
    private SendHistory35Service sendHistory35Service;


    @Override
    public void run(ApplicationArguments var1) throws Exception {
        ssa();
    }

    /**
     * 更新前台没有签名的记录
     *
     * @throws Exception
     */
    public void ssa() throws Exception {
        //File file = new File("/home/sioowork/114/xiao.txt");
        File file = new File("D:\\hq/xiao.txt");
        InputStreamReader read = null;// 考虑到编码格式
        try {
            read = new InputStreamReader(new FileInputStream(file), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(read);
        String mobile = null;
        List<String> mobiles = new ArrayList<>();
        try {
            while ((mobile = bufferedReader.readLine()) != null) {
                if (mobile != null && mobile != "") {
                    mobiles.add(mobile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> mobiles.stream().parallel().forEach(i -> {
                    SendingVo vo = new SendingVo();
                    vo.setMobile(Long.parseLong(i));
                    List<SendingVo> list = new ArrayList<>();
                    List<SendingVo> historyAndRptcode21 = sendHistoryService.findHistoryAndRptcode(vo);
                    List<SendingVo> historyAndRptcode35 = sendHistory35Service.findHistoryAndRptcode(vo);
                    list.addAll(historyAndRptcode21);
                    list.addAll(historyAndRptcode35);
                    Collections.sort(list, Comparator.comparing(SendingVo::getSenddate));
                    PrintWriter out = null;
                    try {
                        out = new PrintWriter("D:\\hq/mobile/wz/" + i + ".txt");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    for (SendingVo sendingVo : list) {
                        String s = sendingVo.getMobile() + "|" + sendingVo.getContent() + "|" + "-1" + "|" + DateUtils.getDay(sendingVo.getSenddate()) + "\r";
                        out.write(s);
                    }
                    out.flush();
                    out.close();
                }
        )).get();
        LOGGER.info("===============END====================");
    }


}

package com.main;

import com.pojo.SendingVo;
import com.service.SendHistory35Service;
import com.service.SendHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
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
        //  ssa();
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
        String mobile;
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
        PrintWriter out =new PrintWriter("D:\\hq/mobile/没记录.txt");
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> mobiles.stream().parallel().forEach(i -> {
                    SendingVo vo = new SendingVo();
                    vo.setMobile(Long.parseLong(i));
                    int count21 = sendHistoryService.countHistoryAndRptcode(vo);
                    int count35 = sendHistory35Service.countHistoryAndRptcode(vo);
                    if (count21 == 0 && count35 == 0) {
                        out.write(i + "\r\n");
                    }

                }
        )).get();
        out.flush();
        out.close();
        LOGGER.info("===============END====================");
    }
}

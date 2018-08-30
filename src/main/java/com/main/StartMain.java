package com.main;

import com.pojo.SendingVo;
import com.service.BlackMobileService;
import com.service.SendHistoryService114;
import com.util.FilePrintUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);

    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Autowired
    private BlackMobileService blackMobileService;


    @Override
    public void run(ApplicationArguments var1) throws Exception {
      /*  int maxid = 7819495;
        int minid = 11522;
        List<Integer> beginIndex = new ArrayList<>();
        while (minid <= maxid) {
            beginIndex.add(minid);
            minid = minid + 1000;
        }
        System.out.println("=========start=========");
        ForkJoinPool myPool = new ForkJoinPool(8);
       // OutputStream os = new FileOutputStream("d:/hq/files/mobile_35.txt");
        OutputStream os = new FileOutputStream("/home/sioowork/114/userblack.txt");
        final PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "utf-8"));
        myPool.submit(() -> beginIndex.stream().parallel().forEach(i -> {
                    List<String> mobiles = blackMobileService.getBlackMobile(i, i + 1000);
                    if(mobiles!=null&&mobiles.size()>0){
                        for (String t : mobiles) {
                            out.write(t.toString() + "\r\n");
                        }
                    }
                }
        )).get();
        out.flush();
        out.close();
        System.out.println("=========end=========");*/
    }

    public void exportBatch() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,发送时间,状态";
        contents.add(title);
        int uid = 90370;
        for (int i = 20180512; i <= 20180512; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String c = StringUtils.replace(v.getContent(), ",", ".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate() + "," + v.getRptcode();
                contents.add(content);
            });
        }

        FilePrintUtil.getInstance().write("d:/hq/files/" + uid + ".csv", contents, "GBK");


    }

    public void bigBlackMobile() throws Exception {

    }

}

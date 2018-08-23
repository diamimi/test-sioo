package com.main;

import com.pojo.SendingVo;
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


    @Override
    public void run(ApplicationArguments var1) throws Exception {
       // LOGGER.info("===============start===================");
       // exportBatch();
        //LOGGER.info("===============end===================");
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

}

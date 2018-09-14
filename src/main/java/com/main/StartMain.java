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
      /*  LOGGER.info("start");
        exportBatchHistory();
        LOGGER.info("end");*/

    }

    public void exportBatchHistory() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态,回执时间";
        contents.add(title);
        int uid = 50660;
        SendingVo vo = new SendingVo();
        vo.setUid(uid);
        // vo.setLevel(0);
        addBatchContent(20180804, 20180805, contents, vo);
        FilePrintUtil.getInstance().write("/home/sioowork/114/" + uid + "_.csv", contents, "gbk");
    }


    public void addBatchContent(int start, int end, List<String> contents, SendingVo vo) {
        for (int i = start; i <= end; i++) {
            String tableName = String.valueOf(i).substring(2);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String c = StringUtils.replace(v.getContent(), ",", ".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode()+","+v.getRpttime();
                contents.add(content);

            });
        }
    }

}

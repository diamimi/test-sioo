package com;

import com.pojo.SendingVo;
import com.service.SendHistoryService114;
import com.util.FilePrintUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 14:05 2018/9/6
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class PiCi114 {


    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Test
    public void piciRecord() {
        List<String> contents=new ArrayList<>();
        String title="内容,号码数,日期";
        contents.add(title);
        int uid=506551;
        SendingVo vo = new SendingVo();
        vo.setUid(uid);
        pici(20180801,20180831,contents,vo);
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + "_8月.csv", contents, "gbk");
    }

    public void pici(int start, int end, List<String> contents, SendingVo vo) {
        for (int i = start; i <= end; i++) {
            String time=String.valueOf(i);
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.getPiCi(vo);
            list.stream().forEach(v -> {
                String c = v.getContent().replace(",", ".");
                String content = c + "," + v.getContentNum()+","+ time;
                contents.add(content);
            });
        }
    }
}

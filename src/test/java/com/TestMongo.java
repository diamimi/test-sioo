package com;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.pojo.SendingVo;
import com.service.SendHistoryService114;
import com.util.MongoManager;
import org.bson.BasicBSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HeQi
 * @Date:Create in 17:33 2018/7/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class TestMongo {

    @Autowired
    private SendHistoryService114 sendHistoryService114;


    @Test
    public void lsls() throws Exception {
        //b mobile c rptcode d id g senddate
        BasicDBObject where = new BasicDBObject();
        where.put("c", "XREJECT");
        where.put("g", new BasicBSONObject("$gt", 20180730000000l));
        Map<Long, SendingVo> map = new HashMap<>();
        List<DBObject> obj = MongoManager.getInstance().find("sms_report_push", where);
        obj.stream().parallel().forEach((o) -> {
            String id = o.get("d").toString();
            String mobile = o.get("b").toString();
            if (!map.containsKey(id)) {
                BasicDBObject w = new BasicDBObject();
                w.put("a", Long.valueOf(id));
                w.put("f", Long.valueOf(mobile));
                DBObject sms_send_history_unknown = null;
                try {
                    sms_send_history_unknown = MongoManager.getInstance().findOne("sms_send_history_unknown", w);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(sms_send_history_unknown!=null){
                    String date = sms_send_history_unknown.get("d").toString();
                    SendingVo v = new SendingVo();
                    v.setMobile(Long.parseLong(mobile));
                    v.setSenddate(Long.parseLong(date));
                    map.put(Long.valueOf(id), v);
                }
            }
        });

        for (Map.Entry<Long, SendingVo> entry : map.entrySet()) {
            SendingVo value = entry.getValue();
            SendingVo vo = new SendingVo();
            vo.setTableName("0731");
            vo.setMobile(value.getMobile());
            vo.setRptcode("DELIVRD");
            vo.setUid(40058);
            List<SendingVo> history = sendHistoryService114.findHistory(vo);
            if (history.size() > 0) {
                for (SendingVo sendingVo : history) {
                    if ((sendingVo.getSenddate() - value.getSenddate()) < 10 && sendingVo.getSenddate() > value.getSenddate()) {
                        DBObject w = new BasicDBObject();
                        w.put("a", entry.getKey());
                        w.put("f", value.getMobile());
                        DBObject set = new BasicDBObject();
                        set.put("q", sendingVo.getContentNum());
                        set.put("r", 0);
                        MongoManager.getInstance().update1("sms_send_history_unknown", set, w);
                        DBObject w1 = new BasicDBObject();
                        w1.put("d", entry.getKey());
                        w1.put("b", value.getMobile());
                        DBObject set1 = new BasicDBObject();
                        set1.put("c", "DELIVRD");
                        MongoManager.getInstance().update1("sms_report_push", set1, w1);
                        MongoManager.getInstance().update1("sms_report", set1, w1);
                    }
                }
            }
        }

    }


}

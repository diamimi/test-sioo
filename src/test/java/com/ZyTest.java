package com;

import com.mongodb.BasicDBObject;
import com.util.SequoiaDBUtil;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.Test;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 18:21 2018/8/24
 */
public class ZyTest {

    @Test
    public void findUserDayCount() {


        BSONObject where = new BasicBSONObject();
        where.put("hisid", 90602821);
        BSONObject set = new BasicBSONObject();
        set.put("arrfail", 0);
        SequoiaDBUtil.getInstance().update("sms_send_history_detail", where, set, null);
    }


    @Test
    public void ge() {
        BSONObject where = new BasicBSONObject();
        where.put("arrsucc",  new BasicBSONObject("$gt", 0));
        where.put("uid",  90563);
        where.put("arrfail",  new BasicBSONObject("$gt", 0));
        where.put("senddate",  new BasicBSONObject("$gt", 20181025000000L));
       // List<BSONObject> list = SequoiaDBUtil.getInstance().find("sms_send_history_detail", where);
      //  System.out.println(list.size());
        BSONObject set = new BasicBSONObject();
        set.put("arrfail", 0);
        SequoiaDBUtil.getInstance().update("sms_send_history_detail", where, set, null);
        BSONObject basicBSONObject = new BasicBSONObject("salary", new BasicDBObject("$gt", 10000));
    }

    /**
     * 查询日期
     */
    @Test
    public void sss() {
        BSONObject where = new BasicBSONObject();
        BSONObject dayu = new BasicBSONObject("senddate", new BasicDBObject("$gt", 20181024000000L));
        BSONObject xiaoyu = new BasicBSONObject("senddate", new BasicDBObject("$lt", 20181025000000L));
     //   where.put("arrfail",  new BasicBSONObject("$gt", 0));
        //where.put("arrsucc",  new BasicBSONObject("$gt", 0));
        where.putAll( dayu);
        where.putAll( xiaoyu);
        where.put("uid",51134);
        List<BSONObject> list = SequoiaDBUtil.getInstance().find("sms_send_history_detail", where);
        for (BSONObject bsonObject : list) {
            System.out.println(bsonObject.toString());
        }
    }

    @Test
    public void s11ss(){
        BSONObject where = new BasicBSONObject();
        where.put("senddate",20181020);
        where.put("uid",51101);
        BSONObject set = new BasicBSONObject();
        set.put("succ", 60514);
        SequoiaDBUtil.getInstance().update("sms_user_day_count", where, set, null);

    }
}

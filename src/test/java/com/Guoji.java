package com;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoManager;
import org.junit.Test;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 16:59 2018/9/17
 */
public class Guoji {

    @Test
    public void mogo() throws Exception {
        DBObject dbObject=new BasicDBObject();
        dbObject.put("e",37);
        List<DBObject> sms_send_history_unknown = MongoManager.getInstance().find("sms_send_history_unknown", dbObject);
        for (DBObject object : sms_send_history_unknown) {
            System.out.println(object.get("f")+","+object.get("d")+","+object.get("h"));
            //.out.println(object.get("f"));
            //System.out.println(object.get("d"));
           // System.out.println(object.get("h"));
        }
    }
}

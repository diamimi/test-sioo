package com;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.util.MongoDBUtil;
import org.bson.BasicBSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 17:33 2018/7/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class TestMongo {

    @Autowired
    private

    @Test
    public void lsls() throws Exception{
        //b mobile c rptcode g senddate
        BasicDBObject where = new BasicDBObject();
        where.put("c", "XREJECT");
        where.put("g",new BasicBSONObject("$gt",20180730000000l));
        List<DBObject> obj = MongoDBUtil.getInstance().find("sms_report_push", where);
        System.out.println(obj.size());


    }

}

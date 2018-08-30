package com;

import com.util.SequoiaDBUtil;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.Test;

/**
 * @Author: HeQi
 * @Date:Create in 18:21 2018/8/24
 */
public class ZyTest {

    @Test
    public void findUserDayCount() {
        BSONObject where = new BasicBSONObject();
        where.put("mdstr", "6d91fcd65d25bcac");
        BSONObject set = new BasicBSONObject();
        set.put("content", "【普惠金融】恭喜您通过初审成为普惠金融体验用户，额度86000元，请在24小时内点击链接 http://t.cn/RkTJFTE 回T退订");
        SequoiaDBUtil.getInstance().update("sms_sending_release", where, set, null);
    }
}

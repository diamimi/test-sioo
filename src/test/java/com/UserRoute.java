package com;

import com.util.FilePrintUtil;
import com.util.FileRead;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 12:58 2018/9/21
 */
public class UserRoute {

    @Test
    public  void route(){
        List<String> read = FileRead.getInstance().read("D:\\hq\\files/1.txt", "utf-8");
        List<String> outs=new ArrayList<>();
        for (String uid : read) {
            String sql="INSERT INTO `smshy`.`sms_user_route` ( `routetype`, `content`, `routechannel`, `aid`, `uid`, `remark`, " +
                    "`addtime`, `mtype`, `contentType`, `province`, `city`) VALUES ('1', '验证码', '62', '26', '"+uid+"', NULL, " +
                    "'2018-09-21 12:52:57', '1', '0', '0', '0');";
            outs.add(sql);
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/sql.txt",outs,"utf-8");
    }
}

package com;

import com.pojo.SendingVo;
import com.service.SendHistoryService114;
import com.service.SmsUserSendingService;
import com.util.FileRead;
import com.util.SequoiaDBUtil;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 12:00 2018/8/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsUserSendgdingTest {

    @Autowired
    private SmsUserSendingService smsUserSendingService;

    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Test
    public void update() {
        SendingVo vo = new SendingVo();
        vo.setUid(90418);
        List<SendingVo> voList = smsUserSendingService.findSmsUserSending(vo);
        voList.stream().parallel().forEach(v->{
            String content=v.getContent();
            if(content.contains("【爱生活】")){
                content= StringUtils.replace(content,"【爱生活】","【商虎】");
                v.setContent(content);
                v.setExpid("11"+v.getUid());
                smsUserSendingService.updateSmsUserSending(v);
            }
        });
    }

    @Test
    public void insert(){
        SendingVo vo=new SendingVo();
        vo.setUid(40067);
        vo.setRptcode("ID:1241");
        vo.setStarttime(20180802000000l);
        vo.setEndtime(20180803000000l);
        List<SendingVo> sendingVoList=smsUserSendingService.findList(vo);
        sendingVoList.parallelStream().forEach((v->{
            smsUserSendingService.insertSmsUserSending(v);
        }));
    }

    @Test
    public void sss(){
        List<String> read = FileRead.getInstance().read("D:\\hq\\files/mobile.txt", "utf-8");
        read.stream().forEach(l->{
            BSONObject where = new BasicBSONObject();
            where.put("mobile", Long.parseLong(l));
            where.put("uid",50577);
            List<BSONObject> list = SequoiaDBUtil.getInstance().find("sms_send_history_detail", where);
            BSONObject bsonObject = list.get(0);
            SendingVo vo=new SendingVo();
            vo.setHisid(Long.valueOf(bsonObject.get("hisid").toString()));
            vo.setMobile(Long.valueOf(bsonObject.get("mobile").toString()));
            vo.setContent("【广润物业】【关于A区公寓供电抄表到户的通知】\n" +
                    "尊敬的客天下业主：\n" +
                    "为进一步规范水晶山A区公寓供电设施管理，尽快解决历史遗留的“A区公寓商业用电”的问题，经物业公司与供电局长期的沟通协调，现将水晶山A区公寓改为供电局直接抄表到户，解决了一直困扰物业公司代收代缴电费过程中产生的电表（线）损耗，总、分表差额、业主拒交电费等问题。业主可自行到高埔供电局办理抄表到户手续，或委托物业公司办理。\n" +
                    "一、自行办理需要以下资料：\n" +
                    "1、业主身份证复印件；\n" +
                    "2、购房合同或房产证复印件；\n" +
                    "3、代扣费用银行卡复印件（要求河源市）\n" +
                    "二、委托物业公司办理需要以下资料：\n" +
                    "1、业主身份证复印件；\n" +
                    "2、购房合同或房产证复印件；\n" +
                    "3、代扣费用银行卡复印件（要求河源市）；\n" +
                    "4、到物业办公室填写委托银行代扣电费协议，需交代办费；\n" +
                    "注：\n" +
                    "1、请各位业主尽快办理谢谢。\n" +
                    "2、办理抄表到户后，每月电费从银行卡中扣除，办理手续前需缴清前期所欠的电费。\n" +
                    "3、已托管、已办理的业主请忽略此信息，如有疑问可致电廖雪光：18933739619。\n" +
                    " \n" +
                    "河源市广润物业管理有限公司\n" +
                    "2018年8月29日\n" +
                    "回T退订");
            vo.setSenddate(Long.valueOf(bsonObject.get("senddate").toString()));
            vo.setExpid(bsonObject.get("senddate").toString());
            vo.setMtype(Integer.valueOf(bsonObject.get("mtype").toString()));
            vo.setUid(50577);
            vo.setChannel(Integer.valueOf(bsonObject.get("channel").toString()));
            vo.setContentNum(Integer.valueOf(bsonObject.get("contentNum").toString()));
            vo.setPid(Integer.valueOf(bsonObject.get("pid").toString()));
            vo.setLocation(bsonObject.get("location").toString());
            smsUserSendingService.insertSmsUserSending(vo);

        });

    }
}

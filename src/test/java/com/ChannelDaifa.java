package com;

import com.alibaba.fastjson.JSONObject;
import com.sioo.client.cmpp.vo.DeliverVo;
import com.sioo.client.cmpp.vo.SubmitRspVo;
import com.util.FileRead;
import com.util.RabbitMQProducerUtil;
import com.util.SequoiaDBUtil;
import org.apache.commons.lang.StringUtils;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

/**
 * 处理通道提交代发状态
 *
 * @Author: HeQi
 * @Date:Create in 10:21 2018/8/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ChannelDaifa {

    @Autowired
    private RabbitMQProducerUtil rabbitMQProducerUtil;
//{"channel":29,"channelDays":180803,"expid":"902661","hisId":683977218,"id":0,"mobile":"15593021059","msgId":8030901228428893,"mtstat":"","pid":1114070015,"standard":1,"state":0,"uid":902661,"userDays":180803}

  /*  @Test
    public void sss() throws Exception{
        DeliverVo deliver = new DeliverVo(29, Long.parseLong("8030901228428893"), "15593021059","", "2018-08-03 09:01:25");
        deliver.setChannelDays(180803);
        deliver.setUserDays(180803);
        deliver.setUid(Integer.parseInt("902661"));
        deliver.setPid(Integer.parseInt("1114070015"));
        deliver.setHisId(Integer.parseInt("683977218"));
        deliver.setRpt_code("DELIVRD");

        SubmitRspVo submitRspVo = new SubmitRspVo(29,683977218, 15593021059l + "", Long.parseLong("8030901228428893"),
                0, 180803, 180803, 902661, 1114070015, "902661", 0, "");
        rabbitMQProducerUtil.send("GATEWAY_QUEUE_RESP", submitRspVo);
       // rabbitMQProducerUtil.send("GATEWAY_QUEUE_DELIVER", deliver);
    }*/

    @Test
    public void daifa() throws Exception {
        Map<Long, DeliverVo> daifa = new HashMap<>();
        List<String> daifaList = FileRead.getInstance().read("D:\\hq/1111.txt","");
        for (String s : daifaList) {
            s = "{" + StringUtils.substringAfter(s, "{");
            JSONObject parse = JSONObject.parseObject(s);
            String msgId = parse.get("msgId").toString();
            String mobile = parse.get("mobile").toString();
            String uid = parse.get("uid").toString();
            String pid = parse.get("pid").toString();
            String hisId = parse.get("hisId").toString();
            String expid = parse.get("expid").toString();
            Calendar c = Calendar.getInstance();
            String receivetime = c.get(Calendar.YEAR) + "-" + ((c.get(Calendar.MONTH) + 1) < 10 ? ("0" + (c.get(Calendar.MONTH) + 1)) : (c.get(Calendar.MONTH) + 1))
                    + "-" + (c.get(Calendar.DAY_OF_MONTH) < 10 ? ("0" + c.get(Calendar.DAY_OF_MONTH)) : c.get(Calendar.DAY_OF_MONTH)) + " " +
                    (c.get(Calendar.HOUR_OF_DAY) < 10 ? ("0" + c.get(Calendar.HOUR_OF_DAY)) : c.get(Calendar.HOUR_OF_DAY)) + ":" + (c.get(Calendar.MINUTE) < 10 ?
                    ("0" + c.get(Calendar.MINUTE)) : c.get(Calendar.MINUTE)) + ":" + (c.get(Calendar.SECOND) < 10 ? ("0" + c.get(Calendar.SECOND)) : c.get(Calendar.SECOND));
            DeliverVo deliver = new DeliverVo(29, Long.parseLong(msgId), mobile, "", receivetime);
            deliver.setChannelDays(180803);
            deliver.setUserDays(180803);
            deliver.setUid(Integer.parseInt(uid));
            deliver.setPid(Integer.parseInt(pid));
            deliver.setHisId(Integer.parseInt(hisId));
            deliver.setExpid(expid);
            daifa.put(deliver.getMsgId(), deliver);
        }
        Map<Long, String> resMap = new HashMap<>();
        List<String> resp = FileRead.getInstance().read("D:\\hq/222.txt","");
        for (String content : resp) {
            content = StringUtils.substringAfter(content, "[rpt ok]");
            String[] split = content.split(",");
            String msgId = split[1];
            String rptcode = split[3];
            String rpttime = split[4];
            resMap.put(Long.valueOf(msgId), rptcode + "_" + rpttime);
        }
        for (Map.Entry<Long, DeliverVo> entry : daifa.entrySet()) {
            String s = resMap.get(entry.getKey());
            String rptcode = StringUtils.substringBefore(s, "_");
            String rpttime = StringUtils.substringAfter(s, "_");
            DeliverVo value = entry.getValue();
            value.setRpt_code(rptcode);
            value.setRpt_time(rpttime);
            SubmitRspVo submitRspVo = new SubmitRspVo(29, value.getHisId(), value.getMobile() + "", value.getMsgId(),
                    0, 180803, 180803, value.getUid(), value.getPid(), value.getExpid(), 0, "");
            rabbitMQProducerUtil.send("GATEWAY_QUEUE_RESP", submitRspVo);
            rabbitMQProducerUtil.send("GATEWAY_QUEUE_DELIVER", value);
        }

    }

    @Test
    public void ss1s() throws Exception {
        boolean isRight = 1 > 2;
        try {
            assert isRight : "程序错误";//在:后写自定义的异常
            System.out.println("程序正常");
        } catch (AssertionError e) {
            // TODO: handle exception
            System.out.println(e.getMessage());//返回值：程序错误。(没错，这就是我们自定义的异常)


        }
    }

    @Test
    public void sss() throws Exception {
        BasicBSONObject where = new BasicBSONObject();
        where.put("channel", 53);
        where.put("senddate", new BasicBSONObject("$gt", 20180803000000l));
        where.put("senddate", new BasicBSONObject("$lt", 20180803070000l));
        where.put("arrsucc", 0);
        List<BSONObject> list = SequoiaDBUtil.getInstance().find("sms_send_history_detail", where);
        List<String> rptList = FileRead.getInstance().read("D:\\hq\\files/gd.txt","");
        Map<String, List<DeliverVo>> rptcodeMap = new HashMap<>();
        rptList.stream().forEach(r -> {
            r = "{" + StringUtils.substringAfter(r, " - {");
            JSONObject parse = JSONObject.parseObject(r);
            String msgId = parse.get("msgId").toString();
            String rptcode = parse.get("rpt_code").toString();
            String rpttime = parse.get("rpt_time").toString();
            String hisId = parse.get("hisId").toString();
            String mobile = parse.get("mobile").toString();
            String uid = parse.get("uid").toString();
            String pid = parse.get("pid").toString();
            if (!rptcodeMap.containsKey(hisId)) {
                List<DeliverVo> msgids = new ArrayList<>();
                DeliverVo deliverVo = new DeliverVo();
                deliverVo.setMsgId(Long.valueOf(msgId));
                deliverVo.setRpt_code(rptcode);
                deliverVo.setRpt_time(rpttime);
                deliverVo.setMobile(mobile);
                deliverVo.setUid(Integer.parseInt(uid));
                deliverVo.setPid(Integer.parseInt(pid));
                deliverVo.setHisId(Integer.parseInt(hisId));
                msgids.add(deliverVo);
                rptcodeMap.put(hisId, msgids);
            } else {
                List<DeliverVo> strings = rptcodeMap.get(hisId);
                DeliverVo deliverVo = new DeliverVo();
                deliverVo.setMsgId(Long.valueOf(msgId));
                deliverVo.setRpt_code(rptcode);
                deliverVo.setRpt_time(rpttime);
                deliverVo.setMobile(mobile);
                deliverVo.setUid(Integer.parseInt(uid));
                deliverVo.setPid(Integer.parseInt(pid));
                deliverVo.setHisId(Integer.parseInt(hisId));
                strings.add(deliverVo);
                rptcodeMap.put(hisId, strings);
            }
        });
        list.stream().forEach(d -> {
            String id = d.get("hisid").toString();
            List<DeliverVo> put = rptcodeMap.get(id);
            if (put != null && put.size() > 0) {
                for (DeliverVo deliverVo : put) {
                    try {
                        deliverVo.setChannelDays(180803);
                        deliverVo.setUserDays(180803);
                        deliverVo.setChannel(53);
                        rabbitMQProducerUtil.send("GATEWAY_QUEUE_DELIVER", deliverVo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        });
    }
}

package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pojo.SendingBigVo;
import com.pojo.SendingVo;
import com.service.RptService;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:18 2018/7/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryTo114 {

    private static Logger log = LoggerFactory.getLogger(HistoryTo114.class);

    @Autowired
    private RptService rptService;

    @Test
    public void ss() {
        List<SendingVo> list = rptService.findHistory();
        System.out.println(list.size());
    }

    @Test
    public void s1s() {
        int size = 1000;
        int maxid = 583851335;
        int minid = 581373762;
        while (true) {
            if (minid <= maxid) {
                List<SendingVo> lists = rptService.getHistory(minid, minid + size);
                if (lists != null && lists.size() > 0) {
                    for (SendingVo vo : lists) {
                        vo.setId(vo.getHisid());
                        rptService.saveHistory(vo);
                        rptService.saveRpt(vo);
                    }
                }
                minid = minid + size;
            } else {
                log.info("执行完毕");
                break;
            }
        }
    }


    @Test
    public void fileToMongo() throws Exception {
        List<SendingVo> list = new ArrayList<>();
        List<SendingVo> rptList = new ArrayList<>();
        File file = new File("/home/sioowork/middle-service-logs/data.log.2018-07-15");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String sms = null;
        while ((sms = bufferedReader.readLine()) != null) {
           sms=sms.trim();
           if(sms!=null&&!sms.equals("")){
               String content = sms.substring(21);
               SendingBigVo vo = JSON.parseObject(content, new TypeReference<SendingBigVo>() {
               });
               if (vo.getMobile().contains("-")) {
                   String[] strings = vo.getMobile().split(",");
                   for (String t : strings) {
                       SendingVo v = new SendingVo();
                       String phone = StringUtils.substringAfter(t, "-");
                       String hisid = StringUtils.substringBefore(t, "-");
                       v.setMobile(Long.valueOf(phone));
                       v.setContent(vo.getContent());
                       v.setUid(vo.getUid());
                       v.setPid(vo.getPid());
                       v.setId(Integer.valueOf(hisid));
                       v.setSenddate(vo.getSenddate());
                       v.setChannel(vo.getChannel());
                       if(vo.getExpid()==null||vo.getExpid().equals("0")){
                           v.setExpid(vo.getUid()+"");
                       }else {
                           v.setExpid(vo.getExpid());
                       }
                       v.setSource(vo.getSource());
                       v.setMtype(vo.getMtype());
                       SendingVo v1 = rptService.getRptcodeByRpt(v.getId());
                       SendingVo v2=  rptService.getRptContent(vo.getPid());
                       v.setRptcode(v1.getRptcode());
                       v.setRpttime(v1.getRpttime());
                       v.setContent(v2.getContent());
                       v.setContentNum(v2.getContentNum());
                       v.setSucc(v2.getContentNum());
                       v.setFail(0);
                       if(StringUtils.equals(v1.getRptcode(),"DELIVRD")){
                           v.setArrive_fail(0);
                           v.setArrive_succ(v2.getContentNum());
                       }else {
                           v.setArrive_fail(v2.getContentNum());
                           v.setArrive_succ(0);
                       }
                       list.add(v);
                       for (int i = 0; i < v.getContentNum(); i++) {
                           rptList.add(v);
                       }

                   }
               } else {
                   SendingVo v = new SendingVo();
                   String phone = vo.getMobile();
                   v.setMobile(Long.valueOf(phone));
                   v.setContent(vo.getContent());
                   v.setUid(vo.getUid());
                   v.setPid(vo.getPid());
                   v.setId(vo.getId());
                   v.setSenddate(vo.getSenddate());
                   if(vo.getExpid()==null||vo.getExpid().equals("0")){
                       v.setExpid(vo.getUid()+"");
                   }else {
                       v.setExpid(vo.getExpid());
                   }
                   v.setSource(vo.getSource());
                   v.setMtype(vo.getMtype());
                   SendingVo v1 = rptService.getRptcodeByHistory(v.getId());
                   v.setRptcode(v1.getRptcode());
                   v.setRpttime(v1.getRpttime());
                   v.setContentNum(v1.getContentNum());
                   v.setContent(v1.getContent());
                   v.setSucc(v1.getContentNum());
                   v.setFail(0);
                   v.setChannel(v1.getChannel());
                   if(StringUtils.equals(v1.getRptcode(),"DELIVRD")){
                       v.setArrive_fail(0);
                       v.setArrive_succ(v1.getContentNum());
                   }else {
                       v.setArrive_fail(v1.getContentNum());
                       v.setArrive_succ(0);
                   }
                   list.add(v);
                   for (int i = 0; i < v1.getContentNum(); i++) {
                       rptList.add(v);
                   }
               }
               if (list.size() >= 200) {
                   rptService.batchInsertHistory(list);
                   rptService.batchInsertRpt(rptList);
                   list.clear();
                   rptList.clear();
               }
           }
        }
        rptService.batchInsertHistory(list);
        rptService.batchInsertRpt(rptList);
    }
}

package com;

import com.pojo.SendingVo;
import com.service.RptService;
import com.service.SendHistoryService;
import com.service.SendHistoryService114;
import com.service.Store21Service;
import com.util.DayUtil;
import com.util.FilePrintUtil;
import com.util.FileRead;
import com.util.StoreUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:18 2018/7/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HistoryTo114New {

    private static Logger log = LoggerFactory.getLogger(HistoryTo114New.class);

    @Autowired
    private RptService rptService;

    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Autowired
    private StoreUtil storeUtil;

    @Autowired
    private Store21Service store21Service;

    @Autowired
    private SendHistoryService sendHistoryService;


    /**
     * 批量用户成功统计
     *
     * @throws Exception
     */
    @Test
    public void exportBatchHistory() throws Exception {
        List<String> days = DayUtil.getDayList(20180901, 20180930);
        int uid = 40058;
        int num = 0;
        for (String day : days) {
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(day.substring(2));
            int succ = sendHistoryService114.getSucc(vo);
            num += succ;
        }
        System.out.println(num);
    }

    /**
     * SINGLE用户
     */
    @Test
    public void exportSingle() throws Exception{
        List<String> days = DayUtil.getDayList(20180801, 20180930);
        int uid = 51089;
        List<String> outs=new ArrayList<>();
        for (String day : days) {
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(day.substring(2));
            List<SendingVo> singleHistory = sendHistoryService114.findSingleHistory(vo);
            if(singleHistory!=null&&singleHistory.size()>0){
                for (SendingVo sendingVo : singleHistory) {
                    if(sendingVo.getContent().contains("百威")){
                        String c=sendingVo.getMobile()+","+sendingVo.getContent()+","+sendingVo.getSenddate1();
                        outs.add(c);
                    }
                }
            }
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/"+uid+".csv",outs,"GBK");
    }


    @Test
    public void exportSingle11() throws Exception{
        List<String> days = DayUtil.getDayList(20180901, 20180930);
        int uid = 50614;
        int total=0;
        List<String> outs=new ArrayList<>();
        for (String day : days) {
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(day.substring(2));
            Integer total1 = sendHistoryService114.getTotal(vo);
            total+=total1;
        }
        System.out.println(total);
    }


    /**
     * 按天统计每天发送总量,成功量,失败,未知
     */
    @Test
    public void buguniao(){
        List<String> days = DayUtil.getDayList(20180901, 20180930);
        int uid = 40058;
        List<String> outs=new ArrayList<>();
        String title="日期,总数,成功,失败,未知";
        outs.add(title);
        for (String day : days) {
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(day.substring(2));
            vo.setExcludeContent("【广汇汽车】");
            Integer total = sendHistoryService114.getTotal(vo);
            Integer succ = sendHistoryService114.getSucc(vo);
            Integer fail = sendHistoryService114.getFail(vo);
            int wz=total-succ-fail;
            String out=day+","+total+","+succ+","+fail+","+wz;
            outs.add(out);
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/"+uid+".csv",outs,"GBK");
    }

    /**
     * 根据后台记录更新前台数据
     */
    @Test
    public void lsa(){
        List<String> contents= FileRead.getInstance().read("D:\\hq\\files/111.txt","utf-8");
        contents.stream().forEach(content->{
            String[] split = content.split("\t");
            String mobile=split[0];
            String pid=split[1];
            SendingVo vo=new SendingVo();
            vo.setMobile(Long.valueOf(mobile));
            vo.setPid(Integer.parseInt(pid));
            vo.setDay(20180921);
            List<SendingVo> rptPush = sendHistoryService.findHistoryAndRptcode(vo);
            if(rptPush!=null&&rptPush.size()>0){
                String rptcode=rptPush.get(0).getRptcode();
                vo.setRptcode(rptcode);
                vo.setRpttime(rptPush.get(0).getRpttime());
                vo.setTableName("0921");
                sendHistoryService114.updateRpt(vo);
            }
        });
    }
}


package com;

import com.pojo.SendingVo;
import com.service.RptService;
import com.service.SendHistoryService114;
import com.util.FilePrintUtil;
import com.util.StoreUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Autowired
    private StoreUtil storeUtil;

    /**
     * 按天导数据
     *
     * @throws Exception
     */
    @Test
    public void sss() throws Exception {
        List<String> BWcontents = new ArrayList<>();
        List<String> SMcontents = new ArrayList<>();
        String title = "号码,内容,时间,公司";
        BWcontents.add(title);
        SMcontents.add(title);
        for (int i = 20180801; i <= 20180809; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(20066);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);


            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate();
                if (v.getContent().contains("百威")) {
                    content = content + ",百威";
                    BWcontents.add(content);
                } else if (v.getContent().contains("苏秘")) {
                    content = content + ",苏秘";
                    SMcontents.add(content);
                }

            });
        }

        for (int i = 20180701; i <= 20180730; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(51089);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);
            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate();
                if (v.getContent().contains("百威")) {
                    content = content + ",百威";
                    BWcontents.add(content);
                } else if (v.getContent().contains("苏秘")) {
                    content = content + ",苏秘";
                    SMcontents.add(content);
                }

            });
        }
        for (int i = 20180801; i <= 20180806; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(51089);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);
            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate();
                if (v.getContent().contains("百威")) {
                    content = content + ",百威";
                    BWcontents.add(content);
                } else if (v.getContent().contains("苏秘")) {
                    content = content + ",苏秘";
                    SMcontents.add(content);
                }

            });
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/百威.csv", BWcontents, "GBK");
        FilePrintUtil.getInstance().write("D:\\hq\\files/苏秘.csv", SMcontents, "GBK");


    }


    /**
     * 导出single history 记录
     *
     * @throws Exception
     */
    @Test
    public void singHistory() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态";
        contents.add(title);
        int uid=90141;
        for (int i = 20180823; i <= 20180823; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);

            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate() + "," + v.getRptcode();
                contents.add(content);

            });
        }
        for (int i = 20180701; i <= 20180731; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);

            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate() + "," + v.getRptcode();
                contents.add(content);

            });
        }
        for (int i = 20180801; i <= 20180801; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);

            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate() + "," + v.getRptcode();
                contents.add(content);

            });
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/"+uid+".csv", contents, "GBK");

    }


    /**
     * 导出批量用户 history 记录
     *
     * @throws Exception
     */
    @Test
    public void exportBatch() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,发送时间,回执时间,状态";
        contents.add(title);
        int uid=90141;
        for (int i = 20180823; i <= 20180823; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String c= StringUtils.replace(v.getContent(),",",".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate() + ","+v.getRpttime()+"," + v.getRptcode();
                contents.add(content);

            });
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/"+uid+".csv", contents, "GBK");
    }


    /**
     * 按天,内容统计
     */
    @Test
    public void countByDayContent() {
        for (int i = 20180808; i <= 20180808; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setTableName(tableName);
            vo.setUid(20066);
            String[] contents = {
                    "超现实！预约车险续保，就能免费获得预约福利。安吉星用户专享，再也不用担心错过最优的福利活动了。活动详情戳：http://dwz.cn/J7eqoKKQ",
            };
            String title = "内容,总数,成功,失败,未知";
            List<String> outs = new ArrayList<>();
            outs.add(title);
            for (String content : contents) {
                vo.setContent(content);
                Integer total = sendHistoryService114.getTotal(vo) == null ? 0 : sendHistoryService114.getTotal(vo);
                if (total == 0) {
                    continue;
                }
                Integer succ = sendHistoryService114.getSucc(vo) == null ? 0 : sendHistoryService114.getSucc(vo);
                Integer fail = sendHistoryService114.getFail(vo) == null ? 0 : sendHistoryService114.getFail(vo);
                Integer wz = sendHistoryService114.getWz(vo) == null ? 0 : sendHistoryService114.getWz(vo);
                //  outs.add(content + "," + total + "," + succ + "," + fail + "," + wz);
                System.out.println(total + "," + succ + "," + fail + "," + wz);
            }
           /* if (outs.size() > 1) {
                FilePrintUtil.getInstance().write("D:\\hq\\files/" + vo.getUid() + "_2018" + tableName + ".csv", outs, "GBK");
            }*/
        }
    }


    /**
     * 按照内容统计
     */
    @Test
    public void countByDayContentGroup() {
        for (int i = 20180816; i <= 20180816; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setTableName(tableName);
            vo.setUid(90568);
            List<String> contentList = sendHistoryService114.getContentList(vo);
            String title = "内容,总数,成功,失败,未知";
            List<String> outs = new ArrayList<>();
            outs.add(title);
            for (String content : contentList) {
                vo.setContent(content);
                Integer total = sendHistoryService114.getTotal(vo) == null ? 0 : sendHistoryService114.getTotal(vo);
                if (total == 0) {
                    continue;
                }
                Integer succ = sendHistoryService114.getSucc(vo) == null ? 0 : sendHistoryService114.getSucc(vo);
                Integer fail = sendHistoryService114.getFail(vo) == null ? 0 : sendHistoryService114.getFail(vo);
                Integer wz = sendHistoryService114.getWz(vo) == null ? 0 : sendHistoryService114.getWz(vo);
                 outs.add(StringUtils.replace(content,",",".") + "," + total + "," + succ + "," + fail + "," + wz);
            }
            if (outs.size() > 1) {
                FilePrintUtil.getInstance().write("D:\\hq\\files/" + vo.getUid() + "_2018" + tableName + ".csv", outs, "GBK");
            }
        }
    }

    @Test
    public void countByDay() {
        int uid = 90617;
        for (int i = 20180801; i <= 20180814; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setTableName(tableName);
            vo.setUid(uid);

            String title = "总数,成功,失败,未知";
            List<String> outs = new ArrayList<>();
            outs.add(title);
            Integer total = sendHistoryService114.getTotal(vo) == null ? 0 : sendHistoryService114.getTotal(vo);
            if (total == 0) {
                continue;
            }
            Integer succ = sendHistoryService114.getSucc(vo) == null ? 0 : sendHistoryService114.getSucc(vo);
            Integer fail = sendHistoryService114.getFail(vo) == null ? 0 : sendHistoryService114.getFail(vo);
            Integer wz = sendHistoryService114.getWz(vo) == null ? 0 : sendHistoryService114.getWz(vo);
            outs.add(total + "," + succ + "," + fail + "," + wz);
            if (outs.size() > 1) {
                FilePrintUtil.getInstance().write("D:\\hq\\files/" + vo.getUid() + "_2018" + tableName + ".csv", outs, "GBK");
            }
        }
    }


    @Test
    public void countRpt() {
        int uid = 90617;
        int total = 0;
        for (int i = 20180801; i <= 20180814; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setTableName(tableName);
            vo.setUid(uid);
            vo.setRptcode("XA:0001");
            int count= sendHistoryService114.getSingleTotal(vo) == null ? 0 : sendHistoryService114.getSingleTotal(vo);
            total=total+count;
        }
        System.out.println(total);
    }


    @Test
    public void getSign(){
        Set<String> contents = new HashSet<>();
        int uid=51013;
        for (int i = 20180822; i <= 20180822; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            vo.setRptcode("XA:0003");
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String sign = storeUtil.getSign(v.getContent());
                contents.add(sign);

            });
        }
        contents.stream().forEach(System.out::println);
    }

}


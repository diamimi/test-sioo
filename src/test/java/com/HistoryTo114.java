package com;

import com.pojo.SendingVo;
import com.service.RptService;
import com.service.SendHistoryService114;
import com.service.Store21Service;
import com.util.ExcelUtil;
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
import java.util.stream.Collectors;

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

    @Autowired
    private Store21Service store21Service;

    /**
     * 按天导数据
     *
     * @throws Exception
     */
    @Test
    public void sss() throws Exception {
        //   List<String> BWcontents = new ArrayList<>();
        List<String> SMcontents = new ArrayList<>();
        String title = "号码,内容,时间,公司";
        // BWcontents.add(title);
        SMcontents.add(title);
        SendingVo vo = new SendingVo();
        int uid = 506551;
        vo.setUid(uid);
        addSingleContent(20180701, 20180731, SMcontents, vo);
        addSingleContent(20180501, 20180531, SMcontents, vo);
        addSingleContent(20180601, 20180630, SMcontents, vo);
        addSingleContent(20180701, 20180731, SMcontents, vo);
        addSingleContent(20180801, 20180831, SMcontents, vo);
        addSingleContent(20180901, 20180901, SMcontents, vo);
        List<String> collect = SMcontents.stream().filter(s -> s.contains("苏秘")).collect(Collectors.toList());
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", collect, "gbk");
    }


    /**
     * 导出single history 记录
     *
     * @throws Exception
     */
    @Test
    public void exportSingHistory() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态";
        contents.add(title);
        int uid = 506551;
        SendingVo vo = new SendingVo();
        vo.setUid(uid);
        // vo.setLevel(0);
        addBatchContent(20180701, 20180731, contents, vo);
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", contents, "gbk");
    }

    @Test
    public void exportBatchHistory() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态";
        contents.add(title);
        int uid = 506551;
        SendingVo vo = new SendingVo();
        vo.setUid(uid);
        // vo.setLevel(0);
        addBatchContent(20180817, 20180831, contents, vo);
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + "_8月_3.csv", contents, "gbk");
    }

    public void addSingleContent(int start, int end, List<String> contents, SendingVo vo) {
        for (int i = start; i <= end; i++) {
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);
            list.stream().forEach(v -> {
                String c = v.getContent().replace(",", ".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode();
                contents.add(content);

            });
        }
    }

    public void addSmSingleContent(int start, int end, List<String> contents, SendingVo vo) {
        for (int i = start; i <= end; i++) {
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);
            list.stream().forEach(v -> {
                if (v.getContent().contains("苏秘")) {
                    String c = v.getContent().replace(",", ".");
                    String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode();
                    contents.add(content);
                }

            });
        }
    }

    public void addBatchContent(int start, int end, List<String> contents, SendingVo vo) {
        for (int i = start; i <= end; i++) {
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String c = StringUtils.replace(v.getContent(), ",", ".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode();
                contents.add(content);
            });
        }
    }

    @Test
    public void singHistoryCsv() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态";
        contents.add(title);
        int uid = 80171;
        SendingVo vo = new SendingVo();
        vo.setUid(uid);
        // vo.setLevel(0);
        for (int i = 20180801; i <= 20180831; i++) {
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);
            list.stream().forEach(v -> {
                String c = StringUtils.replace(v.getContent(), ",", ".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode();
                contents.add(content);

            });
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", contents, "gbk");

    }

    /**
     * 导出single history 签名和内容
     *
     * @throws Exception
     */
    @Test
    public void singHistoryGroupByContent() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "内容#庚柴喻@签名";
        contents.add(title);
        int uid = 70;
        SendingVo vo = new SendingVo();
        vo.setUid(uid);
        vo.setRptcode("ID:1241");
        for (int i = 20180903; i <= 20180903; i++) {
            String tableName = String.valueOf(i);
            vo.setTableName(tableName);
            List<SendingVo> list = store21Service.singHistoryGroupByContent(vo);
            list.stream().forEach(v -> {
                String content = v.getContent() + "#庚柴喻@" + storeUtil.getSign(v.getContent());
                contents.add(content);

            });
        }
        ExcelUtil.getInstance().export(contents, "D:\\hq\\files/" + uid + ".xls");

    }


    /**
     * 导出批量用户 history 记录
     *
     * @throws Exception
     */
    @Test
    public void exportBatch() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,发送时间,状态";
        contents.add(title);
        int uid = 90141;
        for (int i = 20180903; i <= 20180903; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            //vo.setLevel(0);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String c = StringUtils.replace(v.getContent(), ",", ".");
                String content = v.getMobile() + "," + c + "," + v.getSenddate() + "," + v.getRptcode();
                contents.add(content);

            });
        }

        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", contents, "GBK");
    }

    /**
     * 导出批量用户 history 记录
     *
     * @throws Exception
     */
    @Test
    public void exportBatch1() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码#庚柴喻@内容#庚柴喻@发送时间";
        contents.add(title);
        int uid = 90615;
        SendingVo vo = new SendingVo();
        vo.setRptcode("DELIVRD");
        vo.setUid(uid);
        for (int i = 20180901; i <= 20180901; i++) {
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String content = v.getMobile() + "#庚柴喻@" + v.getContent() + "#庚柴喻@" + v.getSenddate1();
                contents.add(content);
            });
        }
        ExcelUtil.getInstance().export(contents, "D:\\hq\\files/" + uid + ".xls");
    }


    @Test
    public void exportBatchCsv() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,发送时间";
        contents.add(title);
        int uid = 90615;
        SendingVo vo = new SendingVo();
        vo.setRptcode("DELIVRD");
        vo.setUid(uid);
        for (int i = 20180901; i <= 20180901; i++) {
            String tableName = String.valueOf(i).substring(4);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + StringUtils.replace(v.getContent(), ",", ".") + "," + v.getSenddate1();
                contents.add(content);
            });
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", contents, "GBK");
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
                outs.add(StringUtils.replace(content, ",", ".") + "," + total + "," + succ + "," + fail + "," + wz);
            }
            if (outs.size() > 1) {
                FilePrintUtil.getInstance().write("D:\\hq\\files/" + vo.getUid() + "_2018" + tableName + ".csv", outs, "GBK");
            }
        }
    }

    @Test
    public void countByDay() {
        int uid = 906117;
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
            int count = sendHistoryService114.getSingleTotal(vo) == null ? 0 : sendHistoryService114.getSingleTotal(vo);
            total = total + count;
        }
        System.out.println(total);
    }


    @Test
    public void getSign() {
        Set<String> contents = new HashSet<>();
        int uid = 51013;
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

    @Test
    public void getFailReason() {
        String title = "日期#庚柴喻@内容";
        List<String> contents = new ArrayList<>();
        contents.add(title);
        SendingVo vo = new SendingVo();
        int uid = 90525;
        vo.setUid(uid);
        for (int i = 20180601; i <= 20180630; i++) {
            String tableName = String.valueOf(i);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.getFailReason(vo);
            if (list != null && list.size() > 0) {
                String content = i + "#庚柴喻@";
                for (SendingVo sendingVo : list) {
                    content = content + "状态:" + sendingVo.getRptcode() + ",描述:" + sendingVo.getContent() + ",数量:" + sendingVo.getUid() + ";";
                }
                contents.add(content);
            }
        }
        for (int i = 20180701; i <= 20180731; i++) {
            String tableName = String.valueOf(i);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.getFailReason(vo);
            if (list != null && list.size() > 0) {
                String content = i + "#庚柴喻@";
                for (SendingVo sendingVo : list) {
                    content = content + "状态:" + sendingVo.getRptcode() + ",描述:" + sendingVo.getContent() + ",数量:" + sendingVo.getUid() + ";";
                }
                contents.add(content);
            }
        }
        for (int i = 20180801; i <= 20180831; i++) {
            String tableName = String.valueOf(i);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.getFailReason(vo);
            if (list != null && list.size() > 0) {
                String content = i + "#庚柴喻@";
                for (SendingVo sendingVo : list) {
                    content = content + "状态:" + sendingVo.getRptcode() + ",描述:" + sendingVo.getContent() + ",数量:" + sendingVo.getUid() + ";";
                }
                contents.add(content);
            }
        }
        for (int i = 20180901; i <= 20180902; i++) {
            String tableName = String.valueOf(i);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.getFailReason(vo);
            if (list != null && list.size() > 0) {
                String content = i + "#庚柴喻@";
                for (SendingVo sendingVo : list) {
                    content = content + "状态:" + sendingVo.getRptcode() + ",描述:" + sendingVo.getContent() + ",数量:" + sendingVo.getUid() + ";";
                }
                contents.add(content);
            }
        }
        ExcelUtil.getInstance().export(contents, "D:\\hq\\files/" + uid + ".xls");
    }

}


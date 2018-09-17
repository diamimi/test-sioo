package com;

import com.pojo.SendingVo;
import com.service.*;
import com.util.*;
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

    @Autowired
    private SendHistoryService sendHistoryService;

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
        int uid = 81155;
        List<String> reads= FileRead.getInstance().read("D:\\hq\\files/1.txt","gbk");
        for (String s : reads) {
            String[] split = s.split("\t");
            String mobile=split[0];
            String cotent=split[1];
            String date=split[2];
            String rptcode=split[3];
            String c = StringUtils.replace(cotent, ",", ".");
            SendingVo v=new SendingVo();
            v.setSenddate(Long.valueOf(date));
            String cc=mobile + "," + c + "," +v.getSenddate1() + "," + rptcode;
            contents.add(cc);
        }
        List<String> days1 = DayUtil.getDayList(20180401, 20180601);
        for (String s : days1) {
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(s.substring(2));
            List<SendingVo> singleHistory = sendHistoryService114.findSingleHistory(vo);
            if (singleHistory != null && singleHistory.size() > 0) {
                singleHistory.stream().forEach(v -> {
                    String c = StringUtils.replace(v.getContent(), ",", ".");
                    String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode();
                    contents.add(content);

                });
            }
        }
        List<String> days = DayUtil.getDayList(20180614, 20180914);
        for (String day : days) {
            String tableName = day.substring(2);
            SendingVo vo = new SendingVo();
            vo.setUid(uid);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);
            if (list != null && list.size() > 0) {
                list.stream().forEach(v -> {
                    String c = StringUtils.replace(v.getContent(), ",", ".");
                    String content = v.getMobile() + "," + c + "," + v.getSenddate1() + "," + v.getRptcode();
                    contents.add(content);

                });
            }
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
     * 麻烦统计一下50872账号15号发的【魔龙之怒】【紫府苍穹】这两个签名的成功失败未知
     */
    @Test
    public void countByDay() {
        int uid = 50872;
        List<String> dayList = DayUtil.getDayList(20180915, 20180915);
        String title = "日期,内容,总数,成功,失败,未知";
        List<String> outs = new ArrayList<>();
        outs.add(title);
        for (String table : dayList) {
            String[] contents={"【魔龙之怒】","【紫府苍穹】"};
            for (String c : contents) {
                String tableName = table.substring(2);
                SendingVo vo = new SendingVo();
                vo.setTableName(tableName);
                vo.setUid(uid);
                vo.setContent(c);
                Integer total = sendHistoryService114.getTotal(vo) == null ? 0 : sendHistoryService114.getTotal(vo);
                if (total == 0) {
                    continue;
                }
                Integer succ = sendHistoryService114.getSucc(vo) == null ? 0 : sendHistoryService114.getSucc(vo);
                Integer fail = sendHistoryService114.getFail(vo) == null ? 0 : sendHistoryService114.getFail(vo);
                Integer wz = sendHistoryService114.getWz(vo) == null ? 0 : sendHistoryService114.getWz(vo);
                String content = table + ","+c+"," + total + "," + succ + "," + fail + "," + wz;
                outs.add(content);
            }

        }
        if (outs.size() > 1) {
            FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", outs, "GBK");
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
        List<String> days = DayUtil.getDayListOfMonth(2018, 6, 9);
        for (String day : days) {
            String tableName = day;
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.getFailReason(vo);
            if (list != null && list.size() > 0) {
                String content = day + "#庚柴喻@";
                for (SendingVo sendingVo : list) {
                    content = content + "状态:" + sendingVo.getRptcode() + ",描述:" + sendingVo.getContent() + ",数量:" + sendingVo.getUid() + ";";
                }
                contents.add(content);
            }
        }
        ExcelUtil.getInstance().export(contents, "D:\\hq\\files/" + uid + ".xls");
    }


    /**
     * 从4月1号开始。到现在50655和506551账户。分别的汇总统计，以及0-5秒多少，5-10秒多少，10-30秒多少，30-60秒，60秒以上
     */
    @Test
    public void bsd() {
        List<String> days = DayUtil.getDayList(20180401, 20180519);
        SendingVo vo = new SendingVo();
        vo.setUid(506551);
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        int c5 = 0;
        for (String day : days) {
            vo.setTableName(day.substring(2));
            List<SendingVo> succList = sendHistoryService114.getSuccList(vo);
            if (succList != null && succList.size() > 0) {
                for (SendingVo sendingVo : succList) {
                    if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 5) {
                        c1 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 10 && (sendingVo.getRpttime() - sendingVo.getSenddate()) > 5) {
                        c2 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 30 && (sendingVo.getRpttime() - sendingVo.getSenddate()) > 10) {
                        c3 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 60 && (sendingVo.getRpttime() - sendingVo.getSenddate()) > 30) {
                        c4 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) > 60) {
                        c5 += sendingVo.getContentNum();
                    }
                }
            }
            System.out.println(day);
        }
        System.out.println(c1 + "," + c2 + "," + c3 + "," + c4 + "," + c5);
    }

    @Test
    public void bsd1() {
        List<String> days = DayUtil.getDayList(20180520, 20180910);
        SendingVo vo = new SendingVo();
        vo.setUid(506551);
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        int c5 = 0;
        for (String day : days) {
            vo.setTableName(day.substring(2));
            List<SendingVo> succList = sendHistoryService114.getSuccBatchList(vo);
            if (succList != null && succList.size() > 0) {
                for (SendingVo sendingVo : succList) {
                    if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 5) {
                        c1 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 10 && (sendingVo.getRpttime() - sendingVo.getSenddate()) > 5) {
                        c2 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 30 && (sendingVo.getRpttime() - sendingVo.getSenddate()) > 10) {
                        c3 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) <= 60 && (sendingVo.getRpttime() - sendingVo.getSenddate()) > 30) {
                        c4 += sendingVo.getContentNum();
                    } else if ((sendingVo.getRpttime() - sendingVo.getSenddate()) > 60) {
                        c5 += sendingVo.getContentNum();
                    }
                }
            }
            System.out.println(day);
        }
        System.out.println(c1 + "," + c2 + "," + c3 + "," + c4 + "," + c5);
    }


}


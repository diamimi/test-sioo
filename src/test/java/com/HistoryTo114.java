package com;

import com.pojo.SendingVo;
import com.service.RptService;
import com.service.SendHistoryService114;
import com.util.FilePrintUtil;
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
public class HistoryTo114 {

    private static Logger log = LoggerFactory.getLogger(HistoryTo114.class);

    @Autowired
    private RptService rptService;

    @Autowired
    private SendHistoryService114 sendHistoryService114;

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
     * @throws Exception
     */
    @Test
    public void sss1() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态";
        contents.add(title);
        for (int i = 20180727; i <= 20180727; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(8041926);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findSingleHistory(vo);

            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate()+","+v.getRptcode();
                contents.add(content);

            });
        }


        FilePrintUtil.getInstance().write("D:\\hq\\files/8041926.csv", contents, "GBK");


    }



    /**
     * 导出批量用户 history 记录
     * @throws Exception
     */
    @Test
    public void exportBatch() throws Exception {
        List<String> contents = new ArrayList<>();
        String title = "号码,内容,时间,状态";
        contents.add(title);
        for (int i = 20180810; i <= 20180810; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setUid(90141);
            vo.setTableName(tableName);
            List<SendingVo> list = sendHistoryService114.findHistory(vo);

            list.stream().forEach(v -> {
                String content = v.getMobile() + "," + v.getContent() + "," + v.getSenddate()+","+v.getRptcode();
                contents.add(content);

            });
        }


        FilePrintUtil.getInstance().write("D:\\hq\\files/90141.csv", contents, "GBK");


    }


    /**
     * 按天,内容统计
     */
    @Test
    public void countByDayContent() {
        for (int i = 20180804; i <= 20180804; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setTableName(tableName);
            vo.setUid(90246);
            String[] contents = {
                    "【东龙】【爱生活】您在七秒贷的借款审批已通过，本次可借2000元！戳 http://t.cn/RgJz1k6 获取验证码下载七秒贷即可提现！回T退订",
                    "【东龙】您在七秒贷的借款审批已通过，本次可借2000元！戳 http://t.cn/RgJz1k6 获取验证码下载七秒贷即可提现回T退订",
                    "【东龙】您在七秒贷的借款审批已通过，本次可借2000元！戳 http://t.cn/RgJz1k6 获取验证码下载七秒贷即可提现！回T退订",
            };
            String title = "内容,总数,成功,失败,未知";
            List<String> outs = new ArrayList<>();
            outs.add(title);
            for (String content : contents) {
                //vo.setContent(content);
                Integer total = sendHistoryService114.getTotal(vo) == null ? 0 : sendHistoryService114.getTotal(vo);
                if (total == 0) {
                    continue;
                }
                Integer succ = sendHistoryService114.getSucc(vo) == null ? 0 : sendHistoryService114.getSucc(vo);
                Integer fail = sendHistoryService114.getFail(vo) == null ? 0 : sendHistoryService114.getFail(vo);
                Integer wz = sendHistoryService114.getWz(vo) == null ? 0 : sendHistoryService114.getWz(vo);
                outs.add(content + "," + total + "," + succ + "," + fail + "," + wz);
            }
            if (outs.size() > 1) {
                FilePrintUtil.getInstance().write("D:\\hq\\files/" + vo.getUid() + "_2018" + tableName + ".csv", outs, "GBK");
            }
        }
    }

    @Test
    public void countByDay() {
        for (int i = 20180802; i <= 20180802; i++) {
            String tableName = String.valueOf(i).substring(4);
            SendingVo vo = new SendingVo();
            vo.setTableName(tableName);
            vo.setUid(90581);
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





}

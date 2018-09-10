package com;

import com.pojo.SendingVo;
import com.service.SendHistoryService114;
import com.util.CalContentNum;
import com.util.ExcelUtil;
import com.util.FilePrintUtil;
import com.util.FileRead;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: HeQi
 * @Date:Create in 11:02 2018/7/25
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Anjxing {
    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Test
    public void sss() throws Exception {
        File xlsFile = new File("D:\\hq\\files/1.xlsx");
        InputStream is = new FileInputStream(xlsFile);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
        List<String> outs = new ArrayList<>();
        SendingVo vo = new SendingVo();
        int uid = 20066;
        vo.setUid(uid);
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                int total = Integer.valueOf(ExcelUtil.getInstance().getCellValue(row, 5));
                int esucc = Integer.valueOf(ExcelUtil.getInstance().getCellValue(row, 6));
                if (total - esucc < 10) {
                    continue;
                }
                String content = ExcelUtil.getInstance().getCellValue(row, 9);
                vo.setContent(content);
                vo.setTableName("0831");
                Integer succ = sendHistoryService114.getSucc(vo) == null ? 0 : sendHistoryService114.getSucc(vo);
                Integer fail = sendHistoryService114.getFail(vo) == null ? 0 : sendHistoryService114.getFail(vo);
                String out = total + "," + succ + "," + fail + "," + content;
                outs.add(out);
            }
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + uid + ".csv", outs, "gbk");
    }

    @Test
    public void insertSendHistory() {
        String c = "您的星享之旅已开启，指定时间内使用安吉星相关服务，即可轻松赢取车载Wi-Fi流量、全音控免提电话通话时长、续约优惠券等贴心礼遇，更有机会获取安吉星4年免费服务哦！详情请登录安吉星手机应用http://t.cn/RPNXH7w（首页点击“星享之旅”）。";
        String content="【安吉星】"+c+"回T退订";
        int contentNum= CalContentNum.calcContentNum(content);
        List<String> mobiles = FileRead.getInstance().read("D:\\hq\\files\\bf/星享之旅.txt", "utf-8");
        mobiles.stream().parallel().forEach(m -> {
            SendingVo vo=new SendingVo();
            vo.setHisid(10);
            vo.setContent(content);
            vo.setContentNum(contentNum);
            vo.setMobile(Long.parseLong(m));
            vo.setStat(0);
            vo.setRptcode("DELIVRD");
            vo.setUid(1);
            vo.setSenddate(Long.valueOf(senddate()));
            vo.setDay(201808);
            vo.setRpttime(vo.getSenddate());
            vo.setMdstr(DigestUtils.md5Hex(content));
            sendHistoryService114.insertSendHistoryAjx(vo);
        });

    }

    public String senddate() {
        Random random = new Random();
        int min = random.nextInt(60);
        String minstr = "";
        if (min < 10) {
            minstr = "0" + min;
        } else {
            minstr = min + "";
        }

        int sec = random.nextInt(60);
        String secstr = "";
        if (sec < 10) {
            secstr = "0" + sec;
        } else {
            secstr = sec + "";
        }
        String date = "2018090710" + minstr + secstr;
        return date;
    }

    @Test
    public void length(){
        String content="【安吉星】尊敬的车主，本月是您的生日，在这特别的日子里，安吉星诚挚地祝您健康快乐、幸福永远！行车路上吉星陪伴，愿平安与您一生相随。 回T退订";
        System.out.println(content.length());
    }
}

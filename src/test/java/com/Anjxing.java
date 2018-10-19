package com;

import com.pojo.SendingVo;
import com.service.AnjxService;
import com.service.SendHistoryService;
import com.service.SendHistoryService114;
import com.util.*;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

/**
 * @Author: HeQi
 * @Date:Create in 11:02 2018/7/25
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class Anjxing {
    @Autowired
    private AnjxService anjxService;

    @Autowired
    private SendHistoryService114 sendHistoryService114;

    @Autowired
    private SendHistoryService sendHistoryService;

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

    public String senddate(String d) {
        String[] ds = d.split("\\.");
        String month=ds[0];
        if(Integer.valueOf(month)<10){
            month="0"+month;
        }
        String day = ds[1];
        if(Integer.valueOf(day)<10){
            day="0"+day;
        }
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
        String date = "2018"+month + day + "09" + minstr + secstr;
        return date;
    }

    /**
     * 造假数据
     */
    @Test
    public void createData()  {
        Map<String, String> map = new HashMap<>();
        String fileName = "20181012补发";
        List<String> listFiles = FileNameUtil.getListFiles("D:\\hq\\安吉星数据\\" + fileName, "", false);
        Sheet sheet = ExcelUtil.getInstance().getSheet("D:\\hq\\安吉星数据/content.xlsx", 0);
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                String title = ExcelUtil.getInstance().getCellValue(row, 0);
                String content = "【安吉星】" + ExcelUtil.getInstance().getCellValue(row, 1) + "回T退订";
                map.put(title, content);
            }
        }
        for (String listFile : listFiles) {
            try {
                String name = StringUtils.substringBetween(listFile, fileName + "\\", ".txt");
                String key = StringUtils.substringBefore(name, "_");
                String date = StringUtils.substringAfter(name, key + "_");
                String content = map.get(key);
                int contentNum = CalContentNum.calcContentNum(content);
                List<String> mobiles = FileRead.getInstance().read(listFile, "utf-8");
                mobiles.stream().parallel().forEach(mobile -> {
                    SendingVo vo = new SendingVo();
                    vo.setHisid(11);
                    vo.setContent(content);
                    vo.setContentNum(contentNum);
                    vo.setMobile(Long.parseLong(mobile));
                    vo.setStat(99);
                    vo.setRptcode("DELIVRD");
                    vo.setUid(1);
                    vo.setSenddate(Long.valueOf(senddate(date)));
                    vo.setDay(201808);
                    vo.setRpttime(vo.getSenddate());
                    vo.setMdstr("20181012");
                 anjxService.insertSendHistoryAjx(vo);
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void dongtai(){
        List<String> read = FileRead.getInstance().read("D:\\hq\\安吉星数据/HFC.txt", "utf-8");
        for (String s : read) {
            try {
                String mobile=StringUtils.substringBefore(s,",");
                String id=StringUtils.substringAfter(s,",");
                SendingVo vo = new SendingVo();
                vo.setHisid(10);
                String content="【安吉星】重要通知：根据国家工信部《电话用户真实身份信息登记规定》等文件要求，请尽快对车辆的全音控免提电话（设备编号["+id+"]）进行实名制认证，获取免提电话号码可在按下车内安吉星白键后说“本车号码”，或通过车机屏“安吉星”图标查看；认证完成后，您的全音控免提电话服务将重新开启。开始认证请点击http://t.cn/RES4Efc 。回T退订";
                //  System.out.println(content);
                int contentNum=CalContentNum.calcContentNum(content);
                vo.setContent(content);
                vo.setContentNum(contentNum);
                vo.setMobile(Long.parseLong(mobile));
                vo.setStat(99);
                vo.setRptcode("DELIVRD");
                vo.setUid(1);
                vo.setSenddate(Long.valueOf(senddate("10.08")));
                vo.setDay(201808);
                vo.setRpttime(vo.getSenddate());
                vo.setMdstr("20181008");
                anjxService.insertSendHistoryAjx(vo);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新sendtime=0的数据
     */
    @Test
    public void ss() {
        SendingVo vo = new SendingVo();
        vo.setSenddate(0l);
        while (true) {
            List<SendingVo> list = anjxService.findOneHistory(vo);
            if (list != null && list.size() > 0) {
                list.stream().parallel().forEach(v -> {
                    v.setUid(20066);
                    v.setEndtime(v.getRpttime());
                    List<SendingVo> byConditon = sendHistoryService.findByConditon(v);
                    if (byConditon != null && byConditon.size() > 0) {
                        v.setSenddate(byConditon.get(0).getSenddate());
                        anjxService.updateHistory(v);
                    } else {
                        v.setSenddate(2l);
                        anjxService.updateHistory(v);
                    }
                });
            } else {
                break;
            }
        }

        System.out.println("==========end============");
    }
}

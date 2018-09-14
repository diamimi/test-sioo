package com;

import com.pojo.SendingVo;
import com.service.GhService;
import com.service.RptService;
import com.service.SendHistoryService;
import com.service.SendHistoryService114;
import com.util.DayUtil;
import com.util.FilePrintUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 17:13 2018/7/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GhTest {

    private final static Logger log = LoggerFactory.getLogger(ExcelTest.class);

    @Autowired
    private GhService ghService;


    @Autowired
    private RptService rptService;

    @Autowired
    private SendHistoryService sendHistoryService;

    @Autowired
    private SendHistoryService114 sendHistoryService114;


    /**
     * 根据114的记录更新21记录
     */
    @Test
    public void ss1s() {
        List<String> days = DayUtil.getDayList(20180701, 20180831);
        for (String day : days) {
            SendingVo vo = new SendingVo();
            vo.setUid(40058);
            vo.setRptcode("DELIVRD");
            vo.setTableName(day.substring(2));
            List<SendingVo> history = sendHistoryService114.findHistorySucc(vo);
            history.stream().parallel().forEach(h -> {
                h.setUid(40058);
                sendHistoryService.updateToSuccess(h);
            });
            System.out.println(day);
        }
    }


    @Test
    public void readExcel() throws Exception {
        File xlsFile = new File("D:\\hq/机构配置.xlsx");
        InputStream is = new FileInputStream(xlsFile);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
        HSSFWorkbook exportworkbook = new HSSFWorkbook();
        HSSFSheet exportsheet = exportworkbook.createSheet("sheet");
        int i = 0;
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                String uid = row.getCell(0).getStringCellValue().trim();
                String name = row.getCell(1).getStringCellValue().trim();
                String company = "";
                if (row.getCell(2) != null) {
                    company = row.getCell(2).getStringCellValue().trim();
                }
                Integer fail = rptService.findGhSubFail(uid);
                if (fail == null) {
                    fail = 0;
                }
                Integer total = rptService.findGhTotal(uid);
                Integer succNum = rptService.findGhSucc(uid);
                Integer failNum = rptService.findGhFail(uid);
                if (succNum == null) {
                    succNum = 0;
                }
                if (failNum == null) {
                    failNum = 0;
                }
                int wz = total - fail - succNum - failNum;
                HSSFRow rowExport = exportsheet.createRow(i);
                HSSFCell uidCell = rowExport.createCell(0);
                uidCell.setCellValue(uid);
                HSSFCell usernameCell = rowExport.createCell(1);
                usernameCell.setCellValue(name);
                HSSFCell companyCell = rowExport.createCell(2);
                companyCell.setCellValue(company);
                HSSFCell totalCell = rowExport.createCell(3);
                totalCell.setCellValue(total);
                HSSFCell failCell = rowExport.createCell(4);
                failCell.setCellValue(fail);
                HSSFCell asuccCell = rowExport.createCell(5);
                asuccCell.setCellValue(succNum);
                HSSFCell afCell = rowExport.createCell(6);
                afCell.setCellValue(failNum);
                HSSFCell wzCell = rowExport.createCell(7);
                wzCell.setCellValue(wz);
                i++;
            }
        }
        FileOutputStream output = new FileOutputStream("d:/广汇2季度.xls");
        exportworkbook.write(output);
        output.flush();
    }




    /**
     * 增加分区
     */
    @Test
    public void addParion() {
        List<String> dayListOfMonth = DayUtil.getDayList(2018, 10, 12);
        for (String date : dayListOfMonth) {
            String d = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
            String dayAfter = DayUtil.getDayAfter(d);
            String content = "ALTER TABLE smshy_new.sms_send_history_unknown ADD PARTITION (PARTITION phisotry_" + date + " VALUES LESS THAN (" + dayAfter + "000000));";
            System.out.println(content);
        }

    }

    /**
     * 查询21 广汇每天成功和失败
     */
    @Test
    public void siooGhCount() {
        List<String> dayListOfMonth = DayUtil.getDayListOfMonth(2018, 7, 8);
        String title="日期,总数,成功,失败,未知";
        List<String> outs = new ArrayList<>();
        outs.add(title);
        for (String d : dayListOfMonth) {
            SendingVo vo = new SendingVo();
            vo.setStarttime(Long.valueOf(d + "000000"));
            vo.setEndtime(Long.valueOf(DayUtil.getDayAfter(d) + "000000"));
            vo.setUid(40058);
            vo.setExcludeContent("【广汇汽车】");
            Integer total = sendHistoryService.countTotal(vo);
            Integer succ = sendHistoryService.countSucc(vo);
            Integer fail = sendHistoryService.countFail(vo);
            int wz=total-succ-fail;
            String content =d+","+ total + "," + succ + "," + fail+","+wz;
            outs.add(content);
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/sioo.csv", outs, "GBK");
    }


    /**
     * 查询114广汇每天数量统计
     */
    @Test
    public void ghCount114(){
        List<String> dayListOfMonth = DayUtil.getDayListOfMonth(2018, 7, 8);
        String title="日期,总数,成功,失败,未知";
        List<String> outs = new ArrayList<>();
        outs.add(title);
        for (String s : dayListOfMonth) {
            SendingVo vo=new SendingVo();
            vo.setUid(40058);
          //  vo.setContent("【广汇汽车】");
            vo.setExcludeContent("【广汇汽车】");
            vo.setTableName(s.substring(2));
            Integer succ = sendHistoryService114.getSucc(vo);
            Integer total = sendHistoryService114.getTotal(vo);
            Integer fail = sendHistoryService114.getFail(vo);
            Integer wz = sendHistoryService114.getWz(vo);
            String content =s+","+ total + "," + succ + "," + fail+","+wz;
            outs.add(content);
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/114-gh-exclude.csv", outs, "GBK");
    }




    /**
     * 根据21的发送状态更新记录
     */
    @Test
    public void updateFrom21() {
        List<String> dayList = DayUtil.getDayList(20180719, 20180831);
        for (String s : dayList) {
            SendingVo vo = new SendingVo();
            String start = s + "000000";
            String end = DayUtil.getDayAfter(s) + "000000";
            vo.setStarttime(Long.valueOf(start));
            vo.setEndtime(Long.valueOf(end));
            List<SendingVo> failList = ghService.findFailList(vo);
            failList.stream().forEach(sendingVo -> {
                String time = sendingVo.getSenddate1();
                sendingVo.setStarttime(sendingVo.getSenddate());
                sendingVo.setEndtime(Long.valueOf(DayUtil.getDaySec(time, 15)));
                sendingVo.setUid(40058);
                SendingVo sendingVo1 = sendHistoryService.findSucc21(sendingVo);
                if (sendingVo1 != null) {
                    if (sendingVo.getArrive_succ() != sendingVo1.getArrive_succ() || sendingVo.getArrive_fail() != sendingVo1.getArrive_fail())
                        sendingVo.setArrive_succ(sendingVo1.getArrive_succ());
                    sendingVo.setArrive_fail(sendingVo1.getArrive_fail());
                    ghService.updateToSucc(sendingVo);
                }
            });
            System.out.println(s);
        }

    }


}

package com;

import com.pojo.SendingVo;
import com.pojo.UserDayCount;
import com.service.RptService;
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
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: HeQi
 * @Date:Create in 17:13 2018/7/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GhTest {

    private final static Logger log = LoggerFactory.getLogger(ExcelTest.class);


    @Autowired
    private RptService rptService;


    @Test
    public void sss1() throws Exception{
        List<SendingVo> list = rptService.findFail();
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> list.stream().parallel().forEach(vo -> {
            SendingVo v = rptService.findWz21(vo);
            if (v != null) {
                if (v.getArrive_succ() > 0&&v.getArrive_fail()==0) {
                    log.info("mobile:{},id:{},senddate:{}",vo.getMobile(),vo.getId(),vo.getSenddate());
                }
            }
        }
        )).get();
     /*   for (SendingVo vo : list) {
            SendingVo v = rptService.findWz21(vo);
            if (v != null) {
                if (v.getArrive_succ() > 0&&v.getArrive_fail()==0) {
                    log.info("mobile:{},id:{},senddate:{}",vo.getMobile(),vo.getId(),vo.getSenddate());
                }
            }
        }*/
    }



    @Test
    public void sss() {
        List<SendingVo> list = rptService.findWz();
        for (SendingVo vo : list) {
            SendingVo v = rptService.findWz21(vo);
            if (v != null) {
                if (v.getArrive_fail() > 0 || v.getArrive_succ() > 0) {
                    vo.setArrive_succ(v.getArrive_succ());
                    vo.setArrive_fail(v.getArrive_fail());
                    rptService.updateGhHistory(vo);
                }
            }
        }
    }


    @Test
    public void readExcel() throws Exception {
        File xlsFile = new File("d:/广汇2季度-7-19.xls");
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
                Integer total=rptService.findGhTotal(uid);
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


    @Test
    public void exportExcel() throws Exception {
        List<UserDayCount> list = rptService.getGhUserDayCount();
        Map<Integer, UserDayCount> map = new HashMap<>();
        for (UserDayCount userDayCount : list) {
            if (map.containsKey(userDayCount.getUid())) {
                UserDayCount dayCount = map.get(userDayCount.getUid());
                dayCount.setTotal(userDayCount.getTotal() + dayCount.getTotal());
                dayCount.setFail(userDayCount.getFail() + dayCount.getFail());
                dayCount.setAsucc(userDayCount.getAsucc() + dayCount.getAsucc());
                dayCount.setAf(userDayCount.getAf() + dayCount.getAf());
                map.put(userDayCount.getUid(), dayCount);
            } else {
                map.put(userDayCount.getUid(), userDayCount);
            }
        }
        List<Map.Entry<Integer, UserDayCount>> list1 = new ArrayList<>(map.entrySet());
        Collections.sort(list1, (o1, o2) -> o2.getValue().getTotal().compareTo(o1.getValue().getTotal()));
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("对象报表");
        int i = 0;
        for (Map.Entry<Integer, UserDayCount> mapping : list1) {
            UserDayCount u = mapping.getValue();
            u.setWz(u.getTotal() - u.getAf() - u.getAsucc() - u.getFail());
            HSSFRow row = sheet.createRow(i);
            HSSFCell uid = row.createCell(0);
            uid.setCellValue(u.getUid());
            HSSFCell username = row.createCell(1);
            username.setCellValue(u.getUsername());
            HSSFCell company = row.createCell(2);
            company.setCellValue(u.getCompany());
            HSSFCell total = row.createCell(4);
            total.setCellValue(u.getTotal());
            HSSFCell fail = row.createCell(5);
            fail.setCellValue(u.getFail());
            HSSFCell asucc = row.createCell(6);
            asucc.setCellValue(u.getAsucc());
            HSSFCell af = row.createCell(7);
            af.setCellValue(u.getAf());
            HSSFCell wz = row.createCell(8);
            wz.setCellValue(u.getWz());
            i++;
        }
        FileOutputStream output = new FileOutputStream("d:/广汇2季度.xls");
        workbook.write(output);
        output.flush();

    }

}

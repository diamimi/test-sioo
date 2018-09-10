package com;

import com.pojo.SendingVo;
import com.pojo.UserDayCount;
import com.service.GhService;
import com.service.RptService;
import com.service.Store21Service;
import com.service.StoreGhService;
import com.util.ExcelUtil;
import com.util.FilePrintUtil;
import org.apache.commons.lang.StringUtils;
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
    private GhService ghService;


    @Autowired
    private RptService rptService;

    @Autowired
    private StoreGhService storeGhService;

    @Autowired
    private Store21Service store21Service;


    @Test
    public void ss() {
        List<String> ghList = storeGhService.findList();

        List<String> List21 = store21Service.findList();
        int expend = 4006921;
        for (String s : ghList) {
            if (!List21.contains(s)) {
                System.out.println("INSERT INTO `smshy`.`sms_user_signstore` ( `uid`, `store`, `expend`, `status`, `userstat`, " +
                        "`signtime`, `addtime`, `type`, `channel`, `expendqd`, `expend2`, `userexpend`) VALUES" +
                        " ( '40058', '" + s + "', '" + expend + "', '0', '1', NULL, '2018-06-30 17:08:48', '2', '0', NULL, '" + expend + "', '40058" + expend + "');");
                expend++;
            }
        }
    }


    @Test
    public void sss1() throws Exception {
        List<SendingVo> list = rptService.findFail();
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> list.stream().parallel().forEach(vo -> {
                    SendingVo v = rptService.findWz21(vo);
                    if (v != null) {
                        if (v.getArrive_succ() > 0 && v.getArrive_fail() == 0) {
                            log.info("mobile:{},id:{},senddate:{}", vo.getMobile(), vo.getId(), vo.getSenddate());
                        }
                    }
                }
        )).get();
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


    @Test
    public void exportExcel() throws Exception {
        List<UserDayCount> list = ghService.getGhUserDayCount();
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
            HSSFCell total = row.createCell(3);
            total.setCellValue(u.getTotal());
            HSSFCell fail = row.createCell(4);
            fail.setCellValue(u.getFail());
            HSSFCell asucc = row.createCell(5);
            asucc.setCellValue(u.getAsucc()+u.getWz());
            HSSFCell af = row.createCell(6);
            af.setCellValue(u.getAf());
            HSSFCell city = row.createCell(7);
            city.setCellValue(u.getCity());
            i++;
        }
        FileOutputStream output = new FileOutputStream("d:/广汇-1,17,18,19.20.xls");
        workbook.write(output);
        output.flush();
    }

    @Test
    public void countByDay() {
        String title = "号码,批次,时间,内容,条数";
        SendingVo vo = new SendingVo();
        String filename = "广汇";
        // vo.setUsername(filename);
        for (int i = 20180401; i <= 20180419; i++) {
            List<String> outs = new ArrayList<>();
            outs.add(title);
            long start = i;
            long end = start + 1;
            vo.setStarttime(start * 1000000l);
            vo.setEndtime(end * 1000000l);
            vo.setContent("【广汇汽车】");
            List<SendingVo> list = ghService.getHistorySucc(vo);
            list.stream().forEach(v -> {
                String c = StringUtils.remove(v.getContent(), "\r");
                c = StringUtils.remove(c, "\n");
                c = StringUtils.remove(c, "\t");
                c = StringUtils.replace(c, ",", ".");
                String content = v.getMobile() + "," + v.getPid() + "," + v.getSenddate() + "," + c + "," + v.getContentNum();
                outs.add(content);
            });
            FilePrintUtil.getInstance().write("D:\\hq\\files/" + filename + "_" + start + ".csv", outs, "GBK");
        }

    }


    @Test
    public void countByDay2() {
        String title = "号码,批次,时间,内容,条数";
        SendingVo vo = new SendingVo();
        String filename = "广汇";
        List<String> outs = new ArrayList<>();
        outs.add(title);
        long start = 20180625140000l;
        long end = 20180625160000l;
        vo.setStarttime(start );
        vo.setEndtime(end);
        vo.setContent("【广汇汽车】");
        List<SendingVo> list = ghService.getHistorySucc(vo);
        list.stream().forEach(v -> {
            String c = StringUtils.remove(v.getContent(), "\r");
            c = StringUtils.remove(c, "\n");
            c = StringUtils.remove(c, "\t");
            c = StringUtils.replace(c, ",", ".");
            String content = v.getMobile() + "," + v.getPid() + "," + v.getSenddate() + "," + c + "," + v.getContentNum();
            outs.add(content);
        });
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + filename + "_" + start + ".csv", outs, "GBK");

    }


    @Test
    public void countByDay1() {
        String title = "时间,条数";
        String filename = "广汇";
        List<String> outs = new ArrayList<>();
        outs.add(title);
        SendingVo vo = new SendingVo();
        vo.setUid(1);
        for (int i = 20180401; i <= 20180430; i++) {
            long start = i;
            long end ;
            if (start == 20180430) {
                end = 20180501;
            } else {
                end = start + 1;
            }
            vo.setStarttime(start * 1000000l);
            vo.setEndtime(end * 1000000l);
            vo.setContent("【广汇汽车】");
            Integer succ = ghService.getSucc(vo);
            String content = i + "," + succ;
            outs.add(content);
        }
        for (int i = 20180501; i <= 20180531; i++) {
            long start = i;
            long end ;
            if (start == 20180531) {
                end = 20180601;
            } else {
                end = start + 1;
            }
            vo.setStarttime(start * 1000000l);
            vo.setEndtime(end * 1000000l);
            vo.setContent("【广汇汽车】");
            Integer succ = ghService.getSucc(vo);
            String content = i + "," + succ;
            outs.add(content);
        }
        for (int i = 20180601; i <= 20180630; i++) {
            long start = i;
            long end;
            if (start == 20180630) {
                end = 20180701;
            } else {
                end = start + 1;
            }
            vo.setStarttime(start * 1000000l);
            vo.setEndtime(end * 1000000l);
            vo.setContent("【广汇汽车】");
            Integer succ = ghService.getSucc(vo);
            String content = i + "," + succ;
            outs.add(content);
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + filename + ".csv", outs, "GBK");
    }


    @Test
    public void excel() throws Exception {
        List<String> outs = new ArrayList<>();
        String title = "用户,总数,成功,失败";
        outs.add(title);
        Map<String,UserDayCount> map=new HashMap<>();
        String[] array={
                "D:\\hq\\files/4月汇总-.xlsx",
                "D:\\hq\\files/5月汇总-.xlsx",
                "D:\\hq\\files/6月汇总-.xlsx",
        };
        for (String s : array) {
            File xlsFile = new File(s);
            InputStream is = new FileInputStream(xlsFile);
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
            for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    String username = ExcelUtil.getInstance().getCellValue(row, 0);
                    String gh_total = ExcelUtil.getInstance().getCellValue(row, 1);
                    String gh_success = ExcelUtil.getInstance().getCellValue(row, 2);
                    String gh_fail = ExcelUtil.getInstance().getCellValue(row, 3);
                    if(!map.containsKey(username)){
                        UserDayCount userDayCount=new UserDayCount();
                        userDayCount.setTotal(Integer.valueOf(gh_total));
                        userDayCount.setAsucc(Integer.valueOf(gh_success));
                        userDayCount.setAf(Integer.valueOf(gh_fail));
                        map.put(username,userDayCount);
                    }else {
                        UserDayCount userDayCount=map.get(username);
                        userDayCount.setTotal(userDayCount.getTotal()+Integer.valueOf(gh_total));
                        userDayCount.setAsucc(userDayCount.getAsucc()+Integer.valueOf(gh_success));
                        userDayCount.setAf(userDayCount.getAf()+Integer.valueOf(gh_fail));
                        map.put(username,userDayCount);
                    }
                }
            }
        }
        List<Map.Entry<String,UserDayCount>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, (o1, o2) -> o2.getValue().getTotal().compareTo(o1.getValue().getTotal()));
        for(Map.Entry<String,UserDayCount> entry:list){
            String content=entry.getKey()+","+entry.getValue().getTotal()+","+entry.getValue().getAsucc()+","+entry.getValue().getAf();
            outs.add(content);
        }
        FilePrintUtil.getInstance().write("D:\\hq\\files/广汇_汇总.csv", outs, "GBK");
    }

    @Test
    public void sss11(){
        Integer[] ids={1,17,18,19,20};
        List<String> outs=new ArrayList<>();
        Arrays.stream(ids).forEach(i->{
            for (int j = 4; j <= 8; j++) {
                SendingVo vo=new SendingVo();
                vo.setUid(i);
                long start=Long.parseLong("20180"+j+"01000000");
                long end=Long.parseLong("20180"+(j+i)+"01000000");
                vo.setStarttime(start);
                vo.setEndtime(end);
                Integer total=ghService.getTotal(vo)==null?0:ghService.getTotal(vo);
                Integer succ=ghService.getSucc(vo)==null?0:ghService.getSucc(vo);
                Integer fail=ghService.getFail(vo)==null?0:ghService.getFail(vo);
                Integer wz=total-succ-fail;
                String content=i+","+String.valueOf(start).substring(0,6)+","+total+","+succ+","+fail+","+wz;
                outs.add(content);

            }
        });
        FilePrintUtil.getInstance().write("D:\\hq\\files/广汇.csv", outs, "GBK");
    }

    @Test
    public void exportMxByUid(){
        Integer[] ids={1,17,18,19,20};
        SendingVo vo=new SendingVo();
        vo.setStarttime(20180801000000l);
        vo.setEndtime(20180901000000l);
        Arrays.stream(ids).forEach(i->{
            vo.setUid(i);
            mxByUid(vo,"8月");
        });

    }

    public void mxByUid(SendingVo vo,String fileName){
        String title = "账号,号码,批次,时间,内容,条数";
        List<String> outs=new ArrayList<>();
        outs.add(title);
        List<SendingVo> sendingVos=ghService.getHistorySucc(vo);
        sendingVos.stream().forEach((v->{
            String c = StringUtils.remove(v.getContent(), "\r");
            c = StringUtils.remove(c, "\n");
            c = StringUtils.remove(c, "\t");
            c = StringUtils.replace(c, ",", ".");
            String content =v.getUsername()+","+ v.getMobile() + "," + v.getPid() + "," + v.getSenddate1() + "," + c + "," + v.getContentNum();
            outs.add(content);
        }));
        FilePrintUtil.getInstance().write("D:\\hq\\files/" + vo.getUid() + "_" + fileName + ".csv", outs, "GBK");
    }


}

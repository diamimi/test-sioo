package com;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.csvreader.CsvWriter;
import com.pojo.*;
import com.service.*;
import com.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: HeQi
 * @Date:Create in 10:48 2018/7/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Test21 {

    private static Logger log = LoggerFactory.getLogger(Test21.class);
    @Autowired
    private SendHistoryService sendHistoryService;

    @Autowired
    private SendHistory35Service sendHistory35Service;

    @Autowired
    private UserDayCountService userDayCountService;


    @Autowired
    private MobileAreaService21 mobileAreaService21;

    @Autowired
    private HistoryContentFor5Service historyContentFor5Service;

    @Autowired
    private StoreUtil storeUtil;

    @Autowired
    private SmsUserUtil smsUserUtil;


    /**
     * 根据日志回复记录
     */
    @Test
    public void logToContentFor5(){
        storeUtil.loadUserSign(null,null);
        smsUserUtil.loadUser();
        List<String> read = FileRead.getInstance().read("D:\\hq\\files/data.log","utf-8");
        read.stream().forEach(s->{
            s="{"+ StringUtils.substringAfter(s," - {");
            SendingBigVo vo = JSON.parseObject(s, new TypeReference<SendingBigVo>() {});
            SmsUser smsUser = SmsCache.USER.get(vo.getUid());
            String content=vo.getContent();
            if(!StoreUtil.hasStore(vo.getContent())){
                 content=storeUtil.addSign(vo.getContent(),vo.getUid(),smsUser.getSignPosition(),smsUser.getUsertype());
            }
            SendingVo v=new SendingVo();
            v.setContent(content);
            v.setUid(vo.getUid());
            v.setSenddate(20180816l);
            v.setPid(vo.getPid());
            historyContentFor5Service.add(v);
        });
    }

    @Test
    public void sss() throws Exception {
        List<UserDayCount> list = userDayCountService.getUserDayCount();
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
        HSSFSheet sheet = workbook.createSheet("统计");
        HSSFRow r = sheet.createRow(0);
        HSSFCell uid0 = r.createCell(0);
        uid0.setCellValue("用户id");
        HSSFCell username0 = r.createCell(1);
        username0.setCellValue("用户名");
        HSSFCell company0 = r.createCell(2);
        company0.setCellValue("公司");
        HSSFCell total0 = r.createCell(4);
        total0.setCellValue("提交总数");
        HSSFCell asucc0 = r.createCell(5);
        asucc0.setCellValue("回执成功");
        HSSFCell af0 = r.createCell(6);
        af0.setCellValue("回执失败");
        HSSFCell wz0 = r.createCell(7);
        wz0.setCellValue("未知");
        int i = 1;
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
            HSSFCell asucc = row.createCell(5);
            asucc.setCellValue(u.getAsucc());
            HSSFCell af = row.createCell(6);
            af.setCellValue(u.getAf());
            HSSFCell wz = row.createCell(7);
            wz.setCellValue(u.getWz());
            i++;
        }
        FileOutputStream output = new FileOutputStream("d:/sioo_6月.xls");
        workbook.write(output);
        output.flush();


    }


    /**
     * send_user_history
     */

    @Test
    public void sla() {
        SendingVo sendingVo = new SendingVo();
        sendingVo.setUid(51089);
        sendingVo.setStarttime(20180725000000L);
        sendingVo.setEndtime(20180803000000L);
        List<SendingVo> list = sendHistoryService.findByConditon(sendingVo);
        int bwContentNum = 0;
        int smContentNum = 0;
        for (SendingVo vo : list) {
            if (vo.getContent().contains("【百威】")) {
                bwContentNum = bwContentNum + vo.getContentNum();
            } else if (vo.getContent().contains("【苏秘37°】")) {
                smContentNum = smContentNum + vo.getContentNum();
            }
        }
        System.out.println(bwContentNum + "," + smContentNum);
        //806+122+964,66007+27572
    }

    @Test
    public void charuhaoma() {
        List<String> list = FileRead.getInstance().read("D:\\hq\\1/111.txt","");
        List<MobileArea> mobileAreaList = mobileAreaService21.findList();
        Map<String, MobileArea> moMap = new HashMap<>();
        mobileAreaList.stream().parallel().forEach((mo -> {
            moMap.put(mo.getNumber(), mo);
        }));
        List<SendingVo> insertList = new ArrayList<>();
        long id = 99;
        for (String s : list) {
            SendingVo vo = new SendingVo();
            long mobile = Long.parseLong(s);
            vo.setMobile(mobile);
            vo.setUid(51110);
            int mtype = MyUtils.checkMobileType(s);
            String number = s.substring(0, 7);
            String location = "全国";
            if (moMap.get(number) != null) {
                location = moMap.get(number).getProvince();
            }
            vo.setMtype(mtype);
            vo.setLocation(location);
            String date = this.senddate();
            vo.setSenddate(Long.valueOf(date));
            vo.setContent("【钱宝袋】恭喜您，钱宝袋为您授信10000信用额 度，微信关注：钱宝袋，即可申请取现。回T退订");
            vo.setContentNum(1);
            vo.setExpid("51110");
            vo.setPid(1000);
            int channel = 0;
            if (mtype == 1) {
                channel = 8;
            } else if (mtype == 2) {
                channel = 29;
            } else if (mtype == 4) {
                channel = 48;
            }
            vo.setChannel(channel);
            vo.setId(id);
            insertList.add(vo);
            id++;
        }
        if (insertList.size() > 300) {
            int limitSize = 300;
            int part = insertList.size() / limitSize;
            for (int i = 0; i < part; i++) {
                List<SendingVo> subList = insertList.subList(0, limitSize);
                sendHistoryService.batchInsert(subList);
                insertList.subList(0, limitSize).clear();
            }
            if (!insertList.isEmpty()) {
                sendHistoryService.batchInsert(insertList);
            }
        } else {
            sendHistoryService.batchInsert(insertList);
        }

    }

    @Test
    public void ss() throws Exception {

        try {
            log.info("===========start=================");
            List<SendingVo> sendingVos = sendHistoryService.export();
            log.info("===========开始插入数据=================");
            CsvWriter csvWriter = new CsvWriter("D:\\hq\\files/0619.csv", ',', Charset.forName("GBK"));
            String[] titel = new String[3];
            titel[0] = "号码";
            titel[1] = "内容";
            titel[2] = "时间";
            csvWriter.writeRecord(titel);
            for (SendingVo sendingVo : sendingVos) {
                sendingVo.setContent("【钱宝袋】恭喜您，钱宝袋为您授信10000信用额 度，微信关注：钱宝袋，即可申请取现。回T退订");
                String[] contents = new String[3];
                contents[0] = String.valueOf(sendingVo.getMobile());
                contents[1] = sendingVo.getContent();
                contents[2] = String.valueOf(sendingVo.getSenddate());
                csvWriter.writeRecord(contents);
            }
            csvWriter.close();
            log.info("============END=================");
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
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
        String date = "2018061909" + minstr + secstr;
        return date;
    }

    @Test
    public void export() {
        File file = new File("d:/hq/xiao测试号码(1).txt");
        InputStreamReader read = null;// 考虑到编码格式
        try {
            read = new InputStreamReader(new FileInputStream(file), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(read);
        String mobile = null;
        try {
            while ((mobile = bufferedReader.readLine()) != null) {
                if (mobile != null && mobile != "") {
                    SendingVo vo = new SendingVo();
                    vo.setMobile(Long.parseLong(mobile));
                    List<SendingVo> list = new ArrayList<>();
                    List<SendingVo> historyAndRptcode21 = sendHistoryService.findHistoryAndRptcode(vo);
                    for (SendingVo sendingVo : historyAndRptcode21) {
                        list.add(sendingVo);
                    }
                    List<SendingVo> historyAndRptcode35 = sendHistory35Service.findHistoryAndRptcode(vo);
                    for (SendingVo sendingVo : historyAndRptcode35) {
                        list.add(sendingVo);
                    }
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet("统计");
                    HSSFRow r = sheet.createRow(0);
                    HSSFCell uid0 = r.createCell(0);
                    uid0.setCellValue("手机号");
                    HSSFCell username0 = r.createCell(1);
                    username0.setCellValue("内容");
                    HSSFCell company0 = r.createCell(2);
                    company0.setCellValue("发送时间");
                    HSSFCell total0 = r.createCell(3);
                    total0.setCellValue("回执状态");
                    int i = 1;
                    Collections.sort(list, (v1, v2) -> v1.getSenddate().compareTo(v2.getSenddate()));
                    for (SendingVo sendingVo : list) {
                        HSSFRow row = sheet.createRow(i);
                        HSSFCell phone = row.createCell(0);
                        phone.setCellValue(sendingVo.getMobile());
                        HSSFCell content = row.createCell(1);
                        content.setCellValue(sendingVo.getContent());
                        HSSFCell senddate = row.createCell(2);
                        senddate.setCellValue(sendingVo.getSenddate());
                        HSSFCell rptcode = row.createCell(3);
                        rptcode.setCellValue(sendingVo.getRptcode());
                        i++;
                    }
                    FileOutputStream output = new FileOutputStream("d:/hq/mobile/" + mobile + ".xls");
                    workbook.write(output);
                    output.flush();
                    output.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Test
    public void export1() throws Exception {
        File file = new File("/home/sioowork/114/xiao测试号码(1).txt");
        InputStreamReader read = null;// 考虑到编码格式
        try {
            read = new InputStreamReader(new FileInputStream(file), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(read);
        String mobile = null;
        List<String> mobiles = new ArrayList<>();
        try {
            while ((mobile = bufferedReader.readLine()) != null) {
                if (mobile != null && mobile != "") {
                    mobiles.add(mobile);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> mobiles.stream().parallel().forEach(i -> {
                    SendingVo vo = new SendingVo();
                    vo.setMobile(Long.parseLong(i));
                    List<SendingVo> list = new ArrayList<>();
                    List<SendingVo> historyAndRptcode21 = sendHistoryService.findHistoryAndRptcode(vo);
                    for (SendingVo sendingVo : historyAndRptcode21) {
                        list.add(sendingVo);
                    }
                    List<SendingVo> historyAndRptcode35 = sendHistory35Service.findHistoryAndRptcode(vo);
                    for (SendingVo sendingVo : historyAndRptcode35) {
                        list.add(sendingVo);
                    }
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    HSSFSheet sheet = workbook.createSheet("统计");
                    HSSFRow r = sheet.createRow(0);
                    HSSFCell uid0 = r.createCell(0);
                    uid0.setCellValue("手机号");
                    HSSFCell username0 = r.createCell(1);
                    username0.setCellValue("内容");
                    HSSFCell company0 = r.createCell(2);
                    company0.setCellValue("发送时间");
                    HSSFCell total0 = r.createCell(3);
                    total0.setCellValue("回执状态");
                    int k = 1;
                    Collections.sort(list, (v1, v2) -> v1.getSenddate().compareTo(v2.getSenddate()));
                    for (SendingVo sendingVo : list) {
                        HSSFRow row = sheet.createRow(k);
                        HSSFCell phone = row.createCell(0);
                        phone.setCellValue(sendingVo.getMobile());
                        HSSFCell content = row.createCell(1);
                        content.setCellValue(sendingVo.getContent());
                        HSSFCell senddate = row.createCell(2);
                        senddate.setCellValue(sendingVo.getSenddate());
                        HSSFCell rptcode = row.createCell(3);
                        rptcode.setCellValue(sendingVo.getRptcode());
                        k++;
                    }
                    try {
                        FileOutputStream output = new FileOutputStream("/home/sioowork/114/mobile/" + i + ".xls");
                        workbook.write(output);
                        output.flush();
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        )).get();
        log.info("===============END====================");
    }
}

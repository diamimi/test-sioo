package com.main;

import com.csvreader.CsvWriter;
import com.pojo.MobileArea;
import com.pojo.SendingVo;
import com.service.MobileAreaService21;
import com.service.SendHistory35Service;
import com.service.SendHistoryService;
import com.util.FileRead;
import com.util.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);


    @Autowired
    private SendHistoryService sendHistoryService;

    @Autowired
    private SendHistory35Service sendHistory35Service;

    @Autowired
    private MobileAreaService21 mobileAreaService21;


    @Override
    public void run(ApplicationArguments var1) throws Exception {
      // ss();
    }

    /**
     * 更新前台没有签名的记录
     *
     * @throws Exception
     */
    public void ssa() throws Exception {
        //File file = new File("/home/sioowork/114/xiao.txt");
        File file = new File("D:\\hq/xiao.txt");
        InputStreamReader read = null;// 考虑到编码格式
        try {
            read = new InputStreamReader(new FileInputStream(file), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(read);
        String mobile;
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
        PrintWriter out = new PrintWriter("D:\\hq/mobile/没记录.txt");
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> mobiles.stream().parallel().forEach(i -> {
                    SendingVo vo = new SendingVo();
                    vo.setMobile(Long.parseLong(i));
                    int count21 = sendHistoryService.countHistoryAndRptcode(vo);
                    int count35 = sendHistory35Service.countHistoryAndRptcode(vo);
                    if (count21 == 0 && count35 == 0) {
                        out.write(i + "\r\n");
                    }

                }
        )).get();
        out.flush();
        out.close();
        LOGGER.info("===============END====================");
    }



    public void charuhaoma() throws Exception {
        List<String> list = FileRead.getInstance().read("/home/sioowork/114/22.txt");
        List<MobileArea> mobileAreaList = mobileAreaService21.findList();
        Map<String, MobileArea> moMap = new HashMap<>();
        mobileAreaList.stream().forEach((mo -> {
            moMap.put(mo.getNumber(), mo);
        }));
        AtomicLong atomicLong = new AtomicLong(1032730329);
        LOGGER.info("===============START====================");
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> list.stream().parallel().forEach(s -> {
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
                    vo.setContent("【钱宝袋】恭喜您，钱宝袋为您授信5000信用额 度，微信关注：钱宝袋，即可申请取现。回T退订");
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
                    vo.setId(atomicLong.incrementAndGet());
                    sendHistoryService.insertHistory(vo);
                }
        )).get();
        LOGGER.info("============END=================");

    }

    public void ss()  {
        try {
            LOGGER.info("===========start=================");
            List<SendingVo> sendingVos = sendHistoryService.export();
            LOGGER.info("===========开始插入数据=================");
            CsvWriter csvWriter = new CsvWriter("/home/sioowork/114/0619.csv", ',', Charset.forName("GBK"));
            String[] titel = new String[3];
            titel[0] = "号码";
            titel[1] = "内容";
            titel[2] = "时间";
            csvWriter.writeRecord(titel);
            for (SendingVo sendingVo : sendingVos) {
                sendingVo.setContent("【钱宝袋】恭喜您，钱宝袋为您授信10000信用额 度，微信关注：钱宝袋，即可申请取现。回T退订");
                String[] contents = new String[3];
                contents[0] = String.valueOf(sendingVo.getMobile()).substring(0,7)+"****";
                contents[1] = sendingVo.getContent();
                contents[2] = String.valueOf(sendingVo.getSenddate());
                csvWriter.writeRecord(contents);
            }
            csvWriter.close();
            LOGGER.info("============END=================");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
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
        String date = "2018062209" + minstr + secstr;
        return date;
    }

}

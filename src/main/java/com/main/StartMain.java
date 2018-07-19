package com.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.pojo.SendingBigVo;
import com.pojo.SendingVo;
import com.service.RptService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);

    @Autowired
    private RptService rptService;


    @Override
    public void run(ApplicationArguments var1) throws Exception {
       // ssa();
    }

    /**
     * 更新前台没有签名的记录
     *
     * @throws Exception
     */
    public void ssa() throws Exception {
        List<Long> list = new ArrayList<>();
        for (long i = 589785737; i <= 594284104; i++) {
            list.add(i);
        }
        LOGGER.info("============start====================");
        final int[] k = {0};
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> list.stream().parallel().forEach(i -> {
                    List<SendingVo> sendingVos = rptService.findHistory114(i);
                    if (sendingVos.size() > 0) {
                        for (SendingVo sendingVo : sendingVos) {
                            if (!hasStore(sendingVo.getContent())) {
                                SendingVo vo = rptService.findSendHistory21(sendingVo.getHisid());
                                if(vo!=null){
                                    if (vo.getMobile().equals(sendingVo.getMobile())) {
                                        LOGGER.info("mobile:{},hisid:{},content:{}" ,sendingVo.getMobile(),sendingVo.getHisid(),sendingVo.getContent());
                                        sendingVo.setContent(vo.getContent());
                                        sendingVo.setContentNum(vo.getContentNum());
                                        rptService.updateHistory114(sendingVo);
                                        k[0]++;

                                    }
                                }
                            }
                        }
                    }
                }
        )).get();
        LOGGER.info("============end====================," + k[0]);
    }

    private boolean hasStore(String content) {
        return ((content.startsWith("【") && StringUtils.contains(content, "】")) || (content.endsWith("】") && StringUtils.contains(content, "【")));
    }

    public void ss() throws Exception {
        List<SendingVo> list = new ArrayList<>();
        File file = new File("/home/sioowork/middle-service-logs/data.log.2018-07-15");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String sms = null;
        while ((sms = bufferedReader.readLine()) != null) {
            sms = sms.trim();
            if (sms != null && !sms.equals("")) {
                String content = sms.substring(21);
                SendingBigVo vo = JSON.parseObject(content, new TypeReference<SendingBigVo>() {
                });
                if (vo.getMobile().contains("-")) {
                    String[] strings = vo.getMobile().split(",");
                    for (String t : strings) {
                        SendingVo v = new SendingVo();
                        String phone = StringUtils.substringAfter(t, "-");
                        String hisid = StringUtils.substringBefore(t, "-");
                        v.setMobile(Long.valueOf(phone));
                        v.setContent(vo.getContent());
                        v.setUid(vo.getUid());
                        v.setPid(vo.getPid());
                        v.setId(Integer.valueOf(hisid));
                        v.setSenddate(vo.getSenddate());
                        v.setChannel(vo.getChannel());
                        if (vo.getExpid() == null || vo.getExpid().equals("0")) {
                            v.setExpid(vo.getUid() + "");
                        } else {
                            v.setExpid(vo.getExpid());
                        }
                        v.setSource(vo.getSource());
                        v.setMtype(vo.getMtype());
                        list.add(v);
                    }
                } else {
                    SendingVo v = new SendingVo();
                    String phone = vo.getMobile();
                    v.setMobile(Long.valueOf(phone));
                    v.setContent(vo.getContent());
                    v.setUid(vo.getUid());
                    v.setPid(vo.getPid());
                    v.setId(vo.getId());
                    v.setSenddate(vo.getSenddate());
                    v.setChannel(vo.getChannel());
                    if (vo.getExpid() == null || vo.getExpid().equals("0")) {
                        v.setExpid(vo.getUid() + "");
                    } else {
                        v.setExpid(vo.getExpid());
                    }
                    v.setSource(vo.getSource());
                    v.setMtype(vo.getMtype());
                    list.add(v);
                }
            }
        }
        bufferedReader.close();
        LOGGER.info("===============start==============");
        ForkJoinPool myPool = new ForkJoinPool(8);
        myPool.submit(() -> list.stream().parallel().forEach(v -> {
            int count = rptService.findHistory(v.getId());
            if (count == 0) {
                LOGGER.info(JSON.toJSONString(v));
            }
        })).get();
        LOGGER.info("===============end==============");

    }
}

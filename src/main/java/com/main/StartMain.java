package com.main;

import com.pojo.SendingVo;
import com.service.AnjxService;
import com.service.SendHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);

    @Autowired
    private AnjxService anjxService;

    @Autowired
    private SendHistoryService sendHistoryService;

    @Override
    public void run(ApplicationArguments var1) throws Exception {
      /*  LOGGER.info("start");
        ss();
        LOGGER.info("end");*/

    }

    public void ss(){
        List<Integer> ids=new ArrayList<>();
        for(int id=270685;id<=8432543;id++){
            ids.add(id);
        }
        ids.stream().parallel().forEach(id->{
            SendingVo vo=new SendingVo();
            vo.setSenddate(0L);
            vo.setId(id);
            SendingVo v=anjxService.findOneHistory(vo);
            if(v!=null){
                v.setUid(20066);
                v.setEndtime(v.getRpttime());
                List<SendingVo> byConditon = sendHistoryService.findByConditon(v);
                if(byConditon!=null&&byConditon.size()>0){
                    vo.setSenddate(byConditon.get(0).getSenddate());
                    anjxService.updateHistory(vo);
                }else {
                    vo.setSenddate(1l);
                    anjxService.updateHistory(vo);
                }
            }
        });
    }


}

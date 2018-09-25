package com.main;

import com.service.AnjxService;
import com.service.SendHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

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



}

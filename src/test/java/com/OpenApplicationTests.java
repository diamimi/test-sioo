package com;

import com.dbbatch.DatabaseTransaction;
import com.dbbatch.DbService;
import com.pojo.SendingVo;
import com.service.RptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenApplicationTests {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenApplicationTests.class);

	@Autowired
	private RptService rptService;

	@Test
	public void contextLoads() {

		System.out.println("ssssss");
	}

	@Test
	public void file() throws Exception {
		File file = new File("d:/114.txt");
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String sms = null;
		List<String> hisids = new ArrayList<>();
		List<SendingVo> list = new ArrayList<>();
        while ((sms = bufferedReader.readLine()) != null) {
            if (sms != null && sms != "") {
                hisids.add(sms.trim());
            }
        }
        hisids.parallelStream().forEach(h->{
			SendingVo vo = null;
			try {
				vo = rptService.findOne(h);
			} catch (Exception e) {
				LOGGER.info(h+"");
			}
			if (vo != null) {
                list.add(vo);
            }
        });
		if (list.size() > 200) {
			int limitSize = 200;
			int part = list.size() / limitSize;
			for (int i = 0; i < part; i++) {
				List<SendingVo> subList = list.subList(0, limitSize);
				DatabaseTransaction trans = new DatabaseTransaction(false);
				try {
					DbService db = new DbService(trans);
					db.updateRpt(subList);
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage());
				} finally {
					trans.close();
				}
				list.subList(0, limitSize).clear();
			}
			if (!list.isEmpty()) {
				DatabaseTransaction trans = new DatabaseTransaction(false);
				try {
					DbService db = new DbService(trans);
					db.updateRpt(list);
				} catch (Exception ex) {
					LOGGER.error(ex.getMessage());
				} finally {
					trans.close();
				}
			}
		} else {
			DatabaseTransaction trans = new DatabaseTransaction(false);
			try {
				DbService db = new DbService(trans);
				db.updateRpt(list);
			} catch (Exception ex) {
				LOGGER.error(ex.getMessage());
			} finally {
				trans.close();
			}
		}
	}

}

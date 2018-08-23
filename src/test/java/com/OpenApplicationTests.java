package com;

import com.service.RptService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


}

package com.floodmonitor;

import com.floodMonitor.pojo.HistoryInfo;
import com.floodMonitor.mapper.HistoryInfoMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class JunitTest {

	
	@Test
	public void testMapper() throws Exception {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		HistoryInfoMapper mapper = ac.getBean(HistoryInfoMapper.class);
        HistoryInfo hi = mapper.findById(1);
        System.out.println(hi.getMonitoringValue());
	}
}

package com.floodmonitor.service.Impl;

import com.floodMonitor.pojo.HistoryInfo;
import com.floodMonitor.service.FloodMonitorMsg;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Created by zhuyao on 2018/05/17.
 */
public class floodMonitorMsgImplTest {



    @Test
    public void testMapper() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        FloodMonitorMsg floodMonitorMsg = ac.getBean(FloodMonitorMsg.class);
        HistoryInfo historyInfoById = floodMonitorMsg.findHistoryInfoById(1);
        System.out.println(historyInfoById.getMonitoringValue());
    }

}
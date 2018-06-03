package com.floodMonitor.service.Impl;

import com.floodMonitor.mapper.HistoryInfoMapper;
import com.floodMonitor.mapper.MonitorStatusMapper;
import com.floodMonitor.mapper.PointInfoMapper;
import com.floodMonitor.pojo.HistoryInfo;
import com.floodMonitor.pojo.MonitorStatus;
import com.floodMonitor.pojo.PointInfo;
import com.floodMonitor.service.FloodMonitorMsg;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhuyao on 2018/05/17.
 */
public class FloodMonitorMsgImpl implements FloodMonitorMsg {

    @Autowired
    private HistoryInfoMapper historyInfoMapper;
    @Autowired
    private MonitorStatusMapper monitorStatusMapper;
    @Autowired
    private PointInfoMapper pointInfoMapper;

    @Override
    public HistoryInfo findHistoryInfoById(Integer id) {
        return historyInfoMapper.findById(id);
    }

    @Override
    public MonitorStatus findMonitorStatusById(Integer id) {
        return monitorStatusMapper.findById(id);
    }

    @Override
    public PointInfo findPointInfoById(Integer id) {
        return pointInfoMapper.findById(1);
    }
}

package com.floodMonitor.service;

import com.floodMonitor.pojo.HistoryInfo;
import com.floodMonitor.pojo.MonitorStatus;
import com.floodMonitor.pojo.PointInfo;

/**
 * Created by zhuyao on 2018/05/17.
 */
public interface FloodMonitorMsg {
    HistoryInfo findHistoryInfoById(Integer id);
    MonitorStatus findMonitorStatusById(Integer id);
    PointInfo findPointInfoById(Integer id);
}

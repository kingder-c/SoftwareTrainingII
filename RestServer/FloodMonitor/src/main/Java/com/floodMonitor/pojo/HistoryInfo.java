package com.floodMonitor.pojo;

import java.util.Date;

/**
 * Created by zhuyao on 2018/05/10.
 */
public class HistoryInfo {
    private Integer id;
    private Integer monitoringValue;
    private Date updateTime;

    public HistoryInfo() {

    }

    public HistoryInfo(Integer id, int monitoringValue, Date updateTime) {
        this.id = id;
        this.monitoringValue = monitoringValue;
        this.updateTime = updateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonitoringValue() {
        return monitoringValue;
    }

    public void setMonitoringValue(int monitoringValue) {
        this.monitoringValue = monitoringValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

package com.floodMonitor.pojo;

import java.util.Date;

/**
 * Created by zhuyao on 2018/05/10.
 */
public class MonitorStatus {
    private Integer id;
    private Integer monitorValue;
    private Date updateTime;

    public MonitorStatus(Integer id, int monitorValue, Date updateTime) {
        this.id = id;
        this.monitorValue = monitorValue;
        this.updateTime = updateTime;
    }

    public MonitorStatus() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMonitorValue() {
        return monitorValue;
    }

    public void setMonitorValue(int monitorValue) {
        this.monitorValue = monitorValue;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

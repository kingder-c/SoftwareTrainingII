package com.floodMonitor.pojo;

/**
 * Created by zhuyao on 2018/05/10.
 */
public class PointInfo {
    private Integer id;
    private String monitorName;
    private Integer locationX;
    private Integer locationY;
    private String monitorType;

    public PointInfo() {

    }

    public PointInfo(Integer id, String monitorName, Integer locationX, Integer locationY, String monitorType) {
        this.id = id;
        this.monitorName = monitorName;
        this.locationX = locationX;
        this.locationY = locationY;
        this.monitorType = monitorType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public Integer getLocationX() {
        return locationX;
    }

    public void setLocationX(Integer locationX) {
        this.locationX = locationX;
    }

    public Integer getLocationY() {
        return locationY;
    }

    public void setLocationY(Integer locationY) {
        this.locationY = locationY;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }
}

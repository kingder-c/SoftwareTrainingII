package com.floodMonitor.controller;

import com.alibaba.fastjson.JSONObject;
import com.floodMonitor.pojo.HistoryInfo;
import com.floodMonitor.pojo.MonitorStatus;
import com.floodMonitor.pojo.PointInfo;
import com.floodMonitor.service.FloodMonitorMsg;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.awt.*;

/**
 * Created by zhuyao onf 2018/05/17.
 */
@Path("FloodMonitor")
public class FloodMonitorController {

    @Autowired
    private FloodMonitorMsg floodMonitorMsg;

    @GET
    @Path("/getHistoryInfo")
    @Produces("application/json")
    public String getHistoryInfo(@Context HttpServletRequest request) {
        String id = request.getParameter("id");
        HistoryInfo historyInfo = floodMonitorMsg.findHistoryInfoById(Integer.valueOf(id));
        JSONObject msgObj = new JSONObject();
        msgObj.put("id",historyInfo.getId());
        msgObj.put("updateTime",historyInfo.getUpdateTime());
        msgObj.put("monitoringValue",historyInfo.getMonitoringValue());
        return msgObj.toJSONString();
    }
    @GET
    @Path("/getPointInfo")
    @Produces("application/json")
    public String getPointInfo(@Context HttpServletRequest request) {
        String type = request.getParameter("type");
        PointInfo pointInfo = floodMonitorMsg.findPointInfoByMonitorType(type);
        JSONObject msgObj = new JSONObject();
        msgObj.put("id",pointInfo.getId());
        msgObj.put("locationX",pointInfo.getLocationX());
        msgObj.put("locationY",pointInfo.getLocationY());
        msgObj.put("monitorName",pointInfo.getMonitorName());
        msgObj.put("monitortype",pointInfo.getMonitorType());
        return msgObj.toJSONString();
    }
    @GET
    @Path("/getMonitorValue")
    @Produces("application/json")
    public String getMonitorValue(@Context HttpServletRequest request) {
        String id = request.getParameter("id");
        MonitorStatus monitorStatus = floodMonitorMsg.findMonitorStatusById(Integer.valueOf(id));
        JSONObject msgObj = new JSONObject();
        msgObj.put("id",monitorStatus.getId());
        msgObj.put("updateTime",monitorStatus.getUpdateTime());
        msgObj.put("monitoringValue",monitorStatus.getMonitorValue());
        return msgObj.toJSONString();
    }
}

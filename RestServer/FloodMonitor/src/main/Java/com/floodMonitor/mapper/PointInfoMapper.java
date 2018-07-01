package com.floodMonitor.mapper;


import com.floodMonitor.pojo.PointInfo;

public interface PointInfoMapper {

    PointInfo findById(Integer id);
    PointInfo findByTpye(String monitorType);

}

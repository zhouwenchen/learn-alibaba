package com.pig4cloud.alibaba.gateway.mapper;

import com.pig4cloud.alibaba.gateway.bean.GateWayEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MayiktGatewayMapper {
    @Select("SELECT ID AS ID, route_id as routeid, route_name as routeName,route_pattern as routePattern\n" +
            ",route_type as routeType,route_url as routeUrl\n" +
            " FROM mayikt_gateway\n")
    public List<GateWayEntity> gateWayAll();

    @Update("update mayikt_gateway set route_url=#{routeUrl} where route_id=#{routeId};")
    public Integer updateGateWay(@Param("routeId") String routeId, @Param("routeUrl") String routeUrl);

}

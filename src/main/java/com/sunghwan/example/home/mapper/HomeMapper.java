package com.sunghwan.example.home.mapper;

import com.sunghwan.example.home.domain.Home;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface HomeMapper {
    //@Select("SELECT * FROM home WHERE name = #{name}")

    public Home readHome(String name);
}

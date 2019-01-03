package com.springboot.wubilan.service;

import com.springboot.wubilan.entity.Userinfo;

import java.util.List;

public interface UserInfoService {
    int deleteByPrimaryKey(Long id);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
    List<Userinfo> selectAllPage(Userinfo userinfo);

}

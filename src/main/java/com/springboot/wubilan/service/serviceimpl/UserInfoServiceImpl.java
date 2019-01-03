package com.springboot.wubilan.service.serviceimpl;

import com.springboot.wubilan.dao.UserinfoMapper;
import com.springboot.wubilan.entity.Userinfo;
import com.springboot.wubilan.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Override
    public int deleteByPrimaryKey(Long id) {
        return  userinfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Userinfo record) {
        return userinfoMapper.insert(record);
    }

    @Override
    public int insertSelective(Userinfo record) {
        return userinfoMapper.insertSelective(record);
    }

    @Override
    public Userinfo selectByPrimaryKey(Long id) {
        return userinfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Userinfo record) {
        return userinfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Userinfo record) {
        return userinfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Userinfo> selectAllPage(Userinfo userinfo) {
        return userinfoMapper.selectAllPage(userinfo);
    }
}

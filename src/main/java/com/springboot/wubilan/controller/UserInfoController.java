package com.springboot.wubilan.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.wubilan.SignUtil;
import com.springboot.wubilan.entity.Userinfo;
import com.springboot.wubilan.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户id查询该用户
     * @param id
     * @return
     */
    @GetMapping("/seclectOne")
    public Userinfo getUserInfo(@RequestParam Long  id){
      return   userInfoService.selectByPrimaryKey(id);
    }

    /**
     * 分页条件查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/selectAllPage")
    public PageInfo<Userinfo> getUserInfoPage(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        Userinfo userinfo=new Userinfo();
//        userinfo.setUserName("吴碧兰");
        userinfo.setPassword("123456");
        PageHelper.startPage(pageNum,pageSize);
        List<Userinfo> userinfos=userInfoService.selectAllPage(userinfo);
        PageInfo<Userinfo> pageInfo=new PageInfo<>(userinfos);
        return pageInfo;

    }

    @PostMapping("/test")
    public Map<String, String> test(@RequestBody Map data){
        Map<String, String> map = new HashMap<>();
        Map<String, String> mapSigature = new HashMap<>();
        mapSigature.put("userId", data.get("userId").toString());
        //根据用户id（userId）获取加密签名key（sign）
        String key = "a4e00d7c7394097c182d4b6d7b0638df";//这是定时任务配置文件中的scheduler.sign.key:
        try {
            String sign = SignUtil.getSigature(key, mapSigature);
            //根据生成的签名sign与请求获取的的签名sign是否相等，来判断签名是否成功
            if (!sign.equals(data.get("sign"))) {
                map.put("code", "FAIL");
                map.put("message", "签名错误，调用目标地址失败！");
//                logger.info("签名错误，调用目标地址失败！");
            } else {
                //TODO(调用该定时任务，实现自己的业务逻辑)
                map.put("code", "SUCCESS");
                map.put("message", "调用了目标地址成功！");
//                logger.info("调用目标地址成功！");
            }
        } catch (Exception e) {
            map.put("code", "FAIL");
            map.put("message", "系统异常，调用目标地址失败！");
        }
        return map;
    }
}

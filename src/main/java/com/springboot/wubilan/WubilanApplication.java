package com.springboot.wubilan;

import com.springboot.wubilan.entity.User;
import com.springboot.wubilan.entity.Userinfo;
import com.springboot.wubilan.util.JsonList;
import com.springboot.wubilan.util.JsonResultUtil;
import com.springboot.wubilan.util.ResultEunm;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

@SpringBootApplication
@MapperScan(basePackages = {"com.springboot.wubilan.dao"})
public class WubilanApplication {

    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(WubilanApplication.class, args);
        JsonList<User> jsonList=new JsonList<>();
//        jsonList.setCode(ResultEunm.FAIL);
        jsonList.setMessage("sssssssssssss");
        System.out.println(Userinfo.class.isAnnotationPresent(Override.class));
        Class<?> userInfoClass=Userinfo.class;
        Method[] methods=userInfoClass.getMethods();
        for (Method method : methods) {
            System.out.println("method----start1111111111111111111");
            System.out.println(method);
            System.out.println(method.getReturnType().getName());
            Class[] parameterizedTypes=method.getParameterTypes();
            for(Class class1 : parameterizedTypes){
                System.out.println("class1.getName()---------------");
                System.out.println(class1.getName());
            }
            System.out.println("method----end222222222222222222222");
        }
        System.out.println();
        System.out.println();
        System.out.println();
        Field[] fields=userInfoClass.getFields();
        System.out.println(fields.length);
        for(Field field : fields){
            System.out.println("field---start333333333333333333");
            System.out.println(field);
            System.out.println("field---end4444444444444444444");
            System.out.println(field.getName());
        }

        Class<?> userInfo=Class.forName("com.springboot.wubilan.entity.Userinfo");
    }
}

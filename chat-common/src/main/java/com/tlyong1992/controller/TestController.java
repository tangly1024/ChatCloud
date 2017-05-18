package com.tlyong1992.controller;

import com.tlyong1992.common.annotation.Pro;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger 控制器案例模版
 * @author yesh
 *         (M.M)!
 *         Created by 2017/5/7.
 */
@Api(description = "测试用例控制器")
@Pro
@RestController
@RequestMapping("/swagger")
public class TestController {

    @ApiOperation(value = "TEST接口简介",notes = "接口详细描述")
    @ApiImplicitParam(name = "user",dataType = "User")
    @ResponseBody
    @RequestMapping(value = "helloTest" ,method = RequestMethod.POST)
    public String hello(@RequestBody User user){
        return "Hello Swagger : my name is "+user.getName();
    }
}
class  User {
    private int id;
    private int name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
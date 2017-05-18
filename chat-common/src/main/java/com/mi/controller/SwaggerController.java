package com.tlyong1992.controller;

import com.tlyong1992.common.annotation.Dev;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * Swagger 控制器案例模版
 * @author yesh
 *         (M.M)!
 *         Created by 2017/5/7.
 */
@Api(description = "Swagger模版控制器")
@Dev
@RestController
@RequestMapping("/swagger")
public class SwaggerController {
    private static final Logger log = Logger.getLogger(SwaggerController.class);

    @ApiOperation(value = "接口简介",notes = "接口详细描述")
    @ApiImplicitParam(name = "name",required = true,paramType = "query" ,dataType = "String")
    @ResponseBody
    @RequestMapping(value = "hello" ,method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name",required = false) String name){
        return "Hello Swagger : my name is "+ name;
    }

}

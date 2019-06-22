package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckgroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {


    @Reference
    CheckgroupService checkgroupService;

    /**
     * 添加检查组
     * 注: 在添加时应注意在serviceImpl业务层将检查组记录和检查组与检查项的关系表分开处理
     *     处理两个表关系时 定义相应方法并写对应数据处理 dao  方法
     *     并且在业务层应在相应的添加检查组和 表关系 传相应的参数 将接口参数分别传入两个方法中
     * @param checkitemIds
     * @param checkGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(Integer[] checkitemIds,@RequestBody CheckGroup checkGroup){

        System.out.println("checkGroup"+checkGroup);
        System.out.println("checkitemIds"+Arrays.toString(checkitemIds));

        try {
            checkgroupService.add(checkGroup,checkitemIds);

            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);

    }

    /**
     * PageResult  返回值是一个封装了 total和rows的Javabean
     *
     * @param queryPageBean  用封装了分页查询的bean类接受前端数据
     * @return
     */
    @RequestMapping("/findByPage")
     public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        System.out.println("查询条件: "+queryPageBean);

            return  checkgroupService.findByPage(queryPageBean);

    }

    /**
     * 根据id 来查询检查组的所有数据
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){

        try {
          CheckboxGroup checkboxGroup= checkgroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkboxGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    /**
     * 根据 id 来查询检查组包含的所有项 id 用于 页面回显
     * @param id
     * @return
     */
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer  id){
        try {
            List<Integer> checkitemIds=   checkgroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkitemIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
    }
}

package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    CheckItemService checkItemService;


    /**
     * 查询所有的检查项
     * @return
     */
    @RequestMapping("/findAll")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//使用注解开启权限控制
    public Result findAll(){
        try {
           List<CheckItem> checkItems= checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItems);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }


    /**
     * 添加检查组
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem){


        try {
            checkItemService.add(checkItem);
            return  new Result(true,MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.ADD_CHECKITEM_FAIL);

    }


    /**
     * 分页查询数据
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findByPage")
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//使用注解开启权限控制
    public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){
        try {
            PageResult pageResult = checkItemService.findByPage(queryPageBean.getCurrentPage(),
                    queryPageBean.getPageSize(),queryPageBean.getQueryString());
            System.out.println(queryPageBean);
            return  pageResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}

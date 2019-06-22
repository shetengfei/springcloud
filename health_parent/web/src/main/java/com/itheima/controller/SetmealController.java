package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiniuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/12
 * @description ：
 * @version: 1.0
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {


    @Reference
    SetmealService setmealService;
    @RequestMapping("/findByPage")
        public PageResult findByPage(@RequestBody QueryPageBean queryPageBean){

        try {
           PageResult pageResult =setmealService.findByPage(queryPageBean);
            return pageResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 添加检查套餐
     * @param checkgroupIds
     * @param setmeal
     * @return
     */
    @RequestMapping("/add")
    public Result add(Integer[]checkgroupIds,@RequestBody Setmeal setmeal){
        System.out.println("添加时前端获取的数据checkgroupIds:" + Arrays.toString(checkgroupIds));
        System.out.println("添加时前端获取的数据setmeal"+setmeal);

        try {
            setmealService.add(checkgroupIds,setmeal);

            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new Result(false,MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    /**
     * 查询所有的检查组 用于显示在新建窗口上
     * @return
     */
    @RequestMapping("/findAll")
    public Result findAll(){

        try {
            //查询所有检查组记录用List<Checkgroup> 集合接收
         List<CheckGroup> checkGroups= setmealService.findAll();
         return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroups);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    /**
     * 添加窗口上传图片
     * @param imgFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile imgFile){  //前后端 imgFile参数名必须一致
                                                                //否则报 java.lang.NullPointerException  空指针异常
        System.out.println("前端获取的图片名imgfile :  "+imgFile);

        try {
            //生成唯一的文件名称
            String uuid = UUID.randomUUID().toString().replace("_", "");
            //获取文件的扩展名
            String ginalFilename = imgFile.getOriginalFilename();

            String extendname=ginalFilename.substring(ginalFilename.lastIndexOf("."));
            //拼接唯一文件名
            String fileName=uuid+extendname;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
    }
}

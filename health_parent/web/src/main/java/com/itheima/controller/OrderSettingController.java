package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/13
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    OrderSettingService orderSettingService;

    /**
     * 批量上传预约设置信息
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile excelFile) {
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);

            //将String[]转换为OrderSetting对象,并放入List集合

            List<OrderSetting> orderSettingList = new ArrayList<>();

            if (list != null && list.size() > 0) {
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting();
                    //预约日期
                    orderSetting.setOrderDate(new SimpleDateFormat("yyyy/MM/dd").parse(strings[0]));
                    orderSetting.setNumber(Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
            }

            //System.out.println("web层 orderSettingList: " + orderSettingList);

            orderSettingService.addOrderSettingList(orderSettingList);

            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (RuntimeException run) {
            run.printStackTrace();
            return new Result(false, run.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * 根据年月来查询本月预约设置
     * @param date
     * @return
     */
    @RequestMapping("/findByDate")
    public Result findByDate(String date) {

        //System.out.println("web层 接收到的date: " + date);

        try {
            List<Map<String,Object>> list = orderSettingService.findByDate(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     * 单个设置预约
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editordersettingBymonth")
    public Result editordersettingBymonth(@RequestBody OrderSetting orderSetting){

        //System.out.println("web层 orderSetting: " + orderSetting);

        try {
            //接收前端传回的数据
            List<OrderSetting> orderSettingList=new ArrayList<>();
            orderSettingList.add(orderSetting);
            orderSettingService.addOrderSettingList(orderSettingList);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (RuntimeException e) {
            e.printStackTrace();
            e.getMessage();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);


    }
}

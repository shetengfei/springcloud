package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/19
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    public static void main(String[] args) {
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        //1.1添加当前日前的第前12月
        calendar.add(Calendar.MONTH, -12);
        // System.out.println(calendar.get(Calendar.YEAR));
        //System.out.println(calendar.get(Calendar.MONTH)+1);

        //2.1获取十二个月份
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            months.add(dateFormat.format(calendar.getTime()));

        }
        System.out.println(months);
    }

    @Reference
    ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        //定义一个Map集合存放monthlist和查询到每个月的用户数
        Map<String, Object> map = new HashMap<>();
        //定义一个Arraylist用于存储y轴上将要展示的月份
        List<String> months = new ArrayList<>();
        //1.获取日历
        Calendar calendar = Calendar.getInstance();
        //1.1添加当前日前的第前12月
        calendar.add(Calendar.MONTH, -12);
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH) + 1);

        //2.1获取十二个月份
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            //System.out.println(calendar.get(Calendar.MONTH));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

            months.add(dateFormat.format(calendar.getTime()));
        }
        map.put("months", months);
        //2.2 按每个月份获取客户注册数量(count 1)查询
        List<Integer> memberCount = reportService.findUserCountBymonths(months);
        map.put("memberCount", memberCount);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }


    /**
     * 套餐占比 查询不需要参数
     * 将要返回给前端的数据   setmealNames  所有套餐的名字放在一个集合中
     * setmealCount  每个套餐被预约的数量
     * 要展示的数据格式:
     * "data":{
     * "setmealNames":["套餐1","套餐2","套餐3"],
     * "setmealCount":[
     * {"name":"套餐1","value":10},
     * {"name":"套餐2","value":30},
     * {"name":"套餐3","value":25}
     * ]
     * }
     *
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {

        try {//1.定义一个map集合用于存放 setmealNames,setmealCount作为键存放数据
            //用于前端获取数据展示
            Map<String, Object> map = new HashMap<>();

            //2. 定义一个list用于存放setmealNames 套餐名字
            List<String> setmealNames = new ArrayList<>();

            //3.定义一个list存放每个  前端对象数据
            List<Map<String, String>> setmealCount = reportService.getSetmealCount();


            //4. 遍历map,将  套餐名取出放入一个list集合中
            for (Map<String, String> mealname : setmealCount) {
                String name = mealname.get("name");
                setmealNames.add(name);
            }

            //5. 将查询的  setmealNames,setmealCount 放入map集合
            map.put("setmealNames", setmealNames);
            map.put("setmealCount", setmealCount);

            //6.将数据返回前端
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);

    }

    /**
     * 运营数据统计
     * <p>
     * 前端需要的数据格式   对象{对象{key:value,
     * ...,
     * *                   数组:[对象1{},对象2{}
     * ],
     * }}
     * data:{
     * reportData:{
     * reportDate:null,
     * todayNewMember :0,
     * totalMember :0,
     * thisWeekNewMember :0,
     * thisMonthNewMember :0,
     * todayOrderNumber :0,
     * todayVisitsNumber :0,
     * thisWeekOrderNumber :0,
     * thisWeekVisitsNumber :0,
     * thisMonthOrderNumber :0,
     * thisMonthVisitsNumber :0,
     * hotSetmeal :[
     * {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
     * {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
     * ]
     * }}
     *
     * @return
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {

        try {
            //定义一个List<Map<String,Object>> 用于存放将要返回前端的数据
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
    }

    /**
     * excl 表数据的下载
     *
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws IOException {


        try {
            //1. 获取运营数据
            Map<String, Object> businessReportData = reportService.getBusinessReportData();
            //2. 获取excel模板对象
            //获取模板的真实路径
            String excelPath = request.getSession().getServletContext().getRealPath("/template")+ File.separator+"report_template.xlsx";
            //获取模板excle的流对象
            FileInputStream inputStream = new FileInputStream(new File(excelPath));
            //创建excel模板的工作薄对象
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            //3. 把运营数据存储到excel指定的位置
            //获取第一个工作表
            XSSFSheet sheet = workbook.getSheet("Sheet1");
            //获取工作表的某一行
            XSSFRow row = sheet.getRow(2);
            //获取某单元格
            XSSFCell cell = row.getCell(5);
            //获取导出运营数据的日期
            Object reportDate = businessReportData.get("reportDate");
            //给单元格赋值
            cell.setCellValue(String.valueOf(reportDate));

            //获取工作表的某一行
            row = sheet.getRow(4);
            //获取某单元格
            cell = row.getCell(5);
            //获取今日新增会员
            Object todayNewMember = businessReportData.get("todayNewMember");
            //给单元格赋值
            cell.setCellValue(String.valueOf(todayNewMember));

            //获取工作表的某一行
            row = sheet.getRow(4);
            //获取某单元格
            cell = row.getCell(7);
            //获取总会员数
            Object totalMember = businessReportData.get("totalMember");
            //给单元格赋值
            cell.setCellValue(String.valueOf(totalMember));

            //获取工作表的某一行
            row = sheet.getRow(5);
            //获取某单元格
            cell = row.getCell(5);
            //获取本周新增会员
            Object thisWeekNewMember = businessReportData.get("thisWeekNewMember");
            //给单元格赋值
            cell.setCellValue(String.valueOf(thisWeekNewMember));

            //获取工作表的某一行
            row = sheet.getRow(5);
            //获取某单元格
            cell = row.getCell(7);
            //获取本月新增会员数
            Object thisMonthNewMember = businessReportData.get("thisMonthNewMember");
            //给单元格赋值
            cell.setCellValue(String.valueOf(thisMonthNewMember));

            //获取工作表的某一行
            row = sheet.getRow(7);
            //获取某单元格
            cell = row.getCell(5);
            //获取今日预约数
            Object todayOrderNumber = businessReportData.get("todayOrderNumber");
            //给单元格赋值
            cell.setCellValue(String.valueOf(todayOrderNumber));

            //获取工作表的某一行
            row = sheet.getRow(7);
            //获取某单元格
            cell = row.getCell(7);
            //获取今日到诊数
            Object todayVisitsNumber = businessReportData.get("todayVisitsNumber");
            //给单元格赋值
            cell.setCellValue(String.valueOf(todayVisitsNumber));

            //获取工作表的某一行
            row = sheet.getRow(8);
            //获取某单元格
            cell = row.getCell(5);
            //获取本周预约数
            Object thisWeekOrderNumber = businessReportData.get("thisWeekOrderNumber");
            //给单元格赋值
            cell.setCellValue(String.valueOf(thisWeekOrderNumber));

            //获取工作表的某一行
            row = sheet.getRow(8);
            //获取某单元格
            cell = row.getCell(7);
            //获取本周到诊数
            Object thisWeekVisitsNumber = businessReportData.get("thisWeekVisitsNumber");
            //给单元格赋值
            cell.setCellValue(String.valueOf(thisWeekVisitsNumber));

            //获取工作表的某一行
            row = sheet.getRow(9);
            //获取某单元格
            cell = row.getCell(5);
            //获取本月预约数
            Object thisMonthOrderNumber = businessReportData.get("thisMonthOrderNumber");
            //给单元格赋值
            cell.setCellValue(String.valueOf(thisMonthOrderNumber));

            //获取工作表的某一行
            row = sheet.getRow(9);
            //获取某单元格
            cell = row.getCell(7);
            //获取本月到诊数
            Object thisMonthVisitsNumber = businessReportData.get("thisMonthVisitsNumber");
            //给单元格赋值
            cell.setCellValue(String.valueOf(thisMonthVisitsNumber));


            //给热门套餐导出数据
            //获取热门套餐
            List<Map<String,Object>> hotSemeal = (List<Map<String, Object>>) businessReportData.get("hotSetmeal");
            int rowNum = 12;
            for (Map<String, Object> setmeal : hotSemeal) {
                row = sheet.getRow(rowNum);
                //获取套餐名称的单元格
                cell = row.getCell(4);
                cell.setCellValue(String.valueOf(setmeal.get("name")));
                //获取套餐名称的单元格
                cell = row.getCell(5);
                cell.setCellValue(String.valueOf(setmeal.get("setmeal_count")));
                //获取套餐名称的单元格
                cell = row.getCell(6);
                cell.setCellValue(String.valueOf(setmeal.get("proportion")));
                cell = row.getCell(7);
                cell.setCellValue(String.valueOf(setmeal.get("remark")));
                //行号 +1
                rowNum ++ ;
            }
            //4. 响应用户
            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            //响应的类型为excel
            response.setContentType("application/vnd.ms-excel");
            //attachment --- 作为附件下载
            //filename -- 指定文件名
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            out.flush();
            out.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
        return  null;
}
}

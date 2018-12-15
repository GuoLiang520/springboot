package com.luo.util;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class JxlUtil {

    private static Logger logger = LoggerFactory.getLogger(JxlUtil.class) ;

    public static void  export(HttpServletResponse response, List<Map<String,Object>> list, String fileName){
        WritableWorkbook workbook = null;
        OutputStream os =null;
        try {
            os = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("gb2312"), "iso8859-1")+".xls");//fileName为下载时用户看到的文件名利用jxl 将数据从后台导出为excel
            response.setHeader("Content-Type", "application/msexcel");
            workbook = Workbook.createWorkbook(os);

            Sheet sheet1 = workbook.createSheet(fileName, 0);

            //设置样式
            WritableFont wf_title = new WritableFont(WritableFont.createFont("宋体"),25);
            WritableCellFormat wcf_title = new WritableCellFormat(wf_title);
            wcf_title.setAlignment(Alignment.CENTRE);
            WritableFont wf_head = new WritableFont(WritableFont.createFont("宋体"),15);
            WritableCellFormat wcf_head = new WritableCellFormat(wf_head);
            wcf_head.setAlignment(Alignment.LEFT);
            WritableFont wf_body = new WritableFont(WritableFont.createFont("宋体"),10);
            WritableCellFormat wcf_body = new WritableCellFormat(wf_body);
            wcf_body.setAlignment(Alignment.CENTRE);

            //设置列宽
            ((WritableSheet) sheet1).setColumnView(0, 15);
            ((WritableSheet) sheet1).setColumnView(1, 25);
            ((WritableSheet) sheet1).setColumnView(2, 35);
            //设置标题
            //合并单元格 开始 （1.列 2.行）接受 （3.列 4. 行）
            ((WritableSheet) sheet1).mergeCells(0, 0, 2, 0);
            ((WritableSheet) sheet1).addCell(new Label(0, 0, fileName, wcf_title));
            //设置表头
            ((WritableSheet) sheet1).addCell(new Label(0, 1, "姓名", wcf_head));
            ((WritableSheet) sheet1).addCell(new Label(1, 1, "年龄", wcf_head));
            ((WritableSheet) sheet1).addCell(new Label(2, 1, "密码", wcf_head));

            //表格数据
            int rowNum = 2;
            for(Map<String,Object> map : list){
                ((WritableSheet) sheet1).addCell(new Label(0, rowNum, StringUtil.trim(map.get("name")), wcf_body));
                ((WritableSheet) sheet1).addCell(new Label(1, rowNum, StringUtil.trim(map.get("age")), wcf_body));
                ((WritableSheet) sheet1).addCell(new Label(2, rowNum, StringUtil.trim(map.get("password")), wcf_body));
                rowNum++;
            }
            workbook.write();
        }catch (Exception e){
            logger.error("Jxl错误", e);
        }finally {
            if(workbook != null){
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

package com.luo.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private static String EXCEL2003 = "xls";
    private static String EXCEL2007= "xlsx";

    /**
     * 设置response头，和文件名称
     * @param response 响应头
     * @param fileName 文件名称
     * @param fileType 文件类型
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName,String fileType){
        response.setContentType("application/octet-stream;charset=utf-8");
        try {
            response.setHeader("content-disposition","attachment;filename="+new String(fileName.getBytes("gb2312"),"ISO8859-1")+"."+fileType);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Cache-Control", "no-cache");
    }

    /**
     * 获取workbook类型
     * @param fileType Excel文件类型
     * @return workbook实例
     */
    public static Workbook getWorkbook(String fileType){
        if(EXCEL2003.equals(fileType)){
            return  new HSSFWorkbook();
        }else if(EXCEL2007.equals(fileType)) {
            return  new XSSFWorkbook();
        }
        return  new HSSFWorkbook();
    }

    /**
     * 居中
     * @param workbook workb
     * @return cell样式
     */
    public static CellStyle getCenter(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    /**
     * 居左
     * @param workbook workb
     * @return cell样式
     */
    public static CellStyle getLeft(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
        return cellStyle;
    }

    /**
     * 居右
     * @param workbook workb
     * @return cell样式
     */
    public static CellStyle getRight(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        return cellStyle;
    }

    /**
     * 字体样式
     * @param workbook workbook
     * @param fontName 字体名称
     * @param fontSize 字体大小
     * @return 字体样式
     */
    public static Font fontStyle(Workbook workbook,String fontName,short fontSize){
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints(fontSize);
        return font;
    }

    /**
     * 设置表头
     * @param sheet sheet
     * @param rowsNum 行数
     * @param style 样式
     * @param heads 表头数组
     */
    public static int setHead(Sheet sheet,int rowsNum,CellStyle style,String[] heads){
        if(heads.length > 0) {
            Row row;
            Cell cell;
            int cellsNum = 0;//列数
            row = sheet.createRow(rowsNum++);
            for (String head : heads) {
                cell = row.createCell(cellsNum++);
                cell.setCellValue(head);
                cell.setCellStyle(style);
            }
        }
        return  rowsNum;
    }

    /**
     * 设置行宽
     * @param sheet sheet
     * @param widths 行宽数组
     */
    public static void setWidth(Sheet sheet,int[] widths){
        if(widths.length > 0) {
            int cellsNum = 0;//列数
            for (int width : widths) {
                sheet.setColumnWidth(cellsNum, 256*width+184);
                cellsNum++;
            }
        }
    }

    /**
     * 生成workbook
     * @param response 响应头
     * @param list  数据源
     * @param fileName  文件名称
     * @param fileType 文件格式
     * @param heads 表头
     * @param parms 字段名称
     * @param cellWidth 列宽
     * @return workboot实例
     */
    public static Workbook genbody(HttpServletResponse response, List<Map<String,Object>> list,String fileName,String fileType,String[] heads,String[] parms,int[] cellWidth){
        setResponseHeader(response,fileName,fileType);
        Workbook workbook = getWorkbook(fileType);
        Sheet sheet = workbook.createSheet("sheet1");
        //行数
        int rowsNum = 0;
        int cellsNum;//列数
        //标题行
        //合并第一行
        //参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列
        CellRangeAddress regions = new CellRangeAddress(0,0,0,heads.length-1);
        sheet.addMergedRegion(regions);
        Row titleRow = sheet.createRow(rowsNum++);
        Cell titleCell = titleRow.createCell(0);
        CellStyle titleStyle = getCenter(workbook);
        titleCell.setCellValue(fileName);
        titleStyle.setFont(fontStyle(workbook,"宋体",(short) 25));
        titleCell.setCellStyle(titleStyle);
        //设置列宽
        setWidth(sheet,cellWidth);
        //设置头
        CellStyle headStyle = getCenter(workbook);
        headStyle.setFont(fontStyle(workbook,"宋体",(short) 15));
        rowsNum = setHead(sheet,rowsNum,headStyle,heads);
        //设置表格体
        Row row;
        Cell cell;
        CellStyle bodyStyle = getCenter(workbook);
        for(Map<String,Object> map : list){
            row = sheet.createRow(rowsNum++);
            cellsNum = 0;
            for (String parm : parms){
                String val = StringUtil.trim (map.get(parm));
                cell = row.createCell(cellsNum++);
                cell.setCellValue(val);
                cell.setCellStyle(bodyStyle);
            }
        }
        return workbook;
    }

}

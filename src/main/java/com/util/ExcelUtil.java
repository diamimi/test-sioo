package com.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.Collection;

/**
 * @Author: HeQi
 * @Date:Create in 11:08 2018/8/2
 */
public class ExcelUtil {

    public static class FilePrintUtilHolder {
        public static ExcelUtil filePrintUtil = new ExcelUtil();
    }

    public static ExcelUtil getInstance() {
        return FilePrintUtilHolder.filePrintUtil;
    }

    public String getCellValue(Row row, int colomn) {
        String cell_value = row.getCell(colomn).toString();
        if (StringUtils.endsWith(cell_value, ".0")) {
            cell_value = StringUtils.remove(cell_value, ".0");
        }
        return cell_value;

    }

    public Sheet getSheet(String filename,int index){
        File xlsFile = new File(filename);
        Workbook workbook = null;
        try {
            InputStream is = new FileInputStream(xlsFile);
            workbook = WorkbookFactory.create(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        Sheet sheet = workbook.getSheetAt(index);  //示意访问sheet
        return sheet;
    }

    public void export(Collection<String> c, String filename) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet");
        int i = 0;
        for (String line : c) {
            HSSFRow row = sheet.createRow(i);
            String[] split = line.split("#庚柴喻@");
            int k = 0;
            for (String s : split) {
                HSSFCell cell = row.createCell(k);
                cell.setCellValue(s);
                k++;
            }
            i++;
        }
        try {
            FileOutputStream output = new FileOutputStream(filename);
            workbook.write(output);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileOutputStream;
import java.io.IOException;
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

    public void export(Collection<String> c, String filename) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet");
        int i = 0;
        for (String line : c) {
            HSSFRow row = sheet.createRow(i);
            String[] split = line.split("#@#");
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

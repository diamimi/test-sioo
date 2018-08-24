package com.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:08 2018/8/2
 */
public class ExcelReadUtil {

    public static class FilePrintUtilHolder {
        public static ExcelReadUtil filePrintUtil = new ExcelReadUtil();
    }

    public static ExcelReadUtil getInstance() {
        return FilePrintUtilHolder.filePrintUtil;
    }

    public List<String> getExcel(String filename) {
        try {
            File xlsFile = new File(filename);
            InputStream is = new FileInputStream(xlsFile);
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
            List<String> contents = new ArrayList<>();
            Row row0 = sheet.getRow(0);
            int size = row0.getPhysicalNumberOfCells();
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row != null) {
                    String line = "";
                    for (int i = 0; i < size; i++) {
                        String cell_value = row.getCell(i).toString();
                        if (StringUtils.endsWith(cell_value, ".0")) {
                            cell_value = StringUtils.remove(cell_value, ".0") + "##";
                        } else {
                            cell_value = cell_value + "##";
                        }
                        line += cell_value;
                    }
                    contents.add(line);
                }
            }
            return contents;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCellValue(Row row,int colomn) {
        String cell_value = row.getCell(colomn).toString();
        if (StringUtils.endsWith(cell_value, ".0")) {
            cell_value = StringUtils.remove(cell_value, ".0");

        }
        return cell_value;

    }
}

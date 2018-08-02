package com.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:08 2018/8/2
 */
public class FilePrintUtil {

    public static class FilePrintUtilHolder {
        public static FilePrintUtil filePrintUtil = new FilePrintUtil();
    }

    public static FilePrintUtil getInstance() {
        return FilePrintUtilHolder.filePrintUtil;
    }

    public void write(String filename, List<String> list) {
        try {
            PrintWriter out =new PrintWriter(filename);
            for (String t : list) {
                out.write(t.toString()+"\r\n");
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}

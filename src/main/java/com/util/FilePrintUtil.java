package com.util;

import java.io.*;
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

    public void write(String filename, List<String> list,String code) {
        try {
            File f=new File(filename);
            f.delete();
            PrintWriter out = null;
            OutputStream os = new FileOutputStream(filename);
            try {
                out = new PrintWriter(new OutputStreamWriter(os, code));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            for (String t : list) {
                out.write(t.toString() + "\r\n");
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void writeLine(PrintWriter out, String line) {
        try {
                out.write(line + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 11:08 2018/8/2
 */
public class FileRead {

    public static class FilePrintUtilHolder {
        public static FileRead filePrintUtil = new FileRead();
    }

    public static FileRead getInstance() {
        return FilePrintUtilHolder.filePrintUtil;
    }

    public List<String> read(String filename) {
        List<String> list = new ArrayList<>();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(filename), "GBK");// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String sms = null;
            while ((sms = bufferedReader.readLine()) != null) {
                list.add(sms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

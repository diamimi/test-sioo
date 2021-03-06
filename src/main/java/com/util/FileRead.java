package com.util;

import org.apache.commons.lang.StringUtils;

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

    public List<String> read(String filename,String code) {
        List<String> list = new ArrayList<>();
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(filename), code);// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String sms = null;
            while ((sms = bufferedReader.readLine()) != null) {
                if(!sms.equals("")){
                    sms= StringUtils.remove(sms,"\r");
                    sms= StringUtils.remove(sms,"\n");
                    list.add(sms);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

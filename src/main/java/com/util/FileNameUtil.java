package com.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HeQi
 * @Date:Create in 15:05 2018/9/14
 */
public class FileNameUtil {

    public static List<String> getListFiles(String path, String suffix, boolean isdepth) {
        List<String> lstFileNames = new ArrayList<String>();
        File file = new File(path);
        return FileNameUtil.listFile(lstFileNames, file, suffix, isdepth);
    }

    private static List<String> listFile(List<String> lstFileNames, File f, String suffix, boolean isdepth) {
        // 若是目录, 采用递归的方法遍历子目录
        if (f.isDirectory()) {
            File[] t = f.listFiles();

            for (int i = 0; i < t.length; i++) {
                if (isdepth || t[i].isFile()) {
                    listFile(lstFileNames, t[i], suffix, isdepth);
                }
            }
        } else {
            String filePath = f.getAbsolutePath();
            if (!suffix.equals("")) {
                int begIndex = filePath.lastIndexOf("."); // 最后一个.(即后缀名前面的.)的索引
                String tempsuffix = "";

                if (begIndex != -1) {
                    tempsuffix = filePath.substring(begIndex + 1, filePath.length());
                    if (tempsuffix.equals(suffix)) {
                        lstFileNames.add(filePath);
                    }
                }
            } else {
                lstFileNames.add(filePath);
            }
        }
        return lstFileNames;
    }

}

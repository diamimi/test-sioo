package com;

import com.util.FilePrintUtil;
import com.util.RangeRandom;
import com.util.SequoiaDBUtil;
import org.apache.poi.ss.usermodel.*;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by morrigan on 2017/6/7.
 */
public class ExcelTest {

    private static Logger log = LoggerFactory.getLogger(ExcelTest.class);

    @Test
    public void ss() {
        String content = "【广汇东本泓宇】亲爱的车主：敲黑板，划重点。轮胎打折销售了！！！6.19-6.30日前凭短信进店更换轮胎1条可赠精美指甲套装一份及免费赠送价值80元四轮加氮气一次；更换两条轮胎享9.5折优惠、更换3条及以上立享9折优惠，并赠价值280元四轮定位一次及价值80元四轮加氮气一次.活动仅限15名客户。由于名额有限，先到先得，错过再等一年！详询：028-68613999！申蓉泓宇地址：成都市武侯区武青南路43号！！！回T退订";
        System.out.println(content.length());
    }

    @Test
    public void single() {
        int num = 0;
        List<Integer> mobiles1 = new ArrayList<>(10000000);
        List<Integer> mobiles2 = new ArrayList<>(10000000);
        List<Integer> mobiles3 = new ArrayList<>(10000000);
        Random random = new Random();
        PrintWriter out = null;
        try {
            OutputStream os = new FileOutputStream("D:\\hq\\files/mobiles.txt");
            out = new PrintWriter(new OutputStreamWriter(os, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (num <= 10000000) {
            int i = random.nextInt(3);
            int l = 0;
            if (i == 0) {
                l = RangeRandom.getInstance().getRangeRandom(300000000, 400000000 - 1);
                if (mobiles1.contains(l)) {
                    continue;
                }
                mobiles1.add(l);
            } else if (i == 1) {
                l = RangeRandom.getInstance().getRangeRandom(500000000, 600000000 - 1);
                if (mobiles2.contains(l)) {
                    continue;
                }
                mobiles2.add(l);
            } else if (i == 2) {
                l = RangeRandom.getInstance().getRangeRandom(800000000, 900000000 - 1);
                if (mobiles3.contains(l)) {
                    continue;
                }
                mobiles3.add(l);
            }

            String s = "1" + String.valueOf(l) + random.nextInt(10);
            FilePrintUtil.getInstance().writeLine(out, s);
            num++;
        }
        out.flush();
        out.close();

    }

    @Test
    public void aa() throws Exception {
        String encode = URLEncoder.encode("感谢您 L’Étape nced", "utf-8");
        System.out.println(URLDecoder.decode(encode, "utf-8"));
    }


    @Test
    public void excelToArea() throws Exception {
        File xlsFile = new File("D:\\tuiguangti\\doc\\03_设计开发\\第三方网站对接\\天助网行业和地域信息匹配\\zone");
        InputStream is = new FileInputStream(xlsFile);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String provinceId = row.getCell(0).getStringCellValue().trim();
                String province = row.getCell(1).getStringCellValue().trim();
                String cityId = row.getCell(2).getStringCellValue().trim();
                String city = row.getCell(3).getStringCellValue().trim();
                String countryId = row.getCell(4).getStringCellValue().trim();
                String countryName = row.getCell(5).getStringCellValue().trim();
                if (countryId != null && !countryId.equals("")) {
                    String code = countryId;
                    String area = countryName;
                    String level = "3";
                    int pid = 1937000000 + Integer.parseInt(cityId);
                    int id = 1937000000 + Integer.parseInt(countryId);
                    String fullname = province + "/" + city + "/" + countryName;
                    String sql = "INSERT INTO public.cust_zone_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name," +
                            " created_by, date_created, updated_by, date_updated) VALUES (" + id + "," + 115 + "," + code + ",'" + area + "','" + level + "',1," + pid + ",'1','" + fullname + "'," +
                            "'system','2018-06-30 16:36:20','system','2018-06-30 16:36:20');";
                    System.out.println(sql);
                }
                if (cityId != null && !cityId.equals("") && !second.contains(cityId)) {
                    String code = cityId;
                    String area = city;
                    String level = "2";
                    int pid = 1937000000 + Integer.parseInt(provinceId);
                    int id = 1937000000 + Integer.parseInt(cityId);
                    String fullname = province + "/" + city;
                    String sql = "INSERT INTO public.cust_zone_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name," +
                            " created_by, date_created, updated_by, date_updated) VALUES (" + id + "," + 115 + "," + code + ",'" + area + "','" + level + "',1," + pid + ",'1','" + fullname + "'," +
                            "'system','2018-06-30 16:36:20','system','2018-06-30 16:36:20');";
                    System.out.println(sql);
                    second.add(cityId);
                }
                if (provinceId != null && !provinceId.equals("") && !first.contains(provinceId)) {
                    String code = provinceId;
                    String area = province;
                    String level = "1";
                    int pid = 0;
                    int id = 1937000000 + Integer.parseInt(provinceId);
                    String fullname = province;
                    String sql = "INSERT INTO public.cust_zone_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name," +
                            " created_by, date_created, updated_by, date_updated) VALUES (" + id + "," + 115 + "," + code + ",'" + area + "','" + level + "',1," + pid + ",'1','" + fullname + "'," +
                            "'system','2018-06-30 16:36:20','system','2018-06-30 16:36:20');";
                    System.out.println(sql);
                    first.add(provinceId);
                }


            }
        }
    }


    @Test
    public void excelToSlArea() throws Exception {
        File xlsFile = new File("D:\\tuiguangti\\doc\\03_设计开发\\第三方网站对接\\云合景从\\地区+行业/地区信息_搜了.xlsx");
        InputStream is = new FileInputStream(xlsFile);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String id = row.getCell(0).getStringCellValue().trim();
                String name = row.getCell(1).getStringCellValue().trim();
                String pid = row.getCell(2).getStringCellValue().trim();
                String code = id;
                String area = name;
                String level = "";
                if (Integer.valueOf(pid) == 0) {
                    level = "1";
                } else if (Integer.valueOf(pid) > 36) {
                    level = "3";
                } else {
                    level = "2";
                }
                int parentId = 0;
                if (!pid.equals("0")) {
                    parentId = 1937000000 + Integer.parseInt(pid);
                }
                int i = 1937000000 + Integer.parseInt(code);
                String sql = "INSERT INTO public.cust_zone_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name," +
                        " created_by, date_created, updated_by, date_updated) VALUES (" + i + "," + 116 + "," + code + ",'" + area + "','" + level + "',1," + parentId + ",'1','" + name + "'," +
                        "'system','2018-06-30 16:36:20','system','2018-06-30 16:36:20');";
                System.out.println(sql);
            }
        }
    }

    @Test
    public void excelToIndustry() throws Exception {
        File xlsFile = new File("D:\\tuiguangti\\doc\\03_设计开发\\第三方网站对接\\云合景从\\地区+行业/行业信息_搜了.xlsx");
        InputStream is = new FileInputStream(xlsFile);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
        List<String> first = new ArrayList<>();
        List<String> second = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String firstId = row.getCell(0).getStringCellValue().trim();
                String firstName = row.getCell(1).getStringCellValue().trim().replace("/", "、");
                String secondId = row.getCell(2).getStringCellValue().trim();
                String secondName = row.getCell(3).getStringCellValue().trim().replace("/", "、");
                String thirdId = row.getCell(4).getStringCellValue().trim();
                String thirdName = row.getCell(5).getStringCellValue().trim().replace("/", "、");
                if (firstId != null && !firstId.equals("")) {
                    if (!first.contains(firstId)) {
                        String code = firstId;
                        String name = firstName;
                        String level = "1";
                        String sort_no = "1";
                        int parent_id = 0;
                        int id = Integer.valueOf(firstId) + 1025300000;
                        String full_name = firstName;
                        String sql = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)" +
                                "  VALUES (" + id + ",116,'" + code + "','" + name + "','" + level + "'," + sort_no + "," + parent_id + ",'1','" + full_name + "','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');";
                        log.info(sql);
                        first.add(firstId);
                    }

                }
                if (secondId != null && !secondId.equals("")) {
                    if (!second.contains(secondId)) {
                        String code = secondId;
                        String name = secondName;
                        String level = "2";
                        String sort_no = "2";
                        int parent_id = Integer.parseInt(firstId) + 1025300000;
                        int id = Integer.valueOf(secondId) + 1025300000;
                        String full_name = firstName + "/" + secondName;
                        String sql = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)" +
                                "  VALUES (" + id + ",116,'" + code + "','" + name + "','" + level + "'," + sort_no + "," + parent_id + ",'1','" + full_name + "','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');";
                        log.info(sql);
                        second.add(secondId);
                    }

                }
                if (thirdId != null && !thirdId.equals("")) {
                    String code = thirdId;
                    String name = thirdName;
                    String level = "3";
                    String sort_no = "3";
                    int parent_id = Integer.parseInt(secondId) + 1025300000;
                    int id = Integer.valueOf(thirdId) + 1025300000;
                    String full_name = firstName + "/" + secondName + "/" + thirdName;
                    String sql = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)" +
                            "  VALUES (" + id + ",116,'" + code + "','" + name + "','" + level + "'," + sort_no + "," + parent_id + ",'1','" + full_name + "','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');";
                    log.info(sql);
                }


            }
        }
    }


    @Test
    public void excelToIndustry2() throws Exception {
        File xlsFile = new File("D:\\tuiguangti\\doc\\03_设计开发\\第三方网站对接\\云合景从\\地区+行业/行业信息_商虎.xlsx");
        InputStream is = new FileInputStream(xlsFile);
        Workbook workbook = WorkbookFactory.create(is);
        Sheet sheet = workbook.getSheetAt(0);  //示意访问sheet
        int i = 0;
        //INSERT INTO public.cust_industry_info (id, site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)
        // VALUES (1025220006,112,'7','建材、房地产','1',1,0,'1','建材、房地产','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');
        Map<String, Integer> first = new HashMap<>();
        Map<String, Integer> second = new HashMap<>();
        Map<String, Integer> third = new HashMap<>();
        int baseId = 1025300000;
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String firstName = row.getCell(1).getStringCellValue().trim().replace("/", "、");
                if (firstName != null && !firstName.equals("")) {
                    if (!first.containsKey(firstName)) {
                        baseId++;
                        first.put(firstName, baseId);
                    }

                }
            }
        }

        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String secondName = row.getCell(3).getStringCellValue().trim().replace("/", "、");
                if (secondName != null && !secondName.equals("")) {
                    if (!second.containsKey(secondName)) {
                        baseId++;
                        second.put(secondName, baseId);
                    }
                }

            }
        }


        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String thirdName = row.getCell(5).getStringCellValue().trim().replace("/", "、");
                if (thirdName != null && !thirdName.equals("")) {
                    if (!third.containsKey(thirdName)) {
                        baseId++;
                        third.put(thirdName, baseId);
                    }
                }
            }
        }
        List<String> firstSql = new ArrayList<>();
        List<String> secondSql = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row != null) {
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                String firstId = row.getCell(0).getStringCellValue().trim();
                String firstName = row.getCell(1).getStringCellValue().trim().replace("/", "、");
                String secondId = row.getCell(2).getStringCellValue().trim();
                String secondName = row.getCell(3).getStringCellValue().trim().replace("/", "、");
                String thirdId = row.getCell(4).getStringCellValue().trim();
                String thirdName = row.getCell(5).getStringCellValue().trim().replace("/", "、");
                if (firstId != null && !firstId.equals("")) {
                    if (!firstSql.contains(firstId)) {
                        String code = firstId;
                        String name = firstName;
                        String level = "1";
                        String sort_no = "1";
                        int parent_id = 0;
                        int id = first.get(firstName);
                        String full_name = firstName;
                        String sql = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)" +
                                "  VALUES (" + id + ",116,'" + code + "','" + name + "','" + level + "'," + sort_no + "," + parent_id + ",'1','" + full_name + "','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');";
                        log.info(sql);
                        firstSql.add(firstId);
                    }

                }
                if (secondId != null && !secondId.equals("")) {
                    if (!secondSql.contains(secondId)) {
                        String code = secondId;
                        String name = secondName;
                        String level = "2";
                        String sort_no = "2";
                        int parent_id = first.get(firstName);
                        int id = second.get(secondName);
                        String full_name = firstName + "/" + secondName;
                        String sql = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)" +
                                "  VALUES (" + id + ",116,'" + code + "','" + name + "','" + level + "'," + sort_no + "," + parent_id + ",'1','" + full_name + "','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');";
                        log.info(sql);
                        secondSql.add(secondId);
                    }

                }
                if (thirdId != null && !thirdId.equals("")) {
                    String code = thirdId;
                    String name = thirdName;
                    String level = "3";
                    String sort_no = "3";
                    int parent_id = second.get(secondName);
                    int id = third.get(thirdName);
                    String full_name = firstName + "/" + secondName + "/" + thirdName;
                    String sql = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)" +
                            "  VALUES (" + id + ",116,'" + code + "','" + name + "','" + level + "'," + sort_no + "," + parent_id + ",'1','" + full_name + "','system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');";
                    log.info(sql);
                }


            }
        }

    }


    /**
     * tgj3级地域
     *
     * @throws Exception
     */
    @Test
    public void ssa() throws Exception {
        File file = new File("D:\\tuiguangti\\doc\\03_设计开发\\第三方网站对接\\天助网行业和地域信息匹配\\zone/金泉网_地域.txt");
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), "utf-8");// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String sms = null;
        while ((sms = bufferedReader.readLine()) != null) {
            if (sms != null && !sms.equals("")) {

            }
            bufferedReader.close();
        }
    }


    @Test
    public void sss() {
        BSONObject where = new BasicBSONObject();
        where.put("uid", 90253);
        where.put("senddate", new BasicBSONObject("$gt", 20180805190000l));
        where.put("senddate", new BasicBSONObject("$lt", 20180805200000l));
        long count = SequoiaDBUtil.getInstance().count("sms_send_history_detail", where);
        System.out.println(count);
    }

    @Test
    public void sa() {
        String con = "【互贷网】五周年狂欢，百万京东卡8月送不停，8/10-8/12连享三天会员日，感恩回馈全场最高加息3%+京东卡返利1%，错过再等一年！回T退订";
        System.out.println(con.length());
    }
}

package com;

/**
 * @Author: HeQi
 * @Date:Create in 9:24 2018/8/2
 */

import com.pojo.Area;
import com.pojo.IndustryInfo;
import com.service.DestoonService;
import com.util.FilePrintUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DestoonTest {

    @Autowired
    private DestoonService destoonService;


    //INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated)  VALUES (1025500001,116,'24','汽摩配件','1',1,0,'1','汽摩配件', 'system','2018-06-30 18:36:20','system','2018-06-30 18:36:20');

    @Test
    public void industry() {
        long baseid = 1025600000l - 4l;
        int site_id = 10000;
        List<IndustryInfo> sort = new ArrayList<>();
        List<IndustryInfo> list = destoonService.findIndustryInfoList();
        for (IndustryInfo industryInfo1 : list) {
            if (industryInfo1.getParentId() == 0) {
                IndustryInfo info = new IndustryInfo();
                info.setFullname(industryInfo1.getName());
                info.setName(industryInfo1.getName());
                info.setParentId(industryInfo1.getParentId());
                info.setLevel(1);
                info.setId(industryInfo1.getId());
                sort.add(info);
            } else {
                IndustryInfo industryInfo2 = destoonService.findIndustry(industryInfo1.getParentId());
                if (industryInfo2.getParentId() == 0) {
                    IndustryInfo info = new IndustryInfo();
                    info.setFullname(industryInfo2.getName() + "/" + industryInfo1.getName());
                    info.setName(industryInfo1.getName());
                    info.setParentId(industryInfo2.getId());
                    info.setLevel(2);
                    info.setId(industryInfo1.getId());
                    sort.add(info);
                } else {
                    IndustryInfo industryInfo3 = destoonService.findIndustry(industryInfo2.getParentId());
                    IndustryInfo info = new IndustryInfo();
                    info.setFullname(industryInfo3.getName() + "/" + industryInfo2.getName() + "/" + industryInfo1.getName());
                    info.setName(industryInfo1.getName());
                    info.setParentId(industryInfo2.getId());
                    info.setLevel(3);
                    info.setId(industryInfo1.getId());
                    sort.add(info);
                }
            }
        }
        List<String> contents = new ArrayList<>();
        sort.stream().forEach(i -> {
            long id = baseid + i.getId();
            long parentid = 0;
            if (i.getParentId() > 0) {
                parentid = baseid + i.getParentId();
            }
            String content = "INSERT INTO public.cust_industry_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name," +
                    " created_by, date_created, updated_by, date_updated)  VALUES (" + id + "," + site_id + ",'" + i.getId() + "','" + i.getName() +
                    "','" + i.getLevel() + "'," + i.getLevel() + "," + parentid + ",'1','" + i.getFullname() + "', 'system','2018-08-02 15:36:20','system','2018-08-02 15:36:20');";
            contents.add(content);
        });

        FilePrintUtil.getInstance().write("D:\\tuiguangti\\source\\tuiguangti\\db\\drag\\inc\\1.1.2/一呼万应行业.sql", contents, "utf-8");
    }

    @Test
    public void area() {
        int site_id = 10000;
        long baseid = 1938100000l - 1l;
        List<Area> sort = new ArrayList<>();
        List<Area> list = destoonService.findAreaList();
        for (Area area1 : list) {
            if (area1.getParentid() == 0) {
                Area area = new Area();
                area.setFullname(area1.getAreaname());
                area.setAreaname(area1.getAreaname());
                area.setParentid(area1.getParentid());
                area.setLevel(1);
                area.setAreaid(area1.getAreaid());
                sort.add(area);
            } else {
                Area area2 = destoonService.findArea(area1.getParentid());
                if (area2.getParentid() == 0) {
                    Area info = new Area();
                    info.setFullname(area2.getAreaname() + "/" + area1.getAreaname());
                    info.setAreaname(area1.getAreaname());
                    info.setParentid(area2.getAreaid());
                    info.setLevel(2);
                    info.setAreaid(area1.getAreaid());
                    sort.add(info);
                } else {
                    Area area3 = destoonService.findArea(area2.getParentid());
                    Area info = new Area();
                    info.setFullname(area3.getAreaname() + "/" + area2.getAreaname() + "/" + area1.getAreaname());
                    info.setAreaname(area1.getAreaname());
                    info.setParentid(area2.getAreaid());
                    info.setLevel(3);
                    info.setAreaid(area1.getAreaid());
                    sort.add(info);
                }
            }
        }
        List<String> contents = new ArrayList<>();
        sort.stream().forEach(i -> {
            long id = baseid + i.getAreaid();
            long parentid = 0;
            if (i.getParentid() > 0) {
                parentid = baseid + i.getParentid();
            }
            // INSERT INTO public.cust_zone_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name, created_by, date_created, updated_by, date_updated) VALUES (1938000003,116,3,'北京','1',1,0,'1','北京','system','2018-06-30 16:36:20','system','2018-06-30 16:36:20');

            String content = "INSERT INTO public.cust_zone_info (id,site_id, code, name, level, sort_no, parent_id, is_valid, full_name," +
                    " created_by, date_created, updated_by, date_updated)  VALUES (" + id + "," + site_id + ",'" + i.getAreaid() + "','" + i.getAreaname() +
                    "','" + i.getLevel() + "'," + i.getLevel() + "," + parentid + ",'1','" + i.getFullname() + "', 'system','2018-08-02 15:36:20','system','2018-08-02 15:36:20');";
            contents.add(content);
        });

        FilePrintUtil.getInstance().write("D:\\tuiguangti\\source\\tuiguangti\\db\\drag\\inc\\1.1.2/一呼万应地区.sql", contents, "utf-8");
    }


    @Test
    public void deReg() {
        try {
            String time = String.valueOf(System.currentTimeMillis());
            String auth = DigestUtils.md5Hex(time+"tuiguangjia");
             //String url = "https://www.yhwy.net/tuiguangjia/reg.php";
            String url = "http://localhost:8088/destoon/hq/reg.php";
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(url);
            NameValuePair[] orderInfo = {
                    new NameValuePair("time", time),
                    new NameValuePair("auth", auth),
                    new NameValuePair("company", "安徽格力2"),
                    new NameValuePair("email", "ss1d22@tuiguangjia.com"),
                    new NameValuePair("mobile", "13905546652"),
                    new NameValuePair("telephone", "021-58657868-211"),
                    new NameValuePair("gender", "0"),
                    new NameValuePair("truename", "彭查查"),
                    new NameValuePair("qq", "94554798"),
                    new NameValuePair("areaid", "284"),
                    new NameValuePair("catid", "4"),
                    new NameValuePair("regyear", "2008"),
                    new NameValuePair("business", "游戏软件"),
                    new NameValuePair("address", "上海市虹口区多禄路120弄"),
                    new NameValuePair("content", "这是公司简介这是公司简介这是公司简介这是公司简介"),
            };
            postMethod.setRequestBody(orderInfo);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            httpClient.executeMethod(postMethod);
            String result = new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void auth(){
        String password="lmiAdjes";
        String passsalt="jC4QT8CF";
        String auth = DigestUtils.md5Hex(DigestUtils.md5Hex(DigestUtils.md5Hex(password)) + passsalt);
        System.out.println(auth);
    }


    @Test
    public void delogin() {
        try {
            String url = "http://localhost:8088/destoon/hq/hqlogin.php";
            HttpClient httpClient = new HttpClient();
            PostMethod postMethod = new PostMethod(url);
            NameValuePair[] orderInfo = {
                    new NameValuePair("username", "eetdg3pb12tk"),
                    new NameValuePair("password", "lmiAdjes"),
                    new NameValuePair("forward", "http://localhost:8088/destoon/member"),
                    new NameValuePair("action", "login"),
                    new NameValuePair("auth", "6b91f39dd30855b383ce50d6a5a4b87e"),
            };
            postMethod.setRequestBody(orderInfo);
            postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
            httpClient.executeMethod(postMethod);
            String result = new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com;

import com.util.HttpClientUtil;
import com.util.SmsUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HeQi
 * @Date:Create in 15:12 2018/7/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsSendTest {

    @Autowired
    private SmsUtil smsUtil;


    @Autowired
    private  RestTemplate restTemplate;
    @Test
    public void send() {
        String result = smsUtil.sendSms("【集借号】您的验证码为：123456，请妥善保管", "18621367763");
        System.out.println(result);
    }

    @Test
    public void get() {
        Map<String, String> param = new HashMap<>();
        param.put("uid", "90575");
        param.put("auth", "d33c65c7cb48b9a3c4496b16b24be75f");
        String result = HttpClientUtil.doPost("http://sms.10690221.com:9011/hy/m", param);
        System.out.println(result);
    }

    @Test
    public void sss() throws  Exception{
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<Document>" +
                "<Head>" +
                "<Auth>9ded7260144da4b7f72e10f75943a2a6</Auth>" +
                "<Uid>30032</Uid>" +
                "<Encode>utf-8</Encode>" +
                "<Expid>0</Expid>" +
                "</Head>" +
                "<Detail>" +
                "<Info><Mobile>18621367763</Mobile><Msg><![CDATA[banana]]></Msg></Info>" +
                "<Info><Mobile>18621367763</Mobile><Msg><![CDATA[ppk]]></Msg></Info>"+
                "</Detail>" +
                "</Document>";
        String url = "http://sms.10690221.com/hy/sendMCSms";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(xml, charSet);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpclient.execute(httpPost);
        StatusLine status = response.getStatusLine();
        int state = status.getStatusCode();
        if (state == HttpStatus.SC_OK) {
            HttpEntity responseEntity = response.getEntity();
            String jsonString = EntityUtils.toString(responseEntity);
            System.out.println(jsonString);
        }



    }

    @Test
    public void llll() throws Exception{
        HttpClient httpClient = new HttpClient();
        String url = "http://sms.10690221.com/hy/sendMCSms";
        String uid = "30032";	//用户ID
        String pass = "sioo123";
        String mobile = "18621367763";//发送的手机号码
        String Content = "hi";
        PostMethod postMethod = new PostMethod(url);
     /*   String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?><Document><head><Auth>9ded7260144da4b7f72e10f75943a2a6</Auth><Uid>30032</Uid><Encode>utf-8</Encode><Expid>0</Expid></head><Detail><Info><Mobile>18621367763</Mobile><Msg><![CDATA[apple]]></Msg></Info></Detail></Document>";
        StringEntity entity = new StringEntity(params, charSet);
        postMethod.setr(entity);
        int statusCode = httpClient.executeMethod(postMethod);
        if (statusCode == HttpStatus.SC_OK) {
            String sms = postMethod.getResponseBodyAsString();
            System.out.println("result:" + sms);
        }*/

    }
}

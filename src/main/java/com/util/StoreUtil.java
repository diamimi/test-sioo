package com.util;

import com.pojo.UserSign;
import com.service.SignService21;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: HeQi
 * @Date:Create in 10:08 2018/8/16
 */
@Component
public class StoreUtil {

    private static Logger log = Logger.getLogger(StoreUtil.class);

    @Autowired
    private SignService21 signService21;

    public static boolean hasStore(String content) {
        return ((content.startsWith("【") && StringUtils.contains(content, "】")) || (content.endsWith("】") && StringUtils.contains(content, "【")));
    }


    public String addSign(String content, int uid,  int signposition, int usertype) {

        UserSign userSign = null;
        //如果不存在签名或为强制签名都先找签名
        List<UserSign> signs = null;
        signs = SmsCache.USER_SIGN.get(uid);

        //没找到签名返回
        if (signs == null || signs.size() == 0) {
            return "";
        }
        //否则拿uid等于拓展的那个签名
        if (userSign == null) {
            for (UserSign us : signs) {
                if (us.getExpend().equals(uid + "")) {
                    userSign = us;
                    break;
                }
            }
        }
        // 如果没找到uid=expid对应的签名，从签名列表中拿一个
        if (userSign == null) {
            userSign = signs.get(0);
        }

        //如果找到签名
        if (userSign != null) {
            if (signposition == 1 || signposition == 3) {// 前置
                content=userSign.getStore() +content;
            } else if (signposition == 2) {
                content=content + userSign.getStore();
            }
        }
        return content;
    }


    public void loadUserSign(Integer uid, String store) {
        try {
            this.loadMaxExpend2();
            Map<Integer, List<UserSign>> CHANNEL_SIGN = null;
            List<UserSign> channelList = signService21.findUserSignByUidAndStore(uid, store, 1);
            if (channelList != null) {
                for (UserSign channelSign : channelList) {
                    saveChannelCache(channelSign);
                }
            } else {
                SmsCache.CHANNEL_SIGN = CHANNEL_SIGN;
                SmsCache.CHANNEL_SIGN_INIT = false;
                log.info("从缓存加载通道签名");
            }
            Map<Integer, CopyOnWriteArrayList<UserSign>> USER_SIGN = null;
            Map<String, UserSign> EXPEND_SIGN = null;
            if ((USER_SIGN == null || USER_SIGN.size() == 0) && (EXPEND_SIGN == null || EXPEND_SIGN.size() == 0)) {
                List<UserSign> userSignList = signService21.findUserSignByUidAndStore(uid, store, null);
                if (userSignList != null) {
                    for (UserSign userSign : userSignList) {
                        saveCache(userSign);
                    }
                    log.info("用户" + uid + "，签名加载【" + userSignList.size() + "】个");
                }
            } else {
                SmsCache.USER_SIGN = USER_SIGN;
                SmsCache.EXPEND_SIGN = EXPEND_SIGN;
                log.info("从缓存加载用户签名,拓展签名");
            }

        } catch (Exception e) {
        }
    }

    public void loadMaxExpend2() {
        String max = signService21.findMaxExpend2();
        long longMax = 0;
        if (max == null || max.isEmpty()) {
            longMax = 1;
        } else {
            longMax = Long.parseLong(max);
        }
        if (SmsCache.MAX_EXPEND2.get() < longMax) {
            SmsCache.MAX_EXPEND2.set(longMax);
        }
    }


    /****
     * 保存签名缓存
     *
     * @param userSign
     */
    private void saveCache(UserSign userSign) {
        if (SmsCache.USER_SIGN.containsKey(userSign.getUid())) {
            SmsCache.USER_SIGN.get(userSign.getUid()).add(userSign);
        } else {
            CopyOnWriteArrayList<UserSign> newUserSignList = new CopyOnWriteArrayList<UserSign>();
            newUserSignList.add(userSign);
            SmsCache.USER_SIGN.put(userSign.getUid(), newUserSignList);
        }

        if (userSign.getType() == 2) {
            SmsCache.EXPEND_SIGN.put(userSign.getExpend(), userSign);
        }
    }

    private void saveChannelCache(UserSign userSign) {
        if (SmsCache.CHANNEL_SIGN.containsKey(userSign.getUid())) {
            SmsCache.CHANNEL_SIGN.get(userSign.getUid()).add(userSign);
        } else {
            List<UserSign> newUserSignList = new ArrayList<UserSign>();
            newUserSignList.add(userSign);
            SmsCache.CHANNEL_SIGN.put(userSign.getUid(), newUserSignList);
        }
    }

    public String getSign(String content){
        return "【"+StringUtils.substringBetween(content,"【","】")+'】';
    }


}

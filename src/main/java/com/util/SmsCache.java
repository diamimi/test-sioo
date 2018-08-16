package com.util;

import com.alibaba.fastjson.JSONArray;
import com.pojo.MobileArea;
import com.pojo.SendingVo;
import com.pojo.SmsUser;
import com.pojo.UserSign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author CQL 331737188@qq.com
 * @date : 2016��4��28�� ����1:23:02
 */
public class SmsCache {
    public static boolean CONTROL = true;
    // 警告消息发送开关
    public static boolean alertMsgFlag = false;
    // ҹ�䱨�������ֻ������б�
    public static List<String> alertMsgDayMobiles = null;
    // �ռ䱨�������ֻ������б�
    public static List<String> alertMsgNightMobiles = null;

    public static int RABBIT_PORT = 0;
    public static String RABBIT_HOST = null;
    public static String RABBIT_USERNAME = null;
    public static String RABBIT_PASSWORD = null;
    public static int RABBIT_PREFETCH_SIZE = 0;

    public static int FILE_DEL_FLAG = 1;

    public static int RABBIT_PORT16 = 0;
    public static String RABBIT_HOST16 = null;
    public static String RABBIT_USERNAME16 = null;
    public static String RABBIT_PASSWORD16 = null;
    public static int RABBIT_PREFETCH_SIZE16 = 0;


    public static int RABBIT_PORT114 = 0;
    public static String RABBIT_HOST114 = null;
    public static String RABBIT_USERNAME114 = null;
    public static String RABBIT_PASSWORD114 = null;
    public static int RABBIT_PREFETCH_SIZE114 = 0;
    
    public static String REDIS_HOST = null;

    /**
	 * 已存在消息记录
	 */
	public static Map<String, Integer> sendCacheMap = new HashMap<String, Integer>();
	
	/**
	 * 用户消费记录记数
	 */

	public static BlockingQueue<Long> QUEUE_REDIS_REPEAT = new LinkedBlockingQueue<Long>();// 保存redis重号过滤

    public static Map<String, Integer> contentCount = new ConcurrentHashMap<String, Integer>();// 每天晚上10至次日早上8点用于内容批量内容统计过滤
    public static int isClean = 0;

    // 本地队列处理标记 ，如果为true,肯定还没处理完
    public static Map<String, Boolean> QUEUE_FIRST_FLG = new ConcurrentHashMap<String, Boolean>();
    // public static boolean QUEUE_FIRST_FLG = true;
    public static boolean QUEUE_RESULT_FLG = false;
    public static boolean QUEUE_CHECK_RELEASE_FLG = false;
    public static boolean QUEUE_CHECK_SENDING_FLG = false;
    public static boolean QUEUE_CHECK_ALERT_FLG = false;
    public static boolean QUEUE_SMS_CACHE_FLG = false;
    public static boolean QUEUE_SMS_BATCH_FLG = false;
    public static boolean QUEUE_UPDATE_SENDING_FLG = false;
    public static boolean QUEUE_RATIO_FLG = false;


    public static BlockingQueue<SendingVo> QUEUE_SMSCHECK_NORMAL = new LinkedBlockingQueue<SendingVo>();// 普通队列
    public static BlockingQueue<SendingVo> FAIL_SENDING_VO = new LinkedBlockingQueue<SendingVo>();// 普通队列
    public static BlockingQueue<SendingVo> QUEUE_SENDING_PRIORITIZED = new LinkedBlockingQueue<SendingVo>();// 优先队列
    public static BlockingQueue<SendingVo> QUEUE_BIG = new LinkedBlockingQueue<SendingVo>();//大批量队列


    public static BlockingQueue<SendingVo> QUEUE_SENDING_HISTORY = new LinkedBlockingQueue<SendingVo>();// 历史记录队列
    public static BlockingQueue<SendingVo> QUEUE_SENDING = new LinkedBlockingQueue<SendingVo>();// 发送通道队列
    public static BlockingQueue<SendingVo> QUEUE_SENDING_RELEASE = new LinkedBlockingQueue<SendingVo>();// 审核消息队列
    public static BlockingQueue<SendingVo> QUEUE_REPORT = new LinkedBlockingQueue<SendingVo>();// 状态报告队列

    public static BlockingQueue<String> QUEUE_ALERT = new LinkedBlockingQueue<String>();  // 短信提醒队列

    public static BlockingQueue<SendingVo> QUEUE_RATIO = new LinkedBlockingQueue<SendingVo>();// 扣量队列
    
    public static BlockingQueue<JSONArray> QUEUE_REPEAT_SET = new LinkedBlockingQueue<JSONArray>();//重号过滤保存队列

    // 通道，key:channelId

    public static Map<Integer, String> SYS_STRATEGY_GROUP = new ConcurrentHashMap<>();
    
    /**
     * 记录已经保存的pid
     */
    public static Map<Integer, Integer> PIDCACHE = new ConcurrentHashMap<Integer, Integer>();


    // 通道组，key:channelGroupId

    // 通道签名库，key:expend+store md5
    public static Map<String, List<Map<String, Object>>> CHANNEL_SIGN_EXPEND = new ConcurrentHashMap<String, List<Map<String, Object>>>();

    // 通道黑签名
    public static Map<String, List<Map<String, Object>>> CHANNEL_BLACK_SIGN = new ConcurrentHashMap<String, List<Map<String, Object>>>();

    public static Map<Integer, SmsUser> USER = new ConcurrentHashMap<Integer, SmsUser>();
    // 通道，key:uid
    public static Map<Integer, Map<String, Object>> RPT_RATIO_CONFIG = new ConcurrentHashMap<Integer, Map<String, Object>>();

    public static Map<Integer, Map<String, AtomicLong>> RPT_RATIO_USER_SEND = new ConcurrentHashMap<Integer, Map<String, AtomicLong>>();

    // 短信提醒
    public static Map<Integer, Map<String, Object>> USER_ALERT = new ConcurrentHashMap<Integer, Map<String, Object>>();

    // 用户路由,key:uid

    // 通用签名库，key:uid
    public static Map<Integer, CopyOnWriteArrayList<UserSign>> USER_SIGN = new ConcurrentHashMap<Integer, CopyOnWriteArrayList<UserSign>>();

    // 私有签名库，key:uid
    public static Map<Integer, List<UserSign>> CHANNEL_SIGN = new ConcurrentHashMap<Integer, List<UserSign>>();

    // 签名库，key:expend
    public static Map<String, UserSign> EXPEND_SIGN = new ConcurrentHashMap<String, UserSign>();

    // 最大自增长拓展ID,普通用户用
    public static AtomicLong MAX_EXPEND2 = new AtomicLong();

    // 用户白名单，key:uid+号码前5位
    public static Map<String, List<Long>> USER_WHITE_MOBILE = new ConcurrentHashMap<String, List<Long>>();

    // 用户审核屏蔽词，key:uid
    public static Map<Integer, List<String>> USER_BALCK_WORDS = new ConcurrentHashMap<Integer, List<String>>();

    /**
     * 钓鱼号码
     */
    public static List<Long> FISH_MOBILE = new CopyOnWriteArrayList<>();

    /**
     * 通道连接channelId,type,no
     */

    public static List<String> FISH_CONTENT = new CopyOnWriteArrayList<>();

    /**
     * 用户自动屏蔽词
     */
    public static Map<Integer, List<String>> USER_BALCK_WORDS_AUTO = new ConcurrentHashMap<Integer, List<String>>();

    // 用户白签名，key:uid
    public static Map<Integer, List<String>> USER_WHITE_SIGN = new ConcurrentHashMap<Integer, List<String>>();

    // 用户模板，key:uid
    public static Map<Integer, List<String>> USER_SMS_TEMPLATE = new ConcurrentHashMap<Integer, List<String>>();

    // 重号过滤，key:日期yyyyMMdd+uid；value key:号码前五位，value：号码
    // public static Map<String, Map<Integer, Map<Long, List<Long>>>>
    // USER_REPEAT_MOBILE = new ConcurrentHashMap<String, Map<Integer, Map<Long,
    // List<Long>>>>();


    /**
     * 重号初始化标记
     */
    public static Map<Long, Integer> REPEAT_MOBILE_SIZE = new ConcurrentHashMap<Long, Integer>();


    // 屏蔽地区，key:uid
    public static Map<Integer, List<String>> USER_BLACK_LOCATION = new ConcurrentHashMap<Integer, List<String>>();

    // 屏蔽地区，key:channel_id
    public static Map<Integer, List<String>> CHANNEL_BLACK_LOCATION = new ConcurrentHashMap<Integer, List<String>>();

    //屏蔽地区路由例外uid
    public static Map<String, String> USER_BLACK_LOCATION_EXC_UID = new ConcurrentHashMap<String, String>();

    //屏蔽地区路由
    public static Map<String, Integer> BLACK_LOCATION_ROUTE = new ConcurrentHashMap<String, Integer>();

    // 用户策略组，key:uid
    public static Map<Integer, Map<Integer, String>> USER_STRATEGY_GROUP = new ConcurrentHashMap<Integer, Map<Integer, String>>();

    // 白名单策略组，key:groupid,subkey号码前5位
    public static Map<Integer, Map<String, List<Long>>> GROUP_WHITE_MOBILE = new ConcurrentHashMap<Integer, Map<String, List<Long>>>();

    // 白签名策略组，key:groupid
    public static Map<Integer, List<String>> GROUP_WHITE_SIGN = new ConcurrentHashMap<Integer, List<String>>();

    // 黑签名策略组，key:groupid
    public static Map<Integer, List<String>> GROUP_BLACK_SIGN = new ConcurrentHashMap<Integer, List<String>>();

    // 审核屏蔽词策略组，key:groupid
    public static Map<Integer, List<String>> GROUP_RELEASE_WORDS = new ConcurrentHashMap<Integer, List<String>>();

    /**
     * 审核屏蔽词缓存
     */

    // �Զ����δʲ����飬key:groupid
    public static Map<Integer, List<String>> GROUP_AUTO_WORDS = new ConcurrentHashMap<Integer, List<String>>();

    /**
     * 自动屏蔽词缓存
     */

    // 归属地
    public static Map<Integer, MobileArea> MOBILE_AREA = new ConcurrentHashMap<Integer, MobileArea>();

    //redis统计缓存辅助REPEAT_MOBILE_SIZE 的map
    public static ConcurrentHashMap<String, Integer> countMap = new ConcurrentHashMap<String, Integer>();

    /**
     * 程序启动，只从ehcache加载一次 start
     */

    /**
     * 余额提醒
     */
    public static Boolean USER_ALERT_INIT = true;

    /**
     * 用户黑名单
     */
    public static Boolean USER_BLACK_MOBILE_INIT = true;

    public static Boolean ALL_BLACK_USER_MOBILE_INIT = true;

    public static Boolean CHANNEL_SIGN_INIT = true;

    public static Boolean USER_SIGN_INIT = true;

    public static Boolean EXPEND_SIGN_INIT = true;

    public static Boolean GROUP_WHITE_MOBILE_INIT = true;
    public static Boolean GROUP_WHITE_SIGN_INIT = true;
    public static Boolean GROUP_BLACK_MOBILE_INIT = true;
    public static Boolean GROUP_BLACK_SIGN_INIT = true;
    public static Boolean GROUP_RELEASE_WORDS_SCREENTYPE_INIT = true;
    public static Boolean GROUP_AUTO_WORDS_SCREENTYPE_INIT = true;
    public static Boolean MOBILE_AREA_INIT = true;

    /**
     * 执行缓存本地化操作标记
     */
    public static Boolean CACHE_FLAG = true;

    /**
     * 用户线路同步余额到数据库标记
     */
    public static Map<Integer, Integer> USER_LINE_SYNC_STATE = new ConcurrentHashMap<Integer, Integer>();

    public static BlockingQueue<SendingVo> QUEUE_SENDING_PRIORITIZED_LINE = new LinkedBlockingQueue<SendingVo>();

    public static AtomicLong ATOMIC = new AtomicLong();
    public static AtomicLong SUB_ATOMIC = new AtomicLong();

    public static AtomicLong NOSIGN_TIME = new AtomicLong();
    public static AtomicInteger NOSIGN_COUNT = new AtomicInteger();

    public static long LOG_TIME_SIGN = 0L;
    public static int LOG_TIME_COUNT = 0;
    
    public static AtomicInteger pid = new AtomicInteger(0);

}
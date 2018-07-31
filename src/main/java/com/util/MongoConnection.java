package com.util;


import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;


public class MongoConnection { 
    private static MongoClient mongo = null; 
    private final static MongoConnection instance = new MongoConnection(); 
    public static MongoConnection getInstance() { 
    	init();
      return instance;
    } 
  
    /** 
     * 根据名称获取DB，相当于是连接 
     *  
     * @return
     */
    public  DB getDB() { 
        if (mongo == null) { 
            // 初始化 
            init();
        } 
        return mongo.getDB("SMS");
    } 
  
    /** 
     * 初始化连接池，设置mango链接参数。 
     */
    private static void init() { 
    	 try { 
             mongo = new MongoClient("10.10.137.105", 27017);
         } catch (UnknownHostException e) { 
             e.printStackTrace();
         }
    }

    
} 

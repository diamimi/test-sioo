package com.util;


import com.mongodb.*;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class MongoDBUtil {
	private final static Logger logger = Logger.getLogger(MongoDBUtil.class);
      
	private static MongoConnection mongoConnection= MongoConnection.getInstance();

    private static DB db ;

     

    private final static MongoDBUtil instance = new MongoDBUtil();  

    public static MongoDBUtil getInstance()  {  
 	    init();
        return instance;  

 }  



	public static void init()  {
    	db = mongoConnection.getDB();
    }


   
      


             

    /**  

    * 获取集合（表�? 

    * @param collection  

    */ 

    public static DBCollection getCollection(String collection) {  

         return db.getCollection(collection);  

    }  

  

    /**  

    * ----------------------------------分割�?-------------------------------------  

    */ 

  

    /**  

    * 插入  

    * @param collection  

    * @param map  

    */ 

    public void insert(String collection , DBObject map) {  

         try {  

              DBObject dbObject = (map);  

              getCollection(collection).insert(dbObject);  

         } catch (MongoException e) {  

        	 logger.error(e.getMessage(), e);

         }  

    }  

  

    /**  

    * 批量插入  

    * @param collection  

    * @param list  

    */ 

    public void insertBatch(String collection ,List<DBObject> list) {  

         if (list == null || list.isEmpty()) {  

              return;  

         }  

         try {  

              List<DBObject> listDB = new ArrayList<DBObject>();  

              for (int i = 0; i < list.size(); i++) {  

                   DBObject dbObject = (list.get(i));  

                   listDB.add(dbObject);  

              }  

              getCollection(collection).insert(listDB);  

         } catch (MongoException e) {  

        	 logger.error(e.getMessage(), e);

         }  

    }  

  

    /**  

    * 删除  

    * @param collection  

    * @param map  

    */ 

    public void delete(String collection ,DBObject map) {  

         DBObject obj = (map);  

         getCollection(collection).remove(obj);  

    }  

  

    /**  

      * 删除全部  

      * @param collection  

      * @param map  

      */ 

    public void deleteAll(String collection) {  

         List<DBObject> rs = findAll(collection);  

         if (rs != null && !rs.isEmpty()) {  

              for (int i = 0; i < rs.size(); i++) {  

                   getCollection(collection).remove(rs.get(i));  

              }  

         }  

    }  

  

    /**  

    * 批量删除  

    * @param collection  

    * @param list  

    */ 

    public void deleteBatch(String collection,List<DBObject> list) {  

         if (list == null || list.isEmpty()) {  

              return;  

         }  

         for (int i = 0; i < list.size(); i++) {  

              getCollection(collection).remove((list.get(i)));  

         }  

    }  

  

    /**  

    * 计算满足条件条数  

    * @param collection  

    * @param map  

    */ 

    public long getCount(String collection,DBObject map) {  

         return getCollection(collection).getCount((map));  

    }  

      

    /**  

    * 计算集合总条�? 

    * @param collection  

    * @param map  

    */ 

    public long getCount(String collection) {  

         return getCollection(collection).find().count();  

    }  

  

    /**  

    * 更新  

    * @param collection  

    * @param setFields  

    * @param whereFields  

    */ 

    public void update(String collection,DBObject setFields,  

              DBObject whereFields) {  

         DBObject obj1 = (setFields);  

         DBObject obj2 = (whereFields);  

         getCollection(collection).update(obj2,obj1,true,false );  

    } 
    
   /**
    *根据ID批量更新数据
    * @param collection
    * @param list
    * @param setFields
    */
    public void updateBatchByIDs(String collection,BasicDBObject obj,BasicDBObject setFields){
    	if(obj==null||obj.toString()=="")
    		return ;
    
    		 BasicDBObject  destFields=new BasicDBObject();
  		     destFields.put("_id", obj);
     	     BasicDBObject doc = new BasicDBObject();  
   			 doc.put("$set", setFields); 
     	     getCollection(collection).update(destFields,doc,false,true );
    }
    
    /**
     *根据ID批量更新数据
     * @param collection
     * @param list
     * @param setFields
     */
     public void deleteBatchByIDs(String collection,BasicDBObject obj){
     	if(obj==null||obj.toString()=="")
     		return ;
 		 BasicDBObject  destFields=new BasicDBObject();
	     destFields.put("_id", obj);
  	    getCollection(collection).remove(destFields);
     }

    /**  

    * 查找对象（根据主键_id�? 

    * @param collection  

    * @param _id  

    */ 

    public DBObject findById(String collection,String _id) {  

         DBObject obj = new BasicDBObject();  

         obj.put("_id", ObjectId.massageToObjectId(_id));  

         return getCollection(collection).findOne(obj);  

    }  

  

    /**  

    * 查找集合�?��对象  

    * @param collection  

    */ 

    public List<DBObject> findAll(String collection) {  

         return getCollection(collection).find().toArray();  

    }  

  

    /**  

    * 查找（返回一个对象）  

    * @param map  

    * @param collection  

    */ 

    public DBObject findOne(String collection,DBObject map) {  

         DBCollection coll = getCollection(collection);  

         return coll.findOne((map));  

    }  

  

    /**  

    * 查找（返回一个List<DBObject>�? 

    * @param <DBObject>  

    * @param map  

    * @param collection  

    * @throws Exception  

    */ 

    public List<DBObject> find(String collection,DBObject map) throws Exception {  

         DBCollection coll = getCollection(collection);  

         DBCursor c = coll.find((map));  

         if (c != null)  
              return c.toArray();  
         else 
              return null;  
    } 
    

    public List<DBObject> find(String collection,DBObject map,int limit) throws Exception {  
         DBCollection coll = getCollection(collection);  
         DBCursor c = coll.find((map)).limit(limit);  
         if (c != null) {
        	 return c.toArray();  
         }
         else {
        	 return null;  
         }
    } 
}


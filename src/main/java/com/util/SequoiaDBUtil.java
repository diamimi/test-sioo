package com.util;

import com.sequoiadb.base.*;
import com.sequoiadb.datasource.ConnectStrategy;
import com.sequoiadb.datasource.DatasourceOptions;
import com.sequoiadb.net.ConfigOptions;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * SequoiaDB连接工具
 *
 * @author Administrator
 */
public class SequoiaDBUtil {


    private final static SequoiaDBUtil instance = new SequoiaDBUtil();
    public static SequoiadbDatasource ds = null;

    public static SequoiaDBUtil getInstance() {
        if (ds == null) {
            init();
        }
        return instance;
    }

    public static void init() {
        try {
            ArrayList<String> addrs = new ArrayList<String>();
            String user = "sdbsioo";
            String password = "sioo58657686";
            ConfigOptions nwOpt = new ConfigOptions();
            DatasourceOptions dsOpt = new DatasourceOptions();
            // 提供coord节点地址
            addrs.add("101.227.68.16:11810");
            // 设置网络参数
            nwOpt.setConnectTimeout(500);                // 建连超时时间为500ms。
            nwOpt.setMaxAutoConnectRetryTime(0);    // 建连失败后重试时间为0ms。
            // 设置连接池参数
            dsOpt.setMaxCount(50);                            // 连接池最多能提供200个连接。
            dsOpt.setDeltaIncCount(5);                        // 每次增加10个连接。
            dsOpt.setMaxIdleCount(10);                        // 连接池空闲时，保留20个连接。MaxIdleCount限定的空闲连接关闭，并将存活时间过长（连接已停止收发超过keepAliveTimeout时间）的连接关闭。
            dsOpt.setKeepAliveTimeout(0);                    // 池中空闲连接存活时间。单位:毫秒。0表示不关心连接隔多长时间没有收发消息。
            dsOpt.setCheckInterval(60 * 1000);            // 每隔60秒将连接池中多于
            dsOpt.setSyncCoordInterval(0);                    // 向catalog同步coord地址的周期。单位:毫秒。0表示不同步。
            dsOpt.setValidateConnection(false);            // 连接出池时，是否检测连接的可用性，默认不检测。
            dsOpt.setConnectStrategy(ConnectStrategy.BALANCE);    // 默认使用coord地址负载均衡的策略获取连接。
            // 建立连接池
            ds = new SequoiadbDatasource(addrs, user, password, nwOpt, dsOpt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SequoiadbDatasource getDBSource() {
        if (ds == null) {
            init();
        }
        return ds;
    }

    /**
     * 批量插入
     *
     * @param collectionName 表名
     * @param list           插入对象集合
     */
    public void batchInsert(String collectionName, List<BSONObject> list) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).bulkInsert(list, DBCollection.FLG_INSERT_CONTONDUP);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            for (BSONObject obj : list) {
                System.out.println("[data error]" + obj);
            }
            e.printStackTrace();
        }
    }

    /**
     * 单条插入
     *
     * @param collectionName 表名
     * @param obj            插入对象
     */
    public void insert(String collectionName, BSONObject obj) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).insert(obj);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     * @param collectionName 表名
     * @return
     */
    public List<BSONObject> find(String collectionName) {
        return find(collectionName, null);
    }

    /**
     * 查询
     *
     * @param collectionName 表名
     * @param where          查询条件
     * @return
     */
    public List<BSONObject> find(String collectionName, BSONObject where) {
        return find(collectionName, where, null);
    }

    public BSONObject findOne(String collectionName, BSONObject where) {
        List<BSONObject> list = find(collectionName, where, null);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询
     *
     * @param collectionName 表名
     * @param where          查询条件
     * @param returnField    查询字段
     * @return
     */
    public List<BSONObject> find(String collectionName, BSONObject where, BSONObject returnField) {
        return find(collectionName, where, returnField, null);
    }

    /**
     * 查询
     *
     * @param collectionName 表名
     * @param where          查询条件
     * @param returnField    查询字段
     * @param orderBy        排序
     * @return
     */
    public List<BSONObject> find(String collectionName, BSONObject where, BSONObject returnField, BSONObject orderBy) {
        return find(collectionName, where, returnField, orderBy, null);
    }

    /**
     * 查询
     *
     * @param collectionName 表名
     * @param where          查询条件
     * @param returnField    查询字段
     * @param orderBy        排序
     * @param index          指定查询索引
     * @return
     */
    public List<BSONObject> find(String collectionName, BSONObject where, BSONObject returnField, BSONObject orderBy, BSONObject index) {
        return find(collectionName, where, returnField, orderBy, index, 0, -1);
    }

    /**
     * 查询
     *
     * @param collectionName 表名
     * @param where          查询条件
     * @param returnField    查询字段
     * @param orderBy        排序
     * @param index          指定查询索引
     * @param skipRows       跳过结果数
     * @param returnRows     返回结果数
     * @return
     */
    public List<BSONObject> find(String collectionName, BSONObject where, BSONObject returnField, BSONObject orderBy, BSONObject index, long skipRows, long returnRows) {
        List<BSONObject> list = new ArrayList<BSONObject>();
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            DBCursor cursor = cs.getCollection(collectionName).query(where, returnField, orderBy, index, skipRows, returnRows);
            while (cursor.hasNext()) {
                list.add(cursor.getNext());
            }
            cursor.close();
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 删除
     *
     * @param collectionName 表名
     * @param where          删除条件
     */
    public void delete(String collectionName, BSONObject where) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).delete(where);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @param collectionName 表名
     * @param where          删除条件
     * @param index          指定索引
     */
    public void delete(String collectionName, BSONObject where, BSONObject index) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).delete(where, index);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 更新文档
     *
     * @param collectionName 表名
     * @param where          更新条件
     * @param setField       更新字段
     * @param index          索引
     */
    public void update(String collectionName, BSONObject where, BSONObject setField, BSONObject index) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).update(where, new BasicBSONObject("$set", setField), index);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void upsert(String collectionName, BSONObject where, BSONObject setField, BSONObject index) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).upsert(where, new BasicBSONObject("$set", setField), index);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void upsertExsit(String collectionName, BSONObject where, BSONObject setField, BSONObject index) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).upsert(where, setField, index);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void update(String collectionName, DBQuery dbQuery) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).update(dbQuery);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 增加值
     *
     * @param collectionName
     * @param where
     * @param setField
     * @param index
     */
    public void updateInc(String collectionName, BSONObject where, BSONObject setField, BSONObject index) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            cs.getCollection(collectionName).upsert(where, new BasicBSONObject("$inc", setField), index);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 统计数量
     *
     * @param collectionName 表名
     * @return
     */
    public long count(String collectionName) {
        long count = 0;
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            count = cs.getCollection(collectionName).getCount();
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 统计数量
     *
     * @param collectionName 表名
     * @param where          统计条件
     * @return
     */
    public long count(String collectionName, BSONObject where) {
        long count = 0;
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            count = cs.getCollection(collectionName).getCount(where);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 统计数量
     *
     * @param collectionName 表名
     * @param where          统计条件
     * @param index          索引
     * @return
     */
    public long count(String collectionName, BSONObject where, BSONObject index) {
        long count = 0;
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            count = cs.getCollection(collectionName).getCount(where, index);
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    public void close() {
        ds.close();
    }

    public void createHistoryTable(String collectionName) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            if (!cs.isCollectionExist(collectionName)) {
                DBCollection dbc = cs.createCollection(collectionName);
                dbc.createIndex("_hisid", new BasicBSONObject("hisid", -1), true, false);
                dbc.createIndex("_uid", new BasicBSONObject("uid", 1), false, false);
            }
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 删除表
     *
     * @param collectionName 表名
     */
    public void deleteHistoryTable(String collectionName) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            if (cs.isCollectionExist(collectionName)) {
                cs.dropCollection(collectionName);
            }
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createPushRptTable(String collectionName) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            if (!cs.isCollectionExist(collectionName)) {
                DBCollection dbc = cs.createCollection(collectionName);
                dbc.createIndex("_uid", new BasicBSONObject("uid", 1), false, false);
            }
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createPushMoTable(String collectionName) {
        try {
            Sequoiadb sdb = ds.getConnection();
            CollectionSpace cs = sdb.getCollectionSpace("SMS");
            if (!cs.isCollectionExist(collectionName)) {
                DBCollection dbc = cs.createCollection(collectionName);
                dbc.createIndex("_uid", new BasicBSONObject("uid", 1), false, false);
            }
            ds.releaseConnection(sdb);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

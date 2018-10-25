package com.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.util.SafeEncoder;

public class RedisUtil {

    private static Logger logger = Logger.getLogger(RedisUtil.class);

    public static JedisPool jedisPool = null;

    public static RedisUtil instance = null;

    public static synchronized RedisUtil getInstance() {
        if (instance == null) {
            instance = new RedisUtil();
        }
        return instance;
    }

    public RedisUtil() {
        init();
    }

    public static void init() {
//        String redisHost = "127.0.0.1";
		String redisHost = "192.168.8.16";
        //String redisHost = "102.227.68.16";
        int redisPort = 6379;
       // String redis_password = "sioo58657686";
        int maxActive = 100;
        int maxIdle = 20;
        JedisPoolConfig config = new JedisPoolConfig();
        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        config.setBlockWhenExhausted(true);
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setTestOnBorrow(false);
//        jedisPool = new JedisPool(config, redisHost, redisPort, 100000, redis_password);
		jedisPool = new JedisPool(config, redisHost, redisPort, 100000);
        logger.info("Redis: " + redisHost + ":" + redisPort);
    }

    public void putPidToMsgid(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(key, 604800, value);//存7天
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public String getPidToMsgid(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = getJedis();
            result = jedis.get(key);
            if (result != null) {
                jedis.del(key);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return result;
    }

    public long automaticHisId(String key) {
        Jedis jedis = null;
        long id = 0;
        try {
            jedis = getJedis();
            id = jedis.incr(key);
            if (id > 2000000000) {
                jedis.set(key, 100000000 + "");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return id;
    }

    public long automaticPid() {
        Jedis jedis = null;
        long id = 0;
        try {
            jedis = getJedis();
            id = jedis.incr("api_pid");
            if (id > 2100000000) {
                jedis.set("api_pid", 10000000 + "");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return id;
    }

    public long automaticHisId1(String key) {
        Jedis jedis = null;
        long id = 0;
        try {
            jedis = getJedis();
            jedis.set("api_hisid", "25244085");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return id;
    }

    public void setObject(String key, Object value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.setex(SerializesUtils.serialize(key), seconds, SerializesUtils.serialize(value));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setObject(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(SerializesUtils.serialize(key), SerializesUtils.serialize(value));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public void setBalance(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(SerializesUtils.serialize(key), SafeEncoder.encode(String.valueOf(value)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 扣费
     *
     * @param uid   用户
     * @param count 扣费条数
     * @return 大于0则正常扣费, 小于0则计费失败.
     */
    public int chargedBy(int uid, int count) {
        Jedis jedis = null;
        int balance = -1;
        try {
            jedis = getJedis();
            balance = jedis.decrBy(SerializesUtils.serialize("balance_" + uid), count).intValue();
            if (balance < 0) {//余额小于0,本次扣费无效
                jedis.incrBy(SerializesUtils.serialize("balance_" + uid), count);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return balance;
    }

    /**
     * 充值
     *
     * @param uid   用户
     * @param count 充值条数
     * @return 充值后条数.
     */
    public int accountBy(int uid, int count) {
        Jedis jedis = null;
        int balance = -1;
        try {
            jedis = getJedis();
            balance = jedis.incrBy(SerializesUtils.serialize("balance_" + uid), count).intValue();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return balance;
    }

    public Object getObject(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            byte[] b = jedis.get(SerializesUtils.serialize(key));
            if (b != null) {
                return SerializesUtils.deserialize(b);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public void delete(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.del(SerializesUtils.serialize(key));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * 获取余额
     *
     * @param uid
     * @return
     */
    public int getBalance(int uid) {
        Jedis jedis = null;
        int balance = -1;
        try {
            jedis = getJedis();
            balance = Integer.valueOf(SafeEncoder.encode(jedis.get(SerializesUtils.serialize("balance_" + uid))).toString());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return balance;
    }

    public void close() {
        jedisPool.close();
    }

    public static synchronized Jedis getJedis() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

}

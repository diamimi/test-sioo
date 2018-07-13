package com.util;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpIllegalStateException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Configuration
public class RabbitMQProducerUtil {

    @Value("${rabbit.host}")
    public String host;

    @Value("${rabbit.port}")
    public String port;

    @Value("${rabbit.username}")
    public String username;

    @Value("${rabbit.password}")
    public String password;

    private static final Logger logger = Logger.getLogger(RabbitMQProducerUtil.class);
    // 连接配置
    private ConnectionFactory factory = null;
    public Connection connection = null;
    public Channel channel = null;
    private DeclareOk declareOk;
    public static RabbitMQProducerUtil producerUtil;
    private volatile MessageConverter messageConverter = new SimpleMessageConverter();


    /**
     * 发送
     *
     * @param queueName 队列名称
     * @param obj       发送对象
     * @throws IOException
     */
    public void send(String queueName, Object obj)
            throws IOException {
        channel.basicPublish("", queueName, com.rabbitmq.client.MessageProperties.PERSISTENT_TEXT_PLAIN, serialize(obj));
    }

    /**
     * 发送
     *
     * @param queueName 队列名称
     * @param obj       发送对象
     * @param priority  优先级,最大10,最小为0,数值越大优先级越高
     * @throws IOException
     */
    public void sendPriority(String queueName, Object obj, int priority)
            throws IOException {
        BasicProperties.Builder properties = new BasicProperties.Builder();
        properties.priority(priority);
        channel.basicPublish("", queueName, properties.build(), serialize(obj));
    }

    @Bean
    public RabbitMQProducerUtil getBean() {
        try {
            // 初始化
            factory = new ConnectionFactory();
            factory.setHost(host);
            factory.setPort(Integer.parseInt(port));
            factory.setUsername(username);
            factory.setPassword(password);
            factory.setAutomaticRecoveryEnabled(true);
            factory.setRequestedHeartbeat(20);//心跳时间s
            factory.setNetworkRecoveryInterval(30000);//网络重连失败重试间隔时间ms
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info("[RabbitMQ] Initialization failed...");
            logger.info("host:" + factory.getHost() + ",port:" + factory.getPort() + ",username:" + factory.getUsername() + ",password:" + factory.getPassword());
            logger.error(e.getMessage(), e);
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            logger.info("[RabbitMQ] Initialization failed...");
            logger.info("host:" + factory.getHost() + ",port:" + factory.getPort() + ",username:" + factory.getUsername() + ",password:" + factory.getPassword());
            logger.error(e.getMessage(), e);
        }
        return new RabbitMQProducerUtil();
    }


    /**
     * 单个获取
     *
     * @param queueName 队列名称
     * @return
     */
    public Object getObj(String queueName) {
        try {
            // 持久化//SUBMIT_CMPP_PRIORITY
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-max-priority", 10);
            channel.queueDeclare(queueName, true, false, false, args);
            // 流量控制
            channel.basicQos(1);
            return receive(channel, queueName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 批量获取
     *
     * @param queueName 队列名称
     * @return
     */
    public List<Object> getObjList(String queueName, int limit) {
        List<Object> list = new ArrayList<Object>();
        try {
            // 持久化//SUBMIT_CMPP_PRIORITY
            Map<String, Object> args = new HashMap<String, Object>();
            args.put("x-max-priority", 10);
            channel.queueDeclare(queueName, true, false, false, args);
            // 流量控制
            channel.basicQos(1);
            // 声明消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, false, consumer);
            for (int i = 0; i < limit; i++) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                Object obj = deSerialize(delivery.getBody());
                if (obj != null) {
                    list.add(obj);
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // 反馈给服务器表示收到信息
                }
            }
            return list;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 监听Test
     *
     * @param queueName
     * @param exchangeName
     */
    public void listenerTest(String queueName, String exchangeName) {
        try {
            // 声明direct类型转发器//SUBMIT_EXCHANGE
            channel.exchangeDeclare(exchangeName, "direct", true);
            // 持久化//SUBMIT_CMPP_PRIORITY
            channel.queueDeclare(queueName, false, false, false, null);
            // 流量控制
            channel.basicQos(1);
            // 将消息队列绑定到Exchange
            channel.queueBind(queueName, exchangeName, queueName);
            // 声明消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queueName, false, consumer);
            while (true) {
                // 等待队列推送消息
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                Object obj = deSerialize(delivery.getBody());
                if (obj != null) {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // 反馈给服务器表示收到信息
                    /**
                     * 在这里把obj放入缓存队列即可
                     */
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取队列长度
     *
     * @param queueName 队列名称
     * @return
     */
    public int getQueueListSize(String queueName) {
        int count = 0;
        try {
            declareOk = channel.queueDeclare(queueName, false, false, false,
                    null);
            declareOk.getMessageCount();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info("[RabbitMQ] Get Queue Size failed...");
            logger.error(e.getMessage(), e);
        }
        return count;
    }

    private Object receive(Channel channel, String QUEUE_NAME) throws Exception {
        // 声明消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);
        while (true) {
            // 等待队列推送消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            Object obj = deSerialize(delivery.getBody());
            if (obj != null) {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false); // 反馈给服务器表示收到信息
            }
            return obj;
        }
    }

    public void close() {
        try {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.info(
                    "[RabbitMQ] channel/connection close failed...");
            logger.error(e.getMessage(), e);
        } catch (TimeoutException e) {
            // TODO Auto-generated catch block
            logger.info(
                    "[RabbitMQ] channel/connection close failed...");
            logger.error(e.getMessage(), e);
        }
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream baos = null;
        HessianOutput output = null;
        try {
            baos = new ByteArrayOutputStream(1024);
            output = new HessianOutput(baos);
            output.startCall();
            output.writeObject(obj);
            output.completeCall();
        } catch (final IOException ex) {
            throw ex;
        } finally {
            if (output != null) {
                try {
                    baos.close();
                } catch (final IOException ex) {
                    logger.info("[RabbitMQ] serializee Exception...");
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return baos != null ? baos.toByteArray() : null;
    }

    public Object deSerialize(byte[] in) throws IOException {
        Object obj = null;
        ByteArrayInputStream bais = null;
        HessianInput input = null;
        try {
            bais = new ByteArrayInputStream(in);
            input = new HessianInput(bais);
            input.startReply();
            obj = input.readObject();
            input.completeReply();
        } catch (final IOException ex) {
            throw ex;
        } catch (final Throwable e) {
            logger.info("[RabbitMQ] Failed to decode object...");
            logger.error(e.getMessage(), e);
        } finally {
            if (input != null) {
                try {
                    bais.close();
                } catch (final IOException ex) {
                    logger.info(
                            "[RabbitMQ] Failed to close stream...");
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
        return obj;
    }

    protected Message convertMessageIfNecessary(final Object object) {
        if (object instanceof Message) {
            return (Message) object;
        }
        return getRequiredMessageConverter().toMessage(object,
                new MessageProperties());
    }

    private MessageConverter getRequiredMessageConverter()
            throws IllegalStateException {
        MessageConverter converter = this.getMessageConverter();
        if (converter == null) {
            throw new AmqpIllegalStateException(
                    "No 'messageConverter' specified. Check configuration of RabbitTemplate.");
        }
        return converter;
    }

    public MessageConverter getMessageConverter() {
        return messageConverter;
    }

}

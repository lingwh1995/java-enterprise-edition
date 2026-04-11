package org.bluebridge.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class MqttConfig {

    @Resource
    private MqttProperties mqttProperties;

    /**
     * 1.配置mqtt连接属性
     * @return
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置MQTT版本为自动协商
        //options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_DEFAULT);
        // 设置MQTT版本为3.1.1
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        // 设置服务器地址
        options.setServerURIs(new String[]{ mqttProperties.getBrokerUrl() });
        // 设置用户名
        options.setUserName(mqttProperties.getUsername());
        // 设置密码
        options.setPassword(mqttProperties.getPassword().toCharArray());
        // 设置连接超时时间
        options.setConnectionTimeout(mqttProperties.getTimeout());
        // 设置心跳时间
        options.setKeepAliveInterval(mqttProperties.getKeepalive());
        // 接受离线消息  需要接收离线消息，使用false，不需要离线消息，使用 true
        options.setCleanSession(false);
        // 自动重连
        options.setAutomaticReconnect(true);
        return options;
    }

    /**
     * 2.创建客户端管理器
     * @return
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        // 设置连接属性
        factory.setConnectionOptions(mqttConnectOptions());
        return factory;
    }

    /**
     * 3.创建入站通道
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 4.配置入站通道（接收消息）
     * @return
     */
    @Bean
    public MessageProducer mqttInbound() {
        // 获取其他订阅的主题
        List<String> topics = mqttProperties.getTopics();
        // 把默认订阅主题加入到订阅列表中
        topics.add(0, mqttProperties.getDefaultTopic());
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        mqttProperties.getClientId() + "-inbound",
                        mqttClientFactory(),
                        topics.toArray(new String[0]));
        adapter.setCompletionTimeout(5000);
        // 服务质量等级
        adapter.setQos(1);
        // 设置入站通道
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * 5.创建出站通道
     * @return
     */
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * 6.配置出站通道（发送消息）
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + "-outbound",
                mqttClientFactory());
        // 异步发送（发送消息时将不会阻塞）
        handler.setAsync(true);
        // 默认发布主题
        handler.setDefaultTopic(mqttProperties.getDefaultTopic());
        //设置默认QoS
        handler.setDefaultQos(mqttProperties.getQos());
        // 创建Paho消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        //发送默认按字节类型发送消息
//        defaultPahoMessageConverter.setPayloadAsBytes(true);
        // 设置消息转换器
        handler.setConverter(defaultPahoMessageConverter);
        return handler;
    }

}

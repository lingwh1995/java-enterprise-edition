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
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class MqttConfig {

    @Resource
    private MqttProperties mqttProperties;

    /**
     * 1.创建MQTT连接工厂
     * @return
     */
    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 设置MQTT版本为自动协商
        //options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_DEFAULT);
        // 设置MQTT版本为3.1.1
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        options.setServerURIs(new String[]{ mqttProperties.getBrokerUrl() });
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        // 清除会话
        options.setCleanSession(true);
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
        factory.setConnectionOptions(mqttConnectOptions());
        return factory;
    }

    /**
     * 配置入站通道（接收消息）
     * @return
     */
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
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
        // 设置输出通道
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    /**
     * 4. 配置出站通道（发送消息）
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler(
                mqttProperties.getClientId() + "-outbound",
                mqttClientFactory());
        // 异步发送
        handler.setAsync(true);
        // 默认发布主题
        handler.setDefaultTopic(mqttProperties.getDefaultTopic());
        return handler;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

}

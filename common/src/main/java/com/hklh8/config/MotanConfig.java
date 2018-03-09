package com.hklh8.config;

import com.weibo.api.motan.config.springsupport.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by GouBo on 2017/12/29.
 */
@Configuration
public class MotanConfig {

    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.hklh8");
        return motanAnnotationBean;
    }

    @Bean(name = "server_protocol")
    public ProtocolConfigBean protocolConfig1() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setMaxContentLength(1048576);
        config.setMaxServerConnection(2000);
        config.setMinWorkerThread(20);
        config.setMaxWorkerThread(800);
        config.setMaxClientConnection(200);
        return config;
    }

    @Bean(name = "server_register")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        /*config.setRegProtocol("local");*/
        config.setRegProtocol("zookeeper");
        config.setAddress("127.0.0.1:2181");
        config.setName("bar_zookeeper1");
        return config;
    }

    @Bean
    public BasicServiceConfigBean baseServiceConfig() {
        BasicServiceConfigBean config = new BasicServiceConfigBean();
        config.setExport("server_protocol:8002");
        config.setAccessLog(false);
        config.setShareChannel(true);
        config.setRegistry("server_register");
        return config;
    }

    //////////////
    @Bean(name = "client_protocol")
    public ProtocolConfigBean protocolConfig2() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setName("motan");
        config.setMaxContentLength(1048576);
        config.setDefault(true);
        config.setHaStrategy("failover");
        config.setLoadbalance("roundrobin");
        return config;
    }

    @Bean(name = "client_registry")
    public RegistryConfigBean registryConfig1() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress("127.0.0.1:2181");
        config.setName("bar_zookeeper");
/*        config.setRegProtocol("direct");
        config.setAddress("127.0.0.1:8001");*/
        return config;
    }


    @Bean
    public BasicRefererConfigBean baseRefererConfig() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("client_protocol");
        config.setRegistry("client_registry");
        config.setCheck(false);
        config.setAccessLog(true);
        config.setRetries(2);
        config.setThrowException(true);
        config.setRequestTimeout(8000);
        return config;
    }
}

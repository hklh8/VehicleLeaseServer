package com.hklh8.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DruidDBConfig {

    private Logger logger = Logger.getLogger(DruidDBConfig.class);

    @Value("${mysql.url}")
    private String url;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    @Value("${mysql.pool.init}")
    private int init;

    @Value("${mysql.pool.minIdle}")
    private int minIdle;

    @Value("${mysql.pool.maxActive}")
    private int maxActive;

    @Bean(destroyMethod = "close", initMethod = "init")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        /*基本属性 url、user、password*/
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        /*配置初始化大小、最小、最大*/
        dataSource.setInitialSize(init);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);

        /*配置获取连接等待超时的时间*/
        dataSource.setMaxWait(60000);

        /*配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒*/
        dataSource.setTimeBetweenEvictionRunsMillis(60000);

        /*配置一个连接在池中最小生存的时间，单位是毫秒*/
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        return dataSource;
    }
}


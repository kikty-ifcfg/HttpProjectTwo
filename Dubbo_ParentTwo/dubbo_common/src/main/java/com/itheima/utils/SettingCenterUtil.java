package com.itheima.utils;/*
@Author:李正铠
@Date:2020年03月28日23时38分
*/

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringValueResolver;
import org.springframework.web.context.support.XmlWebApplicationContext;

import java.util.Properties;

public class SettingCenterUtil extends PropertyPlaceholderConfigurer implements ApplicationContextAware {

    XmlWebApplicationContext applicationContext;

    private void loadZk(Properties props) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3, 10);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", 3000, 1000, retryPolicy);
        client.start();
        try {
            byte[] url = client.getData().forPath("/config/jdbc.url");
            props.setProperty("jdbc.url", new String(url));
            byte[] user = client.getData().forPath("/config/jdbc.user");
            props.setProperty("jdbc.user", new String(user));
            byte[] password = client.getData().forPath("/config/jdbc.password");
            props.setProperty("jdbc.password", new String(password));
            byte[] driver = client.getData().forPath("/config/jdbc.driver");
            props.setProperty("jdbc.driver", new String(driver));

        } catch (Exception e) {
            e.printStackTrace();
        }
        client.close();
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        //载入zookeeper配置信息,即从zookeeper中获取数据源的连接信息
        loadZk(props);
        addWatch(props);
        //添加watch机制,实时监听配置中心的变化,一旦数据变化,重新载入最新的Zookeeper中的配置,放置到props中
        super.processProperties(beanFactoryToProcess, props);
    }

    private void addWatch(Properties props) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3, 10);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1", 3000, 1000, retryPolicy);
        //添加监听,就不要关闭client
        TreeCache treeCache = new TreeCache(client, "/config");

        try {
            treeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if (event.getType() == TreeCacheEvent.Type.NODE_UPDATED) {
                    if (event.getData().getPath().equals("/config/jdbc.url")) {
                        props.setProperty("jdbc.url", new String(event.getData().getData()));
                    } else if (event.getData().getPath().equals("/config/jdbc.driver")) {
                        props.setProperty("jdbc.driver", new String(event.getData().getData()));
                    } else if (event.getData().getPath().equals("/config/jdbc.user")) {
                        props.setProperty("jdbc.user", new String(event.getData().getData()));
                    } else if (event.getData().getPath().equals("/config/jdbc.password")) {
                        props.setProperty("jdbc.password", new String(event.getData().getData()));
                    }
                    applicationContext.refresh();


                }
            }
        });


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=(XmlWebApplicationContext) applicationContext;
    }
}


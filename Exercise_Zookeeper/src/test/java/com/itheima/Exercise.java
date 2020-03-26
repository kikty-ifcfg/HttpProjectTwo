package com.itheima;/*
@Author:李正铠
@Date:2020年03月26日19时31分
*/

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Exercise {

    private CuratorFramework client;

    @Before
    public void before() {
        /**
         *  RetryPolicy： 失败的重试策略的公共接口
         *  ExponentialBackoffRetry是 公共接口的其中一个实现类
         *      参数1： 初始化sleep的时间，用于计算之后的每次重试的sleep时间
         *      参数2：最大重试次数
         参数3（可以省略）：最大sleep时间，如果上述的当前sleep计算出来比这个大，那么sleep用这个时间
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3, 10);

        /**
         *  创建客户端
         * 参数1：连接的ip地址和端口号
         * 参数2：会话超时时间，单位毫秒
         * 参数3：连接超时时间，单位毫秒
         * 参数4：失败重试策略
         */
        client = CuratorFrameworkFactory.newClient("192.168.13.100:2181", 3000, 1000, retryPolicy);
        client.start();

    }

    @After
    public void after() {
        client.close();
    }


    @Test
    public void createTest() throws Exception {
        /**
         * 功能描述: <br>
         * 〈〉创建节点以及子节点
         * @Param: []
         * @Return: void
         * @Date: 2020/3/26 21:02
         */

        //创建节点
//              client.create().forPath("/app2","data".getBytes());

        //创建持久节点,同时创建多层节点   PERSISTENT
//            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/app1/app1_1","data".getBytes());

        //创建带有序号的持久多层节点  PERSISTENT_SEQUENTIAL
//            client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath("/app3/app3_1","data".getBytes());

        //创建临时节点  当客户端close时,节点消失
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath("/lzk/lzk_1","data".getBytes());

        //创建临时节点,带有序号
//        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/lzk2/lzk_1","data".getBytes());

        //睡眠五秒
        Thread.sleep(5000);

        //关闭客户端
        client.close();

    }


    @Test
    public void updateTest() throws Exception {
        /**
         * 功能描述: <br>
         * 〈〉修改节点及子节点数据
         * @Param: []
         * @Return: void
         * @Date: 2020/3/26 21:03
         */

        //修改节点数据
//        client.setData().forPath("/lzk2","test".getBytes());
//        client.setData().forPath("/lzk5/lzk_1","testData".getBytes());
    }

    @Test
    public void queryTest() throws Exception {
        /**
         * 功能描述: <br>
         * 〈〉查询节点数据
         * @Param: []
         * @Return: void
         * @Date: 2020/3/26 21:03
         */
        byte[] bytes = client.getData().forPath("/lzk5");
        System.out.println(bytes);
        //注意:String是java.lang包中的类
        System.out.println(new String(bytes));
    }

    @Test
    public void deleteTest() throws Exception {
        /**
         * 功能描述: <br>
         * 〈〉删除节点及递归删除
         * @Param: []
         * @Return: void
         * @Date: 2020/3/26 21:03
         */
        //删除一个节点
//        client.delete().forPath("/lzk");

        //递归删除
//        client.delete().deletingChildrenIfNeeded().forPath("/app1");

        //强制保证删除一个节点
//        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/app10000000000");
    }


}

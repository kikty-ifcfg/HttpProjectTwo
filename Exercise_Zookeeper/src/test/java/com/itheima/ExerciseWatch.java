package com.itheima;/*
@Author:李正铠
@Date:2020年03月26日21时04分
*/

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExerciseWatch {



    /**
     * 功能描述: <br>
     * 〈〉TreeCache不但能够监听自身节点的变化、也能够监听子节点的变化
     */


    @Test
    public void treeCacheTest() throws Exception {
          /**
                   *  RetryPolicy： 失败的重试策略的公共接口
                   *  ExponentialBackoffRetry是 公共接口的其中一个实现类
                   *      参数1： 初始化sleep的时间，用于计算之后的每次重试的sleep时间
                   *      参数2：最大重试次数
                   参数3（可以省略）：最大sleep时间，如果上述的当前sleep计算出来比这个大，那么sleep用这个时间
                   */
                  RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3,10);

                  /**
                   *  创建客户端
                   * 参数1：连接的ip地址和端口号
                   * 参数2：会话超时时间，单位毫秒
                   * 参数3：连接超时时间，单位毫秒
                   * 参数4：失败重试策略
                   */
                  CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.13.100:2181", 3000, 1000, retryPolicy);
                  client.start();

        /**参数一:连接的客户端对象
         * 参数二:表示监听的路径地址
         */
        TreeCache treeCache =new TreeCache(client,"/lzk");
        //启动监听
        treeCache.start();
        System.out.println(treeCache.getCurrentData("/lzk"));

        //添加监听
        treeCache.getListenable().addListener(new TreeCacheListener() {
            //表示当根节点或者子节点的数据发生变化的时候就会执行该方法
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                if (event.getType()==TreeCacheEvent.Type.NODE_ADDED){
                    System.out.println("节点添加,执行监听");
                    String path = event.getData().getPath();
                    byte[] data = event.getData().getData();
                    String s = new String(data);
                    System.out.println("当前路径是:"+path+"| 数据是:"+s);
                    System.out.println("-------------------");
                }else if (event.getType()==TreeCacheEvent.Type.NODE_REMOVED){
                    System.out.println(event.getData().getPath()+"节点移除");
                    System.out.println("-------------------");
                }else if (event.getType()==TreeCacheEvent.Type.NODE_UPDATED){
                    System.out.println("节点修改,执行监听");
                    String path = event.getData().getPath();
                    byte[] data = event.getData().getData();
                    String s = new String(data);
                    System.out.println("当前路径是:"+path+"| 修改的数据是"+s);
                    System.out.println("-------------------");
                }else if (event.getType()==TreeCacheEvent.Type.INITIALIZED){
                    System.out.println("初始化完成");
                    System.out.println("-------------------");
                }else if (event.getType()==TreeCacheEvent.Type.CONNECTION_SUSPENDED){
                    System.out.println("连接超时");
                    System.out.println("-------------------");
                }else if (event.getType()==TreeCacheEvent.Type.CONNECTION_RECONNECTED){
                    System.out.println("重新连接");
                    System.out.println("-------------------");
                }else if (event.getType()==TreeCacheEvent.Type.CONNECTION_LOST){
                    System.out.println("连接丢失");
                    System.out.println("-------------------");
                }
            }
        });
        //阻塞线程
        System.in.read();

                  //关闭客户端
                  client.close();

    }



}

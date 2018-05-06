package com.chen.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.PrefixFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Hbase操作工具类
 */
public class HBaseUtils {

    HBaseAdmin admin = null;
    Configuration configuration = null;

    private HBaseUtils(){
        configuration = new Configuration();
        configuration.set("hbase.zookeeper.quorum","120.79.204.164:2181");
        configuration.set("hbase.rootdir","hdfs://120.79.204.164/hbase");
        try {
            admin = new HBaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static HBaseUtils hBaseUtils = null;
    public static synchronized HBaseUtils getInstance(){
        if(null == hBaseUtils){
            hBaseUtils = new HBaseUtils();
        }
        return hBaseUtils;
    }

    /**
     * 根据表名获取到HTable实例
     * @param tableName
     * @return
     */
    public HTable getTable(String tableName){
        HTable hTable = null;
        try {
            hTable = new HTable(configuration,tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hTable;
    }

    /**
     * 根据表名和输入条件获取HBase记录数
     * @param tableName
     * @param condition
     * @return
     */
    public Map<String,Long> query(String tableName,String condition) throws IOException{
        Map<String,Long> map = new HashMap<>();
        HTable hTable = getTable(tableName);
        System.out.println("获取HTable.....");
        String cf = "info";
        String qualifier = "click_count";

        Scan scan = new Scan();

        Filter filter = new PrefixFilter(Bytes.toBytes(condition));
        scan.setFilter(filter);
        ResultScanner rs =  hTable.getScanner(scan);
        System.out.println("ResultScanner.....");
       for (Result result :rs){
           String row = Bytes.toString(result.getRow());
            long clickCount = Bytes.toLong(result.getValue(cf.getBytes(),qualifier.getBytes()));
            map.put(row,clickCount);
       }
        System.out.println("返回数据.....");
        return map;
    }

}

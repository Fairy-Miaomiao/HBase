package test;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HBaseFunction {

    private static Configuration conf = null;
    private static Connection conn = null;
    private static Admin admin = null;
    public static AtomicInteger count = new AtomicInteger();

    static {
        conf = HBaseConfiguration.create();
        //conf.set("zookeeper.znode.parent", "/hbase-unsecure");
        conf.set("hbase.zookeeper.quorum", "10.148.137.143");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
    }

    static {
        try {
            conn = ConnectionFactory.createConnection();
            admin = conn.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createTable(String table, String[] columns) {
        /*try {
            connection = ConnectionFactory.createConnection(config);
            admin = connection.getAdmin();

            String tableName = "users";

            if (!admin.isTableAvailable(TableName.valueOf(tableName))) {
                HTableDescriptor hbaseTable = new HTableDescriptor(TableName.valueOf(tableName));
                hbaseTable.addFamily(new HColumnDescriptor("name"));
                hbaseTable.addFamily(new HColumnDescriptor("contact_info"));
                hbaseTable.addFamily(new HColumnDescriptor("personal_info"));
                admin.createTable(hbaseTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (admin != null) {
                    admin.close();
                }

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }*/

        try {
            if (admin.tableExists(TableName.valueOf(table))) {
                System.out.println("Table exists!");
            } else {
                HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(table));
                for (int i = 0; i < columns.length; i++) {
                    HColumnDescriptor desc = new HColumnDescriptor(columns[i]);
                    tableDesc.addFamily(desc);
                }
                admin.createTable(tableDesc);
                System.out.println("Finish create table: " + table);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void putData(String tableName, String rowKey, String family, String qualifier, String value) {
        try {
            Table table = conn.getTable(TableName.valueOf(tableName));
            Put put = new Put(Bytes.toBytes(rowKey));
            put.add(Bytes.toBytes(family), Bytes.toBytes(qualifier), Bytes.toBytes(value));
            table.put(put);
            System.out.println("add "+rowKey+":"+family+"-"+qualifier+":"+value+" in table:"+tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<KeyValue> getByFilter(String tableName, List<String> arr){
        List<KeyValue> res = new ArrayList<KeyValue>();
        try{
            Table table = conn.getTable(TableName.valueOf(tableName));
            FilterList filterList = new FilterList();
            Scan s1 = new Scan();
            for(String v: arr){
                String[] s = v.split(",");
                filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(s[0]), Bytes.toBytes(s[1]),
                        CompareFilter.CompareOp.EQUAL, Bytes.toBytes(s[2])));
            }
            s1.setFilter(filterList);
            ResultScanner ResultScannerFilterList = table.getScanner(s1);
            for(Result rr = ResultScannerFilterList.next(); rr != null; rr = ResultScannerFilterList.next()){
                for(KeyValue kv: rr.list()){
                    res.add(kv);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }

    public static void addNewColumn(String tableName, String columnName){
        try{
            admin.disableTable(TableName.valueOf(tableName));
            HTableDescriptor desc = admin.getTableDescriptor(TableName.valueOf(tableName));
            HColumnDescriptor cdesc = new HColumnDescriptor(columnName);
            desc.addFamily(cdesc);
            admin.addColumn(TableName.valueOf(tableName), cdesc);
            admin.enableTableAsync(TableName.valueOf(tableName));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void delOneRecordFamily(String tableName, String rowKey, String family){
        try{
            Table table = conn.getTable(TableName.valueOf(tableName));
            List<Delete> list = new ArrayList<Delete>();
            Delete del = new Delete(rowKey.getBytes());
            del.deleteFamily(Bytes.toBytes(family));
            list.add(del);
            table.delete(list);
            System.out.println("Del record:" + rowKey + "-" + family + " ... Done.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteTable(String tablename) {
        try {
            admin.disableTable(TableName.valueOf(tablename));
            admin.deleteTable(TableName.valueOf(tablename));
            System.out.println("Finish delete table:" + tablename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


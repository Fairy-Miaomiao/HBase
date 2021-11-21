package test;

import org.apache.hadoop.hbase.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class HBaseMain {
    public static void main(String[] args) {
        //设置表名
        String table="StuClss";
        // 创建表
        HBaseFunction.createTable(table, new String[]{"Student", "Math", "Computer", "English"});
        // 添加表信息
        HBaseFunction.putData(table, "2015001", "Student", "S_No", "2015001");
        HBaseFunction.putData(table, "2015001", "Student", "S_Name", "Li Lei");
        HBaseFunction.putData(table, "2015001", "Student", "S_Sex", "male");
        HBaseFunction.putData(table, "2015001", "Student", "S_Age", "23");

        HBaseFunction.putData(table, "2015002", "Student", "S_No", "2015002");
        HBaseFunction.putData(table, "2015002", "Student", "S_Name", "Han Meimei");
        HBaseFunction.putData(table, "2015002", "Student", "S_Sex", "female");
        HBaseFunction.putData(table, "2015002", "Student", "S_Age", "22");

        HBaseFunction.putData(table, "2015003", "Student", "S_No", "2015003");
        HBaseFunction.putData(table, "2015003", "Student", "S_Name", "Zhang San");
        HBaseFunction.putData(table, "2015003", "Student", "S_Sex", "male");
        HBaseFunction.putData(table, "2015003", "Student", "S_Age", "24");

        HBaseFunction.putData(table, "2015001", "Math", "C_No", "123001");
        HBaseFunction.putData(table, "2015001", "Math", "C_Name", "Math");
        HBaseFunction.putData(table, "2015001", "Math", "C_Credit", "2");
        HBaseFunction.putData(table, "2015001", "English", "C_No", "123003");
        HBaseFunction.putData(table, "2015001", "English", "C_Name", "English");
        HBaseFunction.putData(table, "2015001", "English", "C_Credit", "3");

        HBaseFunction.putData(table, "2015002", "Computer", "C_No", "123002");
        HBaseFunction.putData(table, "2015002", "Computer", "C_Name", "Computer Science");
        HBaseFunction.putData(table, "2015002", "Computer", "C_Credit", "5");
        HBaseFunction.putData(table, "2015002", "English", "C_No", "123003");
        HBaseFunction.putData(table, "2015002", "English", "C_Name", "English");
        HBaseFunction.putData(table, "2015002", "English", "C_Credit", "3");

        HBaseFunction.putData(table, "2015003", "Math", "C_No", "123001");
        HBaseFunction.putData(table, "2015003", "Math", "C_Name", "Math");
        HBaseFunction.putData(table, "2015003", "Math", "C_Credit", "2");
        HBaseFunction.putData(table, "2015003", "Computer", "C_No", "123002");
        HBaseFunction.putData(table, "2015003", "Computer", "C_Name", "Computer Science");
        HBaseFunction.putData(table, "2015003", "Computer", "C_Credit", "5");

        HBaseFunction.putData(table, "2015001", "Math", "SC_Score", "86");
        HBaseFunction.putData(table, "2015001", "English", "SC_Score", "69");

        HBaseFunction.putData(table, "2015002", "Computer", "SC_Score", "77");
        HBaseFunction.putData(table, "2015002", "English", "SC_Score", "99");

        HBaseFunction.putData(table, "2015003", "Math", "SC_Score", "98");
        HBaseFunction.putData(table, "2015003", "Computer", "SC_Score", "95");


        // 查询选修Computer Science的学生成绩
        System.out.println("以下是选修Computer Science的学生和他们的成绩:");
        List<String> arr = new ArrayList<String>();
        arr.add("Computer,C_Name,Computer Science");
        List<KeyValue> res = HBaseFunction.getByFilter(table, arr);
        for(KeyValue kv: res){
            if(new String(kv.getFamily()).equals("Computer") && new String(kv.getQualifier()).equals("SC_Score")){
                System.out.print("学号为 " + new String(kv.getRow()));
                System.out.println(" 的学生的Computer Science成绩为 " + new String(kv.getValue()));
            }
        }

        // 增加新的列族和新列Contact:Email，并添加数据
        HBaseFunction.addNewColumn(table, "Contact");
        HBaseFunction.putData(table, "2015001", "Contact", "Email", "lilie@qq.com");
        HBaseFunction.putData(table, "2015002", "Contact", "Email", "hmm@qq.com");
        HBaseFunction.putData(table, "2015003", "Contact", "Email", "zs@qq.com");

        // 删除学号为2015003的学生的选课记录
        HBaseFunction.delOneRecordFamily(table, "2015003", "Math");
        HBaseFunction.delOneRecordFamily(table, "2015003", "Computer");
        HBaseFunction.delOneRecordFamily(table, "2015003", "English");
        System.out.println("Delete 2015003 information");
        
        //删除表
        HBaseFunction.deleteTable(table);
    }
}

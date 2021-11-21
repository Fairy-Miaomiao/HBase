# FBDP-实验三

邵一淼 191098180

[TOC]



## 需求分析

要求包含以下四张表的数据：

学生表(student)

| 学号S_No | 姓名S_Name | 性别S_Sex | 年龄S_Age |
| -------- | ---------- | --------- | --------- |
| 2015001  | Li Lei     | male      | 23        |
| 2015002  | Han Meimei | female    | 22        |
| 2015003  | Zhang San  | male      | 24        |

课程(course)

| 课程号C_No | 课程名C_Name     | 学分C_Credit |
| ---------- | ---------------- | ------------ |
| 123001     | Math             | 2            |
| 123002     | Computer Science | 5            |
| 123003     | English          | 3            |

选课表(sc)

| 学号SC_Sno | 课程号SC_Cno | 成绩SC_Score |
| ---------- | ------------ | ------------ |
| 2015001    | 123001       | 86           |
| 2015001    | 123003       | 69           |
| 2015002    | 123002       | 77           |
| 2015002    | 123003       | 99           |
| 2015003    | 123001       | 98           |
| 2015003    | 123002       | 95           |

学生表(student)，增加联系方式

| 学号S_No | 姓名S_Name | 性别S_Sex | 年龄S_Age | 联系方式（S_Email) |
| -------- | ---------- | --------- | --------- | ------------------ |
| 2015001  | Li Lei     | male      | 23        | lilie@qq.com       |
| 2015002  | Han Meimei | female    | 22        | hmm@qq.com         |
| 2015003  | Zhang San  | male      | 24        | zs@qq.com          |

最开始的思路如下：

| 学号    | 姓名       | 选课             | 成绩 | ...  |
| ------- | ---------- | ---------------- | ---- | ---- |
| 2015001 | Li Lei     | Math             | 86   |      |
| 2015001 | Li Lei     | English          | 69   |      |
| 2015002 | Han Meimei | Computer Sciece  | 77   |      |
| ...     |            |                  |      |      |
| 2015003 | Zhang San  | Computer Science | 95   |      |

这虽然是很一般的正常思路，但是**不适合列式存储**，反而比较适合行式存储

在阅读了许多列式存储的例子之后，尤其是下图

![image-20211121201006155](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121201006155.png)

逐渐找到了感觉

**将每一行的rowKey定义为学号**，每个学生仅占用一行，学生个人信息作为一个列族，三门学科分别作为一个列族，最后表格如下所示：

![image-20211121202808515](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121202808515.png)



## 代码

代码文件已上传到[Fairy-Miaomiao/HBase (github.com)](https://github.com/Fairy-Miaomiao/HBase)

### Java程序

Java程序代码见JavaCode文件夹

| 类名          | 功能                                                         |
| ------------- | ------------------------------------------------------------ |
| HBaseMain     | 入口类，通过调用HBaseFunction中的方法来完成一些表操作        |
| HBaseFunction | 实现了一些表操作的方法，如创建表、添加数据、查询数据、新增列、删除记录等 |



### shell命令

shell代码见shell.txt



## 运行截图

### 启动hadoop和HBase

![image-20211121203825831](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121203825831.png)

![image-20211121203942453](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121203942453.png)

### Java程序

![image-20211121194828340](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121194828340.png)

![image-20211121194930164](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121194930164.png)

### shell

#### 创建表

![image-20211121123139652](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123139652.png)

![image-20211121123217467](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123217467.png)

![image-20211121123259621](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123259621.png)

![image-20211121123314031](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123314031.png)

![image-20211121123338707](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123338707.png)

![image-20211121123358203](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123358203.png)

![image-20211121123412105](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123412105.png)

![image-20211121123425880](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123425880.png)

![image-20211121123436049](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123436049.png)

#### 查询选修Computer Science的学生的成绩

![image-20211121123448756](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123448756.png)

#### 增加新的列族和新列Contact:Email,并添加数据

（发现少截图了一行代码，最终代码以shell.txt为准）

![image-20211121123629298](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121123629298.png)

#### 删除学号为2015003的学生的选课记录

![image-20211121134622537](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121134622537.png)

#### 删除所创建的表

![image-20211121134654770](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121134654770.png)



## 问题总结及解决方案

### 1、hbase shell中的>变成 '

**问题描述：**在添加数据时，输入了以下命令之后

```
put 'StuClass','2015002,'Student:S_Sex','female'
```

hbase shell中的>就变成了 '，如下图所示，输入其他命令也没有反应

![image-20211121140038070](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121140038070.png)

**解决方案：**经查询资料得知hbase shell中不同符号的含义

|                                           | 含义                                         |
| ----------------------------------------- | -------------------------------------------- |
| hbase(main):021:0*                        | 表示还没输入完整的操作命令                   |
| hbase(main):021:0'<br>hbase(main):021:0'' | 表示操作命令中的单引号或者双引号没有承兑     |
| hbase(main)021:0>                         | 表示刚刚执行完命令，还没有输入下一句操作命令 |

原先输入的命令因为失误少了一个单引号，所以只要再单独输入一个单引号就能恢复正常

### 2、org.apache.hadoop.hbase.client.RetriesExhaustedException: Can't get the locations

问题描述：运行Java程序时报错，

解决方案：在conf中多增加一行zookepper.znode.parent就能解决

![image-20211121202950983](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121202950983.png)



## 其他思考

### 列式存储的优点在哪？

如前文需求分析所提及的那样，似乎在excel等软件十分普及的现在，大家的第一反应都觉得像excel那样的行式存储更易于理解和使用，而列式存储不仅不易理解，在存储数据时也很难想到（对我来说）。

阅读了许多材料，以下仅总结一些我能读懂或者说能产生共鸣的，至于一些看上去很高端但是我可能还没有涉及到的暂不说明

列式存储的优点：

- 同一列存放在一起，数据类型相同，则更好的进行压缩
- 同一列存放在一起，则排序更加方便，基于排序方便，where某一列会更加快

![image-20211121205532075](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20211121205532075.png)

- 列式存储适合**“针对列”的查询**：
  比如select rowid from table_name，因为只会读取图中的第1个绿色部分的数据（查询时只有涉及到的字段会被读取），而select * from table_name limit 1则需要读取column-based stores**所有**绿色部分的数据（虽然目的就是要查询第1行的数据）；但是不适用于insert/update操作比较多的场景，比如当插入1个row时，由于列式存储导致同一个row的数据被分散在多个数据块中，因此需要去遍历所有数据块的数据。此外由于同一个字段连续存储（同一列的内容有很多值是重复的，可以压缩），因此更加便于编码压缩。

  综合来看，列式存储比较适合大数据量（压缩比高）、分析型操作（针对少数几列）；不适合频率较高的删除（全列检索）、更新（重新压缩）操作。

  

## 参考资料

[hbase通过idea操作api_会飞的鱼-CSDN博客](https://blog.csdn.net/u010800708/article/details/86742516)

[HBase的配置 - 简书 (jianshu.com)](https://www.jianshu.com/p/1cf5ab260283)

[Java在HBase数据库创建表_chszs的专栏-CSDN博客_hbase java建表](https://blog.csdn.net/chszs/article/details/47836681)
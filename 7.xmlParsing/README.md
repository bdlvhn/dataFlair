## Hadoop Project 7 - XML Parsing

> 목적 : 두 개의 정형 파일 데이터를 MapReduce를 활용하여 File Join을 수행하는 것을 목표로 합니다.

## KPI–1: Parse xml data into structured format

**Input**
```
<configuration>
<property>
<name>fs.default.name</name>
<value>hdfs://localhost:9000</value>
</property>
<property>
<name>hadoop.tmp.dir</name>
<value>/home/hadoop/hdata/hadoop-${user.name}</value>
</property>
</configuration>
```

**Output**
```
fs.default.name,hdfs://localhost:9000
hadoop.tmp.dir,/home/hadoop/hdata/hadoop-${user.name}
```

**Format of XML**
1. <configuration> tag is the root tag
2. <property> … </property> is a single record
  
**Tasks**
1. Parse XML and convert it into structured data so that processed data can be stored in database
2. From XML data extract name and value tag and store them in comma delimited format
3. Develop a new input format that can read xml data according to specific tags

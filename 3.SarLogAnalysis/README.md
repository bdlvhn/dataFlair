## Hadoop Project 3 - Sar Log Analytics

> 목적 : 서버에서 생성된 sarlog를 분석하여 시스템 리소스 활용도를 식별합니다. 프로젝트의 목표는 클러스터의 모든 노드에 대한 시스템 리소스 활용에 대한 통찰력을 얻는 것입니다.
> 이 애플리케이션은 클러스터에서 작동하는 프레임워크(Hadoop, Greenplum 등)의 문제를 식별하는 데 유용하게 사용할 수 있는 수치입니다.
> 해당 과정을 통해 Map-Reduce Job 을 살펴볼 때 부하 분포가 균일하지 않은 경우와 같은 문제를 식별할 수 있습니다.

## 0. preparation

1.Install sysstat
```
sudo apt-get install sysstat
```
This will be used for scripts which we will schedule to generate system logs

2. Develop scripts to generate system logs
2-1. Prepare a script to generate CPU utilization
```
echo "`hostname` `date +%d-%m-%y,%H:%M` ` sar 1 59 |tail -1 `" >> /home/hadoop/sar/logs/`date
+%m-%y`-cpu-sar.txt &
```
2-2. Prepare a script to generate Memory utilization
```
echo "`hostname` `date +%d%m%y,%H:%M` ` sar -r 1 59 |tail -1 `" >> /home/hadoop/sar/logs/`date
+%m-%y`-memory-sar.txt &
```
2-3. Prepare a script to generate Disk utilization
```
echo "`hostname` `date +%d%m%y,%H:%M` `df -h |head -3|tail -1 `" >> /home/hadoop/sar/logs/`date
+%m-%y`-disk-sar.txt &
```
2-4. Schedule the scripts in crontab
```
crontab –e
* * * * * /PATH/TO/SCRIPT/CPU-SCRIPT
* * * * * /PATH/TO/SCRIPT/MEMORY-SCRIPT
* * * * * /PATH/TO/SCRIPT/DISK-SCRIPT
```
The scheduled scripts will generate logs in the respective files.

3. The format of logs:
3-1. CPU Logs:
```
hostname date-time CPU %user %nice %system %iowait %steal %idle=
hdtr001 240613,20:44 Average: all 4.05 0.00 10.17 0.02 0.00 85.76
```
3-2. Memory Logs:
```
hostname date-time kbmemfree kbmemused %memused kbbuffers kbcached kbcommit %commit
kbactive kbinact
hdtr001 240613,20:50 Average: 473633 319179 40.26 77812 63504 936325 71.31
208009 63161
```
3-3. Disk Logs:
```
hostname date-time Size Used Avail Use% Mounted on
hdtr001 240613,20:50 19G 2.9G 16G 16% /
```

Load the data in HDFS and process using Map-Reduce to identify average Cpu, Memory, Disk utilization.
3-4. Process CPU Logs
```
hadoop dfs –mkdir sarlogs/cpu-logs
hadoop dfs -put sar/logs/08-14-cpu-sar.txt sarlogs/cpu-logs
hadoop jar sarProcessorJob.jar org.tr.hd.log.cpu. SarCpuAggregator sarlogs/cpu-logs sarlogs/processedcpu-logs
```
3-5. Process Memory Logs
```
hadoop dfs –mkdir sarlogs/memory-logs
hadoop dfs -put sar/logs/08-14-memory-sar.txt sarlogs/memory-logs
hadoop jar sarProcessorJob.jar org.tr.hd.log.mem.SarMemoryAggregator sarlogs/memory-logs
sarlogs/processed-memory-logs
```
3-6. Process Disk Logs
```
hadoop dfs –mkdir sarlogs/disk-logs
hadoop dfs -put sar/logs/08-14-disk-sar.txt sarlogs/disk-logs
hadoop jar sarProcessorJob.jar org.tr.hd.log.disk.SarDiskAggregator sarlogs/disk-logs sarlogs/processeddisk-logs
```

## 1. input data
```
phddn001        240613,20:44        Average:        all        4.05        0        10.17        0.02        0        85.76
phddn001        240613,20:45        Average:        all        4.09        0        10.31        0.02        0        85.59
phddn001        240613,20:46        Average:        all        4.12        0        10.43        0.02        0        85.44
phddn001        240613,20:47        Average:        all        4.09        0        10.46        0.02        0        85.44
...
```

## 2. Input to Mapper

**key	value**
```
hostName + \t + date	        CPU     %user     %nice   %system  %iowait   %steal  <%idle>
phddn001	240613        Average:        all        4.05        0        10.17        0.02        0        85.76
phddn001	240613        Average:        all        4.09        0        10.31        0.02        0        85.59
phddn001	240613        Average:        all        4.12        0        10.43        0.02        0        85.44
phddn001	240613        Average:        all        4.09        0        10.46        0.02        0        85.44
..
```

## 3. Processing (map) (custom business logic)

**take a cpu usage by substituting idle from 100**
```
100 - %idle
```

## 4. Output from the mapper (write to local disk)

**key value**
```
phddn001	240613	14.24
phddn001	240613	14.41
phddn001	240613	14.56
phddn001	240613	14.56
...
```

## 5. Input to reducer (All the values corresponding to same key goes to same reducer)

**key value**
```
phddn001	240613	sum / length
phddn001	250613	sum / length
phddn001	260613	sum / length
...
```

## 6. Processing in reducer (custom business logic)

**aggregate average for each keys**
```
phddn001	240613	sum / length
phddn001	250613	sum / length
phddn001	260613	sum / length
```

## 7. Output from the reducer (Final output)

**key value**
```
phddn001	240613	14.37%
phddn001	250613	14.49%
phddn001	260613	14.69%
...
```

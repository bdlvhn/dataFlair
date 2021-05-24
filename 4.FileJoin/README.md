## Hadoop Project 4 - File Join

> 목적 : 두 개의 정형 파일 데이터를 MapReduce를 활용하여 File Join을 수행하는 것을 목표로 합니다.

## 0. preparation

File Join : join the contents of 2 files

First File (Contains Employee Name Data) (size 1TB)
```
id,name
101,Gaurav
102,Rohit
103,Karishma
104,Darshan
105,Divya
```

Second File (Contains Employee Department Data) (size 1TB)
```
id,dept
101,Sales
102,Research
103,NMG
104,Admin
105,HR
```

## 1. input data
** First File (Contains Employee Name Data)**
```
id,name
101,Gaurav
102,Rohit
103,Karishma
104,Darshan
105,Divya
...
```
**Second File (Contains Employee Department Data)**
```
id,dept
101,Sales
102,Research
103,NMG
104,Admin
105,HR
...
```

## 2. Input to Mapper

**key	value**
```
0	101,Gaurav
1	102,Rohit
2	103,Karishma
3	104,Darshan
4	105,Divya

5	101,Sales
6	102,Research
7	103,NMG
8	104,Admin
9	105,HR
...
```

## 3. Processing (map) (custom business logic)

**(key, value) = (#, name)**
**(key, value) = (#, dept)**

## 4. Output from the mapper (write to local disk)

**key value**
```
101	Gaurav
102	Rohit
103	Karishma
104	Darshan
105	Divya

101	Sales
102	Research
103	NMG
104	Admin
105	HR
...
```

## 5. Input to reducer (All the values corresponding to same key goes to same reducer)

**key value**
```
101	Gaurav,Sales
102	Rohit,Research
103	Karishma,NMG
104	Darshan,Admin
105	Divya,HR
...
```

## 6. Processing in reducer (custom business logic)

**(key, value) = (null, [id,name,dept])**

## 7. Output from the reducer (Final output)

**key value**
```
null	101,Gaurav,Sales
null	102,Rohit,Research
null	103,Karishma,NMG
...
```

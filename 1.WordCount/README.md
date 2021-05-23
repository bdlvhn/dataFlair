## Hadoop Project 1 - WordCount

> 목적 : 데이터 파일을 읽어와서 단어 단위로 word count를 실행하는 MapReduce를 작성한다.
데이터셋 : Input Data => 128 MB Blocks

## 1. input data
```
DataCompany provides training on cutting edge technologies
DataCompany is the leading training provider, we have trained 1000s of candidates
Training focuses on practical aspects which industry needs rather than theoretical knowledge
...
```

## 2. Input to Mapper

**key	value**
```
0	DataCompany provides training on cutting edge technologies
57	DataCompany is the leading training provider, we have trained 1000s of candidates
108	Training focuses on practical aspects which industry needs rather than theoretical knowledge
..
```

## 3. Processing (map) (custom business logic)

**count all the words**
```
DataCompany	1
provides	1
training	1
on		1
cutting		1
edge		1
technologies	1
```

## 4. Output from the mapper (write to local disk)

**key value**
```
DataCompany	1
provides	1
training	1
on		1
cutting		1
edge		1
technologies	1
...
```

## 5. Input to reducer (All the values corresponding to same key goes to same reducer)

**key values**
```
DataCompany	[1,1,1,1,1......]
training	[1,1,1,1,1......]
...
```

## 6. Processing in reducer (custom business logic)

**sum all the counts**
```
330
```

## 7. Output from the reducer (Final output)

**key value**
```
DataCompany	330
training	500
technologies	25
...
```

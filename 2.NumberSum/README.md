## Hadoop Project 2 - NumberSum

> 목적 : 데이터 파일을 읽어와서 모든 숫자의 합을 반환하는 MapReduce를 작성한다.

## 1. input data
```
1 2 45 67 8 43
14 95 3 63 72 2
56 78 12 9 4 3 77
...
```

## 2. Input to Mapper

**key	value**
```
sum	1 2 45 67 8 43
sum	14 95 3 63 72 2
sum	56 78 12 9 4 3 77
..
```

## 3. Processing (map) (custom business logic)

**sum all the numbers**
```
1 2 45 67 8 43
->
1+2+45+67+8+43
```

## 4. Output from the mapper (write to local disk)

**key value**
```
sum		166
sum		249
sum		369
...
```

## 5. Input to reducer (All the values corresponding to same key goes to same reducer)

**key values**
```
sum		[166 249 369 3149 1439 158 395 135 123 ...]
...
```

## 6. Processing in reducer (custom business logic)

**sum all the numbers**
```
166+249+369+3149+1439+158+395+135+123+...
```

## 7. Output from the reducer (Final output)

**key value**
```
313,406,134,096
```

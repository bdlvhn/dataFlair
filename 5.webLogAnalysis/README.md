## Hadoop Project 5 - Web Log Analysis

> 목적 : 두 개의 정형 파일 데이터를 MapReduce를 활용하여 File Join을 수행하는 것을 목표로 합니다.

## 0. preparation

**What is weblog?**

Weblogs are where web server (like apache) records events like visitors to your site and problems it's
encountered. Your web server records all the visitors to your site. There you can see what files users are
accessing, how the web server responded to requests, and other information like what kind of web
browsers visitors are using, etc.

**Sample web log data**
```
133.128.48.53 - - [01/Jan/2012:01:55:42 +0530] "GET /mobiles/smart-phones/sony-xperia-m-dualandroid-smart-phone-white.html HTTP/1.1" 200 1466 "Mozilla/5.0 (Windows NT 6.2)
AppleWebKit/537.17 (KHTML, like Gecko) Chrome/24.0.1312.56 Safari/537.17" "-"
```

**Format of Weblogs**
1. Remote-IP
2. Remote-log-name
3. user
4. time
5. request-string
6. status-code
7. byte-string
8. user-agent
9. referral


## KPI-1 : Parse weblogs into structured format

**Input (Raw Log Record)**
```
50.57.190.149 - - [22/Apr/2012:07:12:41 +0530] "GET /computers/laptops.html?brand=819 HTTP/1.0"
200 12530 "-" "-"
```
**Output (Processed Log Record)**
```
50.57.190.149 - - 22/Apr/2012:07:12:41 +0530 GET
/computers/laptops.html?brand=819 HTTP/1.0 computers - - laptops.html
brand=819 200 12530 - -
```

**Format of Input Data**
1. remote-IP
2. remote-log-name
3. user
4. time
5. request-string
6. status-code
7. byte-string
8. user-agent
9. referral

**Tasks**
1. Fields should be ‘Tab’ separated
2. Remove ‘[ ]’ from date-time field
3. Remove ‘””’ from request-string
4. Parse request-string into structured format /cat-1/cat-2/cat-3/cat-4/page?param  cat-1 cat-2 cat-3 cat-4 page param
5. Remove ‘””’ from user-agent
6. Remove ‘””’ from referral
7. Handle bad records, store bad records in a specific file
** First File (Contains Employee Name Data)**

## KPI-2: Count of page views by individual user

**Input (Processed Log Record)**
```
50.57.190.149 - - 22/Apr/2012:07:12:41 +0530 GET
/computers/laptops.html?brand=819 HTTP/1.0 computers - - laptops.html
brand=819 200 12530 - -
```

**Format of Processed weblogs (Input Data)**
1. remoteIP
2. remotelogname
3. user
4. time
5. request-string
6. category-1
7. category-2
8. category-3
9. page
10. param
11. status-code
12. byte-string
13. user-agent
14. referral

**Task**
1. Take the pre-processed weblogs as input
2. Count the number of page visited by each individual user (User wise page-visit distribution)

## KPI–3: Count of page views by catagery-1

**Input (Processed Log Record)**
```
50.57.190.149 - - 22/Apr/2012:07:12:41 +0530 GET
/computers/laptops.html?brand=819 HTTP/1.0 computers - - laptops.html
brand=819 200 12530 - -
```
**Format of Processed weblogs (Input Data)**
1. remoteIP
2. remotelogname
3. user
4. time
5. request-string
6. category-1
7. category-2
8. category-3
9. page
10. param
11. status-code
12. byte-string
13. user-agent
14. referral

**Task**
1. Take the pre-processed weblogs as input
2. Count the number of page visited based on categories (Category wise page-visit distribution)

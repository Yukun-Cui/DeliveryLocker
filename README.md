# DeliveryLocker

这是一个使用java原生Socket实现的外卖柜系统，它不含任何第三方类库，使用Oracle Jdk11即可运行。

## 功能

### 连接认证

每个客户端拥有唯一的设备编码（定义在src/client/bean/Final.java中，可以进行修改），需要在服务器端verify.dat文件将ip地址与设备编号注册才可以连接服务器。

### 外卖柜管理

用户取外卖、外卖员添加外卖，外卖员删除外卖，外卖员查看外卖，外卖员查看查看外卖柜占用情况。

### 多线程

以每次数据请求为单位创建线程，不同设备id之间资源独立，分别在服务器存有对应的数据文件（设备id.dat），支持重启。

### 安全性

加入了ip地址+设备id认证。

## 使用方法

使用Oracle Jdk11运行下面两个程序即可：

### 服务端

src/server/ServerMain.java

### 客户端

src/client/controller/DeliveryMain.java

### 取餐码

取餐码是外卖的唯一识别id，请牢记！

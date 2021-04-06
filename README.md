# bcmall [![Awesome](https://awesome.re/badge.svg)](http://xjjdog.cn) [![Build Status](https://travis-ci.com/xjjdog/bcmall.svg?branch=master)](https://travis-ci.com/xjjdog/bcmall)

`bcMall` 是一个以`教学`为目的的电商系统。bcMall将为你展现一个典型的系统演进过程，所使用的主流技术完全开放。

它包含ToB复杂业务、互联网高并发业务、缓存应用；DDD、微服务指导。模型驱动、数据驱动。了解大型服务进化路线，编码技巧、学习Linux，性能调优。Docker/k8s助力、监控、日志收集、中间件学习。前端技术、后端实践等。主要技术：`SpringBoot`+`JPA`+`Mybatis-plus`+`Antd`+`Vue3`。


# 项目信息

通过下面的链接可快速体验。

## 后台管理模块 (toB)

- Java后端管理代码 ：https://github.com/xjjdog/bcMall
- 后台体验地址：http://bcmall.xjjdog.cn/
- 配套教程和文档：http://xjjdog.cn/ 
- Antd前端管理代码：整理上传中...


| 截图 | 截图 | 截图 |
| ----- | ----- |----- |
| <img src='http://s0-img.xjjdog.cn/github/1.png' /> | <img src='http://s0-img.xjjdog.cn/github/2.png' /> |<img src='http://s0-img.xjjdog.cn/github/3.png' /> |
| <img src='http://s0-img.xjjdog.cn/github/4.png' /> | <img src='http://s0-img.xjjdog.cn/github/5.png' /> |<img src='http://s0-img.xjjdog.cn/github/6.png' /> |


## 售卖模块 (toC) 

敬请期待...

# 模块划分

`bcMall`采用了一种更加容易理解、扩展性更强的模块划分方式。更方便的由单体应用向分布式应用过渡。

<img src='http://s0-img.xjjdog.cn/github/bcmall-xmind.png' />

由于B端的技术栈，与C端的不太一样，按照模块划分可以使用单独的技术栈而互不影响。做后台管理，没必要使用C端的技术折腾自己。

为了支持原型驱动开发模式，bc-utils提供了`magicjpa`和`smartjdbc`两种无敌的代码hack模式，使用很少很少的代码就可以实现功能的快速验证。

想赚外快的同学可以直接拿走这两个工具。

参考代码：

- [magicjpa](https://github.com/xjjdog/bcMall/tree/master/bc-utils/src/main/java/cn/xjjdog/bcmall/utils/quickdev/magicjpa)
- [smartjdbc](https://github.com/xjjdog/bcMall/tree/master/bc-utils/src/main/java/cn/xjjdog/bcmall/utils/quickdev/smartjdbc)

# 主要技术

前端使用开箱即用的`antd pro`，开发语言采用`typescript`。ts是最接近后端开发的一门语言，比vue开发效率高很多，一周撸一个系统不是梦。

后端主要是用`springboot`。主要的技术点有：

- `mysql mariadb`  关系型数据库
- `redis`KV数据库
- `elasticsearch` NoSQL+全文检索
- `spring-boot-jpa` 应用广泛的ORM框架。快速开发，告别恼人的表设计
- `mybatis-plus` 为特殊模块准备的ORM框架
- `hibernate-validator` 专业的参数验证框架
- `swagger` 文档生成器，rest接口测试
- `p6spy` 打印sql，调试功能
- `guava` 广泛应用的google的工具类库
- `vavr` lambda表达式扩展包，缩减代码
- `mapstruct` 对象转换类库，节省getset
- `lombok` 地球人都知道的节省代码工具类
- `hikaricp` 速度最快的数据库连接池
- `jwt+spring security` 权限认证
- `jsoup` 爬虫工具
- `flyway` 表变更维护工具
- `kafka` 高可靠高吞吐的消息队列
- `nginx` 反向代理软件
- `oss` 阿里云的图片服务

# 开发工具

在开发中，使用了`Macbook`、`Windows`、`Linux`等桌面平台，所以bcMall是跨平台的。另外还使用了如下的开发工具：

- `CentOS Linux` 运行容器，与大厂接轨
- `Windows WSL2` 运行容器，Windows下最好用的Linux环境  
- `IDEA社区版` 专业的Java IDE，社区版不占资源
-  `VSCode` 前端开发工具
-  `DBeaver` 数据库管理软件
-  `Ominigraffle | viso` 绘图软件
-  `XMind ZEN` 思维导图工具
-  `React Developer Tools` React代码调试
-  `Postman | curl` API接口快速调试工具

# 文档 & 技术解析

最新配套文章，将第一时间发布到《小姐姐味道》微信公众号。直接搜索`xjjdog`或者扫描下面的二维码关注即可。

![](docs/qrcode_for_gh_183eb256f8af_258.jpg)

更多支持：加`xjjdog0`好友，进入讨论组。

# 精选教程 


# Linux

告别简单的知识点罗列，趣味性故事讲解命令使用。更有高赞Linux教程等你探索。

[【快速掌握Linux】](http://xjjdog.cn/linux/15614525129972.html) &nbsp;&nbsp;
[【高级必备】](http://xjjdog.cn/linux/15720582092957.html)] &nbsp;&nbsp;

#### Linux 三剑客

[【三剑客 - Sed】](http://xjjdog.cn/linux/15584290692459.html) &nbsp;&nbsp;
[【三剑客 - Awk】](http://xjjdog.cn/linux/15609056089588.html) &nbsp;&nbsp;
[【三剑客 - Vim】](http://xjjdog.cn/linux/15402953116952.html) &nbsp;&nbsp;
[【Sed高级功能】](http://xjjdog.cn/linux/15592912624798.html) &nbsp;&nbsp;

#### 实践

[【挖矿脚本解析】](http://xjjdog.cn/linux/15750912640763.html) &nbsp;&nbsp;
[【故障排查脚本】](http://xjjdog.cn/linux/15649871905254.html) &nbsp;&nbsp;
[【tail命令趣解】](http://xjjdog.cn/linux/16042874144624.html) &nbsp;&nbsp;
[【常见问题 - LWP分析】](http://xjjdog.cn/linux/15441696619678.html) &nbsp;&nbsp;
[【常见问题 - Kill -9】](http://xjjdog.cn/linux/15928741434512.html) &nbsp;&nbsp;
[【常见问题 - 删库跑路】](http://xjjdog.cn/linux/15928741434512.html) &nbsp;&nbsp;


#### 趣解Java

[【多线程总结】](http://xjjdog.cn/arch/15514102727042.html) &nbsp;&nbsp;
[【parllelStream陷阱】](http://xjjdog.cn/arch/15997289508341.html) &nbsp;&nbsp;
[【map与flatMap】](http://xjjdog.cn/arch/15579957479709.html) &nbsp;&nbsp;
[【一句话设计模式】](http://xjjdog.cn/arch/15554085020739.html) &nbsp;&nbsp;
[【PECS】](http://xjjdog.cn/arch/15757842084359.html) &nbsp;&nbsp;


#### JVM

[【内存分布详解】](http://xjjdog.cn/arch/15906512011578.html) &nbsp;&nbsp;
[【JMC】](http://xjjdog.cn/arch/15858945675534.html) &nbsp;&nbsp;
[【JVM故障排查 - 1】](http://xjjdog.cn/arch/15951334389254.html) &nbsp;&nbsp;
[【JVM故障排查 - 2】](http://xjjdog.cn/arch/15970485648112.html) &nbsp;&nbsp;
[【堆外内存排查】](http://xjjdog.cn/arch/15430579562701.html) &nbsp;&nbsp;

#### 架构

[【微服务】](http://xjjdog.cn/arch/15643605998324.html) &nbsp;&nbsp;
[【监控体系】](http://xjjdog.cn/arch/15409564958362.html) &nbsp;&nbsp;
[【日志收集】](http://xjjdog.cn/arch/15863351939061.html) &nbsp;&nbsp;
[【消息】](http://xjjdog.cn/arch/15674047975982.html) &nbsp;&nbsp;
[【分库分表】](http://xjjdog.cn/arch/15416612166951.html) &nbsp;&nbsp;
[【存储】](http://xjjdog.cn/arch/15669607931578.html) &nbsp;&nbsp;
[【安全】](http://xjjdog.cn/arch/15979028621876.html) &nbsp;&nbsp;
[【高并发高可用】](http://xjjdog.cn/arch/15665521425450.html) &nbsp;&nbsp;
[【Spring】](http://xjjdog.cn/arch/16011057235424.html) &nbsp;&nbsp;

## 精选脑图

[okmind](http://mind.xjjdog.cn)

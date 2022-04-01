### 基于抖音数据分析App

##### 题目：

抖音播客数据爬虫



##### 原因：

1.熟悉并使用Android开发相关知识；

2.尝试学习爬虫相关知识，拓宽知识面；

3.根据所学知识，抓包、爬取抖音数据，结合Android开发知识，完成一个具有挑战性和实用性的App。



##### 目标：

基本目标：

1.通过抓包分析，成功爬取抖音短视频数据；

2.整合分析数据，开发简要App显示数据结果。



扩展目标：

1.爬取多种数据，包括但不限于（通过数据进行排序）：

- 用户粉丝数排行统计（总量排行、增量排行）
- 视频点赞数排行统计
- 商品销量排行统计
- 商品销量最多店铺排行统计

2.分析计算数据，整理实现总体统计图概览（折线图/饼状图）；

3.完善App功能和设计，实时显示排行和具体条目（用户、视频）信息。



##### 技术栈：

爬虫：WebMagic+selenium+jdbc+MySql

后端：SpringBoot

前端：Android（Kotlin）



------------------------------- 3/26 ---------------------------------------------------

##### 一阶段目标：抓取抖音数据包并分析Json

方案一：尝试使用模拟器+Fiddler代理+Xposed+JustTrustMe框架抓取数据包：

使用雷电模拟器（因为可以配置代理和桥接网卡）

Fiddler代理并且抓包

Xposed+JustTrustMe拦截HTTPS的证书验证

Tip：根据大量博客文章，尝试多种方法，可能文章方法和框架有点老，抖音迭代速度已经修复，使用模拟器出现无法访问网络问题（大概率和证书的处理有关）。



方案二：使用Fiddler抓取web版抖音数据包：

成功抓取，并大致了解收发数据包流程：

- 每秒会发送心跳包
- 获取当前视频时会返回下一个视频的用户头像（提前缓存）

成功抓取视频列表数据包

成功抓取用户数据包

解析Json中....



------------------------------- 3/27 ---------------------------------------------------

使用WebMagic爬虫框架，尝试使用爬虫爬取学院官网新闻页面。爬取成功

尝试爬取抖音页面，通过返回页面发现页面应该是靠JS动态渲染的，尝试解决方法。



------------------------------- 3/28 ---------------------------------------------------

通过抓包，发现请求视频信息的json的get请求。

```
GET https://www.douyin.com/aweme/v1/web/aweme/related/?device_platform=webapp&aid=6383&channel=channel_pc_web&aweme_id=7079932780649499918&count=20&filterGids=&version_code=170400&version_name=17.4.0&cookie_enabled=true&screen_width=1536&screen_height=864&browser_language=zhCN&browser_platform=Win32&browser_name=Edge&browser_version=99.0.1150.52&browser_online=true&engine_name=Blink&engine_version=99.0.4844.74&os_name=Windows&os_version=10&cpu_core_num=8&device_memory=8&platform=PC&downlink=10&effective_type=4g&round_trip_time=100&webid=7075536590172882447&msToken=shF5NBcA8uOHcvbYShjAXxxtGdpNcXZiMZXlt_I7LhbTrg4-rXiXzVL0X6C7r1tKOaYGPLjMet0kPEmvTk1AGuyzWM-t5KouhIgiUlvwzPuHeA5J45Mldx4=&X-Bogus=DFSzswSOz/hANj13Sl1-FVXAIQ-Q&_signature=_02B4Z6wo00001tgGQrQAAIDDuwyC3jkthRLYAkYAANRNUn6q-p8ORZkib1-uNNJcHWqe0m6ZCCR71s1Mx8OMlp.w5EXsqdjFAV2L4xBmJ6MiROk0Yc.yOMe-azM-A1wrY0WAyin7bu86nQTfb8 
```

但请求是实时的，需要有一个_signature的实时签名



------------------------------- 3/29 ---------------------------------------------------

_signature算法破解较为困难，考虑使用Selenium模拟一个浏览器，进行动态渲染后，再抓取元素。但是这种方式速度就会慢不少。

最后能够成功爬取视频信息，并能获取到想要内容并输出到控制台。



------------------------------- 3/31 ---------------------------------------------------

尝试使用Jdbc+MySql，将数据存放到数据库，方便后续处理。
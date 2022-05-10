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

爬虫：WebMagic+seleium+jdbc+MySql+Xvfb

后端：SpringBoot

前端：Android（Kotlin）



##### log：

------------------------------- 3/26 ---------------------------------------------------

##### 一阶段：抓取抖音数据包并分析Json

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



##### 二阶段：使用爬虫框架编写爬虫程序

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



------------------------------- 4/5 ---------------------------------------------------

数据库设计完毕，目前能够爬取用户（粉丝数、获赞数）和视频（点赞、收藏、评论、url等）

windows下运行正常，尝试部署到Linux服务器上

Linux服务器上无法运行chrom浏览器，尝试使用firefox--------失败

最终尝试在windows下运行爬虫程序，使用jdbc远程连接服务器Mysql数据库存储数据

开启Mysql数据库远程连接需要注意：

> 1.开启user权限
>
> 2.配置Mysql监听0.0.0.0任意端口（默认127.0.0.1）
>
> 3.云服务器防火墙开启3306端口



##### 三阶段：基于SpringBoot编写简单接口

------------------------------- 4/6 ---------------------------------------------------

完善爬虫程序...

创建SpringBoot后端项目，连接数据库进行数据处理

搭建环境，编写简单的接口



##### 四阶段：开发Android端App并完善后端接口

> TODO：
>
> 1.学习Kotlin
>
> 2.基于Kotlin仿造“蝉妈妈”App开发Android端功能
>
> 3.根据Android端相应功能完善后端接口

------------------------------- 4/7 ---------------------------------------------------

学习Kotlin...



------------------------------- 4/10 ---------------------------------------------------

通过B站视频、文档，已经初步学会了Kotlin

构建Android端项目，设计并明确App功能

**功能需求：**

主页

- 概览图

用户

- 根据粉丝数、获赞数排行显示
- 根据粉丝数、获赞数增量排行显示
- 能够监控某一位用户的涨粉、获赞动态
- 能够获取具体用户信息并显示

视频

- 根据点赞、评论、收藏数排序显示
- 根据点赞、评论、收藏数增量排序显示
- 能够显示某个具体视频

个人中心



------------------------------- 4/11 ---------------------------------------------------

BottomNavigationView + Viewpager2搭建页面框架

学习Android MVVM框架

构建大致的MVVM框架



------------------------------- 4/12 ---------------------------------------------------

学习并成功构建Android MVVM框架

编写登录界面



------------------------------- 4/13 ---------------------------------------------------

登录界面初步完成

编写后端登录注册接口，完善登录功能



------------------------------- 4/14 ---------------------------------------------------

进行主页页面设计



------------------------------- 4/15 ---------------------------------------------------

需要用户粉丝增量数据，根据现有爬虫技术无法计算增量；

改进爬虫：

尝试部署在服务器上 ---> seleium无法获取页面元素（浏览器无页面显示渲染）

尝试使用无头浏览器 ---> 通过更改webMagic框架源码，成功使用了无头浏览器，，，但是，被抖音的反爬机制干掉，获取到的是一个验证码页面

尝试使用Xvfb 进行虚拟图像处理 ---> 成功在服务器上使用有头浏览器进行渲染，最终爬取数据成功。



------------------------------- 4/16 ---------------------------------------------------

完善数据库、完善JavaBean，成功计算出用户粉丝数增量，并返回给App



------------------------------- 4/17 ---------------------------------------------------

通过请求接口，设计并实现“涨粉达人榜”功能

优化UI设计



------------------------------- 4/18 ---------------------------------------------------

实现“热播视频榜”前后端逻辑与设计



------------------------------- 4/19 ---------------------------------------------------

设计“下拉菜单”功能



------------------------------- 4/20 ---------------------------------------------------

完善“下拉菜单”功能框架，基本实现样式



------------------------------- 4/21 ---------------------------------------------------

封装DropdownMenu下拉菜单框架

完善并实现了“达人库”、“视频库”两个板块



------------------------------- 4/22 ---------------------------------------------------

实现“个人中心”页面和“设置页面”



------------------------------- 4/23 ---------------------------------------------------

实现单个item详情页和“搜索”功能



------------------------------- 4/24 ---------------------------------------------------

实现“联想搜索功能”，完善主页-功能区设计



------------------------------- 4/25 ---------------------------------------------------

优化登录功能设计，实现“短信验证”登录（待真机调试）



------------------------------- 4/27 ---------------------------------------------------

设计实现“收藏”功能（前后端）



------------------------------- 4/28 ---------------------------------------------------

完善优化“收藏”功能并成功实现

规划后面阶段



##### 五阶段：部署运行环境

> TODO：
>
> 1.后端代码部署到云端服务器上，并能够正常请求
>
> 2.服务器搭建数据库环境
>
> 3.Android端完善需要联网功能（虚拟机没有连接外网，有些功能暂未实装）
>
> 4.完成整个项目的开发阶段



------------------------------- 5/5 ---------------------------------------------------

部署springboot项目到Linux云服务器



------------------------------- 5/6 ---------------------------------------------------

成功部署云服务器环境（包括数据库）

实现视频详情查看功能

实现类抖音的短视频滑动功能



------------------------------- 5/7 ---------------------------------------------------

实现“短信验证登录”功能接入

载入用户头像

后端实现定时任务（每天定时更新数据）



------------------------------- 5/9 ---------------------------------------------------

实现视频的搜索功能

优化UI设计

调试修复一些bug



------------------------------- 5/10 ---------------------------------------------------

进一步优化UI设计

优化数据刷新逻辑

修复一些bug



##### 六阶段：测试项目
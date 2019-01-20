---
layout: post
title:  "dails log"
date:   2019-01-19 14:36:54 +0800
categories: dails update
tags:
- dzl84394.github.io
- haha

---


### 简介
基于 Apache Log4j2,用于规范及简化基于spring boot 框架开发的日志输出

#### 1.1 rest接口自动输出
注解了@RequestMapping的所有接口，因为这些接口是一般是对外提供服务的，记录入参和出参比较重要，所以默认输出日志

#### 1.2使用@MyLoggable,自动输出日志
注解了@MyLoggable的接口，自动输出日志


### 技术说明
aop，在方法入口，记录入参，时间，方法名的并发数+1
在方法执行完成之后，记录出参，一层，计算耗时，方法名的并发-1，
打印日志




如果是RequestMapping的接口，会检查tracid是否有值，如果有值，就在ThreadLocal里面记录该值，并且每次打印日志的时候，输出TraceId，方便跟踪日志
如果有多个组件均使用本插件，那么追踪TraceId是个很好的方式




### 使用
maven
log4j.xml



### 例子
- jar
https://github.com/dzl84394/dails3/tree/master/dails-plugins-log

- test
https://github.com/dzl84394/dms/tree/master/dms-log-test

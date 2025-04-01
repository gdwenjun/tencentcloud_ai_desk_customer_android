# 介绍
 本文主要介绍如何快速跑通腾讯智能客服用户端 Android Desk User Demo（智能客服），只要按照如下步骤进行配置，就可以跑通用户端的 Android Desk User Demo。

## 前提条件
了解在线客服相关术语及相关配置，并已完成以下步骤：添加客服、配置技能组、创建会话服务流，详情请参见 [快速入门](https://cloud.tencent.com/document/product/269/96059)。

## 环境与版本
- Android Studio
- Gradle-8.0.2
- Android Gradle Plugin Version-8.0

## 操作步骤
1. 下载 Desk-Demo 源码

   根据实际业务需求 [DeskDemo](https://tccc.qcloud.com/assets/doc/user/release/DeskDemo.zip) 源码。
  
2. 配置 Desk-Demo 工程文件

打开终端目录的工程，找到对应的 GenerateTestUserSig 文件，路径为  DeskDemo/app/src/main/java/com/tencent/customer/deskdemo/debug/GenerateTestUserSig.java 设置 GenerateTestUserSig 文件中的相关参数：
   - SDKAPPID：请设置为 步骤1 中获取的实际应用 SDKAppID。
   - SECRETKEY：请设置为 步骤2 中获取的实际密钥信息。

   ![](https://qcloudimg.tencent-cloud.cn/image/document/fec11a361569b9afbfdbb303cef5b068.png)

   > !  本文提到的获取 UserSig 的方案是在客户端代码中配置 SECRETKEY，该方法中 SECRETKEY 很容易被反编译逆向破解，一旦您的密钥泄露，攻击者就可以盗用您的腾讯云流量，因此该方法仅适合本地跑通 Demo 和功能调试。
   正确的 UserSig 签发方式是将 UserSig 的计算代码集成到您的服务端，并提供面向 App 的接口，在需要 UserSig 时由您的 App 向业务服务器发起请求获取动态 UserSig。更多详情请参见 [服务端生成 UserSig](https://cloud.tencent.com/document/product/269/32688#GeneratingdynamicUserSig)。
   
3. 编译运行
   用 Android Studio 导入工程直接编译运行即可。

## 源码集成步骤
   如不需要修改源码，可忽略该步骤。
1. 把文件夹 deskcore、deskcommon、deskchat、deskcontact、deskcustomerserviceplugin、aideskcustomer 复制到你项目的根目录下。
2. 修改项目的 setting.gradle，加入以下内容：
```agsl
// 集成智能客服相关代码
include ':deskcore'
include ':deskcommon'
include ':deskchat'
include ':deskcontact'
include ':deskcustomerserviceplugin'
include ':aideskcustomer'
```
3. 在 App 的 build.gradle 中添加：
```agsl
 implementation project(':aideskcustomer')
```

## 运行效果
 基本功能如下图所示
![](https://qcloudimg.tencent-cloud.cn/image/document/25c873a08c7efc38c9add8a842f58053.png)


## 联系我们

点此进入 [IM 社群](https://zhiliao.qq.com)，享有专业工程师的支持，解决您的难题。

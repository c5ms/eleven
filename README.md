# eleven

#### 介绍

micro service learning project

#### 软件架构

- eleven-application 应用层，提供有状态的应用逻辑
  - eleven-app-demo 一个示例应用，运行于用户接入网络，对接 GUI 界面，提供定制化应用逻辑！
  - eleven-app-upms 用户权限管理的应用层，可运行，可作为微服务发布
  - eleven-app-upms-client  用户权限管理的应用层客户端，可以作为其他应用层远程微服务调用
- eleven-component 公共组件层，提供工具包，基础库。
  - eleven-core        核心包，提供：工具库，通用场景类，通用异常类，通用枚举类，通用工具类
  - eleven-core-feign  远程客户端调用核心支持
  - eleven-core-rest   Web Restful Endpoint 层支持
  - eleven-core-app    核心应用层支持
  - eleven-core-service 公共领域服务层支持
- eleven-domain 领域服务层，提供无状态领域服务
  - eleven-domain-upms-core 用户权限管理的共享内核
  - eleven-domain-upms-service 用户权限管理领域的服务层
- eleven-deploy 部署脚本


#### 请求链路
![请求链路](./doc/微服务请求链路.png "请求链路")


#### 技术选型
- 注册中心 & 配置中心 使用 nacos 做配置中心和注册中心处理
- 服务通信  服务之间通讯使用 spring cloud - openFeign 框架，数据序列化使用 jackson 。
- 网关  网关使用 spring cloud - gateway
- 负载均衡 docker swarm 自带负载均衡
- 链路追踪  spring cloud - sleuth 处理请求链路，采集服务器使用 zipkin 框架，GUI 显示使用 jaeger(可考虑将 skywalking 封装到 docker 里面替代)
- 日志检测  直接 tcp 写入到 logstash
- mq  rabbitmq + 自研消息系统协调器
- 缓存 本地缓存使用caffeine 分布式缓存使用 redis 缓存（redisson 做数据网格客户端）


#### todo 
1. ID 策略，数据库采用自增还是 UUID，还是雪花算法/雨滴算法？
2. CQRS的使用，该不该使用聚合根？和 command Handler 模式？
3. 是否需要领域层+应用层的模式？
4. 搭建框架用的业务领域，建议做个 TODO 最简单


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

1. xxxx
2. xxxx
3. xxxx

#### 参与贡献

1. Fork 本仓库
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request

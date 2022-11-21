# eleven

#### 介绍

micro service learning project

#### 软件架构

- eleven-application 应用层，提供有状态的应用逻辑
  - eleven-app-demo 一个示例应用，运行于用户接入网络，对接 GUI 界面，提供定制化应用逻辑！
- eleven-component 公共组件层，提供工具包，基础库。
- eleven-domain 领域服务层，提供无状态领域服务
  - eleven-domain-client 领域客户端层支持
  - eleven-domain-core  领域内核层支持
  - eleven-domain-service  领域服务层支持
  - eleven-domain-upms-client   用户权限管理领域客户端
  - eleven-domain-upms-core     用户权限管理领域内核
  - eleven-domain-upms-service  用户权限管理领域服务，提供通用业务逻辑
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

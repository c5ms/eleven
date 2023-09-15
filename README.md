# eleven

#### 前言
首先请你清楚的了解什么是 DDD，我不打算说明白什么是 DDD， 但是我首先要告诉你 DDD 并没有要求你做的事情。
1. DDD 并不要求你一定要有 infrastructure domain application service 。。。这些个包名
2. DDD 并不要求你一定要用 CQRS，而且 Command 不一定就要命名为 XxxCommand，这种结论很奇怪，尤其是在我们中国。
3. 用模仿现实的拟人化分层，好过生冷的 infrastructure domain application service...因为首先是部门，然后是职责，最后是手段。
4. Domain-driven design 听上去其实很奇怪，难道还有不受领域驱动的设计么？其实有，只是不应该有，比如面向数据库设计，和面向数据库编程。

然后我想告诉你 DDD 希望你做到的：
1. 将一个无法再分的业务领域单独在一个限界上下文中设计，开发，注意：是无法再分的。
2. 利用好前人总结的设计模式。
3. 根据 OOP 特性保护好你的类，努力达到高内聚、低耦合的目标，尽量遵守开闭原则。

#### 代码结构

- eleven-application 应用层，提供有状态的应用逻辑
    - eleven-api    一个示例应用，描述了一个聚合的 API 层，你可以假设这里的东西是你的开放接口 API
    - eleven-deploy 运行部署脚本，给 devOps 团队使用的
    - eleven-upms   用户权限管理可运行程序
- eleven-component 公共组件层，提供工具包，基础库。
    - eleven-core 核心包，提供：工具库，通用场景类，通用异常类，通用枚举类，通用工具类，安全模块
    - eleven-core-app 应用层支持
    - eleven-core-domain 领域层支持
- eleven-module 业务模块
    - eleven-upms-api 用户权限管理模块-应用层共享内核
    - eleven-upms-domain 用户权限管理模块-核心领域业务

#### 编码约定
- manager 通常用于表示较底层的支撑，通常会被多个 service 依赖来提供能力，但是不直接被终端层直接调用。
- service 通常用于表示业务主体逻辑，被 endpoint 层直接调用，提供直接的业务能力。

1. xxxHelper 为特定业务场景下的工具类，提供静态方法,比如：PageableQueryHelper 将系统的分页模型->
   技术手段分页模型，AuthenticationHelper
2. xxxUtil 为特定技术场景下的工具类，提供静态方法,比如：NetworkUtil#ping(ip)
3. xxxConvertor 为业务领域下的转换类，只可以单向转换 domain->Dto(DataTransferObject),domain->Vo(ViewObject)
4. domain+Service 位领域对象的服务层，提供最核心的领域服务，只直接处理领域下的使用场（Action 命令），和领域事件发布

#### 请求链路

![请求链路](src/main/doc/微服务请求链路.png "请求链路")

#### 技术选型

- 注册中心 & 配置中心 使用 nacos 做配置中心和注册中心处理
- 服务通信 服务之间通讯使用 spring cloud - openFeign 框架，数据序列化使用 jackson 。
- 网关 网关使用 spring cloud - gateway
- 负载均衡 docker swarm 自带负载均衡
- 链路追踪 spring cloud - sleuth 处理请求链路，采集服务器使用 zipkin 框架，GUI 显示使用 jaeger(可考虑将 skywalking 封装到
  docker 里面替代)
- 日志检测 直接 tcp 写入到 logstash
- mq rabbitmq + 自研消息系统协调器
- 缓存 本地缓存使用caffeine 分布式缓存使用 redis 缓存（redisson 做数据网格客户端）

#### 接口设计

状态码采用 http 标准状态码，统一处理器为 `RestApiAdvice`，按照约定：4XX 表示客户端错误，5xx 表示服务器端错误，2xx 表示正确响应，3xx
表示重定向；

参考 ：

- https://rapidapi.com/blog/put-vs-patch/
- https://google.aip.dev/134#patch-and-put

##### 4xx 状态码

- 资源不存在 404 NOT_FOUND ，在必须的父级数据不存在，或者是地址不存在的时候响应
- 权限不足 - 403 FORBIDDEN ，在指定操作没有权限的时候
- 校验失败 - 422 UNPROCESSABLE_ENTITY 在提交的数据校验失败，或者业务前提条件不足的时候
- 服务器错误 - 500 INTERNAL_SERVER_ERROR 在服务器端出现错误，应该由程序员来解决的时候

##### 2xx 使用

2xx 状态码暂定只使用 200 一个状态，既：只要服务器能正确处理请求，并且处理正确，则相应 200
状态码。有一个歧义是，比如登入的时候用户名错误这种响应，其实并不不存在任何处理错误，而是正确处理的结果，所以首先状态码应该响应
200，然后在相应内容中应该包含登入失败的原因/编码。

比如：

```
POST /auth/credentials/verify {username,password}

========> response <=========
status 200

{pass:false,message:"your login id is not found!"}

```

诸如此类，在特定场景下的处理结果被拒绝，需要给出拒绝理由的场景，服务器应该响应 200，并且在结果中描述清楚结果代码以及原因。

##### rest api 设计

- 创建 POST /XXX 响应为 200 OK 表示资源被创建,成功的时候相应创建的资源，拒绝的时候响应失败原因，带有 4xx 状态
- 读取 GET /XXX 响应为 200 OK 表示资源被读取到，成功的时候响应此资源，拒绝的时候响应失败原因，带有 4xx 状态
- 更新 PUT /XXX 响应为 200 OK 表示全量修改一个资源，比如根据用户 ID 修改用户全部信息,此类操作接口幂等，成功的时候不响应任何结果，拒绝的时候响应失败原因，带有
  4xx 状态
- 修改 PATCH /XXX 响应为 200 OK 表示局部更新一个资源，比如修单独修改用户名，或者是将一个网站文档进行发布（PATCH
  /art/{16}/publish）此接口可返回资源处理结果，比如文章发布的流水记录；拒绝的时候响应失败原因，带有 4xx 状态
- DELETE /XXX 响应为 200 OK 表示资源被删除，成功的时候不响应任何结果，拒绝的时候响应失败原因，带有 4xx 状态


##### 微服务调用交互
内部定义了一个 @InnerApi [InnerApi.java](eleven-starter%2Feleven-starter-core%2Fsrc%2Fmain%2Fjava%2Fcom%2Feleven%2Fcore%2Frest%2Fannonation%2FInnerApi.java) 用于标记一个 endpoint 为内部 API ，这类 API 通常用于提供给内部其他服务调用。
在框架启动阶段会自动找到该标记的 API 进行预处理，包括： 授权机制，路径修缮，日志等处理，并且对该类型的终端,也就是内部服务的终端，我们有如下约定：

1. 内部 API 会被自动添加 /_inner (@see com.eleven.core.rest.ElevenRestConstants.INNER_API_PREFIX) 前缀.
2. 在大部分情况下，一个服务职能有一个内部 API 终端，并且只能有一个调用客户端。
3. 内部 API 不可以使用 @RequestMapping 注解来标记类.
4. 客户端不可以在 @feignClient 注解中标记 path 属性.

按照上述约定,我们会在每一个服务中有且只有一个内部接口,并且该接口以 /_inner 开头，feignClient会在初始化的时候为每一个请求方法增加 /_inner 属性，例如：

```java
// 服务提供
@InnerApi
@RequiredArgsConstructor
public class UpmsApiV1 implements UpmsClient {

    private final AccessTokenService accessTokenService;

    // 这个请求将会被暴露为 /_inner/readToken?token={token}
    @GetMapping("/readToken")
    public Optional<Token> readToken(@RequestParam("token") String token) {
        return accessTokenService.readToken(token).map(AccessToken::toToken);
    }
}

// 服务客户端
@FeignClient(value = UpmsConstants.SERVICE_NAME)
public interface UpmsClient {

    // 这个请求将会请求 /_inner/readToken?token={token}
    @Operation(summary = "读取令牌")
    @GetMapping("/readToken")
    Optional<Token> readToken(@RequestParam("token") String token);
}

```


##### 常见问题

1. 为什么不全部响应 200，然后在自定义结果中给出错误提示？

> 业务逻辑的处理前提 api 文档中会说的很清楚，为什么没有运行成功调用接口的客户端应该非常清楚才对。如果是由于其他用户没有执行某种操作导致业务无法继续执行，并不是统一接口设计的问题，而是具体业务场景的问题。


```shell
mvn versions:set -DnewVersion="$APP_VERSION" -DgenerateBackupPoms=false
mvn -N versions:update-child-modules -DgenerateBackupPoms=false
```

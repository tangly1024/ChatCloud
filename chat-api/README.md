[![License](https://img.shields.io/badge/license-MIT-blue.svg)](http://blog.csdn.net/fjnpysh)

# MI - API 模块  

## 技术要点

- **`Eureka Netflix`** 云端服务发现，一个基于REST的服务，用于定位服务，以实现云端中间层服务发现和故障转移

- **`Hystrix Netflix`** 熔断器，容错管理工具，通过熔断机制控制服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力

### V1.0 releases

- 摘录要点
  
  - `完成服务注册中心可集群配置化、高可用性`
  
  - `完成消费者双模式消费` `(常用：Ribbon模式)`
  

### 主要更新：

- 注册中心的服务与发现 Eureka server；
  
   - `chat-eureka-server`(注册中心一)
   
   - `chat-eureka-server-bak`(注册中心二)
   
- 服务注册与消费 Eureka Client

   - `chat-eureka-client` (服务提供者一)
   
   - `chat-eureka-client-api` (服务提供者二)
   
   - `chat-eureka-consumer` (服务消费者 Feign Ribbon)
    
- 更新 `README.md` 等相关文档和示例；

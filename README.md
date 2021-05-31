# ddd-event
  聚合是事务的边界，如果一个请求需要修改多个聚合，这时可以根据业务需求选择事务一致性或者最终一致性。事务一致性可以考虑多阶段提交解决方案，而最终一致性则常使用事件的发布订阅来实现。  
  多个限界上下文之间的事件发布需要引入第三方中间件来实现，而大部分的业务事件都是在一个上下文中发生的，一个上下文内就没必要引入中间件这样高成本的组件。  
  Spring-data提供了一些工具用于发布领域事件，但是基于内存的，在一些情况下，存在事件丢失的问题。  
  领域事件需要在事务提交成功的时候发布，所以需要监听事务的状态。本项目基于事务的回调机制，实现对事件的保存，失败重发。

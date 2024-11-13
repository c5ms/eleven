## DDD
ddd 不是一个技术规范，也没有什么行为准则，在DDD中，强调如下一些要点，你可以把他们当作核心准则（类似于一个接口），然后使用我们的项目规范作为实现：

1. 有一个分层用来专门描述业务领域知识，这一层不知道任何应用逻辑，比如事物，权限，异步，多线程等。
   2. 外界无法破坏内部结构。
   3. 这一层所处理的所有数据都且仅在这一层中可以操作。
2. 领域数据的操作一定要有实际意义，不会暴露无意义的setter方法，反例比如setStatus(started|ended)。
3. 依赖于抽象，而不关心具体实现，或者说用接口来表示一个不确定如何实现的行为，然后由有能力实现的地方去提供实现。
4. 高层只依赖于底层，controller->service->manager->domain 绝对不会底层向上调用高层，但是低层可以通过**事件驱动**来触发高层逻辑



>  As for me, the main idea of DDD is about Ubiquitous Language, Bounded Contexts and Context Mapping.



- infrastructure 会用技术手段来实现接口，

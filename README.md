# 手写一个基于socket通信的RPC框架
一个简单的入手项目，用于自己理解什么是RPC框架，除此之外并没有什么卵用。
## server provider
监听一个特顶的端口，将字节流序列化成一个表示调用的方法和方法参数的对象，通过这个对象进行反射调用，
将发射调用的结果写回到socket。

## client
- 对需要远程调用的接口进行动态代理。
- 代理对象负责将调用的方法名和参数封装成一个特殊对象，然后转成JSON格式发送到remote server中。
- 同时代理对象取出remote server写入到socket中的结果。

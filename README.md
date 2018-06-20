# websocket
springmvc+spring security实现websocket功能

使用mvn jetty:run 运行项目，需要security 认证的用户，直接写入内存中，只有两个用户 张三，李四，密码都是123456。

在浏览器输入http://localhost:8080直接进入首页，三个h1 分别表示websocket ,socktJS,stomp 演示Demo
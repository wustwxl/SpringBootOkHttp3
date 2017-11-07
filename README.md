# SpringBoot + SpringMVC + Mybatis + Swagger2 + Websocket + OkHttp3

**统一异常处理**
工具包utils中的MyException为统一异常处理类
返回类型主要包括两个属性 String code, String msg。
code 为异常类型,
msg 为异常信息。
  
**统一返回类型**
工具包utils中的MessageVo为统一返回类型处理类
返回类型主要包括三个属性 String code, String info, Object data。
code 为处理结果对应编码, 
info 为处理结果对应信息, 
data 为接口返回数据。

**Websocket**
Websocket实现了浏览器与服务器全双工(full-duplex)通信——允许服务器主动发送信息给客户端。
测试Websocket:
http://localhost:63342/demo/templates/socket.html

**使用OKHttp3发送请求、接收响应**
OKHttp3Util类封装了post/get请求方法。
通过创建OkHttpClient实体，用来构建一次请求。
而Request类用来设置我们请求需要的参数，之后就可以通过client来发送请求。
最后我们就得到了这次请求的响应response。

**Banner**
启动Spring Boot项目的时候，控制台会默认输出启动图案
1.在src/main/resources下新建一个banner.txt文档 
2.通过 http://patorjk.com/software/taag 网站生成需要的字符，
将字符拷贝到步骤1所创建的txt文档中


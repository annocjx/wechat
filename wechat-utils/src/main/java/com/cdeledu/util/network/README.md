#[网络编程](http://baike.baidu.com/link?url=SPZUFG_y0HeM2SPg85bIQ4eAs7lEZIHqyNm1bhoXtqsJZZSqBNgoFt-6INu0cvtotPsOvm-Fy-_hgJOZP8Fd8_)
--------------

TCP与UDP区别
--------------

`TCP---传输控制协议` 提供的是面向连接、可靠的字节流服务。当客户和服务器彼此交换数据前，必须先在双方之间建立一个TCP连接，之后才能传输数据。TCP提供超时重发，丢弃重复数据，检验数据，流量控制等功能，保证数据能从一端传到另一端。


`TCP---传输控制协议` 用户数据报协议，是一个简单的面向数据报的运输层协议。UDP不提供可靠性，它只是把应用程序传给IP层的数据报发送出去，但是并不能保证它们能到达目的地。由于UDP在传输数据报前不用在客户和服务器之间建立一个连接，且没有超时重发等机制，故而传输速度很快

---------------------


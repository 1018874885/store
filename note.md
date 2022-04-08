###### 用户会话Session
session对象主要存在服务器端，可以用于保存服务器的临时数据的对象，所保存的数据在整个项目中都可以通过访问来获取，
把session的数据看做一个共享的数据，首次登录时所获取的用户数据，转移到session对象中即可
session.getAttribute("key“)可以获取session中的数据，将这种行为封装到工具类中或父类BaseController中即可

1.封装session对象中 数据的获取（将其封装在父类中），还有 数据的设置（当用户登录成功后进行数据的设置，设置到全局session对象中）

2.在父类中封装获取两个数据 uid与username 的方法 获取用户头像的方法之后封装在cookie中

3.再登录的方法中将数据封装在session对象中

##项目说明
### imooc_MybatisAutoResponse
根据慕课网《通过自动回复机器人学Mybatis---加强版》视频的案例所编写的一个练习小项目。

##### 功能：

* 使用JSP和Servlet模拟一个微信公众号的自动回复功能
* 使用MyBatis根据需求从数据库中查询数据，并通过Servlet将其传递到前端页面中
* 可以直接通过JSP页面对数据库中的条目进行增删查询

##### 环境：

* 开发平台：Windows 7，Eclipse
* Web：Tomcat 7
* jar包：log4j-1.2.17，mybatis-3.4.1，mysql-connector-java-5.1.39-bin，taglibs-standard-compat-1.2.5，taglibs-standard-impl-1.2.5，taglibs-standard-jstlel-1.2.5，taglibs-standard-spec-1.2.5

##### 使用简介：
* 运行com.imooc.servlet.InitTalkServlet.java执行模拟自动回复页面
* 运行com.imooc.servlet.ListServlet.java执行后端管理页面
##### 备注：
本项目是根据上述教程所编写的练习。有些功能还不完善有待后续补充，比如后端管理页面中的“新增”功能。
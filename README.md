记录了学习SpringBoot的过程：  
基础知识学习并代码实践(base)：  
1.从最简单的controller层获取URL路径、URL参数、http请求和响应中的参数，开始起步学习。  
2.尝试获取配置文件、资源文件。    
3.尝试Bean的配置类、XML注入。  
4.尝试整合Thymeleaf模板及常用操作，以及静态页面的使用，
然后编写了一个页面和服务端交互流程实践：用户提交页面表单->Controller获取表单信息->输出到另外一个页面返回给用户。      
5.尝试数据验证错误处理(SpringBoot官方推荐这种方法不好用，所以编写了自定义基础WEB拦截器，用于输入验证和错误提示)、
配置常见http错误到自定义错误页、全局异常处理、自定义业务AOP拦截器。    
6.数据库相关：使用Druid连接MySQL8，整合mybatis，实践了CRUD操作，并简单尝试了事务控制和Druid监控。   
7.整合Kafka：生产者发送消息，消费者接收消息实践，注意序列化和发送失败处理。  
8.邮箱服务：使用qq邮箱开启了邮箱服务，简单尝试了发送邮件能力。  
9.定时调度：尝试了定时调度、Cron两种方式，并引入多线程的Thread.sleep看互相之间的关系。  
10.整合Redis：关键点(1)RedisTemplate操作模板异常捕获繁琐，编写一个工具类RedisUtil封装；
(2)因为Redis中只支持String和byte[]，所以虽可以正常从Redis中set/get数据，
但查看Redis中的数据是乱码会影响生产环境问题定位，所以基于fastjson自定义序列化和反序列化处理对象解决该问题。  
11.整合Shiro：学习了Subject、SecurityManager、Realm的概念后，逐步动手实践各种基础Realm，
最后主要实践了生产环境常用的从数据库或缓存获取信息的自定义CustomRealm，重写认证和鉴权方法，认证密码加密加盐。  
  

## base
学习并编码实践SpringBoot的一些基本常用操作：   

- UrlTransParamController.java  
1.使用了URL路径方式@PathVariable传参，获取URL路径。   
2.URL参数方式@RequestParam传参，获取URL路径、参数。   
然后@RestController返回String到页面显示。    

- GetHttpParamController.java  
从HTTP请求和响应中获取参数：  
HTTP请求HttpServletRequest request、HTTP响应HttpServletResponse response。     

- GetApplicationConfigController.java，GetApplicationConfig.java  
1.@Value注解方式从application.yml获取配置。  
2.Config类方式@ConfigurationProperties从application.yml获取配置。  

- Geti18nConfigController.java，AbstractBaseController.java   
读取资源文件：  
application.yml中增加spring.messages.basename配置i18n资源文件person.properties的路径和名称i18n/person，
编写在同一的Controller抽象类AbstractBaseController中，统一封装装载资源对象MessageSource，Geti18nConfigController extends AbstractBaseController，
getMessage读取资源文件person.properties中的配置。  

- Bean的配置类、XML注入方式      
1.Bean的配置类注入方式(InfoService.java，InfoServiceImpl.java，GetBeanConfig.java，GetBeanConfigController.java)：  
使用Bean配置类GetBeanConfig的@Configuration方式，new并返回一个InfoServiceImpl，GetBeanConfigController可以从Spring容器装载使用。  
注入调用时，@Resource(name="XX")要与Bean配置类GetBeanConfig中的@Bean(name = "XX") 名称完全一样。  
2.Bean的XML注入方式(InfoService.java，InfoServiceImpl.java，spring-common.xml，GetBeanConfigController.java)：  
启动类加注解@ImportResource(locations = {"classpath:spring-common.xml"})。  
注入调用时，@Resource(name="XX")要与spring-common.xml中的bean id = XX 名称完全一样。  

- 整合Thymeleaf模板、静态页面的使用   
ThymeleafController.java，thymeleaf_person.html，index.html，footer.html                 
1.在resources/templates/目录下，编写Thymeleaf模板--thymeleaf_person.html，其语法极其严格，注意每个标签最后一定要有结束标记“/”。  
2.编写ThymeleafController，注意此时使用的是@Controller注解，要传的参数压入Model，return到上述Thymeleaf模板路径+文件名。  
3.模板渲染页面是不可直接URL访问的，静态页面可直接URL访问，要放置在resources/static目录下，例如index.html。   
4.th:utext：不推荐使用，只是为了测试thymeleaf的utext可以不转义，得到原生html。  
5.Thymeleaf使用#{}读取资源文件person.properties。  
6.Thymeleaf使用()可以做简单计算。  
7.Thymeleaf使用@{路径}进行资源文件定位，可调用JS，可访问Controller中的URL。  
8.Model传递的本质就是属于request属性范围，所以HttpServletRequest也可以通过HTTP内置对象request、Session、Application(ServletContext)将信息传递给Thymeleaf。    
9.Thymeleaf也可以使用类似JSP的方式获取HTTP内置对象中的属性，不过不推荐，这样应该在服务端来做。  
10.Thymeleaf接收来自Controller的对象输出。*可与object一起使用，$是单独使用的   
11.Thymeleaf的逻辑判断：if,unless,switch,case，eq,gt,le...  
12.Thymeleaf对List和Map对象的迭代处理foreach。    
13.(1)被包含页footer.html中待被包含内容使用th:fragment标签。   
(2)标签替换th:replace，是被包含页将主页相应内容和标签都替换，原始宿主标签(被包含页footer.html的footer)还在，但包含标签消失(包含页，也就是本页面的div被替换掉不在了)。   
(3)推荐使用：标签包含th:include，主页只包含被包含页的内容，而不使用被包含页的标签，原始宿主标签消失(被包含页footer.html的footer)，保留包含的标签(包含页，也就是本页面的div还在)。
此外，还支持th:with传参给被包含页，方便与其他前端开发框架整合。   
14.Thymeleaf支持使用#dates.format格式化时间日期输出格式，支持使用#lists操作list，支持使用#sets操作set，支持使用#maps操作map，支持使用#strings操作字符串。       

- 用户提交页面表单->Controller获取表单信息->输出到另外一个页面返回给用户      
ErrorHandleController.java，person_add.html，PersonVo.java，AbstractBaseController.java     
1.ErrorHandleController中的preAddPerson用于获取用户最初URL访问，return跳转到form提交表单person_add.html页面。  
2.form提交表单person_add.html中的action="url相对路径"，指明了提交表单信息到该url路径待处理。  
3.ErrorHandleController中的addPerson，用于获取该url，处理方式是获取表单信息，放入PersonVo对象中，String返回给浏览器显示。   
4.PersonVo类要与form提交表单信息字段对应，系统能自动注入int，char，String类，但其他类例如Date或自定义类，需要自行编写转换代码。   
5.在AbstractBaseController抽象类中，使用注解@InitBinder统一增加，例如解决将页面请求过来的字符串格式日期，转换为java.util.Date。  

- 数据验证错误处理    
PersonVo.java，ValidationMessages.properties，ErrorHandleController.java         
基于hibernate-validation.jar工具包能力，在PersonVo属性上使用注解，做数据验证错误处理。  
错误结果的转义配置在ValidationMessages.properties中，Spring官方推荐，但这明显不靠谱，不如直接编写反射拦截器方便。  
数据验证的错误信息，在ErrorHandleController中，使用BindingResult获取。  

- SpringBoot2.X配置错误页    
ErrorPageController.java，404.html         
使用ThymeLeaf模板时，springBoot会自动到src/main/resources/templates/error/下寻找404.htm、500.html的错误提示页面；  
如果没有使用ThymeLeaf模板时，SpringBoot会到静态资源文件夹寻找404.htm、500.html的错误提示页面。  
如果不想放上述路径，编写ErrorPageController implements ErrorController，从http request中获取status_code，分支跳转到指定error页面。  

- 全局异常处理    
GlobalExceptionHandler.java，error.html          
编写统一全局异常处理GlobalExceptionHandler：借用AOP能力，使用@ControllerAdvice作为一个Controller层切面处理，在Controller层，获取所有异常@ExceptionHandler(value = Exception.class)，
并将异常请求和exception信息addObject到ModelAndView中记录，返回给error.html的thymeleaf模板。   
例如，在ErrorHandleController中，特意构造一个System.out.println(1/0)，生成个ArithmeticException; GlobalExceptionHandler中的@ControllerAdvice会拦截该异常，通过ModelAndView返回给500.html页面显示。  

- 自定义基础WEB拦截器，可用于输入验证和错误提示       
MyInterceptor.java , MyWebMvcConfigurer.java           
编写自己自定义的拦截器MyInterceptor implements HandlerInterceptor：  
preHandle是接口请求之前处理；postHandle是接口正常处理之后；afterCompletion是无论接口正常或异常处理之后。  
编写拦截器配置类MyWebMvcConfigurer implements WebMvcConfigurer：   
注：在SpringBoot2.0及Spring 5.0中， WebMvcConfigurerAdapter已被废弃，官方推荐直接实现WebMvcConfigurer接口。
registry.addInterceptor注册自己自定义的拦截器MyInterceptor，addPathPatterns指定URL拦截。     
拦截器依赖于web框架，在SpringMVC中就是依赖于SpringMVC框架。
在实现上基于Java的反射机制，属于面向切面编程（AOP）的一种运用。  
由于拦截器是基于web框架的调用，因此可以使用Spring的依赖注入（DI）进行一些业务操作，
同时一个拦截器实例在一个controller生命周期之内可以多次调用。  
但缺点是只能对controller请求进行拦截，对其他的一些比如直接访问静态资源的请求则没办法进行拦截处理--使用过滤器。                       

- 自定义业务AOP拦截器        
ServiceAspect.java中使用Aspect注解，execution表达式指明要拦截的业务类(InfoServiceImpl)及方法，
实现业务service层的拦截，写了个环绕@Around拦截器，在GetBeanConfigController中URL访问调用尝试。   
注：pom.xml需要增加spring-boot-starter-aop。   

- 使用Druid连接MySQL8         
application.yml中datasource配置项，配置了MySQL8的连接信息，同时使用了DruidDataSource，性能很高，同时具备很好监控性，推荐！ 
编写DataSourceTest测试类，测试连接OK。   
注意：   
1.pom.xml中的mysql-connector-java的版本号要与安装的MySQL版本号一致。  
2.使用Druid，pom.xml中除了增加druid外，还要增加mybatis-spring-boot-starter。   

- mybatis整合   
1.pom.xml中增加mybatis-spring-boot-starter。   
2.application.yml中指定mybatis.cfg.xml、model、mapper文件路径。   
3.对应放置resources\mybatis\mybatis.cfg.xml。  
4.main\java\com\ww\base\model\DeptModel中，要有无参构造方法。   
5.main\java\com\ww\base\mapper\DeptMapper接口中，增加@Mapper注解。每个都加太麻烦，所以更推荐在启动类增加@MapperScan("com.ww.base.mapper")。   
6.main\java\com\ww\base\service\DeptService和DeptServiceImpl，调用DeptMapper。  
7.Mapper的注解方式：在java\com\ww\base\mapper\DeptMapper接口中使用注解，实现CRUD。  
8.Mapper的XML方式(官方更推荐注解方式)：resources\mybatis\mapper\Dept.xml：  
8.1 namespace指定Mapper层对应接口类DeptMapper的全路径名称(写完尝试能点选过去)。  
8.2 resultMap上的标签：id属性是XML中该标签的唯一标识，type属性是数据库表对应的Model的类名。  
8.3 resultMap内部子标签：id是主键标识，result是普通字段，column是列名，property是对应Model中的属性名，jdbcType属性一定要大写！     
9.test\java\com\ww\base\mapper\DeptMapperTest.java编写上述CRUD的JUnit测试验证。   
10.application.yml可以调整mapper类的log level=trace看具体执行的SQL语句。  
PS：SQL建库建表脚本dept.sql。  

- 事务控制    
1.主启动类要加@EnableTransactionManagement。   
2.业务层接口增加事务注解，如：@Transactional(propagation = Propagation.REQUIRED)。   

- Druid监控   
1.打开总开关：application.yml中增加spring.datasource.filters: stat,wall,logback。  
2.pom.xml增加log4j。  
3.编写DruidConfig配置类：ServletRegistrationBean配置后台访问鉴权信息。FilterRegistrationBean配置前台访问哪些做监控，哪些不做监控。   
4.页面访问DeptController的URL，DeptController调用DeptService调用DeptMapper，通过Druid访问MySQL。  
5.Druid监控页面http://localhost/druid 查看。   

- Kafka生产者和消费者    
KafkaMessage.java，KafkaProducerServiceImpl.java，KafkaConsumerServiceImpl.java      
1.pom.xml增加spring-kafka。  
2.application.yml中增加Kafka的服务端server、生产者producer、消费者consumer配置。   
3.model中定义了一个KafkaMessage，用于封装向Kafka发送的消息。   
4.编写KafkaProducerServiceImpl：  
定义了一个泛型类KafkaProducerServiceImpl<T>，T就是发送的消息对象，序列化使用了阿里的fastjson。 
使用 KafkaTemplate.send消息，Spring Boot自动装配，没必要自己再定义一个Kafka配置类。   
消息发送后，在回调类里面做成功和失败的处理操作：ListenableFutureCallback 类有两个方法，分别是onSuccess和onFailure。     
5.编写KafkaConsumerServiceImpl：  
使用@KafkaListener注解监听topics消息，此处的topics支持多个，与Producer中的KafkaTemplate.send的topic一致。  
使用@Header(KafkaHeaders.RECEIVED_TOPIC)直接获取topic。  
从ConsumerRecord<?, ?> record中得到的partition、offset、消息内容等信息。  
PS：判断接收记录record是否NULL--JDK8新特性：使用Optional避免null导致的NullPointerException，使用Optional.isPresent()：判断值是否存在。  
6.编写测试类KafkaSendReceiveTest，调用KafkaProducerServiceImpl.send实现向Kafka发消息并接收。   

- 邮箱服务   
SendMailTest.java      
1.pom.xml引入spring-boot-starter-mail。  
2.application.yml中增加mail的相关配置，其中用户名和密码需要从邮箱服务器分配获取。  
3.编写一个SendMailTest的测试类：使用SimpleMailMessage对象设置邮件的发送方、接收方、主题、内容等信息，然后使用javaMailSender.send(simpleMailMessage)。   

- 定时调度   
SchedulerConfig.java，MyScheduler.java        
1.启动类开启定时任务调度总开关@EnableScheduling。    
2.默认的定时任务是串行执行的，生产环境一般使用并行的：需要编写一个配置类SchedulerConfig implements SchedulingConfigurer，setScheduler一个线程池Executors.newScheduledThreadPool。  
3.MyScheduler中，使用@Scheduled注解，分别实践了fixedRate和cron两种方式的定时任务执行，并引入多线程的Thread.sleep对比分析。   

- Redis整合      
1.pom.xml引入spring-boot-starter-data-redis。      
2.application.yml中配置redis服务端连接信息。   
3.RedisUtil.java：RedisTemplate操作模板异常捕获繁琐，编写一个工具类RedisUtil封装。  
4.RedisTest.java：此时就能向Redis使用RedisUtil封装的方法来正常set和get数据了。  
5.此时有一个问题是查看Redis中的数据是乱码，因为Redis中只支持String和byte[]，所以需要序列化和反序列化解决：  
5.1 pom.xml引入fastjson，用于对象set到redis时序列化为JsonString，从redis中get JsonString反序列化为对象。  
5.2 RedisConfig.java：编写配置类，主要用于定义key和value的序列化和反序列化。  
5.3 TedisCacheManager：Redis 容易出现缓存问题（超时、Redis 宕机等），当使用 spring cache 的注释 Cacheable、Cacheput 等处理缓存问题时，
我们无法使用 try catch 处理出现的异常，所以最后导致结果是整个服务报错无法正常工作。
通过自定义 TedisCacheManager 并继承 RedisCacheManager 来处理异常可以解决这个问题。
SpringBoot2.0目前还不支持连接多个Redis，编码实现方案为：application.yml中再增加一套Redist配置，然后编写一个配置类读取该配置实现，参考：
https://blog.csdn.net/dawn_after_dark/article/details/82112399   

- Shiro整合   
前提：pom.xml中添加shiro-core依赖。  
1.初次尝试Shiro的认证功能AuthenticationTest.java：    
1.1构建Realm(初次使用SimpleAccountRealm)，并向Realm中addAccount用户名和密码信息。  
1.2.构建SecurityManager环境(初次使用DefaultSecurityManager)，并setRealm之前构建的Realm，然后启用。  
1.3.SecurityUtils.getSubject()获得主体Subject，UsernamePasswordToken设置主体提交的用户名和密码，提交认证请求subject.login(token)。  
1.4捕获UnknownAccountException、IncorrectCredentialsException异常并处理。  
2.初次尝试Shiro的鉴权功能AuthorizationTest.java：   
登录认证过程同上，以下为不同点。  
2.1构建Realm(初次使用SimpleAccountRealm)，除了向Realm中addAccount用户名和密码外，还加上了角色信息。
2.2登录认证subject.login(token)后，使用subject.checkRole验证单个角色或subject.checkRoles同时验证满足多个角色要求。  
3.IniRealmTest.java：  
IniRealm，可读取配置文件获取用户名、密码和角色信息。生产环境肯定不用这种Realm，所以简单尝试。  
4.JdbcRealmTest.java、jdbc_realm.sql：  
JdbcRealm设置数据源setDataSource(dataSource)，可读取数据库中用户名、密码、角色和权限信息。  
注意，JdbcRealm默认是不检查权限的(全部拒绝)，需要手工打开：jdbcRealm.setPermissionsLookupEnabled(true)。  
查看JdbcRealm源码，根据其查询语句编写了相应的用户名、密码、角色和权限信息建表和插入数据脚本jdbc_realm.sql。  
5.JdbcRealmCustomizedTest.java、jdbc_realm_customized.sql：  
因项目中表结构基本不可能和JdbcRealm定义的表结构一样，可以通过如下赋值自己的SQL语句做JDBC查询：  
通过用户名查密码：jdbcRealm.setAuthenticationQuery(userSql);    
通过用户名查角色：jdbcRealm.setUserRolesQuery(roleSql);  
通过用户名查权限：jdbcRealm.setPermissionsQuery(permSql);  
6.CustomRealm.java、CustomRealmTest.java、EncryptUtil.java：   
Realm和DAO层是分离的，所以项目一般使用自定义Realm：CustomRealm extends AuthorizingRealm：  
6.1重写认证方法(doGetAuthenticationInfo)：  
6.1.1从主体Subject传过来的用户认证请求信息中，使用(String) authenticationToken.getPrincipal()获取用户名。 
6.1.2通过用户名，到数据库或缓存中获取该用户密码凭证，密码是加盐再加密的。   
6.1.3设置setCredentialsMatcher的加密算法名称和迭代次数，然后加入Realm中customRealm.setCredentialsMatcher(matcher);  
加盐：authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));   
6.2重写鉴权方法(doGetAuthorizationInfo)：      
6.2.1从已经通过认证的PrincipalCollection获取用户名(String) principalCollection.getPrimaryPrincipal();  
6.2.2通过用户名，到数据库或缓存中获取该用户角色和权限信息  
6.2.3将该用户的角色信息和权限信息，放入SimpleAuthorizationInfo

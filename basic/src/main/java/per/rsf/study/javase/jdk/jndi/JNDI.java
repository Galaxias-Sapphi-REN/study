package per.rsf.study.javase.jdk.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * https://docs.oracle.com/javase/jndi/tutorial/
 * https://ejbvn.wordpress.com/category/week-1-enterprise-java-architecture/day-04-using-jndi-for-naming-services-and-components/
 * JNDI Java 命名和目录接口（Java Naming and Directory Interface）
 * 1.使用场景：
 * Enterprise JavaBean（EJB）企业应用程序的基础，适用于维护少量稳定的数据，所有服务器均可访问。
 * 客户端使用JNDI API查找EJB组件，Java消息服务（JMS）队列和主题以及Java数据库连接（JDBC）数据源等。
 * <p>
 * 由服务器是执行绑定，取消绑定和重新绑定操作。
 * 客户端通过提供名称来执行对象的查找。
 * <p>
 * 2.作用：
 * 解耦：通过注册、查找JNDI服务，可以直接使用服务，而无需关心服务提供者，逻辑与资源解耦
 * <p>
 * 3.结构：
 * javax.naming：命名操作；
 * javax.naming.directory：目录操作；
 * javax.naming.event：在命名目录服务器中请求事件通知；
 * javax.naming.ldap：提供LDAP支持；
 * javax.naming.spi：允许动态插入不同实现
 * <p>
 * 4. 命名服务
 * 命名将对象与名称相关联的过程称为绑定。
 * 案例
 * DNS : 通过主机名而不是其数字IP地址来引用主机。
 * COS : 用于 CORBA 应用程序注册 CORBA 对象的另一种命名服务。
 * NDS : 用于存储用户和组信息以进行身份​​验证。
 * LDAP
 * 【总结】：命名系统提供唯一名称到对象的映射。通过一个名称，按照命名约定，返回存储对象。
 * <p>
 * 5. 目录服务 = 索引 + 缓存 + IO
 * 分层数据库，用于存储对象以进行快速检索，并且不经常进行插入，删除和更新。
 * 案例
 * 电话簿黄页 | LDAP | Active Directory
 * 【总结】：是管理共享信息的存储和分发的方法。
 * <p>
 * 6. JNDI 标准化对各种命名和目录服务的访问
 * API : 应用程序组件使用API​​来访问命名和目录服务。
 * SPI : 将命名和目录服务的提供程序插入J2EE平台。
 * JNDI是接口或API，而不是实现，用于将访问层抽象为命名和目录服务提供者
 * 使用方法：
 * a. 客户端首先建立与JNDI服务的连接（有时称为JNDI树）。
 * b. 创建一个上下文以促进对系统组件和资源的访问。（上下文Context共享命名约定）
 * Context ctx = new InitialContext();
 * c. 某些命名服务提供子上下文，该子上下文类似于文件系统中的子目录。
 * d. 上下文操作
 * bind()添加条目 | 重复绑定 NameAlreadyBoundException
 * rebind()替换条目名称
 * lookup()查找或定位对象 | 获得Object强制转换为所需对象的类，没找到 NamingException
 * unbind()删除条目
 * rename()重命名
 * listBindings()枚举list
 * close()JNDI服务断开连接，释放Context资源
 * <p>
 * 7. 验证用户访问系统资源的安全性机制
 * 默认的InitialContext（未指定参数）将创建一个供匿名客户端使用的新Context。
 * prop.put(Context. SECURITY_PRINCIPAL, "Laura");
 * prop.put(Context. SECURITY_CREDENTIALS, "whiskers");
 * <p>
 * 8. J2EE应用：开发与部署环境隔离(Java应用程序，小程序，servlet，JSP，TagLib和EJB)
 * 从EJB 1.1开始，所有Bean都有一个默认的JNDI上下文，称为企业命名上下文。
 * 默认上下文存在于名为java：comp / env的命名空间
 * 资源工厂引用放置在按其资源管理器类型区分的子树中
 * jdbc : JDBC DataSource references
 * jms : JMS connection factories
 * mail : JavaMail connection factories
 * ejb : EJB component factories (home interface)
 * 案例：
 * //执行JNDI查找以获得队列名称
 * QueueConnectionFactory qcf = (QueueConnectionFactory)ctx.lookup("java:comp/env/jms/QueueCF");
 * Queue q = (Queue)ctx.lookup("java:comp/env/jms/StudentQueue"0;
 * //执行JNDI查找以获得JDBC连接池工厂
 * DataSource ds = (DataSource)ctx.lookup("java:/comp/env/jdbc/myDBPool");
 * <resource-ref><res-ref-name>jdbc/myDBPool</res-ref-name>
 * <res-type>java.sql.DataSource</res-type></resource-ref>
 * <p>
 * 9. 最佳实践
 * 每个远程JNDI查找远程方法调用，建立与JNDI服务的连接，都是昂贵的任务。
 * 缓存句柄和上下文引用可以提高性能
 * <p>
 * 10. Tomcat JNDI
 * tomcat已经集成该服务（内置并默认使用DBCP连接池），简单来说就是键值对的mapping，而且在tomcat服务器启动的首页configuration中就已经有完成的示例代码。
 * 要想使用tomcat的JNDI服务，只需要导入相关的jar包，建立所需的配置文件，采用JDK的命名服务API根据配置名称即可获得相应的服务。
 * META-INF配置文件：comtext.xml
 * 服务名称 : "jdbc/mybatis-jndi"
 * 服务类型 : "javax.sql.DataSource"
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Context>
 * <Resource name="jdbc/mybatis-jndi" auth="Container" type="javax.sql.DataSource"
 * maxActive="100" maxIdle="30" maxWait="10000" username="root"
 * password="密码" driverClassName="com.mysql.jdbc.Driver"
 * url="jdbc:mysql://localhost:3306/javadb" />
 * </Context>
 * <p>
 * 11. Spring JNDI
 * 数据源配置
 * META-INF/context.xml
 * <?xml version="1.0" encoding="UTF-8"?>
 * <Context>
 * <Resource name="jdbc/demoDB" auth="Container"
 * type="javax.sql.DataSource"
 * driverClassName="com.mysql.jdbc.Driver"
 * url="jdbc:mysql://localhost:3306/demo"
 * username="root"
 * password="1234"
 * maxActive="50"
 * maxIdle="30"
 * maxWait="10000" />
 * </Context>
 * applicationContext.xml
 * <bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
 * <property name="jndiName">
 * <value>java:comp/env/jdbc/portalDataService</value>
 * </property>
 * </bean>
 * 不使用jndi时
 * <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
 * <property name="driverClassName" value="${jdbc.driver}" />
 * <property name="url" value="${jdbc.url}?allowMultiQueries=true" />
 * </bean>
 */
public class JNDI {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put(Context.INITIAL_CONTEXT_FACTORY, "");

        Connection conn = null;
        try {
            // 连接到特定或默认的JNDI服务并建立新的上下文
            // JNDI自动从应用程序的类路径和JAVA_HOME/lib/jndi.properties中的所有定义中读取应用程序资源文件
            Context ctx = new InitialContext();//prop
            // 绑定或添加新的名称/对象映射
            ctx.bind("", "");
            // 使用名称在命名服务中查找对象
            DataSource ds = (DataSource) ctx.lookup("java:config/mysql.config.xml￿");
            conn = ds.getConnection();
            PreparedStatement ps = conn.prepareStatement("select host, user from user ;");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString("host") + " ：" + rs.getString("user"));
            }
            conn.close();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }
}

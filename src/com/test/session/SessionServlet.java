package com.test.session;

import java.bean.User;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Session对象不是替代Cookie技术，Session借助cookie的特点，只让浏览器保存少量的不重要的数据，然后将重要数据保存在服务器的session中
 * Session:会话对象 作用：在一次会话(浏览器打开第一次访问服务器到浏览器关闭的过程)中不同的web资源中保存数据 特点：
 * 只关心浏览器是否关闭，不关心服务器是否重启 不同浏览器不共享会话 在jsp页面中直接可以使用，
 * servlet中需要调用request.getSession()方法获取 Session对象创建时间： 第一次调用getSession方法时会创建
 * 访问jsp页面：翻译之后的Servlet中已经调用过了，创建了一个Session对象 访问Servlet：手动调用request.getSession()
 * 
 * 服务器创建Session对象时： 1.先创建Session对象： 在服务器中保存会话数据 2.再创建Cookie("JSESSIONID" ,
 * "唯一的字符串")并设置到响应报文中交给浏览器 浏览器默认保存一次会话： 给浏览器一个唯一的标记 Set-Cookie:
 * JSESSIONID=5CB07A76E54F1CE96646D12489A4F540; Path=/11_web_Session; HttpOnly
 * 
 * 3.服务器在内部维护了一个SessionMap<String , HttpSession> 将session和对应的cookie绑定:
 * map.put("唯一的字符串" , session);
 * 
 * 第二次调用不在创建新的session对象，需要使用以前的 session对象创建的原理： request.getSession执行的流程
 * 服务器会先从请求报文中获取一个Cookie[name=JSESSIONID] - 如果没有： 证明浏览器第一次访问服务器 服务器走创建Session的步骤
 * - 如果有：Cookie: JSESSIONID=5CB07A76E54F1CE96646D12489A4F540 证明浏览器已经访问过服务器了
 * 服务器获取请求报文的JSESSIONID对应的cookie的value值
 * 在根据id值去map中获取对应的session对象直接使用，保证一次会话中使用的是同一个session对象
 * 
 * Session对象在服务器中的最大不活动销毁时间： session对象在服务器中如果超过一定时间没人使用，服务器认为会话已经结束了
 * 从浏览器最后一次使用session对象开始计时超过一定的时间，服务器会将session对象销毁掉 修改： 1、全局修改：
 * 在tomcat的配置文件中[servers/web.xml中 可以给工作空间所有项目的session对象统一设置过期时间]
 * <session-config> <session-timeout>30</session-timeout> </session-config>
 * 2、当前项目内修改： 修改当前项目所有的session对象的过期时间 在项目的web.xml文件中设置 3、给具体的某个Session对象进行设置：
 * session.setMaxInActiveInterval(int seconds);
 * 
 * 问题： 为什么浏览器能够在一次会话的 多次请求中 使用会话域共享数据(多个资源中获取到的是同一个session对象)？ - 第一次访问服务器
 * 如果服务器调用了getSession方法，服务器会创建一个JSESSIONID的Cookie交给浏览器 浏览器保存一次会话 -
 * 本次会话内如果浏览器再次访问服务器，浏览器自动会将JSESSIONID的cookie提交给服务器，服务器可以根据cookie的value值获取到对应的session对象
 * 就可以使用同一个session对象绑定的数据 - 当浏览器重新打开，内存清空，再访问服务器时就没有了JSESSIONID，就使用不了上次的会话对象了
 * 
 * 浏览器重启后失去了获取服务器session对象的key了，虽然服务器中的session对象可能还会存在
 * 问题：可以不可以让session对象作用范围超过一次会话？ 开发中一定不会改Session对象的作用范围
 * 修改浏览器保存JSESSIONIDcookie的有效时间
 * 
 * 为什么会话对象只关心浏览器是否重启，和服务器重启没有关系?[对象流：将对象序列化保存到本地，也可以将本地的数据反序列化到内存中加载成对象]
 * 浏览器重启后：失去了上次的session对象对应的JSESSIONID，会话结束了 服务器重启后：会清空服务器的内存，session对象保存在服务器中
 * 重启后仍然存在，证明服务器在关闭前会将session对象想办法持久化保存 当服务器启动成功时又可以将本地持久化的session加载到内存中重新生成对象
 * 活化：服务器将本地硬盘上session.ser文件内的内容加载到内存中还原成SessionMap的过程
 * 钝化：服务器正常关闭时将内存中的SessionMap序列化到本地硬盘上session.ser文件内的过程
 * 如果自定义类的对象存到session中，希望能够和Session一起活化钝化，必须实现序列化接口
 * 
 * 
 * 
 */
public class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1、创建Session对象：此方法默认先判断session对象是否存在，如果存在直接获取使用，如果不存在则创建
		HttpSession session = request.getSession();
		// session.setMaxInactiveInterval(30);
		session.setMaxInactiveInterval(20);// my exe
		// 2、向域中设置属性值
		session.setAttribute("date", new Date());
		session.setAttribute("username", "张大超");
		session.setAttribute("myname", "张俊超");
		session.setAttribute("user", new User(111, "luolina", "123456", "luolina@163.com"));
		// session对象的相关方法
		System.out.println("session对象对应的JSESSIONID：" + session.getId());
		System.out.println("session是不是新创建的：" + session.isNew());
		// 默认1800秒
		System.out.println("session对象的最大不活动销毁时间：" + session.getMaxInactiveInterval());
		
		// 唯一字符串创建的规则： 32位的16进制的字符串
		// 用户的机器码+时间戳 拼接生成的

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
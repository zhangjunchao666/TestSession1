package java.bean;

import java.io.Serializable;

/**
 * 对应bs_user表的实体类
 *    javabean的属性名和 表的字段名尽量保持一致,BeanHandler将查询到的数据封装位对象时会通过反射根据查询到的数据库的字段名获取javabean的对应的属性
  */
public class User implements Serializable{
	/**
	 * 用户id，主键
	 * 	- 包装类属性，对象在初始化时如果没有设置值默认为null
	 * 	- 基本类型，默认值0
	 */
	private Integer id;
	/**
	 * 账号
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 邮箱
	 */
	private String email;
	public User(Integer id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	public User() {
		super();
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
	
}

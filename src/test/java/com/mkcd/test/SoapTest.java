package com.mkcd.test;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bush76.core.ws.client.hello.IHelloService;
import com.bush76.core.ws.client.hello.MapConvertor;
import com.bush76.core.ws.client.hello.MapEntry;
import com.bush76.core.ws.client.hello.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.sf.json.JSONObject;

/**
 * 针对wsdl（soap）
 * 
 * @author bush76
 * 
 * 
 * 	先将https://127.0.0.1:8443/springCXF/ws/hello?wsdl下载到本地
 * 
 *  客户端代码生成
 * 
 *  wsimport -p com.bush76.core.ws.client.hello -keep e:/wsdl/hello.wsdl
 *  
 *  留下IHelloService和三个实体类即可
 * 
 *  获取IHelloService两种方式
 * 
 *  1.IHelloService client =
 *  	(IHelloService)CXFUtils.getCXFClientObj(IHelloService.class,HELLO_BASE_URL);
 * 
 *  2.ClassPathXmlApplicationContext context = new
 *  	ClassPathXmlApplicationContext("springCXF-client.xml"); 
 * 	  IHelloService client = (IHelloService) context.getBean("helloClient");
 *
 */
public class SoapTest {

	ClassPathXmlApplicationContext context = null;
	
	IHelloService helloClient = null;

	String result = null;

	Map<String, Object> map = null;
	
	List<User> list = null;
	
	MapConvertor mc = null;

	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("springCXF-client.xml");
		helloClient = (IHelloService) context.getBean("helloClient");
	}

	@After
	public void print() {
		if (result != null) {
			System.out.println(result);
		}
		if (map != null) {
			System.out.println(JSONObject.fromObject(map).toString());
		}
		if(list != null){
			for(User user : list){
				System.out.println(user.getId() + " - " + user.getName() + " - " + user.getPassword());
			}
		}
		if(mc != null){
			List<MapEntry> entrys = mc.getEntries();
			for(MapEntry entry : entrys){
				if(entry.getValue() instanceof User){
					User user = (User)entry.getValue();
					System.out.println(user.getId() + " - " + user.getName() + " - " + user.getPassword());
				}else{
					System.out.println(entry.getKey() + " : " + entry.getValue());
				}
			}
		}
	}

	@Test
	public void say() {
		result = helloClient.say("zhangsan");
	}

	@Test
	public void list() {
		list = helloClient.list();
	}

	@Test
	public void getUserMap() {
		mc = helloClient.getUserMap();
	}

	@Test
	public void getMapObject() {
		mc = helloClient.getMapObject();
	}

	@Test
	public void saveUser() {
		User user = new User();
		user.setId("007");
		user.setName("James Bond");
		user.setPassword("NoWayRemenber");
		result = helloClient.saveUser(user);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void getXStreamUser() {
		String xml = helloClient.getUser();
		XStream xstream = new XStream(new DomDriver());
		map = (Map<String, Object>) xstream.fromXML(xml);
	}

}

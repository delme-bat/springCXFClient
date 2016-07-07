package com.mkcd.test;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.sf.json.JSONObject;

public class RestfulTest {
	
	WebClient client = null;
	
	String result = null;

	@Before
	public void init(){
		client = WebClient.create("https://192.168.1.176:8443/springCXF/ws/user", "config/clientConfig.xml").accept(MediaType.APPLICATION_JSON);
	}
	
	@After
	public void print(){
		System.out.println(result);
	}
	
	@Test
	public void get(){
		result = client.path("/1").get(String.class);
	}

	@Test
	public void post() {
		JSONObject json = new JSONObject();
		json.put("id", "003");
		json.put("name", "wangwu");
		json.put("password", "567");
		result = client.path("/add").type(MediaType.APPLICATION_JSON).post(json.toString(),String.class);
	}

}

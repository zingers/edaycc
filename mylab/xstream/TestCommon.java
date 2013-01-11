package cn.com.xming.xmlparse.xstream;

import static org.junit.Assert.assertEquals;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Test;

import cn.com.xming.framework.util.GetPlainObject;
import cn.com.xming.framework.util.PlainObject;

public class TestCommon {

	@Test
	public void calendar(){
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
		System.out.println(sdf.format(cal.getTime()));
		assertEquals(3,3); 
	}
	
	@Test
	public void bigdecimal(){
		BigDecimal bdm = new BigDecimal("1.43");
		System.out.println(bdm.scale());
		System.out.println(bdm.toString());
		
		Object obj = new BigDecimal("1.43");
		System.out.println(obj);
		assertEquals(3,3);
	}
	
	@Test
	public void regular(){
		String cont = "<Parameter>\r\n" + 
				"    <anyType xsi:type=\"xsd:string\">xx</anyType>\r\n" + 
				"    <anyType xsi:type=\"ArrayOfAnyType\">\r\n" + 
				"      <anyType xsi:type=\"xsd:string\">id001</anyType>\r\n" + 
				"      <anyType xsi:nil=\"true\" />\r\n" + 
				"    </anyType>\r\n" + 
				"    <anyType xsi:type=\"xsd:decimal\">10.01</anyType>\r\n" + 
				"    <anyType xsi:type=\"xsd:dateTime\">2013-01-09T16:02:12</anyType>\r\n" + 
				"    <anyType xsi:type=\"xsd:dateTime\">2013-01-09T16:02:19-08:00</anyType>\r\n" + 
				"    <anyType xsi:type=\"xsd:int\">123</anyType>\r\n" + 
				"    <anyType xsi:type=\"xsd:string\">abc</anyType>\r\n" + 
				"  </Parameter>\r\n" + 
				"";
		String paStr = "    <anyType xsi:type=\"xsd:string\">(.+)</anyType>";
		
		Pattern pattern = Pattern.compile(paStr);
		Matcher matcher = pattern.matcher(cont);
		
		
		/**
		 * 1
		   xx
		   id001
           abc
		 */
		System.out.println(matcher.groupCount());
		//groupcount不是指该正则表达式规则在源文本里总共匹配多少次
		//而是指在每一次匹配模式里， 该抓的目标文本有几部分
		//也就是说，一个正则表达式可能每次抓2-多次匹配小字串
		while(matcher.find()){
			String rs = matcher.group(1);
			System.out.println(rs);
			
		}
		
		assertEquals(3,3);
	}
	
	@Test
	public void reflects(){
		PlainObject po = GetPlainObject.getInstance();
		PropertyDescriptor[] plist = PropertyUtils.getPropertyDescriptors(PlainObject.class);
		//(PlainObject.class);
		
		/*
		 *  bdm:class java.math.BigDecimal
			class:class java.lang.Class
			date:class java.util.Date
			intg:class java.lang.Integer
			name:class java.lang.String
			po2List:interface java.util.List
			po2Set:interface java.util.Set
			time:class java.util.Calendar
		 */
		//注意这里是declared类型，而不是实际类型
		for(PropertyDescriptor pd : plist){
			System.out.println(pd.getName() + ":" +pd.getPropertyType());
		}
		assertEquals(3,3);
	}
	
	
	@Test
	public void date(){
		Date date = new Date(1357789552140l);
		System.out.println(date);
	}
	
}

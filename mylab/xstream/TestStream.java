package cn.com.xming.xmlparse.xstream;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class TestStream {



	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test3();
		//test2();
		test1();
	}

	private static void test1() {
		XStream xs = new XStream();
		InvokeParam1 para = new InvokeParam1();
		para.setMethod("oma");
	

		List<Object> ls1 = new ArrayList<Object>();
		ls1.add(2);
		ls1.add("fff");
		para.setParameter(ls1);
		
		List<Object> ls2 = new ArrayList<Object>();
		ls2.add(2);
		ls2.add("第二层");
		ls2.add(new Date());
		ls2.add(new BigDecimal("1.34"));
		ls2.add(Calendar.getInstance());
		ls1.add(ls2);
		
		
			
		System.out.println(xs.toXML(para));
	}

	private static void test4() {
		XStream xs = new XStream();
		InvokeParam para = getModel();
		
		xs.alias("InvokeParam", InvokeParam.class);
		xs.alias("anytype", Anytype.class);
		
		xs.aliasField("Method", InvokeParam.class, "method");
		xs.aliasField("Parameter", InvokeParam.class, "parameter");
		
		xs.addImplicitCollection(Paras.class, "lsNested");
		
		xs.useAttributeFor(Anytype.class, "type");
		xs.aliasField("xsi:type", Anytype.class, "type");
			
		System.out.println(xs.toXML(para));
	}
	
	private static void test2() {
		XStream xs = new XStream();
		InvokeParam para = getModel();
		
		xs.alias("InvokeParam", InvokeParam.class);
		xs.alias("anytype", Anytype.class);
		
		xs.aliasField("Method", InvokeParam.class, "method");
		xs.aliasField("Parameter", InvokeParam.class, "parameter");
		
		xs.addImplicitCollection(Paras.class, "lsNested");
		
		xs.useAttributeFor(Anytype.class, "type");
		xs.aliasField("xsi:type", Anytype.class, "type");
		//xs.registerConverter(new AnytypeConvertor());	
		System.out.println(xs.toXML(para));
	}
	
	private static void test3() {
		XStream xs = new XStream();
		InvokeParam para = getModel3();
		
		xs.alias("InvokeParam", InvokeParam.class);
		xs.alias("anyType", Anytype.class);
		
		xs.aliasField("Method", InvokeParam.class, "method");
		xs.aliasField("Parameter", InvokeParam.class, "parameter");
		
		xs.addImplicitCollection(Paras.class, "lsNested");
		
		//xs.useAttributeFor(Anytype.class, "type");
		//xs.aliasField("xsi:type", Anytype.class, "type");
		xs.registerConverter(new AnytypeConvertor());	
		System.out.println(xs.toXML(para));
	}
	
	public static InvokeParam getModel(){
		InvokeParam invoke = new InvokeParam();
		invoke.setMethod("L00101");
		Paras paras = new Paras();
		invoke.setParameter(paras);
		List<Anytype> ls1 = new ArrayList<Anytype>();
		Anytype at11 = new Anytype();
		at11.setType("xsd:int");
		at11.setValue("0");
		Anytype at12 = new Anytype();
		at12.setType("xsd:string");
		at12.setValue("Microsoft XPS Document Writer");
		ls1.add(at11);
		ls1.add(at12);
		paras.setLsNested(ls1);
		return invoke;
	}
	
	public static InvokeParam getModel3(){
		InvokeParam invoke = new InvokeParam();
		invoke.setMethod("L00101");
		Paras paras = new Paras();
		List<Anytype> ls0 = new ArrayList<Anytype>();
	
		paras.setLsNested(ls0);
		invoke.setParameter(paras);
		
		Anytype at1 = new Anytype();
		at1.setType("xsd:int");
		at1.setValue("0");
		
		Anytype at2 = new Anytype();
		at2.setType("ArrayOfAnyType");
		List<Anytype> ls2 = new ArrayList<Anytype>();
		at2.setLsNested(ls2);
		
		Anytype at21 = new Anytype();
		ls2.add(at21);
		at21.setType("xsd:string");
		at21.setValue("Microsoft XPS Document Writer");	
	
		
		Anytype at22 = new Anytype();
		ls2.add(at22);
		at22.setType("ArrayOfAnyType");
		List<Anytype> ls22 = new ArrayList<Anytype>();
		at22.setLsNested(ls22);
		
		Anytype at221 = new Anytype();
		at221.setType("xsd:string");
		at221.setValue("Microsoft XPS Document Writer");	
		ls22.add(at221);

		Anytype at222 = new Anytype();
		at222.setType("xsd:string");
		at222.setValue("Microsoft Office Document Image Writer");	
		ls22.add(at222);
		
		Anytype at3 = new Anytype();
		at3.setType("ArrayOfAnyType");
		List<Anytype> ls3 = new ArrayList<Anytype>();
		at3.setLsNested(ls3);
		
		Anytype at31 = new Anytype();
		at31.setType("xsd:string");
		at31.setValue("handy_");
		ls3.add(at31);
		Anytype at32 = new Anytype();
		at32.setType("ArrayOfAnyType");
		ls3.add(at32);
		
		ls0.add(at1);
		ls0.add(at2);
		ls0.add(at3);
		return invoke;
	}
}

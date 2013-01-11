package cn.com.xming.xmlparse.xstream;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Anytype {
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String type;
	private String value;
	private List<Anytype> lsNested;
	
	
	public Anytype() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Anytype(Object obj){
		if(obj == null)
			return ;
		Class clz = obj.getClass();
		if(clz.equals(String.class)){
			this.type = "xsd:string";
			this.value = obj.toString();
		}else if(clz.equals(Integer.class)){
			this.type = "xsd:int";
			this.value = obj.toString();
		}else if(clz.equals(Calendar.class)){
			this.type = "xsd:calendar";
			Calendar cal = (Calendar)obj;
			this.value = sdf.format(cal.getTime());
		}else if(clz.equals(Date.class)){
			this.type = "xsd:date";
			Date cal = (Date)obj;
			this.value = sdf.format(cal.getTime());
		}else if(clz.equals(BigDecimal.class)){
			this.type = "xsd:decimal";
			this.value = obj.toString();
		}else if(clz.equals(java.util.ArrayList.class)){
			this.type = "xsd:decimal";
			this.value = obj.toString();
		}
	}
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Anytype> getLsNested() {
		return lsNested;
	}
	public void setLsNested(List<Anytype> lsNested) {
		this.lsNested = lsNested;
	}
	
	 
}

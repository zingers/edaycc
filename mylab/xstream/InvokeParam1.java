package cn.com.xming.xmlparse.xstream;

import java.util.ArrayList;
import java.util.List;

public class InvokeParam1 {
	private String method;
	private List<Object> parameter = new ArrayList<Object>();
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<Object> getParameter() {
		return parameter;
	}
	public void setParameter(List<Object> parameter) {
		this.parameter = parameter;
	}
	
	public InvokeParam1 add(Object obj){
		if(obj != null)
			this.parameter.add(obj);
		return this;
	}
	

}

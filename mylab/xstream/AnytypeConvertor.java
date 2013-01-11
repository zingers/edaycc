package cn.com.xming.xmlparse.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class AnytypeConvertor implements Converter {

	@Override
	public boolean canConvert(Class type) {
		// TODO Auto-generated method stub
		return type.equals(Anytype.class);
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		// TODO Auto-generated method stub
		Anytype anytype = (Anytype) source;
		writeNode(writer,anytype);
		// writer.setValue(anytype.getValue());
		// writer.startNode("anytype");
		// writer.endNode();
	}

	public void writeNode(HierarchicalStreamWriter writer, Anytype at){
		if (!"ArrayOfAnyType".equals(at.getType())) {
			writer.addAttribute("xsi:type", at.getType());
			writer.setValue(at.getValue());
		} else {
			writer.addAttribute("xsi:type", at.getType());
			if(at.getLsNested()!=null)
			for(Anytype nest : at.getLsNested()){
				//context.convertAnother(nest);
				writer.startNode("anyType");
				writeNode(writer,nest);
				writer.endNode();
			}
		}

	}
	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		// TODO Auto-generated method stub
		Anytype anytype = new Anytype();
		reader.moveDown();
		anytype.setValue(reader.getValue());
		reader.moveUp();
		return anytype;
	}

}

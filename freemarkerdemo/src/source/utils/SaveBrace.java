package source.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @author 王童
 *
 */
public class SaveBrace {
	
	
	/**
	 * 该方法用于统一给xml文件添加标签括号
	 * @param path 文件目录
	 * @return  反馈信息
	 */
	@SuppressWarnings("unchecked")
	public static String saveToBarce(String path){
		//"D:\\a.xml"
		File file=new File(path);
		SAXReader saxReader = new SAXReader();   
        Document document=null;
		try {
			document = saxReader.read(new FileInputStream(file));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "文件未找到异常";
		} catch (DocumentException e1) {
			e1.printStackTrace();
			return "文件发生异常";
			
		}  
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main");
        XPath xpath=document.createXPath("//w:t");
        xpath.setNamespaceURIs(map);
        List<Element> e= xpath.selectNodes(document);
        for(Element c:e){
        	String text=c.getText();
        	//在此限定只为英文字母赋值
        	String regex="[a-zA-Z]+";
        	System.out.println(text);
        	if(text!=null&&!"".equals(text)&&text.matches(regex)){
        		c.setText("${"+text+"}");
            	System.out.println(c.getText());
        	}
        	
        }
        XMLWriter writer = null;  

        try{  
            OutputFormat format  = new OutputFormat("");  
            format.setLineSeparator("");  
            format.setEncoding("UTF-8");  
            writer= new XMLWriter(new FileOutputStream(file),format);  
            writer.write(document);  
            writer.flush();  
            writer.close();  
            return "success";
        }catch(Exception ex){  
            ex.printStackTrace();  
        }
		return null;  
        
		
	}
	public static void main(String[] args) {
		saveToBarce("C:\\Users\\hanxifa01\\Desktop\\高压所目前工作内容\\cvt.xml");
		
	}

}

package source.test.free;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import source.test.Entity;
import source.utils.FreeMarkerUtil;
import source.utils.UuidUtil;

public class FreemarkerTest {
	public static void main(String[] args) {
		String wordPath="F:/test/";
		Map<String,Object>map=new HashMap<String,Object>();
		List<Entity>entityList=new ArrayList<Entity>();
		entityList.add(new Entity("1","小明","14"));
		entityList.add(new Entity("2","小红","24"));
		map.put("entityList",entityList);
		map.put("title","标题");
		try {
			//word
			File f = FreeMarkerUtil.createDoc(map,"test","/source/test/free",UuidUtil.get32UUID(),wordPath,".doc");
			
			//txt
			//File f = FreeMarkerUtil.createDoc(map,"test2","/source/test/free",UuidUtil.get32UUID(),wordPath,".txt");
			System.out.println(f.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

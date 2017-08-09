package source.test.excel;

import java.io.InputStream;
import java.util.List;

import com.alibaba.fastjson.JSON;

import source.test.Entity;
import source.utils.ExcelUtil;


public class ExcelTest {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try {
			InputStream is= ExcelTest.class.getClassLoader().getResourceAsStream("exceltest.xls");
			List<Entity> list = new ExcelUtil().getExcelDate(is,0,1,new Entity());
			//可能读出来含有空值（比如空行中有空格） 需要进行处理
			System.out.println(JSON.toJSONString(list));
		} catch ( Exception e) {
			 
			e.printStackTrace();
		}  
	}

}

package source.utils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @author wt
 */
public class ExcelUtil {
	
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;
	
	@SuppressWarnings("unchecked")
	public List getExcelDate(InputStream is, int sheetNum,
			int beginRowNum,Object obj) throws InstantiationException, IllegalAccessException, SecurityException, NoSuchFieldException {
		try {
			// 获取POI操作对象
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 创建存储数据集合 用来存放取出的数据
		List<Object> list = new ArrayList<Object>();
		sheet = wb.getSheetAt(sheetNum);
		// 获取第一行即标题行
		row = sheet.getRow(0);
		// 得到sheet内总行数
		int rowCountNum = sheet.getLastRowNum();
		// 正文内容应该从beginRowNum行开始
		int index=1;
		for (int i = beginRowNum; i <= rowCountNum; i++) {
			// 获取当期行
			row = sheet.getRow(i);
			if(row==null) continue;
			// 创建实体类 赋值
			Object objNew=obj.getClass().newInstance();
			
			Iterator<HSSFCell> cellIterator = row.iterator();
			while(cellIterator.hasNext()){
				HSSFCell next = cellIterator.next();
				String value = isNull(next);
				PropertyDescriptor propertyDescriptor = BeanInfoUtil.getPropertyDescriptorForIndex(objNew,index++);
				if(propertyDescriptor==null) continue;
				BeanInfoUtil.saveMathod(propertyDescriptor, objNew, value);
			}
			//恢复下标
			index=1;
			// 放入集合中
			list.add(objNew);
		}
		return list;
	}
	
	public String isNull(HSSFCell cell) {
		if (cell == null) {
			return "";
		} else {
			return getStringCellValueForOnLine(cell);
		}
	}
	
	public String getStringCellValueForOnLine(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			try {
				strCell = String.valueOf(cell.getStringCellValue());
			} catch (IllegalStateException e) {
				strCell = String.valueOf(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				strCell = dateFormat.format(HSSFDateUtil.getJavaDate(cell
						.getNumericCellValue()));
			}else if(cell.getCellStyle().getDataFormatString().indexOf("%")!=-1){
				strCell=cell.getNumericCellValue()*100+"%";//百分比校验
			} else {
				DecimalFormat nf = new DecimalFormat("0");// 格式化数字
				strCell = nf.format(cell.getNumericCellValue());
			}
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		return strCell;
	}
}

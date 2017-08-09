package source.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
 

 
/**
 * @author wt
 * Javabean必须标准 ，比如链式调用或格式不规范会有问题
 */
public class BeanInfoUtil{
	
	public static  PropertyDescriptor getPropertyDescriptorForIndex(Object obj,int index) throws SecurityException, NoSuchFieldException{
			BeanInfo info = null;
			try {
				info = Introspector.getBeanInfo(obj.getClass());
			} catch (IntrospectionException e) {
				e.printStackTrace();
			}
			PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
			for(PropertyDescriptor ped:propertyDescriptors){
				
				if(ped.getName().equals("class")) continue;
				Field declaredField =null;
					declaredField = obj.getClass().getDeclaredField(ped.getName());
				boolean annotationPresent = declaredField.isAnnotationPresent(index.class);
				if(!annotationPresent){
					continue;
				}
				index  indexVal=declaredField.getAnnotation(index.class);
				if(indexVal.value()==index){
					return ped;
				}
			}
			return null;
	}
	
	
	public static void  saveMathod(PropertyDescriptor pde,Object obj,String val){
		try{
				Method writeMethod = pde.getWriteMethod();
				writeMethod.invoke(obj,val);
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}

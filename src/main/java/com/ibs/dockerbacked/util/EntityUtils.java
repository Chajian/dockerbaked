package com.ibs.dockerbacked.util;



import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 实体类打印
 */
public class EntityUtils {

    public static void print(Object o){
        Class obejctClass = o.getClass();
        Field[] fields = obejctClass.getDeclaredFields();

        System.out.println(obejctClass.toString()+"{\n");
        for(Field field:fields){
            String typeName = field.getType().getName();
            System.out.println(field.getName()+":"+getValue(o,field.getType(),field.getName()));
        }
    }

    public static String toString(Object o){
        Class obejctClass = o.getClass();
        Field[] fields = obejctClass.getDeclaredFields();
        String out = "";
        out += obejctClass.toString()+"{\n";
        for(Field field:fields){
            if(!field.getName().equals("serialVersionUID"))
                out += field.getName()+":"+getValue(o,field.getType(),field.getName())+",\n";
        }
        out += "}";
        return out;
    }

    /**
     *
     * get方法
     * @param type 返回的类型
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getValue(Object c,Class type,String fieldName)  {
        char First = fieldName.charAt(0);
        First -=32;
        fieldName = First+fieldName.substring(1,fieldName.length());
        try {
            return excuteMethod(c,"get" + fieldName, null, null);
//            return type.cast(excuteMethod(c,"get" + fieldName, null, null));
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    /**
     * 执行某函数
     * @param methodName
     * @param parameteType
     * @param invokeParamete
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Object excuteMethod(Object cMain,String methodName, Class[] parameteType, Object[] invokeParamete) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = cMain.getClass().getDeclaredMethod(methodName,parameteType);
        return method.invoke(cMain,invokeParamete);
    }

}

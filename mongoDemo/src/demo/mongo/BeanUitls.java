package demo.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.codecs.configuration.CodecRegistry;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by uas on 2017/2/15.
 */
public class BeanUitls {

    public static DBObject toDBObject(Object bean){
        DBObject dbObject = new BasicDBObject();


        //属性名
        List<Field> fieldList = new ArrayList<Field>();
        Field[] fields= bean.getClass().getDeclaredFields();
        Collections.addAll(fieldList,fields);
        //方法名
        final List<Method> tmpList = new ArrayList<Method>();
        Method[] methods = bean.getClass().getMethods();
        Collections.addAll(tmpList,methods);
        final List<Method> methodList = tmpList.stream().filter(item->item.getName().startsWith("get")).collect(Collectors.toList());
       // bean.getClass().

        fieldList.forEach(item-> {
            try {
                String method = getGetMethod(item.getName(), methodList);
                dbObject.put(item.getName(), bean.getClass().getMethod(method).invoke(bean));
            }catch (Exception e){
                System.out.println(e.getStackTrace());
            }
        });

        return dbObject;
    }

    public static /*<T> T*/ void  toBean(org.bson.Document document /*,Class<T> clazz*/){
        document.forEach((key,value)->{
            System.out.println(key+":"+value);
        });
    }

    public static String getGetMethod(String field,List<Method> methodList) throws Exception {
        if(field != null && !field.isEmpty()) {
            char[] cs = field.toCharArray();
            if ((cs[0] >= 'a' && cs[0] <= 'z') || (cs[0] >= 'A' && cs[0] <= 'Z')) {
                cs[0] -= 32;
            }
            final String method = "get" + String.valueOf(cs);
            if (methodList.stream().anyMatch(item -> item.getName().equals(method))) {
                return method;
            } else {
                throw new Exception("没有匹配到方法！");
            }
        }
        throw new Exception("属性名不能为空！");
    }
}

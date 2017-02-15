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
 * Created by 郑杰民 on 2017/2/15.
 */
public class BeanUitls {
    private static final String METHOD_TYPE_GET = "get";
    private static final String METHOD_TYPE_SET = "set";

    /**
     * 将实体类转换成DBObject。
     * @param bean
     * @return
     */
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

        fieldList.forEach(item-> {
            try {
                String method = getMethod(item.getName(), methodList,METHOD_TYPE_GET);
                dbObject.put(item.getName(), bean.getClass().getMethod(method).invoke(bean));
            }catch (Exception e){
                System.out.println(e.getStackTrace());
            }
        });

        return dbObject;
    }

    /**
     * 将org.bson.Document 转换成实体类。
     * @param document
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(org.bson.Document document ,Class<T> clazz){

        try {
            Object bean = clazz.newInstance();

            //属性名
            List<Field> fieldList = new ArrayList<Field>();
            Field[] fields= bean.getClass().getDeclaredFields();
            Collections.addAll(fieldList,fields);
            //方法名
            final List<Method> tmpList = new ArrayList<Method>();
            Method[] methods = bean.getClass().getMethods();
            Collections.addAll(tmpList,methods);
            final List<Method> methodList = tmpList.stream().filter(item->item.getName().startsWith("set")).collect(Collectors.toList());

            document.forEach((key, value) -> {
                try {
                    //获取方法名
                    String method = getMethod(key, methodList,METHOD_TYPE_SET);
                    //方法名与实体类的方法名匹配
                    if(method != null && !method.isEmpty()){
                        //获取值作用域
                        if (fieldList.stream().anyMatch(item -> item.getName().equals(key))) {
                            Field field = clazz.getDeclaredField(key);
                            field.setAccessible(true);
                            field.set(bean, value);
                        }
                    }
                }catch (Exception e) {
                    System.out.println(e.getStackTrace());
                }
            });

            return (T)bean;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 匹配方法。
     * @param field
     * @param methodList
     * @param type
     * @return
     * @throws Exception
     */
    public static String getMethod(String field,List<Method> methodList,String type) throws Exception {
        if(field != null && !field.isEmpty()) {
            char[] cs = field.toCharArray();
            if ((cs[0] >= 'a' && cs[0] <= 'z')) {
                cs[0] -= 32;
            }
            final String method = type + String.valueOf(cs);
            if (methodList.stream().anyMatch(item -> item.getName().equals(method))) {
                return method;
            } else {
                throw new Exception("没有匹配到方法！");
            }
        }
        throw new Exception("属性名不能为空！");
    }
}

package ro.panzo.secureshares.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoker {
    private Object target;
    private String methodName;
    private List<String> paramClasses;
    private List<Object> paramObjects;

    private Map<String, Class> builtInMap = new HashMap<String, Class>();

    public Invoker() {
        this.paramClasses = new ArrayList<String>();
        this.paramObjects = new ArrayList<Object>();
        builtInMap.put("int", Integer.TYPE );
        builtInMap.put("long", Long.TYPE );
        builtInMap.put("double", Double.TYPE );
        builtInMap.put("float", Float.TYPE );
        builtInMap.put("bool", Boolean.TYPE );
        builtInMap.put("char", Character.TYPE );
        builtInMap.put("byte", Byte.TYPE );
        builtInMap.put("void", Void.TYPE );
        builtInMap.put("short", Short.TYPE );
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setAddParamClasses(String paramClass) {
        this.paramClasses.add(paramClass);
    }

    public void setAddParamObjects(Object paramObject) {
        this.paramObjects.add(paramObject);
    }

    public Object getInvoke(){
        Object result = null;
        try{
            Method method = target.getClass().getDeclaredMethod(methodName, this.getClassParameters());
            result = method.invoke(target, paramObjects.toArray());
        } catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    private Class<?>[] getClassParameters() throws ClassNotFoundException {
        Class<?>[] classes = new Class<?>[paramClasses.size()];
        for(int i = 0; i < paramClasses.size(); i++){
            classes[i] = builtInMap.containsKey(paramClasses.get(i)) ? builtInMap.get(paramClasses.get(i)) : Class.forName(paramClasses.get(i));
        }
        return classes;
    }
}

package ro.panzo.secureshares.util;

import java.util.List;

public class ServiceUtil {
    private static ServiceUtil ourInstance = new ServiceUtil();

    public static ServiceUtil getInstance() {
        return ourInstance;
    }

    private ServiceUtil() {
    }

    public boolean validateEmail(String email){
        return email != null && email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }

    public boolean validateString(String value){
        return value != null && value.length() > 0;
    }

    public boolean validateLong(String value){
        boolean result = false;
        try{
            long number = Long.parseLong(value);
            result = true;
        } catch (Exception ex){}
        return result;
    }

    public String getJSON(boolean status, List<String> messages){
        StringBuffer result = new StringBuffer();
        result.append("{");
        result.append("\"status\"").append(":\"").append(status ? "OK" : "KO").append("\",\"messages\":[");
        for(int i = 0; i < messages.size(); i++){
            if(i > 0){
                result.append(",");
            }
            result.append("{\"message\":\"").append(messages.get(i)).append("\"}");
        }
        result.append("]}");
        return result.toString();
    }
}

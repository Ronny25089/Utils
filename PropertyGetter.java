import java.io.*;
import java.util.Properties;

public class PropertyGetter {
    public static String getClassPathProperty(String key){
        String value = getNullableClassPathProperty(key);
        if(value == null){
            throw new IllegalArgumentException("You may not set property \""+key+"\".");
        }
        return value;
    }

    public static String getPropertyFromPath(String path,String key){
        String value = getNullableProperty(path,key);
        if(value == null){
            throw new IllegalArgumentException("You may not set property \""+key+"\".");
        }
        return value;
    }

    public static String getNullablePropertyFromPath(String path,String key){
        Properties properties = new Properties();
        InputStream in = new FileInputStream(new File(path));
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }

    public static String getNullableClassPathProperty(String key){
        Properties properties = new Properties();
        InputStream in = PropertyGetter.class.getClassLoader().getResourceAsStream("lock.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}

package utility;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
	
	private static Properties defaultProps = new Properties();
	  static {
	    try {
	        FileInputStream in = new FileInputStream("app.properties");
	        defaultProps.load(in);
	        in.close();
	    } catch (Exception e) {
	       e.printStackTrace();         
	    }
	  }	    
	  
	private static String getProperty(String key) {
        return defaultProps.getProperty(key);
    }

    public static String getApiUrl(){
        return getProperty("default.api.url");
    }

    public static String getApiKey(){
        return getProperty("default.api.key");
    }

    public static String getRecEngine(){
        return getProperty("default.recengine");
    }

    public static String getSimEngine(){
        return getProperty("default.simengine");
    }
	  
	  
}

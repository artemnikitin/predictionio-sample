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
	  
	private String getProperty(String key) {
        return defaultProps.getProperty(key);
    }

    public String getApiUrl(){
        return getProperty("default.api.url");
    }

    public String getApiKey(){
        return getProperty("default.api.key");
    }

    public String getRecEngine(){
        return getProperty("default.recengine");
    }

    public String getSimEngine(){
        return getProperty("default.simengine");
    }

    public String pathToMovieLensData(){
        return getProperty("data.movielens");
    }
	  
	  
}

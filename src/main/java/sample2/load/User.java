package sample2.load;

import io.prediction.Client;
import utility.Config;
import utility.Files;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class User {

    private final String path;
    private final String url = Config.getApiUrl();
    private final String key = Config.getApiKey();

    public User(String path){
        this.path = path;
    }

    public void load(){
        long start = System.currentTimeMillis();
        List<String> data = Files.processFileWithData(path);
        uploadData(data);
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

    private void uploadData(List<String> data){
        if(data.size() > 0){
            Client client = new Client(key, url);
            int counter = 0;
            int length = data.size();
            for(String part: data){
                StringTokenizer tokenizer = new StringTokenizer(part, "::");
                String user = tokenizer.nextToken();
                try {
                     client.createUserAsFuture(client.getCreateUserRequestBuilder(user));
                     counter++;
                     System.out.println("User created "+counter+" from "+length);
                } catch (IOException e) {
                     System.out.println("Creating user "+user+" fails with error "+e.getMessage());
                }
            }
            client.close();
        }else{
            System.out.println("Problem with file "+path);
        }
    }


}

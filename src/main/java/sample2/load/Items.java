package sample2.load;

import io.prediction.Client;
import utility.Config;
import utility.Files;

import java.io.IOException;
import java.util.*;

public class Items extends Files {

    private final Config config = new Config();
    private final String path;
    private final String url = config.getApiUrl();
    private final String key = config.getApiKey();

    public Items(String path){
        this.path = path;
    }

    public void load(){
        long start = System.currentTimeMillis();
        List<String> data = processFileWithData(path);
        uploadData(data);
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

    private void uploadData(List<String> data){
        if(data.size() > 0){
            Client client = new Client(key, url);
            int counter = 0;
            int length = data.size();
            for(String part: data){
                part = part.replace("::","@");
                StringTokenizer tokenizer = new StringTokenizer(part, "@");
                String id = tokenizer.nextToken();
                String movie = tokenizer.nextToken();
                String genres = tokenizer.nextToken();
                String [] attributes = getAttributes(movie, genres);
                try {
                    client.createItemAsFuture(client.getCreateItemRequestBuilder(id, attributes));
                    counter++;
                    System.out.println("Item created "+counter+" from "+length);
                } catch (IOException e) {
                    System.out.println("Creating item "+id+" fails with error "+e.getMessage());
                }
            }
            client.close();
        }else{
            System.out.println("Problem with file "+path);
        }
    }

    private String[] getAttributes(String movie, String genres){
        StringTokenizer tokenizer = new StringTokenizer(genres, "|");
        String[] mg = new String[tokenizer.countTokens()];
        int i = 0;
        while(tokenizer.hasMoreElements()){
            mg[i] = tokenizer.nextToken();
            i++;
        }
        String[] result = new String[mg.length + 1];
        result[0] = movie;
        for(i = 1; i < result.length; i++){
            result[i] = "genre="+mg[i - 1];
        }
        return result;
    }


}

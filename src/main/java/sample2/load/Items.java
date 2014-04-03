package sample2.load;

import io.prediction.Client;
import io.prediction.CreateItemRequestBuilder;
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
                String[] attributes = getAttributes(movie, genres);
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
        List<String> result = new ArrayList<>();
        String[] temp = splitNameAndYear(movie);
        result.add(0, temp[0]);
        result.add(1, temp[1]);
        StringTokenizer tokenizer = new StringTokenizer(genres, "|");
        while(tokenizer.hasMoreElements()){
            result.add(tokenizer.nextToken());
        }
        return result.toArray(new String[result.size()]);
    }

    private String[] splitNameAndYear(String movie){
        String name = movie.substring(0, movie.length() - 7);
        String year = movie.substring(movie.length() - 6, movie.length()).replace("(", "").replace(")", "");
        return new String[]{name, year};
    }


}

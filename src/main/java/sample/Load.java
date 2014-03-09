package sample;

import io.prediction.Client;
import io.prediction.CreateItemRequestBuilder;
import io.prediction.CreateUserRequestBuilder;
import sample.model.TestData;
import org.apache.commons.io.FileUtils;
import utility.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

public class Load {

    static Config config = new Config();
    private static final String url = config.getApiUrl();
    private static final String key = config.getApiKey();

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        GenerateData gd = new GenerateData();
        TestData data = gd.asCSV(10000, 150, 15);
        loadFromCSV(data);
    }

    private static void loadFromCSV(TestData td) throws InterruptedException, ExecutionException, IOException {
        long start = System.currentTimeMillis();
        List<String> data;
        List<String> users = td.getUsers();
        List<String> items = td.getItems();
        Client client = new Client(key, url);
        String[] attr = {"provider"};

        try {
            data = FileUtils.readLines(new File("test-data.csv"), "UTF-8");
        } catch (IOException e) {
            data = new ArrayList<String>();
        }

        int users_length = users.size();
        int items_length = items.size();
        int u2i_length = data.size();

        if(users.size() > 0 && items.size() > 0 && data.size() > 0){
            int counter = 0;
            for(String user: users){
                client.createUserAsFuture(client.getCreateUserRequestBuilder(user));
                counter++;
                System.out.println("User created "+counter+" from "+users_length);
            }
            counter = 0;
            for(String item: items){
                client.createItemAsFuture(client.getCreateItemRequestBuilder(item, attr));
                counter++;
                System.out.println("Item created "+counter+" from "+items_length);
            }
            counter = 0;
            for(String data_set: data){
                StringTokenizer tokenizer = new StringTokenizer(data_set.trim(), ",");
                while (tokenizer.hasMoreElements()) {
                    String user = tokenizer.nextToken().trim();
                    String provider = tokenizer.nextToken().trim();
                    client.userActionItemAsFuture(user, "conversion", provider);
                    counter++;
                    System.out.println("User2Item connection added "+counter+" from "+u2i_length);
                }
            }
            client.close();
        } else{
            System.out.println("There is a problem with data...");
        }
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }



}

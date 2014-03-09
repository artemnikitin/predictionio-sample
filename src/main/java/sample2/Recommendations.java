package sample2;

import io.prediction.Client;
import io.prediction.Item;
import io.prediction.User;
import utility.Config;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class Recommendations {

    private static final Config config = new Config();
    private static final String url = config.getApiUrl();
    private static final String key = config.getApiKey();

    public static void main(String[] args){
        getUserInfo("1");
        System.out.println("----------");
        getRecommendation("1", 5);
        System.out.println("----------");
        getItemInfo("1");
        System.out.println("----------");
        getSimilarItem("1", 5);
    }

    private static void getRecommendation(String user, int numOfRec){
        long start = System.currentTimeMillis();
        Client client = new Client(key, url);
        client.identify(user);
        String[] recommendations;
        try{
            recommendations = client.getItemRecTopN(config.getRecEngine(), numOfRec);
        } catch(Exception e){
            recommendations = new String[0];
        }
        System.out.println("For user "+user);
        System.out.println("Recommendations will be: ");
        if(recommendations.length > 0){
            for(String rec: recommendations){
                getItemInfo(rec);
            }
        }else{
            System.out.println("Sorry. We can't find recommendations.");
        }
        client.close();
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void getSimilarItem(String item, int numOfSim){
        long start = System.currentTimeMillis();
        Client client = new Client(key, url);
        String[] recommendations;
        try{
            recommendations = client.getItemSimTopN(config.getSimEngine(), item, numOfSim);
        } catch (Exception e){
            recommendations = new String[0];
        }
        System.out.println("For item "+item);
        getItemInfo(item);
        System.out.println("Similar item will be: ");
        if(recommendations.length > 0){
            for(String rec: recommendations){
                getItemInfo(rec);
            }
        }else{
            System.out.println("Sorry. We can't find similar items.");
        }
        client.close();
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void getItemInfo(String id){
        Client client = new Client(key, url);
        try {
            Item item = client.getItem(id);
            System.out.println(item.getIid() + "  " + Arrays.toString(item.getItypes()));
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        client.close();
    }

    private static void getUserInfo(String id){
        Client client = new Client(key, url);
        try {
            User user = client.getUser(id);
            System.out.println(user.getUid());
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        client.close();
    }

}

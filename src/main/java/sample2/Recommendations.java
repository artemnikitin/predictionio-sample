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

    private static final String url = Config.getApiUrl();
    private static final String key = Config.getApiKey();

    public static void main(String[] args){
        getRecommendation("1", 5);
        System.out.println("----------");
        getRecommendation("1", 5, new String[]{"genre=Drama"});
        System.out.println("----------");
        getSimilarItem("1", 5);
        System.out.println("----------");
        getSimilarItem("1", 5, new String[]{"genre=Drama"});
    }

    private static void getRecommendation(String user, int numOfRec){
        long start = System.currentTimeMillis();
        Client client = new Client(key, url);
        client.identify(user);
        String[] recommendations;
        try{
            recommendations = client.getItemRecTopN(Config.getRecEngine(), numOfRec);
        } catch(Exception e){
            recommendations = new String[0];
        }
        System.out.println("For user "+user);
        System.out.println("Recommendations will be: "+user);
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
            recommendations = client.getItemSimTopN(Config.getSimEngine(), item, numOfSim);
        } catch (Exception e){
            recommendations = new String[0];
        }
        System.out.println("For item "+item);
        getItemInfo(item);
        System.out.println("Similar item is: ");
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

    private static void getRecommendation(String user, int numOfRec, String[] attr){
        long start = System.currentTimeMillis();
        Client client = new Client(key, url);
        client.identify(user);
        Map<String, String[]> recommendations;
        try{
            recommendations = client.getItemRecTopNWithAttributes(Config.getRecEngine(), numOfRec, attr);
        } catch(Exception e){
            recommendations = new HashMap<String, String[]>();
        }
        if(recommendations.size() > 0){
            Set<String> keys = recommendations.keySet();
            for(String key: keys){
                System.out.println("Similar item is "+key);
                System.out.println(Arrays.toString(recommendations.get(key)));
            }
        }else{
            System.out.println("Sorry. We can't find recommendations.");
        }
        client.close();
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void getSimilarItem(String item, int numOfSim, String[] attr){
        long start = System.currentTimeMillis();
        Client client = new Client(key, url);
        Map<String, String[]> recommendations;
        try{
            recommendations = client.getItemSimTopNWithAttributes(Config.getSimEngine(), item, numOfSim, attr);
        } catch (Exception e){
            recommendations = new HashMap<String, String[]>();
        }
        if(recommendations.size() > 0){
            Set<String> keys = recommendations.keySet();
            for(String key: keys){
                System.out.println("Similar item is "+key);
                System.out.println(Arrays.toString(recommendations.get(key)));
            }
        }else{
            System.out.println("Sorry. We can't find similar items.");
        }
        client.close();
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void getItemInfo(String item){
        Client client = new Client(key, url);
        try {
            Item newItem = client.getItem(item);
            System.out.println(newItem.getIid());
            System.out.println(Arrays.toString(newItem.getItypes()));
        } catch (ExecutionException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        client.close();
    }

}

import io.prediction.Client;
import utility.Config;

public class GetRecommendation {

    private static final String url = Config.getApiUrl();
    private static final String key = Config.getApiKey();

    public static void main(String[] args){
        getRecommendation("76366973223", 5);
        System.out.println("----------");
        getSimilarItem("682", 5);
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
        if(recommendations.length > 0){
            for(String rec: recommendations){
                System.out.println("Your recommendation is "+rec);
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
        if(recommendations.length > 0){
            for(String rec: recommendations){
                System.out.println("Similar item is "+rec);
            }
        }else{
            System.out.println("Sorry. We can't find similar items.");
        }
        client.close();
        System.out.println("Total execution time " + (System.currentTimeMillis() - start) + " ms");
    }

}

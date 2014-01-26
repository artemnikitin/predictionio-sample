package sample;

import sample.model.TestData;
import org.apache.commons.io.FileUtils;
import utility.RandomGeneration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateData {

    public static TestData asCSV(int numberOfUsers, int numberOfItems, int itemsPerUser){
        long start = System.currentTimeMillis();
        List<String> result = new ArrayList<String>();
        List<String> users = new ArrayList<String>();
        List<String> items = new ArrayList<String>();
        String prefix = "7";
        Random rand = new Random();

        for(int i = 0; i < numberOfUsers; i++){
            StringBuilder sb = new StringBuilder();
            sb.append(prefix);
            sb.append(RandomGeneration.randomNumberWithoutNull(10));
            users.add(sb.toString());
        }

        for(int i = 0; i < numberOfItems; i++){
            int length = rand.nextInt(1000);
            if(length == 0) length = 1;
            if(!items.contains(length)) items.add(String.valueOf(length));
        }

        for(String user: users){
            for(int i = 0; i < itemsPerUser; i++){
                StringBuilder sb = new StringBuilder();
                sb.append(user);
                sb.append(",");
                sb.append(items.get(rand.nextInt(numberOfItems)));
                result.add(sb.toString());
            }
        }

        try{
            FileUtils.writeLines(new File("test-data.csv"), result);
            System.out.println("File saved. Time spended " + (System.currentTimeMillis() - start) + " ms");
        } catch(Exception e){
            System.out.println("Error happened when program tried to save result in file: " + e);
        }

        return new TestData(users, items);
    }

}

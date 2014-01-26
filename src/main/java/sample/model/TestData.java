package sample.model;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    private static List<String> users = new ArrayList<String>();
    private static List<String> items = new ArrayList<String>();

    public TestData(List<String> users, List<String> items){
        this.users = users;
        this.items = items;
    }

    public static List<String> getUsers() {
        return users;
    }

    public static List<String> getItems() {
        return items;
    }


}

package sample2;

import sample2.load.Items;
import sample2.load.U2I;
import sample2.load.User;
import utility.Config;

public class Upload {

    private static final String users = Config.pathToMovieLensData() + "users.dat";
    private static final String items = Config.pathToMovieLensData() + "movies.dat";
    private static final String interactions = Config.pathToMovieLensData() + "ratings.dat";

    public static void main(String[] args){
        User user = new User(users);
        user.load();
        Items item = new Items(items);
        item.load();
        U2I u2i = new U2I(interactions);
        u2i.load();
    }
}

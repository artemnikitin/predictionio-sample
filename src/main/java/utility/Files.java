package utility;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Files {

    public static List<String> processFileWithData(String path){
        List<String> data;
        try {
            data = FileUtils.readLines(new File(path), "UTF-8");
        } catch (IOException e) {
            data = new ArrayList<String>();
        }
        return data;
    }

}

import api.*;
import api.model.APIParam;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Properties;

public class MainApplication {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("MAIN PARAMETER IS NULL");
        } else if (args.length == 1) {
            System.out.println(args[0]);
        } else if (args.length == 2) {
            System.out.println(args[0]);
            System.out.println(args[1]);
        }

        String PROPERTY_FILE_PATH = new File("src/main/resources/properties/api.properties").getAbsolutePath();
        System.out.println(PROPERTY_FILE_PATH);
        Properties props = getProperties(PROPERTY_FILE_PATH);

        APIParam param = new APIParam(props);

        APIController.APICall(param);

    }

    public static Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream(path);

        try {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

}

package utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpUtils
{

    public static String fetchAPIData(String _url)
    {
        String inline = "";
        try {
            URL url = new URL(_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responsecode = conn.getResponseCode();

            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {

//                String inline = "";
                Scanner scanner = new Scanner(url.openStream());

                //Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                scanner.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inline;
    }
}

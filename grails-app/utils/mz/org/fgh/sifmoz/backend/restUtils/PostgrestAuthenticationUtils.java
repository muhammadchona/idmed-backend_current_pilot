package mz.org.fgh.sifmoz.backend.restUtils;

import org.grails.web.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostgrestAuthenticationUtils {

    public static String getJWTPermission(String baseUri, String username, String pass) {
        HttpURLConnection connection = null;
        String url = baseUri+ "/rpc/login";
        String parameters = "{\"username\":\"" + username + "\",\"pass\":\"" + pass + "\"}";

        String result = "";
        int code = 200;
        try {
            URL siteURL = new URL(url);
            connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(parameters);
            writer.flush();
            connection.setConnectTimeout(3000);
            connection.connect();
            Reader input = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            for (int c; (c = input.read()) >= 0; ) {
                result = result.concat(String.valueOf((char) c));
            }

            result = result.replace("[{", "{");
            result = result.replace("}]", "}");
            if (result.contains("{")) {
                JSONObject jsonObj = new JSONObject(result);
                try {
                    result = jsonObj.get("token").toString();
                } catch (Exception e) {
                    connection.disconnect();
                    e.printStackTrace();
                }
            }
            code = connection.getResponseCode();
            connection.disconnect();
            if (code == 200) {
                return result;
            } else {
                return null;
            }
        } catch (Exception e) {
            if (connection != null)
                connection.disconnect();
            return null;
        }
    }
}

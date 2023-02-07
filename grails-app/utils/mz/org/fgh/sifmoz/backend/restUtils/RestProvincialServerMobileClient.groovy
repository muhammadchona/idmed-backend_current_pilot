package mz.org.fgh.sifmoz.backend.restUtils


import mz.org.fgh.sifmoz.backend.provincialServer.ProvincialServer
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPatch
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.grails.web.json.JSONArray
import org.grails.web.json.JSONObject

class RestProvincialServerMobileClient {

    RestProvincialServerMobileClient() {

    }

    static def postRequestProvincialServerClient(ProvincialServer provincialServer ,String urlPath, StringEntity object) {
        String restUrlBase = provincialServer.getUrlPath()+provincialServer.getPort()
        String restUrl = provincialServer.getUrlPath()+provincialServer.getPort()+urlPath
        String result = ""
        // int code = 200
        try {
            println PostgrestAuthenticationUtils.getJWTPermission(restUrlBase,provincialServer.getUsername(), provincialServer.getPassword())
            CloseableHttpClient client =  HttpClients.createDefault();

            HttpPost request = new HttpPost(restUrl);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + PostgrestAuthenticationUtils.getJWTPermission(restUrlBase,provincialServer.getUsername(), provincialServer.getPassword()))
            request.setHeader("Accept-Encoding", "gzip, deflate, br");
            request.setEntity(object)
            CloseableHttpResponse response = client.execute(request)

            System.out.println(response.getStatusLine().getStatusCode());
            result = response.getStatusLine().getStatusCode()
        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
        println(result)
        return result
    }

    static def patchRequestProvincialServerClient(ProvincialServer provincialServer ,String urlPath, String object) {
        String restUrlBase = provincialServer.getUrlPath()+provincialServer.getPort()
        String restUrl = provincialServer.getUrlPath()+provincialServer.getPort()+urlPath
        String result = ""
        try {

             CloseableHttpClient client =  HttpClients.createDefault();
            HttpPatch request = new HttpPatch(restUrl);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + PostgrestAuthenticationUtils.getJWTPermission(restUrlBase,provincialServer.getUsername(), provincialServer.getPassword()))
            request.setHeader("Accept-Encoding", "gzip, deflate, br");
            request.setEntity(new StringEntity(object))
            CloseableHttpResponse response = client.execute(request)


            System.out.println(response.getStatusLine().getStatusCode());
            result = response.getStatusLine().getStatusCode()
        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
        println(result)
        return result
    }

    static def getRequestProvincialServerClient(ProvincialServer provincialServer ,String urlPath) {
        String restUrlBase = provincialServer.getUrlPath()+provincialServer.getPort()
        String restUrl = provincialServer.getUrlPath()+provincialServer.getPort()+urlPath
        String result = ""
        try {

            CloseableHttpClient client =  HttpClients.createDefault();
            HttpGet request = new HttpGet(restUrl);
            request.setHeader("Accept", "application/json");
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Bearer " + PostgrestAuthenticationUtils.getJWTPermission(restUrlBase,provincialServer.getUsername(), provincialServer.getPassword()))
            request.setHeader("Accept-Encoding", "gzip, deflate, br");
          //  request.setEntity(new StringEntity(object))
            CloseableHttpResponse response = client.execute(request)

            if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) { // success
                String result1 = EntityUtils.toString(response.getEntity());
                System.out.println(result1);
                return new JSONArray(result1)
            }
            System.out.println(response.getStatusLine().getStatusCode());
            result = response.getStatusLine().getStatusCode()

        } catch (Exception e) {
          return  e;
        }
        println(result)
        return result
    }



    static def requestGetDataOnMobileProvincialServerClient(ProvincialServer provincialServer,String urlPath) {
     //   String restUrl = "http://dev.fgh.org.mz:3110"+"/sync_temp_dispense?syncstatus=eq.P" + "&order=pickupdate.desc";
        String restUrl = provincialServer.getUrlPath()+provincialServer.getPort()+urlPath
        String result = ""
        int code = 200
        try {
         //   String userCredentials = new StringBuffer("postgres").append(":").append("postgres").toString()
          //  String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userCredentials.getBytes()))
            println(restUrl)
            // println(basicAuth)
            URL siteURL = new URL(restUrl)
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection()
            connection.setRequestProperty("Authorization", "Bearer " + PostgrestAuthenticationUtils.getJWTPermission("http://dev.fgh.org.mz:3110", provincialServer.getUsername(), provincialServer.getPassword()))
            connection.setRequestMethod("GET")
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setDoInput(true)
            connection.setDoOutput(true);
//            connection.setConnectTimeout(3000)
            // Send post request

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { // success
                BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))
                String inputLine
              ///  List response = new ArrayList<>()
                StringBuffer response = new StringBuffer()
                while ((inputLine = input.readLine()) != null) {
                    response.append(inputLine)
                }
                input.close()

                // print result
              String responseStr = response.toString();
                println(new JSONArray(responseStr))

                return new JSONArray(responseStr)
            } else {
                println("GET request not worked")
                return new JSONObject("{\"sessionId\":null,\"authenticated\":null}")
            }

//            connection.connect()
            code = connection.getResponseCode()
            connection.disconnect()
            if (code == 201) {
                result = "-> Green <-\t" + "Code: " + code;
            } else {
                result = "-> Yellow <-\t" + "Code: " + code;
            }
        } catch (Exception e) {
            result = "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
        println(result)
        return result
    }
}

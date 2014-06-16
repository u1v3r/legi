package sk.rdy.api.theoldreader.http;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Radovan Dvorsky
 * Date: 30.6.2013
 * Time: 20:09
 */
public class HttpManager {

    enum RequestMethod{
        POST,GET,PUT,DELETE;
    }


    private HttpsURLConnection httpConn;

    private static HttpManager ourInstance = new HttpManager();

    public static HttpManager getManager() {
        return ourInstance;
    }

    private HttpManager() {
    }

    public String doPost(String url, String params){
        return doRequest(url, params, RequestMethod.POST, null);
    }

    public String doPost(String url, String params, List<RequestProperty> requestProperties){
        return doRequest(url,params,RequestMethod.POST,requestProperties);
    }

    public String doGet(String url, String params){
        return doRequest(url,params,RequestMethod.GET, null);
    }

    public String doGet(String url, String params, List<RequestProperty> requestProperties){
        return doRequest(url,params,RequestMethod.GET, requestProperties);
    }

    public void doAsyncPost(String url, String params, List<RequestProperty> requestProperties,
                              HttpResponseCallback callback){
        new HttpCallbackThread(url,params,callback,RequestMethod.POST,requestProperties).start();
    }

    public void doAsyncGet(String url, String params, List<RequestProperty> requestProperties,
                            HttpResponseCallback callback){
        new HttpCallbackThread(url,params,callback,RequestMethod.GET,requestProperties).start();
    }

    private String doRequest(String url, String params, RequestMethod requestMethod,
                             List<RequestProperty> requestProperties) {

        StringBuilder responseBuffer = new StringBuilder();

        try {
            HttpsURLConnection conn = (HttpsURLConnection) createConnection(url,params,requestMethod,requestProperties);
            BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            while ((inputLine = buff.readLine()) != null){
                responseBuffer.append(inputLine);
            }
            buff.close();
            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseBuffer.toString();
    }

    private URLConnection createConnection(String url, String params, RequestMethod requestMethod,
                                           List<RequestProperty> requestProperties) {

        try {
            if(requestMethod.equals(RequestMethod.GET) && !params.isEmpty()){
                if(url.contains("?output=json")){
                    url = url + "&" + params;
                }else {
                    url = url + "?" + params;
                }
            }

            URL obj = new URL(url);
            HttpsURLConnection conn =  (HttpsURLConnection) obj.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);

            if(requestProperties != null){
                for (RequestProperty property: requestProperties){
                    conn.setRequestProperty(property.getKey(),property.getValue());
                }
            }

            if(requestMethod.equals(RequestMethod.GET)){
                conn.setRequestMethod(RequestMethod.GET.toString());
                conn.setDoOutput(false);
            } else if(requestMethod.equals(RequestMethod.POST)){
                conn.setRequestMethod(RequestMethod.POST.toString());
                conn.setDoOutput(true);
                if(!params.isEmpty()){
                    DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(params);
                    wr.flush();
                    wr.close();
                }
            }



            return conn;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class HttpCallbackThread extends Thread {

        private final String url;
        private final String params;
        private HttpsURLConnection conn;
        private HttpResponseCallback response;
        private RequestMethod requestMethod;
        private List<RequestProperty> requestProperties;

        public HttpCallbackThread(String url, String params, HttpResponseCallback response,
                                  RequestMethod requestMethod, List<RequestProperty> requestProperties){
            this.url = url;
            this.params = params;
            this.response = response;
            this.requestMethod = requestMethod;
            this.requestProperties = requestProperties;
        }

        @Override
        public void run() {
            try {
                conn = (HttpsURLConnection) createConnection(url,params,requestMethod, requestProperties);
                BufferedReader buff = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer responseBuffer = new StringBuffer();

                while ((inputLine = buff.readLine()) != null){
                    responseBuffer.append(inputLine);
                }

                response.onResponse(responseBuffer.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
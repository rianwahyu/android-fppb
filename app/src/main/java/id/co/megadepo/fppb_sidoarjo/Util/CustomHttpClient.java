package id.co.megadepo.fppb_sidoarjo.Util;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;



public class CustomHttpClient {

	/** The time it takes for our client to timeout */
    public static final int HTTP_TIMEOUT = 20 * 1000; // milliseconds
    Context context;
    /** Single instance of our HttpClient */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
//    String IPInterlocal = "http://152317301379.ip-dynamic.com:8129";
    public static String IPLocal="http://192.168.39.129/depocare/";
//    public static String IP = "http://152317301379.ip-dynamic.com:8129/depocare/";
    public static String IP = Config.getUrl()+"/androidapps/";

    public static String getIP(){
        return IP;
    }

    private static HttpClient getHttpClient(final Context context,boolean loading) {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }
    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     *
     * @param url The web address to post the request to
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpPost(String url, ArrayList<NameValuePair> postParameters,Context context,boolean loading) throws Exception {
        BufferedReader in = null;
    	
        try {
            HttpClient client = getHttpClient(context,loading);
            HttpPost request = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Performs an HTTP GET request to the specified url.
     *
     * @param url The web address to post the request to
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpGet(String url, Context context,boolean loading) throws Exception {
        BufferedReader in = null;
        try {
            HttpClient client = getHttpClient(context,loading);
            HttpGet request = new HttpGet();
            request.setURI(new URI(url));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();

            String result = sb.toString();
            return result;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static String info(Activity activity,String target) {


        ArrayList<NameValuePair> postParameter = new ArrayList<NameValuePair>();
        postParameter.add(new BasicNameValuePair("test", "inisial"));
        String penjelasan = "Disconnected from Server";
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
//			penjelasan = CustomHttpClient.executeHttpPost(CustomHttpClient.getIP()+"/depocare/penjelasanhome.php",postParameter,this,false);
//            penjelasan = CustomHttpClient.executeHttpPost(CustomHttpClient.getIP() + "/depocare/penjelasanhome.php", postParameter, activity, false);
            penjelasan = CustomHttpClient.executeHttpPost(CustomHttpClient.getIP()+target, postParameter, activity, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  penjelasan;
    }
}

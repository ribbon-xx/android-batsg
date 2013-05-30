package com.chauhai.android.batsg.util;

import java.io.InputStream;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.util.Log;

public class HttpUtil {

  private static final String TAG = "HttpUtil";

  /**
   * Connection time out in millisecond.
   */
  public static int connectionTimeOutMillisecond = 10000;

  public static String download(String url) throws Exception {
    return download(url, null);
  }

  /**
   * Download a file and save to file path.
   * If saveFilePath is not specified, then return the content as string.
   * @param url
   * @param saveFilePath
   * @return String if saveFilePath is not specified, null if save to file.
   * @throws Exception
   */
  public static String download(String url, String saveFilePath) throws Exception {
    Log.v(TAG, "Download " + url);

    // Return content (if saveFilePath is not specified).
    String returnStr = null;

    DefaultHttpClient client = createHttpClient();
    client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));

    HttpGet request = new HttpGet();
    request.setURI(new URI(url));

    HttpResponse response = client.execute(request);
    Log.v(TAG, "Download response: " + response.getStatusLine());
    if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
      throw new Exception("Error downloading file " + url);
    }

    // Save content to file.
    InputStream in = response.getEntity().getContent();
    if (saveFilePath != null) {
      FileUtil.save(in, saveFilePath);
    } else {
      returnStr = FileUtil.getContents(in);
    }

    in.close();
    in = null;

    return returnStr;
  }

  /**
   * Create HttpClient.
   * @return
   */
  public static DefaultHttpClient createHttpClient() {
    // Set timeout.
    HttpParams params = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(params, connectionTimeOutMillisecond);
    HttpConnectionParams.setSoTimeout(params, connectionTimeOutMillisecond);
    // Create http client.
    DefaultHttpClient client = new DefaultHttpClient(params);
    return client;
  }

  /**
   * Create HttpClient with Basic Authentication.
   * @param authHost
   * @param authPort
   * @param authUser
   * @param authPassword
   * @return
   */
  public static DefaultHttpClient createHttpClient(String authHost, int authPort,
      String authUser, String authPassword) {
    // Create http client.
    DefaultHttpClient client = createHttpClient();
    // Set authentication parameters if specified.
    client.getCredentialsProvider().setCredentials(
        new AuthScope(authHost, authPort),
        new UsernamePasswordCredentials(authUser, authPassword));
    return client;
  }

  /**
   * Add a random string to the end of URL to prevent caching.
   * @param url
   * @return
   */
  public static String notCacheUrl(String url) {
    return notCacheUrl(url, "timestamp");
  }

  /**
   * Add a random string to the end of URL to prevent caching.
   * @param url
   * @param timeStampParamPrefix
   * @return
   */
  public static String notCacheUrl(String url, String timeStampParamPrefix) {
    // Use ? or & for appending parameter.
    String mark = url.contains("?") ? "&" : "?";

    return url + mark + timeStampParamPrefix + "_" + System.currentTimeMillis() + "=";
  }
}
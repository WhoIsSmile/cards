package laughing.utils.net;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author laughing
 * @date 2017-08-10 08:40:32
 * @description httpClient 工具
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 15000;

    private static final String DEFAULT_CODING = "UTF-8";

    private static final String HTTPS = "https";

    /**
     * @param url
     * @param params
     * @param coding
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Map<String, String> params,
                                String coding) throws Exception {
        return doPost(url, null, params, coding, DEFAULT_TIMEOUT);
    }

    /**
     * @return HttpClient
     * @Description 创建一个不进行正式验证的请求客户端对象 不用导入SSL证书
     */
    public static CloseableHttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(
                    ctx, NoopHostnameVerifier.INSTANCE);

            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    private static List<NameValuePair> mapToNameValuePair(Map<String, String> params) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formParams.add(new BasicNameValuePair(entry.getKey(), entry
                    .getValue()));
        }
        return formParams;
    }

    /**
     * doPost
     *
     * @param url
     * @param params
     * @param coding such as "UTF-8" or "GBK"
     * @return
     * @throws Exception
     */
    public static String doPost(String url, Header[] headers,
                                Map<String, String> params, String coding, int timeout)
            throws Exception {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder().append("");
        CloseableHttpClient httpClient;
        httpClient = HttpConnectionManager.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            RequestConfig config = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            HttpPost httpPost = new HttpPost(url);
            if (headers != null) {
                httpPost.setHeaders(headers);
            }
            httpPost.setConfig(config);
//            httpPost.addHeader("Content-Type", "application/json");
            if (params != null) {
                List<NameValuePair> formParams = mapToNameValuePair(params);
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, coding);
                httpPost.setEntity(uefEntity);
            }
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String content = "";
            br = new BufferedReader(new InputStreamReader(entity.getContent(),
                    coding));
            while ((content = br.readLine()) != null) {
                result.append(content);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("doPost error || " + e.getMessage());
        } finally {
            closeAll(response, br, httpClient);
        }
        return result.toString();
    }

    public static void closeAll(CloseableHttpResponse response, BufferedReader br, CloseableHttpClient httpclient) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            httpclient.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
//	public static String doPost(String url, String postContent, String coding,
//			String contentType) throws Exception {
//		return doPost(url, postContent, coding, contentType, DEFAULT_TIMEOUT);
//	}


    public static String doPostContent(String url, Header[] headers, String postContent, int timeOut, final int retryTimes) throws Exception {
        return doPostContent(url, headers, postContent, DEFAULT_CODING, "application/json", timeOut, retryTimes);
    }

    /**
     * Post Http
     *
     * @param url
     * @param postContent
     * @param contentType such as "application/xml  || application/json"
     * @param coding      such as "UTF-8" or "GBK"
     * @return
     * @throws Exception
     * @throws IOException
     * @throws HttpException
     */
    public static String doPostContent(String url, Header[] headers, String postContent, String coding,
                                       String contentType, int timeout, final int retryCount) throws Exception {


        BufferedReader br = null;
        StringBuilder result = new StringBuilder().append("");
        CloseableHttpClient httpClient;
        httpClient = HttpConnectionManager.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            HttpPost httpPost = new HttpPost(url);
            if (headers != null) {
                httpPost.setHeaders(headers);
            }
            httpPost.setConfig(config);
            StringEntity stringEntity = new StringEntity(postContent);
            httpPost.addHeader("Content-Type", contentType);
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            HttpEntity entity = httpResponse.getEntity();
            String content;
            br = new BufferedReader(new InputStreamReader(entity.getContent(), coding));
            while ((content = br.readLine()) != null) {
                result.append(content);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("doPost content error || " + e.getMessage());
        } finally {
            closeAll(httpResponse, br, httpClient);
        }
        return result.toString();
    }

    public static String doGet(String url, Header[] headers, Map<String, String> params, int timeout) throws Exception {
        return doGet(url, headers, params, DEFAULT_CODING, timeout);
    }

    /**
     * @param url
     * @param coding
     * @return
     * @throws Exception
     */
    public static String doGet(String url, Header[] headers, Map<String, String> params, String coding, int timeout)
            throws Exception {
        BufferedReader br = null;
        StringBuilder result = new StringBuilder().append("");
        CloseableHttpClient httpClient;

        httpClient = HttpConnectionManager.getHttpClient();
        CloseableHttpResponse httpResponse = null;
        try {
            RequestConfig config = RequestConfig.custom()
                    .setSocketTimeout(timeout).setConnectTimeout(timeout).build();
            // 参数
            if (params != null) {
                List<NameValuePair> formParams = mapToNameValuePair(params);
                String paramStr = EntityUtils.toString(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
                url = new StringBuilder(url).append("?").append(paramStr).toString();
            }
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            if (headers != null) {
                httpGet.setHeaders(headers);
            }
            httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            String content = "";
            br = new BufferedReader(new InputStreamReader(entity.getContent(),
                    coding));
            while ((content = br.readLine()) != null) {
                result.append(content);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("doGet error || " + e.getMessage());
        } finally {
            closeAll(httpResponse, br, httpClient);
        }
        return result.toString();
    }

    /**
     * 网络代理
     *
     * @param proxyAddress 代理地址
     * @param proxyPort    代理端口
     * @return 使用只要在相关连接网络代码加上这个方法即可
     */
    public final void netProxy(String proxyAddress, String proxyPort) {
        Properties prop = System.getProperties();
        prop.setProperty("http.proxyHost", proxyAddress);
        prop.setProperty("http.proxyPort", proxyPort);
    }

    public static void main(String[] args) throws Exception {

    }
}

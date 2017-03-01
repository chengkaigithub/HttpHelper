package http;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

import http.listener.IHttpListener;
import http.listener.IHttpService;

import static http.HttpRequestErrorCode.JSONHTTPSERVICE_EXIT_EXCEPTION;

/**
 * Created by chengkai on 2017/3/1.
 */

public class JsonHttpService implements IHttpService {

    private String url;
    private IHttpListener httpListener;

    private byte[] requestData;

    private HttpPost httpPost;
    private HttpClient httpClient = new DefaultHttpClient();

    private ResponseHandler responseHandler = new BasicResponseHandler() {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                httpListener.onSuccess(response.getEntity());
            } else {
                httpListener.onError(statusCode, response.getStatusLine().getReasonPhrase());
            }
            return null;
        }
    };

    @Override
    public void execute() {
        httpPost = new HttpPost(url);
        if (requestData != null) httpPost.setEntity(new ByteArrayEntity(requestData));
        try {
            httpClient.execute(httpPost, responseHandler);
        } catch (IOException e) {
            httpListener.onError(JSONHTTPSERVICE_EXIT_EXCEPTION, e.getMessage());
        }
    }

    @Override
    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }
}

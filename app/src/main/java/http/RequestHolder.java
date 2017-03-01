package http;

import http.listener.IHttpListener;
import http.listener.IHttpService;

/**
 * Created by chengkai on 2017/3/1.
 */

public class RequestHolder<T> {

    private String url;
    private T requestData;
    private IHttpListener httpListener;
    private IHttpService httpService;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getRequestData() {
        return requestData;
    }

    public void setRequestData(T requestData) {
        this.requestData = requestData;
    }

    public IHttpListener getHttpListener() {
        return httpListener;
    }

    public void setHttpListener(IHttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public IHttpService getHttpService() {
        return httpService;
    }

    public void setHttpService(IHttpService httpService) {
        this.httpService = httpService;
    }
}

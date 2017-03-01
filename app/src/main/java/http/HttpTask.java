package http;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

import http.listener.IHttpService;

/**
 * Created by chengkai on 2017/3/1.
 */

public class HttpTask<T> implements Runnable{

    private IHttpService httpService;

    public HttpTask(RequestHolder<T> requestHolder) {
        this.httpService = requestHolder.getHttpService();
        httpService.setHttpListener(requestHolder.getHttpListener());
        httpService.setUrl(requestHolder.getUrl());
        T requestData = requestHolder.getRequestData();
        String requestJson = JSON.toJSONString(requestData);
        try {
            httpService.setRequestData(requestJson.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {}
    }

    @Override
    public void run() {
        httpService.execute();
    }
}

package http;

import java.util.concurrent.FutureTask;

import http.listener.IDataListener;
import http.listener.IHttpListener;
import http.listener.IHttpService;

import static http.HttpRequestErrorCode.TEXTREQUESTHELPER_SENDJSONTEXTREQUEST_EXCEPTION;

/**
 * Created by chengkai on 2017/3/1.
 */

public class TextRequestHelper {

    public static <T, M> void sendJsonTextRequest(
            String url, T requestData, Class<M> entityClass, IDataListener<M> dataListener) {

        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonHttpListener<>(dataListener, entityClass);
        RequestHolder<T> requestHolder = new RequestHolder<>();
        requestHolder.setHttpListener(httpListener);
        requestHolder.setHttpService(httpService);
        requestHolder.setRequestData(requestData);
        requestHolder.setUrl(url);
        HttpTask httpTask = new HttpTask(requestHolder);
        try {
            ThreadManager.getInstance().execute(new FutureTask<Object>(httpTask, null));
        } catch (InterruptedException e) {
            dataListener.onError(TEXTREQUESTHELPER_SENDJSONTEXTREQUEST_EXCEPTION, e.getMessage());
        }
    }

}

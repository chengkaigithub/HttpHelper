package http.listener;

/**
 * Created by chengkai on 2017/3/1.
 */

public interface IHttpService {

    /** 执行请求体 */
    void execute();

    /** 设置请求数据的回调 */
    void setHttpListener(IHttpListener httpListener);

    /** 设置请求的路径url */
    void setUrl(String url);

    /** 设置post请求的参数 */
    void setRequestData(byte[] requestData);

}

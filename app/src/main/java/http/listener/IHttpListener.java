package http.listener;

import org.apache.http.HttpEntity;

/**
 * Created by chengkai on 2017/3/1.
 */

public interface IHttpListener {

    /** 请求成功后回调去解析处理结果层 */
    void onSuccess(HttpEntity httpEntity);

    /** 请求失败后回调处理 */
    void onError(int errorCode, String detailInfo);

}

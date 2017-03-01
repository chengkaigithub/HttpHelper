package http.listener;

/**
 * Created by chengkai on 2017/3/1.
 */

public interface IDataListener<M> {

    /** 请求成功后回调调用层的接口 */
    void onSuccess(M resultBean);

    /** 请求失败后回调调用层的接口 */
    void onError(int errorCode, String msg);

}

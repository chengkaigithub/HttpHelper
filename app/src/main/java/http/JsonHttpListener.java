package http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import http.listener.IDataListener;
import http.listener.IHttpListener;

import static http.HttpRequestErrorCode.JSONHTTPLISTENER_ONSUCCESS_EXCEPTION;


/**
 * Created by chengkai on 2017/3/1.
 */

public class JsonHttpListener<T> implements IHttpListener {

    private IDataListener<T> dataListener;

    private Class<T> entityClass;

    public JsonHttpListener(IDataListener<T> dataListener, Class<T> entityClass) {
        this.dataListener = dataListener;
        this.entityClass = entityClass;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream = null;
        try {
            inputStream = httpEntity.getContent();
            String content = streamToString(inputStream);
            final T result = JSON.parseObject(content, entityClass);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess(result);
                }
            });
        } catch (final IOException e) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onError(JSONHTTPLISTENER_ONSUCCESS_EXCEPTION, e.getMessage());
                }
            });
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
        }
    }

    /** stream转化为string */
    private String streamToString(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {}
            }
        }
        return null;
    }

    @Override
    public void onError(int errorCode, String detailInfo) {
        dataListener.onError(errorCode, detailInfo);
    }
}

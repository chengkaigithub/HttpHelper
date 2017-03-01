package httphelper.com.suixingpay.chengkai.httphelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import http.TextRequestHelper;
import http.listener.IDataListener;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    private String url = "http://gc.ditu.aliyun.com/geocoding?a=苏州市";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = ((TextView) findViewById(R.id.request_result));
    }

    /**
     * 获取网络请求
     */
    public void request(View view) {

        for (int i = 0; i < 20; i++) {
            TextRequestHelper.sendJsonTextRequest(url, null, ResultBean.class, new IDataListener<ResultBean>() {
                @Override
                public void onSuccess(ResultBean resultBean) {
                    setText("success-->" + resultBean.toString() + "\n");
                    Log.e("=======", "result" + resultBean.toString());
                }

                @Override
                public void onError(int errorCode, String msg) {
                    setText("error-->" + errorCode + "-" + msg + "\n");
                    Log.e("=======", "result" + errorCode + "---" + msg);
                }
            });
        }

    }

    /**
     * 设置请求下来的数据在界面上展示
     */
    private void setText(String msg) {
        textView.append(msg);
    }
}

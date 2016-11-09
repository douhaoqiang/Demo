package com.dhq.net;

import retrofit2.Retrofit;

/**
 * DESC
 * Created by douhaoqiang on 2016/11/9.
 */
public class OlderServiceGen extends ServiceGenerator<TestService> {

    private static final String TAG = "OlderServiceGen";

    private static final String BASE_URL = "http://www.baidu.com/";//

    private static OlderServiceGen instance = new OlderServiceGen();

    public static OlderServiceGen getInstance() {
        if (instance == null) {
            synchronized (OlderServiceGen.class) {
                if (instance == null) {
                    instance = new OlderServiceGen();
                }
            }
        }
        return instance;
    }

    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }

    @Override
    public TestService createService() {
        return retrofit.create(TestService.class);
    }


}

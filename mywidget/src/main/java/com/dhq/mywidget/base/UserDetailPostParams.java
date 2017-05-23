package com.dhq.mywidget.base;

/**
 * Created by ddklsy163com on 17/4/10.
 */

public class UserDetailPostParams {

    /**
     * appkey : 888
     * method : home_product
     * request : {"uid":674}
     * rtimes : 1
     * sign : 3b2023dc30cb0e151b33edca23580aa2
     * sn :
     * timestamp : 1495532132639
     */
    /**
     * {"appkey":"888","method":"order_look",
     * "request":{"uid":"674"},"rtimes":1,"sign":"011e300baeaa529472bed70d43131c31","sn":"","timestamp":1495532309940}
     */
    private String appkey="888";
    private String method="order_look";
    private RequestBean request=new RequestBean();
    private int rtimes=1;
    private String sign="011e300baeaa529472bed70d43131c31";
    private String sn="";
    private long timestamp=1495532309940L;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public int getRtimes() {
        return rtimes;
    }

    public void setRtimes(int rtimes) {
        this.rtimes = rtimes;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class RequestBean {
        /**
         * uid : 674
         */

        private String uid="674";

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}

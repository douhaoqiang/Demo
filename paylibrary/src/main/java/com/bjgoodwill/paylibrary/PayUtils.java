package com.bjgoodwill.paylibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import com.alipay.sdk.app.PayTask;
import com.bjgoodwill.paylibrary.alipay.PayResult;
import com.bjgoodwill.paylibrary.constant.PayConstant;
import com.bjgoodwill.paylibrary.util.MD5Util;
import com.bjgoodwill.paylibrary.util.XmlUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dongpeng on 2016/11/23.
 */

public class PayUtils {
    private static PayCallBack callBack;
    private static IWXAPI api;
    private static Context mContext;
    private static PayUtils payUtils;
    private PayUtils(){}
    public static PayUtils getInstance(Context context){
        mContext=context;
        if (payUtils==null){
            payUtils=new PayUtils();
            api = WXAPIFactory.createWXAPI(mContext, PayConstant.WXAppId);
            api.registerApp(PayConstant.WXAppId);
        }
        return payUtils;
    }


    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        try {
                            callBack.success(payResult.getTrade_no());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        try {
                            callBack.failure();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    /**
     *
     * @param zhifubaoSignInfo 支付宝支付
     */
    public void zhifubaoPay(final String zhifubaoSignInfo, PayCallBack callBackInterface){
        callBack=callBackInterface;
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(PayConstant.ZFBAppId);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//        String sign = OrderInfoUtil2_0.getSign(params,PayConstant.RSA_PRIVATE);
//        final String orderInfo = orderParam + "&" + sign;
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                Map<String, String> result = alipay.payV2(zhifubaoSignInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 微信支付--实际调用这个方法
     * @param map
     */
    public void weixinPy(Map<String,String> map){
        /**
         * 时间戳
         */
        Date date = new Date(System.currentTimeMillis());
        String time=date.getTime()/1000+"";//时间戳，十位数

        PayReq req = new PayReq();
        req.appId = map.get("appid");  // 测试用appId
        req.partnerId = map.get("partnerid");
        req.prepayId = map.get("prepayid");
        req.nonceStr = map.get("noncestr");
        req.timeStamp = map.get("timestamp");
//        req.timeStamp = time;
//        req.packageValue = "Sign=WXPay";
        req.packageValue = map.get("packageStr");
//        SortedMap<String, String> m = new TreeMap<>();
//        m.put("appid",map.get("appid"));
//        m.put("partnerid", map.get("mch_id"));//微信支付分配的商户号
//        m.put("noncestr",map.get("nonce_str"));//随机字符串，不长于32位
//        m.put("prepayid", map.get("prepay_id"));//
//        m.put("timestamp",time);//
//        m.put("package", "Sign=WXPay");//

//        req.sign = createSign("UTF-8",m);
        req.sign = map.get("sign");
        api.sendReq(req);
    }

    /**
     *微信支付--测试时调用这个方法
     * @param orderInfo 微信订单信息
     */
    public static void weixinPay(Map<String,String> orderInfo){
        SortedMap<String, String> map = new TreeMap<>();
        map.put("appid", PayConstant.WXAppId);
        map.put("mch_id", PayConstant.mch_id);//微信支付分配的商户号
        map.put("nonce_str", (Math.random() * 100000000) + "");//随机字符串，不长于32位
        map.put("body", "嘉和-测试商品");//商品描述
        map.put("out_trade_no", getOutTradeNo());//商户订单号
        map.put("fee_type", "CNY");//货币类型，人民币为CNY
        map.put("total_fee", "1");//总金额，参数支付金额单位为【分】，参数值不能带小数
        map.put("spbill_create_ip", "123.124.21.34");//终端IP
        map.put("notify_url", "http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");//接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
        map.put("trade_type", "APP");//支付类型
        String sign = createSign("UTF-8", map);
        map.put("sign", sign);
        String xml = XmlUtils.map2xmlBody(map, "XML");
        OkHttpClient client = new OkHttpClient.Builder().build();
        final Request request = new Request.Builder().url("https://api.mch.weixin.qq.com/pay/unifiedorder")
                .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), xml)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("", e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    Date date = new Date(System.currentTimeMillis());
                    String time=date.getTime()/1000+"";//时间戳，十位数
                    String str = response.body().string();
                    Map<String, String> map = XmlUtils.xmlBody2map(str, "xml");
                    PayReq req = new PayReq();
                    req.appId = map.get("appid");  // 测试用appId
                    req.partnerId = map.get("mch_id");
                    req.prepayId = map.get("prepay_id");
                    req.nonceStr = map.get("nonce_str");
                    req.timeStamp = time;
                    req.packageValue = "Sign=WXPay";


                    SortedMap<String, String> m = new TreeMap<>();
                    m.put("appid",map.get("appid"));
                    m.put("partnerid", map.get("mch_id"));//微信支付分配的商户号
                    m.put("noncestr",map.get("nonce_str"));//随机字符串，不长于32位
                    m.put("prepayid", map.get("prepay_id"));//随机字符串，不长于32位
                    m.put("timestamp",time);//随机字符串，不长于32位
                    m.put("package", "Sign=WXPay");//随机字符串，不长于32位

                    req.sign = createSign("UTF-8",m);
                    Log.e("-------",""+req.checkArgs());
                    api.sendReq(req);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    response.body().close();
                }
            }
        });
    }

    /**
     * 生成随机订单号。
     *
     * @return
     */
    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createSign(String characterEncoding, SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + "xlGdwbTCCk2e4DPuIibTZox06VRoveqe");//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }
}

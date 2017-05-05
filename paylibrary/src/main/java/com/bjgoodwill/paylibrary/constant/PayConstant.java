package com.bjgoodwill.paylibrary.constant;

/**
 * Created by dongpeng on 2016/12/1.
 */

public class PayConstant {
    /**
     * 微信支付
     */
    public static String WXAppId="wx84b3eaa39d8ed662";
    public static String mch_id="1415958602";

    /**
     * 支付宝支付
     */
    public static String ZFBAppId="2016111702918754";
    //    构造交易数据并签名必须在商户服务端完成，商户的应用私钥绝对不能保存在商户APP客户端中，也不能从服务端下发。
    /** 商户私钥，pkcs8格式 */
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMCI0Au6AeeSW+MB" +
            "a+MjzckEZijo1jqfmdRpDB14eiWWSiaDvDKqvJRNI4Lys0uaI2Ij+BGPm0l46oq5" +
            "DdnARO5KFcCVLYqsLtLftVdZi3D5lZkaqr1wBdsgg33SxbsUZ0v+eNoel1fco2yz" +
            "V+hKCpFfdegqLHlf6WMA0PbjJxiVAgMBAAECgYEAgwjZ5xE6GJvAk7e08Iq+lE+9" +
            "I5yLqKxUqn6v8yK6pvgpERtZK3aOD7SMNaPuKMcHy9RFEdaVziRQJAecCJXVktPV" +
            "dEUYbyCuvNi4XNmUsJar9z06q41xc6AlOkCATc8J/3FfHv+cBErOJOqnUWE1+Iad" +
            "doitY/Dj+N1AsfLGAmUCQQD2m87rPK64ClFnxWGnywm8WOlpOizIVPhn0wwPhk0O" +
            "BM6TidAXP8Sm5JdCZWpKZCnQFRIvyD7V6YjxEh5neP/vAkEAx93VhokR8dj57sjT" +
            "C4+AZjJlViLIxoSWbCo9lJru6OPypq9ITF8K7/wZWjelw8duZ2n9UlyUf64lNTLL" +
            "WqkruwJBALBMiwzZAP9JQREyprHSEU+5MISttj4xoLR4yHKAzK1s+lG3gDBewjOd" +
            "0BUDc41jNhEwPyQxv9olbmUJUvseIbkCQDUuI4iepDWzxBzzNqSW1FVdB3W5365i" +
            "zRuOyb/m0bcKICCV9yjLCT/91kkZEXRcdlXx78OgeqrWjXPwRgejh80CQEiZymg4" +
            "imTuxxK+ErjDbwrASSpDvixILpbQg/Hjqu47u4vv2fV4sEJyxu6tkO/Y7BcLcJWb" +
            "zGbfLGxEgW3fk4w=";


    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "2088521245412440";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "hscpro@bjgoodwill.com";
}

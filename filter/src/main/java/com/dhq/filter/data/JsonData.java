package com.dhq.filter.data;

import com.dhq.filter.vo.SaleAttributeNameVo;
import com.dhq.filter.vo.SaleAttributeVo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * DESC
 * Created by douhaoqiang on 2017/2/21.
 */
public class JsonData {
    private static final String TAG = "JsonData";

    public static final String filterData="["
            + "{\"nameId\":\"V2QASD\",\"saleVo\":["
            + "{\"value\":\"2核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
            + "{\"value\":\"4核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"6核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"8核\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
            + "],\"name\":\"CPU\"},"
            + "{\"nameId\":\"V2QASD\",\"saleVo\":["
            + "{\"value\":\"全网通\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"移动4G\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
            + "{\"value\":\"电信4G\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"联通4G\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
            + "],\"name\":\"网络制式\"},"
            + "{\"nameId\":\"V2QASD\",\"saleVo\":["
            + "{\"value\":\"OPPO\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"荣耀\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"苹果\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
            + "{\"value\":\"鸭梨\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"月饼\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"vivo\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
            + "],\"name\":\"品牌\"},"
            + "{\"nameId\":\"V2QASD\",\"saleVo\":["
            + "{\"value\":\"音乐\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"},"
            + "{\"value\":\"拍照\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"待机长\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"}"
            + "],\"name\":\"主打\"},"
            + "{\"nameId\":\"V2QLAH\",\"saleVo\":["
            + "{\"value\":\"4.5英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"5英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"5.5英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"0\"},"
            + "{\"value\":\"6英寸\",\"goods\":null,\"goodsAndValId\":\"C6VOWQ\",\"checkStatus\":\"1\"}"
            + "],\"name\":\"尺寸\"}" + "]";


    public static List<SaleAttributeNameVo> getDatas(){
        List<SaleAttributeNameVo> itemData = new ArrayList<SaleAttributeNameVo>();
        JSONArray json = null;
        try {
            json = new JSONArray(filterData);
            for (int i = 0; i < json.length(); i++) {
                SaleAttributeNameVo saleName = new SaleAttributeNameVo();
                JSONObject obj = (JSONObject) json.opt(i);
                saleName.setName(obj.getString("name"));
                List<SaleAttributeVo> list = new ArrayList<SaleAttributeVo>();
                JSONArray array = new JSONArray(obj.getString("saleVo"));
                for (int j = 0; j < array.length(); j++) {
                    JSONObject object = array.getJSONObject(j);
                    SaleAttributeVo vo = new SaleAttributeVo();
                    vo.setGoods(object.getString("goods"));
                    vo.setValue(object.getString("value"));
                    vo.setGoodsAndValId(object.getString("goodsAndValId"));
                    if ("1".equals(object.getString("checkStatus"))) {
                        vo.setChecked(true);
                    } else {
                        vo.setChecked(false);
                    }
                    list.add(vo);
                }
                saleName.setSaleVo(list);
                // 是否展开
                saleName.setNameIsChecked(false);
                itemData.add(saleName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return itemData;

    }

}

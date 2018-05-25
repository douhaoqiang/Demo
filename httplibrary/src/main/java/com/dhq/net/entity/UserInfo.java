package com.dhq.net.entity;

import com.dhq.baselibrary.entity.BaseEntity;

/**
 * DESC
 * Created by douhaoqiang on 2017/11/16.
 */

public class UserInfo extends BaseEntity {


    /**
     * foodId : 1
     * foodName : 扁豆焖面
     * foodPrice : 25
     * foodSum : 2
     * imagePath : http://recipe0.hoto.cn/pic/recipe/l/d4/84/1213652_8b182d.jpg,http://site.meishij.net/r/208/36/1259208/s1259208_142208429462016.jpg,http://site.meishij.net/r/26/238/1309526/s1309526_51030.jpg
     * foodDescribe : 餐品描述
     * monthlySaleNum : 1
     * acclaimRate : 2
     * acclaimNum : 3
     * negativeCommentNum : 4
     */

    private String foodId;
    private String foodName;
    private String foodPrice;
    private String foodSum;
    private String imagePath;
    private String foodDescribe;
    private String monthlySaleNum;
    private String acclaimRate;
    private String acclaimNum;
    private String negativeCommentNum;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getFoodSum() {
        return foodSum;
    }

    public void setFoodSum(String foodSum) {
        this.foodSum = foodSum;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFoodDescribe() {
        return foodDescribe;
    }

    public void setFoodDescribe(String foodDescribe) {
        this.foodDescribe = foodDescribe;
    }

    public String getMonthlySaleNum() {
        return monthlySaleNum;
    }

    public void setMonthlySaleNum(String monthlySaleNum) {
        this.monthlySaleNum = monthlySaleNum;
    }

    public String getAcclaimRate() {
        return acclaimRate;
    }

    public void setAcclaimRate(String acclaimRate) {
        this.acclaimRate = acclaimRate;
    }

    public String getAcclaimNum() {
        return acclaimNum;
    }

    public void setAcclaimNum(String acclaimNum) {
        this.acclaimNum = acclaimNum;
    }

    public String getNegativeCommentNum() {
        return negativeCommentNum;
    }

    public void setNegativeCommentNum(String negativeCommentNum) {
        this.negativeCommentNum = negativeCommentNum;
    }
}

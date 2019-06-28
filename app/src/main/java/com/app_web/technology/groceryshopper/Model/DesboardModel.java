
package com.app_web.technology.groceryshopper.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DesboardModel {

    @SerializedName("IsHardStop")
    @Expose
    private Integer isHardStop;
    @SerializedName("isSharingEnable")
    @Expose
    private String isSharingEnable;
    @SerializedName("isDiscriptionEnable")
    @Expose
    private String isDiscriptionEnable;
    @SerializedName("isCartNotificationEnable")
    @Expose
    private String isCartNotificationEnable;
    @SerializedName("isCartEditingEnable")
    @Expose
    private String isCartEditingEnable;
    @SerializedName("minimum_delivery_amount")
    @Expose
    private String minimumDeliveryAmount;
    @SerializedName("offer_amount")
    @Expose
    private String offerAmount;
    @SerializedName("offer_amount2")
    @Expose
    private String offerAmount2;
    @SerializedName("offer_amount3")
    @Expose
    private String offerAmount3;
    @SerializedName("card_charges")
    @Expose
    private String cardCharges;
    @SerializedName("maintenance")
    @Expose
    private String maintenance;
    @SerializedName("delivery_disable")
    @Expose
    private String deliveryDisable;
    @SerializedName("dineinn_disable")
    @Expose
    private String dineinnDisable;
    @SerializedName("OnlyCollectionDiscount")
    @Expose
    private String onlyCollectionDiscount;
    @SerializedName("MinimumAmtForDiscount")
    @Expose
    private String minimumAmtForDiscount;
    @SerializedName("isClickToCallEnabeled")
    @Expose
    private String isClickToCallEnabeled;
    @SerializedName("isTifinEnable")
    @Expose
    private String isTifinEnable;
    @SerializedName("HomeText")
    @Expose
    private String homeText;
    @SerializedName("isPayPalEnvironmentProduction")
    @Expose
    private String isPayPalEnvironmentProduction;
    @SerializedName("OnlinePaymentEnable")
    @Expose
    private String onlinePaymentEnable;
    @SerializedName("isCreditCard")
    @Expose
    private String isCreditCard;
    @SerializedName("isBooking")
    @Expose
    private String isBooking;
    @SerializedName("phone_number")
    @Expose
    private String phoneNumber;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("sendBoxId")
    @Expose
    private String sendBoxId;
    @SerializedName("productionId")
    @Expose
    private String productionId;
    @SerializedName("map_url")
    @Expose
    private String mapUrl;
    @SerializedName("PlayStoreVersion")
    @Expose
    private String playStoreVersion;
    @SerializedName("playstore_url")
    @Expose
    private Object playstoreUrl;

    public Integer getIsHardStop() {
        return isHardStop;
    }

    public void setIsHardStop(Integer isHardStop) {
        this.isHardStop = isHardStop;
    }

    public String getIsSharingEnable() {
        return isSharingEnable;
    }

    public void setIsSharingEnable(String isSharingEnable) {
        this.isSharingEnable = isSharingEnable;
    }

    public String getIsDiscriptionEnable() {
        return isDiscriptionEnable;
    }

    public void setIsDiscriptionEnable(String isDiscriptionEnable) {
        this.isDiscriptionEnable = isDiscriptionEnable;
    }

    public String getIsCartNotificationEnable() {
        return isCartNotificationEnable;
    }

    public void setIsCartNotificationEnable(String isCartNotificationEnable) {
        this.isCartNotificationEnable = isCartNotificationEnable;
    }

    public String getIsCartEditingEnable() {
        return isCartEditingEnable;
    }

    public void setIsCartEditingEnable(String isCartEditingEnable) {
        this.isCartEditingEnable = isCartEditingEnable;
    }

    public String getMinimumDeliveryAmount() {
        return minimumDeliveryAmount;
    }

    public void setMinimumDeliveryAmount(String minimumDeliveryAmount) {
        this.minimumDeliveryAmount = minimumDeliveryAmount;
    }

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String offerAmount) {
        this.offerAmount = offerAmount;
    }

    public String getOfferAmount2() {
        return offerAmount2;
    }

    public void setOfferAmount2(String offerAmount2) {
        this.offerAmount2 = offerAmount2;
    }

    public String getOfferAmount3() {
        return offerAmount3;
    }

    public void setOfferAmount3(String offerAmount3) {
        this.offerAmount3 = offerAmount3;
    }

    public String getCardCharges() {
        return cardCharges;
    }

    public void setCardCharges(String cardCharges) {
        this.cardCharges = cardCharges;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(String maintenance) {
        this.maintenance = maintenance;
    }

    public String getDeliveryDisable() {
        return deliveryDisable;
    }

    public void setDeliveryDisable(String deliveryDisable) {
        this.deliveryDisable = deliveryDisable;
    }

    public String getDineinnDisable() {
        return dineinnDisable;
    }

    public void setDineinnDisable(String dineinnDisable) {
        this.dineinnDisable = dineinnDisable;
    }

    public String getOnlyCollectionDiscount() {
        return onlyCollectionDiscount;
    }

    public void setOnlyCollectionDiscount(String onlyCollectionDiscount) {
        this.onlyCollectionDiscount = onlyCollectionDiscount;
    }

    public String getMinimumAmtForDiscount() {
        return minimumAmtForDiscount;
    }

    public void setMinimumAmtForDiscount(String minimumAmtForDiscount) {
        this.minimumAmtForDiscount = minimumAmtForDiscount;
    }

    public String getIsClickToCallEnabeled() {
        return isClickToCallEnabeled;
    }

    public void setIsClickToCallEnabeled(String isClickToCallEnabeled) {
        this.isClickToCallEnabeled = isClickToCallEnabeled;
    }

    public String getIsTifinEnable() {
        return isTifinEnable;
    }

    public void setIsTifinEnable(String isTifinEnable) {
        this.isTifinEnable = isTifinEnable;
    }

    public String getHomeText() {
        return homeText;
    }

    public void setHomeText(String homeText) {
        this.homeText = homeText;
    }

    public String getIsPayPalEnvironmentProduction() {
        return isPayPalEnvironmentProduction;
    }

    public void setIsPayPalEnvironmentProduction(String isPayPalEnvironmentProduction) {
        this.isPayPalEnvironmentProduction = isPayPalEnvironmentProduction;
    }

    public String getOnlinePaymentEnable() {
        return onlinePaymentEnable;
    }

    public void setOnlinePaymentEnable(String onlinePaymentEnable) {
        this.onlinePaymentEnable = onlinePaymentEnable;
    }

    public String getIsCreditCard() {
        return isCreditCard;
    }

    public void setIsCreditCard(String isCreditCard) {
        this.isCreditCard = isCreditCard;
    }

    public String getIsBooking() {
        return isBooking;
    }

    public void setIsBooking(String isBooking) {
        this.isBooking = isBooking;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSendBoxId() {
        return sendBoxId;
    }

    public void setSendBoxId(String sendBoxId) {
        this.sendBoxId = sendBoxId;
    }

    public String getProductionId() {
        return productionId;
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(String mapUrl) {
        this.mapUrl = mapUrl;
    }

    public String getPlayStoreVersion() {
        return playStoreVersion;
    }

    public void setPlayStoreVersion(String playStoreVersion) {
        this.playStoreVersion = playStoreVersion;
    }

    public Object getPlaystoreUrl() {
        return playstoreUrl;
    }

    public void setPlaystoreUrl(Object playstoreUrl) {
        this.playstoreUrl = playstoreUrl;
    }

}

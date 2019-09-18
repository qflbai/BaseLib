package com.qflbai.lib.location;

/**
 * @author WenXian Bai
 * @Date: 2018/6/13.
 * @Description:
 */
public class LocationInfo {
    /**纬度****/
    private String latitude = "";
    /**经度******/
    private String longtitude = "";
    /**半径********/
    private String radius = "";
    /**国家代码*********/
    private String countryCode = "";
    /**国家*********/
    private String country = "";
    /**城市代码**********/
    private String cityCode = "";
    /**城市*************/
    private String city = "";
    /**区县******/
    private String district = "";
    /**街道*****/
    private String street = "";
    /**地址*****/
    private String addr = "";
    /**附近*******/
    private String describe = "";

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getRadius() {
        return radius;
    }

    public void setRadius(String radius) {
        this.radius = radius;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

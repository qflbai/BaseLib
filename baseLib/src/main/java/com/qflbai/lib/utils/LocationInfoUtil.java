package com.qflbai.lib.utils;


import java.util.HashMap;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.qflbai.lib.location.LocationInfo;

/**
 * @author WenXian Bai
 * @Date: 2018/3/23.
 * @Description:  位置信息类
 */
public class LocationInfoUtil {
    /**
     * 纬度key
     */
    public static final String KEY_LATITUDE = "key_latitude";
    /**
     * 经度key
     */
    public static final String KEY_LONGTITUDE = "key_longtitude";
    /**
     * 半径
     */
    public static final String KEY_RADIUS = "key_radius";
    /**
     * 国家代号
     */
    public static final String KEY_COUNTRY_CODE = "key_country_code";
    /**
     * 国家
     */
    public static final String KEY_COUNTRY = "key_country";
    /**
     * 城市代号
     */
    public static final String KEY_CITY_CODE = "key_city_code";
    /**
     * 城市
     */
    public static final String KEY_CITY = "key_city";
    /**
     * 区域
     */
    public static final String KEY_DISTRICT = "key_district";
    /**
     * 街道
     */
    public static final String KEY_STREET = "key_street";
    /**
     * 地址
     */
    public static final String KEY_ADDR = "key_addr";
    /**
     * 描述
     */
    public static final String KEY_DESCRIBE = "key_district";


    /**
     * 读取保存的位置信息
     * @param context
     * @return
     */
    private static HashMap<String, String> getInfo(Context context) {
        /**纬度****/
        String latitude = "";
        /**经度******/
        String longtitude = "";
        /**半径********/
        String radius = "";
        /**国家代码*********/
        String countryCode = "";
        /**国家*********/
        String country = "";
        /**城市代码**********/
        String cityCode = "";
        /**城市*************/
        String city = "";
        /**区县******/
        String district = "";
        /**街道*****/
        String street = "";
        /**地址*****/
        String addr = "";
        /**附近*******/
        String describe = "";

        HashMap<String, String> map = new HashMap<String, String>();
        SharedPreferences preferences = context.getSharedPreferences("addressinfo", Context.MODE_PRIVATE);
        latitude = preferences.getString(KEY_LATITUDE, "");
        longtitude = preferences.getString(KEY_LONGTITUDE, "");
        radius = preferences.getString(KEY_RADIUS, "");
        countryCode = preferences.getString(KEY_COUNTRY_CODE, "");
        country = preferences.getString(KEY_COUNTRY, "");
        cityCode = preferences.getString(KEY_CITY_CODE, "");
        city = preferences.getString(KEY_CITY, "");
        district = preferences.getString(KEY_DISTRICT, "");
        street = preferences.getString(KEY_STREET, "");
        addr = preferences.getString(KEY_ADDR, "");
        describe = preferences.getString(KEY_DESCRIBE, "");

        map.put(KEY_LATITUDE, latitude);
        map.put(KEY_LONGTITUDE, longtitude);
        map.put(KEY_RADIUS, radius);
        map.put(KEY_COUNTRY_CODE, countryCode);
        map.put(KEY_COUNTRY, country);
        map.put(KEY_CITY_CODE, cityCode);
        map.put(KEY_CITY, city);
        map.put(KEY_DISTRICT, district);
        map.put(KEY_STREET, street);
        map.put(KEY_ADDR, addr);
        map.put(KEY_DESCRIBE, describe);

        return map;
    }

    /**
     * 位置信息容器
     */
    private static HashMap<String, String> LocationInfoMap;

    /**
     * 获取位置信息
     * @param context
     * @return
     */
    public synchronized static HashMap<String, String> getLocationInfo(Context context) {
        if (LocationInfoMap == null) {
            LocationInfoMap = getInfo(context);
        }

        return LocationInfoMap;
    }

    private static LocationInfo mLocationInfo;
    /**
     * 读取保存的位置信息
     * @param context
     * @return
     */
    private static LocationInfo getLocation1(Context context) {
        /**纬度****/
        String latitude = "";
        /**经度******/
        String longtitude = "";
        /**半径********/
        String radius = "";
        /**国家代码*********/
        String countryCode = "";
        /**国家*********/
        String country = "";
        /**城市代码**********/
        String cityCode = "";
        /**城市*************/
        String city = "";
        /**区县******/
        String district = "";
        /**街道*****/
        String street = "";
        /**地址*****/
        String addr = "";
        /**附近*******/
        String describe = "";

        LocationInfo locationInfo = new LocationInfo();
        SharedPreferences preferences = context.getSharedPreferences("addressinfo", Context.MODE_PRIVATE);
        latitude = preferences.getString(KEY_LATITUDE, "");
        longtitude = preferences.getString(KEY_LONGTITUDE, "");
        radius = preferences.getString(KEY_RADIUS, "");
        countryCode = preferences.getString(KEY_COUNTRY_CODE, "");
        country = preferences.getString(KEY_COUNTRY, "");
        cityCode = preferences.getString(KEY_CITY_CODE, "");
        city = preferences.getString(KEY_CITY, "");
        district = preferences.getString(KEY_DISTRICT, "");
        street = preferences.getString(KEY_STREET, "");
        addr = preferences.getString(KEY_ADDR, "");
        describe = preferences.getString(KEY_DESCRIBE, "");

        locationInfo.setLatitude(latitude);
        locationInfo.setLongtitude(longtitude);
        locationInfo.setRadius(radius);
        locationInfo.setCountryCode(countryCode);
        locationInfo.setCountry(country);
        locationInfo.setCity(city);
        locationInfo.setCityCode(cityCode);
        locationInfo.setDistrict(district);
        locationInfo.setStreet(street);
        locationInfo.setAddr(addr);
        locationInfo.setDescribe(describe);

        return locationInfo;
    }

    /**
     * 获取位置信息
     * @param context
     * @return
     */
    public synchronized static LocationInfo getLocation(Context context) {
        if (mLocationInfo == null) {
            mLocationInfo = getLocation1(context);
        }

        return mLocationInfo;
    }

    /**
     * @author ZhuHongKai
     * @date 2016年6月11日
     * @Description: 保存定位信息
     */
    public static void savaLAddress(Context context, String latitude, String longtitude, String radius,
                                    String countryCode,
                                    String country, String cityCode, String city, String district, String street, String addr,
                                    String describe) {
        SharedPreferences preferences = context.getSharedPreferences("addressinfo", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        /**纬度****/
        if (latitude != null) {
            editor.putString(KEY_LATITUDE, latitude);
        }
        /**经度******/
        if (longtitude != null) {
            editor.putString(KEY_LONGTITUDE, longtitude);
        }
        /**半径********/
        if (radius != null) {
            editor.putString(KEY_RADIUS, radius);
        }
        /**国家代码*********/
        if (countryCode != null) {
            editor.putString(KEY_COUNTRY_CODE, countryCode);
        }
        /**国家*********/
        if (country != null) {
            editor.putString(KEY_COUNTRY, country);
        }
        /**城市代码**********/
        if (cityCode != null) {
            editor.putString(KEY_CITY_CODE, cityCode);
        }
        /**城市*************/
        if (city != null) {
            editor.putString(KEY_CITY, city);
        }
        /**区县******/
        if (district != null) {
            editor.putString(KEY_DISTRICT, district);
        }
        /**街道*****/
        if (street != null) {
            editor.putString(KEY_STREET, street);
        }
        /**地址*****/
        if (addr != null) {
            editor.putString(KEY_ADDR, addr);
        }
        /**附近*******/
        if (describe != null) {
            editor.putString(KEY_DESCRIBE, describe);
        }

        editor.commit();
    }

}

package com.qflbai.lib.utils.bluetooth;

import java.text.NumberFormat;

/**
 * ProjectName: outpdaTotal
 * PackageName: com.suntech.outpdatotal.util.http
 * ClassDesc: 蓝牙设备
 * CreateUser: Corey
 * CreateDate: 2017/6/1 0001 12:01
 * UpdateUser: ******
 * UpdateDate: ******
 * UpdateDesc: ******
 * CopyRight(c) 2016 Shenzhen Sun-Tech Digital Technology Co.Ltd. All rights reserved.
 */

public class BluetoothUtil {

    private static Integer maxVoltage = 4200;  //蓝牙设备的最高电量值
    private static Integer minVoltage = 3500;  //蓝牙设备的最低电量值
    private static String TAG = "BluetoothUtil";
    private static final String enterChar = "\r\n";

    /**
     * 根据当前电压值，计算出电量的百分比
     * @param currentVoltage  单位为毫伏
     * @return
     */
    public static String getDeviceVoltage(String currentVoltage) {

        Integer current = Integer.parseInt(currentVoltage);

        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后0位
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float)(current - minVoltage) / (float)(maxVoltage - minVoltage) * 100);
        result += "%";

        return result;
    }

    /**
     * 去掉data的第一个和最后一个字符串（即去掉“{”和“}”） add by corey20170527
     * @param data
     * @return
     */
    public static String dealBluetoothString(String data){

        if(null == data || data.length() == 0) return null;

        //判断：如果第一个字符是 IMConfig.BLURTOOTH_LEFT_CHAR 则去掉该字符
        if(data.substring(0, 1).equals("{")) {

            data = data.substring(1, data.length());
        }

        //判断：如果最后一个字符是 IMConfig.BLURTOOTH_RIGHT_CHAR 则去掉该字符
        if(data.substring(data.length()-1, data.length()).equals("}")){

            data = data.substring(0, data.length()-1);
        }

        return data;
    }

    /**
     * 删除回车符
     * @param data
     * @return
     */
    public static String removeEnterChar(String data) {

        String returnString;
        if(data.contains(enterChar)) {

            returnString = data.replace(enterChar, "");
            return returnString;
        }
        else {
            return data;
        }
    }

    /**
     * 返回扫码结果的加密文与明文
     * @param data
     * @return
     */
    public static String[] getTwoScanResult(String data) {

        String[] returnArr;
        returnArr = data.split(",");
        return returnArr;
    }
}

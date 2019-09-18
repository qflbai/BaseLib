package com.qflbai.lib.utils.time;

/**
 * @author WenXian Bai
 * @Date: 2018/10/11.
 * @Description:
 */
public class TimeLimitUtils {
    public static final int DELAY = 1000;
    private static long lastClickTime = 0;

    /**
     * 是否短时间内联系点击
     * @return
     */
    public static boolean isFastClick(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime > DELAY) {
            lastClickTime = currentTime;
            return false;
        }else{
            return true;
        }
    }
}

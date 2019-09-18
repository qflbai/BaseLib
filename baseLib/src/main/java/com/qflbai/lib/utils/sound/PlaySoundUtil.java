package com.qflbai.lib.utils.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.qflbai.lib.R;
import com.qflbai.lib.entity.SoundType;

import java.util.HashMap;

import static com.qflbai.lib.entity.SoundType.cancel_no;
import static com.qflbai.lib.entity.SoundType.cancel_ok;
import static com.qflbai.lib.entity.SoundType.enter_no;
import static com.qflbai.lib.entity.SoundType.enter_ok;
import static com.qflbai.lib.entity.SoundType.out_no;
import static com.qflbai.lib.entity.SoundType.out_ok;
import static com.qflbai.lib.entity.SoundType.receiving_goods_offline_ok;
import static com.qflbai.lib.entity.SoundType.receiving_goods_ok;
import static com.qflbai.lib.entity.SoundType.receiving_no;
import static com.qflbai.lib.entity.SoundType.relevance_no;
import static com.qflbai.lib.entity.SoundType.relevance_ok;
import static com.qflbai.lib.entity.SoundType.switch_house_no;
import static com.qflbai.lib.entity.SoundType.switch_house_ok;

public class PlaySoundUtil {

    //播放提示音
    private static SoundPool soundPool;
    private static HashMap<SoundType, Integer> srcSoundMap;

    public static void initSound(Context context) {

        if (soundPool == null) {
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
            srcSoundMap = new HashMap<>();
        }

        srcSoundMap.put(enter_ok, soundPool.load(context, R.raw.enter_ok, 1));
        srcSoundMap.put(enter_no, soundPool.load(context, R.raw.enter_no, 1));
        srcSoundMap.put(out_ok, soundPool.load(context, R.raw.out_ok, 1));
        srcSoundMap.put(out_no, soundPool.load(context, R.raw.out_no, 1));
        srcSoundMap.put(cancel_ok, soundPool.load(context, R.raw.cancel_ok, 1));
        srcSoundMap.put(cancel_no, soundPool.load(context, R.raw.cancel_no, 1));
        srcSoundMap.put(relevance_ok, soundPool.load(context, R.raw.relevance_ok, 1));
        srcSoundMap.put(relevance_no, soundPool.load(context, R.raw.relevance_no, 1));
        srcSoundMap.put(switch_house_ok, soundPool.load(context, R.raw.switch_house_ok, 1));
        srcSoundMap.put(switch_house_no, soundPool.load(context, R.raw.switch_house_no, 1));

        srcSoundMap.put(receiving_goods_ok, soundPool.load(context, R.raw.ic_receiving_ok, 1));
        srcSoundMap.put(receiving_goods_offline_ok, soundPool.load(context, R.raw.raw_receiving_offline_ok, 1));

        srcSoundMap.put(receiving_no,soundPool.load(context,R.raw.receiving_no,1));
    }


    /**
     * @author ZhuHongKai
     * @date 2016年5月20日
     * @Description: 播放提示音
     * sound soundPoolMap.put 中的第几个声音文件
     * loop 重复的次数
     */
    public static void playSound(Context context, SoundType soundType) {
        AudioManager mgr = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);

        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        float volume = streamVolumeCurrent / streamVolumeMax;

        int soundSrc = 0;
        switch (soundType) {
            case enter_ok:
                soundSrc = srcSoundMap.get(enter_ok);
                break;
            case enter_no:
                soundSrc = srcSoundMap.get(enter_no);
                break;
            case out_ok:
                soundSrc = srcSoundMap.get(out_ok);
                break;
            case out_no:
                soundSrc = srcSoundMap.get(out_no);
                break;
            case cancel_ok:
                soundSrc = srcSoundMap.get(cancel_ok);
                break;
            case cancel_no:
                soundSrc = srcSoundMap.get(cancel_no);
                break;
            case relevance_ok:
                soundSrc = srcSoundMap.get(relevance_ok);
                break;
            case relevance_no:
                soundSrc = srcSoundMap.get(relevance_no);
                break;
            case switch_house_ok:
                soundSrc = srcSoundMap.get(switch_house_ok);
                break;
            case switch_house_no:
                soundSrc = srcSoundMap.get(switch_house_no);
                break;

            case receiving_goods_ok:
                soundSrc = srcSoundMap.get(receiving_goods_ok);
                break;

            case receiving_goods_offline_ok:
                soundSrc = srcSoundMap.get(receiving_goods_offline_ok);
                break;

            case receiving_no:
                soundSrc = srcSoundMap.get(receiving_no);
                break;
            default:
                break;
        }
        if (soundSrc == 0) {
            return;
        }
        soundPool.play(soundSrc, volume, volume, 1, 0, 1f);
    }

    public static void pause() {
        if (soundPool != null) {
            soundPool.autoPause();
        }

    }

    public static void resume() {
        if (soundPool != null) {
            soundPool.autoResume();
        }
    }


}

package com.qflbai.lib.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author WenXian Bai
 * @Date: 2018/8/28.
 * @Description:
 */
public class ImageUtil {
    /**
     * 获取缩略图
     * @param imagePath:文件路径
     * @param width:缩略图宽度
     * @param height:缩略图高度
     * @return
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //关于inJustDecodeBounds的作用将在下文叙述
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        int h = options.outHeight;//获取图片高度
        int w = options.outWidth;//获取图片宽度
        int scaleWidth = w / width; //计算宽度缩放比
        int scaleHeight = h / height; //计算高度缩放比
        int scale = 1;//初始缩放比
        if (scaleWidth < scaleHeight) {//选择合适的缩放比
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }

        options.inSampleSize = scale;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把inJustDecodeBounds 设为 false
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     *将文件压缩后覆盖源文件 (质量压缩)
     */
    public static void compressImage(File file) {
        Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 60;//先压缩到80%
        while (baos.toByteArray().length / 1024 > 1000) { // 循环判断如果压缩后图片是否大于200kb,大于继续压缩
            if (options <= 0) {//有的图片过大，可能当options小于或者等于0时，它的大小还是大于目标大小，于是就会发生异常，异常的原因是options超过规定值。所以此处需要判断一下
                break;
            }
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 尺寸压缩
     * @param file
     */
    public void bitmapFactory(File file){
        String imagePath = file.getPath();
        // 配置压缩的参数
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; //获取当前图片的边界大小，而不是将整张图片载入在内存中，避免内存溢出
        BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        ////inSampleSize的作用就是可以把图片的长短缩小inSampleSize倍，所占内存缩小inSampleSize的平方
        options.inSampleSize = caculateSampleSize(options,500,50);
        Bitmap bm = BitmapFactory.decodeFile(imagePath, options); // 解码文件

    }

    /**
     * 计算出所需要压缩的大小
     * @param options
     * @param reqWidth  我们期望的图片的宽，单位px
     * @param reqHeight 我们期望的图片的高，单位px
     * @return
     */
    private int caculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int sampleSize = 1;
        int picWidth = options.outWidth;
        int picHeight = options.outHeight;
        if (picWidth > reqWidth || picHeight > reqHeight) {
            int halfPicWidth = picWidth / 2;
            int halfPicHeight = picHeight / 2;
            while (halfPicWidth / sampleSize > reqWidth || halfPicHeight / sampleSize > reqHeight) {
                sampleSize *= 2;
            }
        }
        return sampleSize;
    }

}

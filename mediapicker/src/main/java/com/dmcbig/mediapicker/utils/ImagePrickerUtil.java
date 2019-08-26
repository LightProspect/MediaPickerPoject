package com.dmcbig.mediapicker.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.dmcbig.mediapicker.PickerActivity;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.TakePhotoActivity;

import java.io.File;

/**
 * 描述：图片选择工具类
 */

public class ImagePrickerUtil {

    /***************************** --mediapicker-- *****************************/
    public static final int SHOOTING_SUCCESS = 101;//拍照完成
    public static final int C0MPLETE_SELECTION = 102;//相册选择文件完成
    public static final int FILMING_VIDEO = 103;//拍摄视频
    public static final int KJNOVA_CLIPPER = 104;//图片裁剪

    public static final int PICKER_IMAGE = 100;//图片
    public static final int PICKER_VIDEO = 102;//视频
    public static final int PICKER_IMAGE_VIDEO = 101;//图片and视频


    /**
     * 拍摄照片
     *
     * @param activity
     */
    public static void Photograph(Activity activity) {
        Intent intent = new Intent(activity, TakePhotoActivity.class); //Take a photo with a camera
        activity.startActivityForResult(intent, SHOOTING_SUCCESS);
    }

    public static void KJNova_Clipper(Activity activity, String url, Uri desUri, int wideProportion, int highProportion) {
        Intent intent1 = new Intent("com.android.camera.action.CROP");
        intent1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent1.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        intent1.putExtra("crop", "true");
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(url)));//
        intent1.putExtra("aspectX", wideProportion);
        intent1.putExtra("aspectY", highProportion);
        intent1.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//                    intent1.putExtra("outputX", 720);
//                    intent1.putExtra("outputY", 720);
        intent1.putExtra("scale", true);
        intent1.putExtra("scaleUpIfNeeded", true);
        intent1.putExtra("return-data", false);
        intent1.putExtra("noFaceDetection", true);
        //将剪切的图片保存到目标Uri中
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        activity.startActivityForResult(intent1, KJNOVA_CLIPPER);
    }

    /**
     * 打开相册
     *
     * @param activity
     * @param count    最大选择几张
     * @param maxSizes 显示最大文件大小(不需要可以传-1 默认为180MB)
     * @param type     判断显示什么类型
     */
    public static void AlbumSelection(Activity activity, int count, long maxSizes, int type) {
        long maxSize;
        if (maxSizes <= 0) {
            maxSize = 188743680L;//long long long long类型
        } else {
            maxSize = maxSizes;
        }
        Intent intent = new Intent(activity, PickerActivity.class);
        switch (type) {
            case PICKER_IMAGE:
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE);//default image and video (Optional)
                break;
            case PICKER_VIDEO:
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_VIDEO);//default image and video (Optional)
                break;
            case PICKER_IMAGE_VIDEO:
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO);//default image and video (Optional)
                break;
            default:
                intent.putExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO);//default image and video (Optional)
                break;
        }
        intent.putExtra(PickerConfig.MAX_SELECT_SIZE, maxSize); //default 180MB (Optional)
        intent.putExtra(PickerConfig.MAX_SELECT_COUNT, count);  //default 40 (Optional)
//        intent.putExtra(PickerConfig.DEFAULT_SELECTED_LIST,defaultSelect); //(Optional)默认选中的照片
        activity.startActivityForResult(intent, C0MPLETE_SELECTION);
    }

    /**
     * 录制视频
     *
     * @param activity
     * @param uri      视频的保存位置(可以不传)
     */
    public static void Filming_Video(Activity activity, Uri uri) {
        Intent intent = new Intent();
        intent.setAction("android.media.action.VIDEO_CAPTURE");
        intent.addCategory("android.intent.category.DEFAULT");

        if (uri != null) {
            // 保存录像到指定的路径
            try {
                uri = Uri.fromFile(TakeVideoUtils.createImageFile(activity, "quxiang_video"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        activity.startActivityForResult(intent, FILMING_VIDEO);
    }


}


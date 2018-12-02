package com.jb.jb_library.constant;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/8/15 14:01
 * @描述： ${TODO} 常量类
 */

public class Constant {
    /**
     * 升级url
     */
    //    public static final String UPDATE_URL = "http://172.21.2.154:8080/update.json";
    public static final String UPDATE_URL = "http://uif.dcb56.com.cn:8088/dcbuif/client/base/version/getlatest";
    public static final String SPF_NAME = "";

    /**
     * 对话框按钮操作类型
     */
    public static final class DIALOG_CLICK_TYPE {
        public static final int CANCLE = 1;//取消
        public static final int ENSURE = 2;//确定
    }

    /**
     * 操作选择的图片
     */
    public static final class OPE_PICTURE_CODE {
        /**
         * 根目录
         */
        public static String ROOT_DIR = null;

        /**
         * 获取本地照片
         */
        public static int TAKE_LOCAL_PICTURE = 1;

        /**
         * 获取相机
         */
        public static int TAKE_PHOTO = 2;

        public static int RESULT_REQUEST_CODE = 3;

        /**
         * 获取4.4以上图片
         */
        public static int TAKE_LOCAL_PICTURE_CROP = 5;

        /**
         * 剪切
         */
        public static int CLIP_CODE = 4;
        public static int VIDEO_CODE = 6;
        public static int VIDEO_WITH_CAMERA = 7;
    }

    /**
     * 剪切图片的模式
     */
    public static final class CORP_MODE {
        public static int SYSTEM_CROP = 1; //系统自带剪切
        public static int CUSTOM_CROP = 2; //自定义剪切模式
    }


}

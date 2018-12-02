package com.bl.lysmarthome.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.at.smarthome.siplib.core.AtSipKitEnv;
import com.at.smarthome.siplib.server.AtSmarthomeInterface;
import com.at.smarthome.siplib.server.IDataUpCallBack;
import com.bl.lysmarthome.LocalManageUtil;
import com.bl.lysmarthome.R;
import com.jb.jb_library.banner.BannerView;
import com.jb.jb_library.banner.holder.HolderCreator;
import com.jb.jb_library.banner.holder.ViewHolder;
import com.jb.jb_library.util.LogUtil;
import com.jb.jb_library.util.StatusBarUtil;
import com.jb.jb_library.util.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IDataUpCallBack {

    public int[] BANNER = new int[]{R.mipmap.home_banner};
    private RelativeLayout mSceneRl;
    private RelativeLayout mTelevisionRl;
    private RelativeLayout mLightingRl;
    private RelativeLayout mAirConditioningRl;
    private RelativeLayout mIntercomRl;
    private RelativeLayout mDoorLockRl;
    private RelativeLayout mCctvRl;
    private RelativeLayout mCurtainRl;
    private RelativeLayout mClubhouseRl;
    private RelativeLayout mLoginRl;
    private RelativeLayout mRegisterRl;
    private RelativeLayout mBingingRl;
    private RelativeLayout mEnglishRl;
    private RelativeLayout mTraditionalRl;
    private RelativeLayout mCnRl;
    private RelativeLayout mLoginOutRl;
    private RelativeLayout mAboutRl;
    private DrawerLayout mDrawerLayout;
    private ImageView mTitleUserIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, UIUtil.getColor(R.color.color_A37A43), 5);

        initView();
        initListener();
        initData();
        setDrawerLeftEdgeSize(MainActivity.this, mDrawerLayout, 1);
        BannerView bannerView = (BannerView) findViewById(R.id.banner_view);
        List<Integer> bannerList = new ArrayList<>();
        for (int i = 0; i < BANNER.length; i++) {
            bannerList.add(BANNER[i]);
        }

        bannerView.setPages(bannerList, new HolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        bannerView.start();
    }

    private void initData() {
        LinearLayout menu = (LinearLayout) findViewById(R.id.slide_menu);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int windowsWidth = metrics.widthPixels;
        int windowsHeight = metrics.heightPixels;
        ViewGroup.LayoutParams params = menu.getLayoutParams();
        params.height = windowsHeight;
        params.width = windowsWidth;
        menu.setLayoutParams(params);

        AtSmarthomeInterface.setDataUpCallBack(this);
        LogUtil.e("---" + AtSipKitEnv.getDeviceAccount(MainActivity.this));
    }

    private void initListener() {
        mTitleUserIv.setOnClickListener(this);
        mSceneRl.setOnClickListener(this);
        mTelevisionRl.setOnClickListener(this);
        mLightingRl.setOnClickListener(this);
        mAirConditioningRl.setOnClickListener(this);
        mIntercomRl.setOnClickListener(this);
        mDoorLockRl.setOnClickListener(this);
        mCctvRl.setOnClickListener(this);
        mCurtainRl.setOnClickListener(this);
        mClubhouseRl.setOnClickListener(this);
        mLoginRl.setOnClickListener(this);
        mRegisterRl.setOnClickListener(this);
        mBingingRl.setOnClickListener(this);
        mEnglishRl.setOnClickListener(this);
        mTraditionalRl.setOnClickListener(this);
        mCnRl.setOnClickListener(this);
        mLoginOutRl.setOnClickListener(this);
        mAboutRl.setOnClickListener(this);
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        mTitleUserIv = (ImageView) findViewById(R.id.iv_home_title_user);
        mSceneRl = (RelativeLayout) findViewById(R.id.rl_scene);
        mTelevisionRl = (RelativeLayout) findViewById(R.id.rl_television);
        mLightingRl = (RelativeLayout) findViewById(R.id.rl_lighting);
        mIntercomRl = (RelativeLayout) findViewById(R.id.rl_intercom);
        mDoorLockRl = (RelativeLayout) findViewById(R.id.rl_door_lock);
        mAirConditioningRl = (RelativeLayout) findViewById(R.id.rl_air_conditioning);
        mCctvRl = (RelativeLayout) findViewById(R.id.rl_cctv);
        mCurtainRl = (RelativeLayout) findViewById(R.id.rl_curtain);
        mClubhouseRl = (RelativeLayout) findViewById(R.id.rl_clubhouse);
        mLoginRl = (RelativeLayout) findViewById(R.id.rl_login);
        mRegisterRl = (RelativeLayout) findViewById(R.id.rl_register);
        mBingingRl = (RelativeLayout) findViewById(R.id.rl_binging);
        mEnglishRl = (RelativeLayout) findViewById(R.id.rl_english);
        mTraditionalRl = (RelativeLayout) findViewById(R.id.rl_traditional);
        mCnRl = (RelativeLayout) findViewById(R.id.rl_cn);
        mLoginOutRl = (RelativeLayout) findViewById(R.id.rl_login_out);
        mAboutRl = (RelativeLayout) findViewById(R.id.rl_about);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalManageUtil.setLocal(newBase));
    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(this, select);
        MainActivity.reStart(this);
    }


    public static void reStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_home_title_user:
                mDrawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.rl_scene:

                break;
            case R.id.rl_television:
                startActivity(new Intent(MainActivity.this, TvControlActivity.class));
                break;
            case R.id.rl_lighting:
                startActivity(new Intent(MainActivity.this, LightControlActivity.class));
                break;
            case R.id.rl_air_conditioning:
                startActivity(new Intent(MainActivity.this, AirConditionerControlActivity.class));
                break;
            case R.id.rl_intercom:

                break;
            case R.id.rl_door_lock:
                startActivity(new Intent(MainActivity.this, DoorLockControlActivity.class));
                break;
            case R.id.rl_cctv:
                // TODO 18/11/23
                break;
            case R.id.rl_curtain:
                startActivity(new Intent(MainActivity.this, CurtainControlActivity.class));
                break;
            case R.id.rl_clubhouse:
                // TODO 18/11/23
                break;
            case R.id.rl_login:
                // TODO 18/11/23
                break;
            case R.id.rl_register:
                // TODO 18/11/23
                break;
            case R.id.rl_binging:
                // TODO 18/11/23
                break;
            case R.id.rl_english:
                selectLanguage(3);
                break;
            case R.id.rl_traditional:
                selectLanguage(2);
                break;
            case R.id.rl_cn:
                selectLanguage(1);
                break;
            case R.id.rl_login_out:
                // TODO 18/11/23
                break;
            case R.id.rl_about:
                // TODO 18/11/23
                break;
            default:
                break;
        }
    }

    //查询待绑定的设备
    private void queryUnBindingDevice() {
        try {
            // {"msg_type":"device_manager","from_role":"各自的角色","from_account":"当前设备唯一标识",
            // "command":"query"}
            JSONObject jsonO = new JSONObject();
            jsonO.put("cmd", "get_unhandle_friend");
            jsonO.put("offset", 0);
            jsonO.put("total", 100);
            LogUtil.e("参数:" + jsonO.toString());
            AtSmarthomeInterface.sendCmdToServer(jsonO);
        } catch (Exception e) {

        }
    }


    /**
     * 查询设备
     */
    private void queryAllDevice(String fromAccount) {
        try {
            // {"msg_type":"device_manager","from_role":"各自的角色","from_account":"当前设备唯一标识",
            // "command":"query"}
            JSONObject jsonO = new JSONObject();
            jsonO.put("msg_type", "device_manager");
            jsonO.put("command", "query");
            jsonO.put("from_role", "phone");
            jsonO.put("from_account", fromAccount);
            LogUtil.e("参数:" + jsonO.toString());
            AtSmarthomeInterface.sendControlCmdToDevice(jsonO);
        } catch (Exception e) {

        }
    }

    @Override
    public void atSmarthomeBackCall(JSONObject jsonObject) {
        LogUtil.e("返回结果:" + jsonObject.toString());
        try {
            if (jsonObject.has("msg_type")) {
                String msg_type = jsonObject.getString("msg_type");
            } else if (jsonObject.has("cmd")) {
                String cmd = jsonObject.getString("cmd");
                String result = jsonObject.getString("result");
                if (result.equals("success")) {
                    if (cmd.equals("get_unhandle_friend")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("usr");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonArrayJSONObject = jsonArray.getJSONObject(i);
                                LogUtil.e("--:" + jsonArrayJSONObject.toString());
                                String friend = jsonArrayJSONObject.getString("friend");
                                String friendName = jsonArrayJSONObject.getString("friend_name");
                                String type = jsonArrayJSONObject.getString("type");
                                JSONObject jsonO = new JSONObject();
                                jsonO.put("cmd", "add_friend");
                                jsonO.put("to_username", friend);
                                jsonO.put("friend_name", friendName);
                                jsonO.put("msg", "室内机");
                                LogUtil.e("参数:" + jsonO.toString());
                                AtSmarthomeInterface.sendCmdToServer(jsonO);
                            }
                        }
                    } else if (cmd.equals("add_friend")) {
                        String toUsername = jsonObject.getString("to_username");
                        queryAllDevice(toUsername);
                    } else if (cmd.equals("get_allfriend")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("usr");
                        if (jsonArray != null && jsonArray.length() > 0) {//绑定了设备
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonArrayJSONObject = jsonArray.getJSONObject(i);
                                String friend = jsonArrayJSONObject.getString("friend");
                                queryAllDevice(friend);
                            }
                        }else{//查询未绑定的设备
                            queryUnBindingDevice();
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class BannerViewHolder implements ViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data, View view) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    private void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");//Right
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x *
                    displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
    }
}

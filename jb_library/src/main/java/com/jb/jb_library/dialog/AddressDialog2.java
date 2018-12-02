package com.jb.jb_library.dialog;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jb.jb_library.R;
import com.jb.jb_library.adapter.SuperBaseAdapter;
import com.jb.jb_library.address.CityModel;
import com.jb.jb_library.address.DistrictModel;
import com.jb.jb_library.address.ProvinceModel;
import com.jb.jb_library.address.XmlParserHandler;
import com.jb.jb_library.holder.BaseHolder;
import com.jb.jb_library.util.LayoutUtil;
import com.jb.jb_library.util.ScreenSizeUtil;
import com.jb.jb_library.util.ToastUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/20 8:54
 * @描述： ${TODO} 仿京东地址选择器
 */

public class AddressDialog2 extends Dialog implements AdapterView.OnItemClickListener, View.OnClickListener {
    private Context         mContext;
    private ProvinceAdapter mProvinceAdapter;
    private boolean mIsProvince = true;
    private boolean             mIsCity;
    private boolean             mIsArea;
    private String              mProvince;
    private String              mCity;
    private TextView            mCancleTv;
    private TextView            mEnsureTv;
    private TextView            mTabProvinceTv;
    private TextView            mTabCityTv;
    private TextView            mTabAreaTv;
    private ListView            mListView;
    private List<ProvinceModel> mProvinceList;
    private List<CityModel>     mCityList;
    private List<ProvinceModel> mProvinceDatas;
    private List<CityModel>     mCityDatas;
    private List<DistrictModel> mDistrictDatas;
    private CityAdapter         mCityAdapter;
    private AreaAdapter         mAreaAdapter;
    private View                mIndicator;
    private View                mView;
    private int RPOVINCE_TAB         = 1;
    private int CITY_TAB             = 2;
    private int AREA_TAB             = 3;
    private int mClickTab            = RPOVINCE_TAB;
    private int mSelectProvinceIndex = -1;
    private int mSelectCityIndex     = -1;
    private int mSelectAreaIndex     = -1;


    public AddressDialog2(Context context) {
        this(context, R.style.BottomDialogStyle);
    }

    public AddressDialog2(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mView = LayoutUtil.inflate(R.layout.address_dialog2);
        setContentView(mView);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        ScreenSizeUtil instance = ScreenSizeUtil.getInstance(mContext);
        //        lp.width = instance.getScreenWidth() * 4 / 5;

        lp.height = instance.getScreenHeight() * 4 / 7;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dismiss();
            }
        });

        initView();
        initAddressData();
        initData();
        updateTabState();
        updataIndicatorState();
    }

    private void initView() {
        mIndicator = mView.findViewById(R.id.indicator);
        mCancleTv = (TextView) mView.findViewById(R.id.address_cancle_tv);
        mEnsureTv = (TextView) mView.findViewById(R.id.address_ensure_tv);
        mTabProvinceTv = (TextView) mView.findViewById(R.id.tab_province_tv);
        mTabCityTv = (TextView) mView.findViewById(R.id.tab_city_tv);
        mTabAreaTv = (TextView) mView.findViewById(R.id.tab_area_tv);
        mListView = (ListView) mView.findViewById(R.id.address_listview);

        mTabProvinceTv.setOnClickListener(this);
        mTabCityTv.setOnClickListener(this);
        mTabAreaTv.setOnClickListener(this);
    }

    private void updateTabState() {
        if (mProvinceDatas.size() > 0)
            mTabProvinceTv.setVisibility(View.VISIBLE);
        else
            mTabProvinceTv.setVisibility(View.GONE);

        if (mCityDatas.size() > 0)
            mTabCityTv.setVisibility(View.VISIBLE);
        else
            mTabCityTv.setVisibility(View.GONE);

        if (mDistrictDatas.size() > 0)
            mTabAreaTv.setVisibility(View.VISIBLE);
        else
            mTabAreaTv.setVisibility(View.GONE);

        mTabProvinceTv.setEnabled(mClickTab != RPOVINCE_TAB);
        mTabCityTv.setEnabled(mClickTab != CITY_TAB);
        mTabAreaTv.setEnabled(mClickTab != AREA_TAB);
    }

    private void updataIndicatorState() {
        //View获得当前线程（即UI线程）的Handler,来更新UI
        mView.post(new Runnable() {
            @Override
            public void run() {
                if (mClickTab == RPOVINCE_TAB) {
                    buildIndicatorAnimatorTowards(mTabProvinceTv).start();
                } else if (mClickTab == CITY_TAB) {
                    buildIndicatorAnimatorTowards(mTabCityTv).start();
                } else if (mClickTab == AREA_TAB) {
                    buildIndicatorAnimatorTowards(mTabAreaTv).start();
                }
            }
        });
    }

    private void initAddressData() {
        AssetManager asset = mContext.getAssets();
        try {
            InputStream input = asset.open("address.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            mProvinceList = handler.getDataList();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mProvinceDatas = new ArrayList<>();
        mCityDatas = new ArrayList<>();
        mDistrictDatas = new ArrayList<>();

        mProvinceDatas.addAll(mProvinceList);
        mProvinceAdapter = new ProvinceAdapter(mProvinceDatas);
        mCityAdapter = new CityAdapter(mCityDatas);
        mAreaAdapter = new AreaAdapter(mDistrictDatas);

        mListView.setAdapter(mProvinceAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (mIsProvince) { //默认是省
            mSelectProvinceIndex = position;
            mSelectCityIndex = -1;
            mSelectAreaIndex = -1;
            mTabCityTv.setText("请选择");
            mDistrictDatas.clear();

            mProvinceAdapter.notifyDataSetChanged();
            mCityDatas.clear();
            ProvinceModel provinceModel = mProvinceList.get(position);
            mProvince = provinceModel.getName();
            mCityList = provinceModel.getCityList();
            mCityDatas.addAll(mCityList);
            mListView.setAdapter(mCityAdapter);

            mIsProvince = false;
            mIsCity = true;
            mClickTab = CITY_TAB;
            mTabProvinceTv.setText(mProvince);
            updateTabState();
            updataIndicatorState();
        } else if (mIsCity) {
            mSelectCityIndex = position;
            mSelectAreaIndex = -1;
            mTabAreaTv.setText("请选择");

            mCityAdapter.notifyDataSetChanged();
            mDistrictDatas.clear();
            CityModel cityModel = mCityDatas.get(position);
            mCity = cityModel.getName();
            List<DistrictModel> districtList = cityModel.getDistrictList();
            mDistrictDatas.addAll(districtList);
            mListView.setAdapter(mAreaAdapter);

            mIsCity = false;
            mIsArea = true;
            mClickTab = AREA_TAB;
            mTabCityTv.setText(mCity);
            updateTabState();
            updataIndicatorState();
        } else {
            mSelectAreaIndex = position;

            mAreaAdapter.notifyDataSetChanged();
            String area = mDistrictDatas.get(position).getName();
            mTabAreaTv.setText(area);
            ToastUtil.showToast(mProvince + mCity + area);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tab_province_tv) {
            mIsProvince = true;
            mIsCity = false;
            mIsArea = false;
            mListView.setAdapter(mProvinceAdapter);
            mClickTab = RPOVINCE_TAB;
            updateTabState();
            updataIndicatorState();
            mListView.setSelection(mSelectProvinceIndex);
        } else if (view.getId() == R.id.tab_city_tv) {
            mIsProvince = false;
            mIsCity = true;
            mIsArea = false;

            mListView.setAdapter(mCityAdapter);
            mClickTab = CITY_TAB;
            updateTabState();
            updataIndicatorState();
            mListView.setSelection(mSelectCityIndex);
        } else if (view.getId() == R.id.tab_area_tv) {
            mIsProvince = false;
            mIsCity = false;
            mIsArea = true;

            mListView.setAdapter(mAreaAdapter);
            mClickTab = AREA_TAB;
            updateTabState();
            updataIndicatorState();
            mListView.setSelection(mSelectAreaIndex);
        }
    }


    private class ProvinceAdapter extends SuperBaseAdapter<ProvinceModel> {

        public ProvinceAdapter(List<ProvinceModel> datas) {
            super(datas);
        }

        @Override
        protected BaseHolder getSpecialHolder() {
            return new ProvinceHolder();
        }
    }


    private class ProvinceHolder extends BaseHolder<ProvinceModel> {

        private TextView  mTv;
        private ImageView mCheckMarkIv;

        @Override
        public View initHolderView() {
            View view = LayoutUtil.inflate(R.layout.item_address_dialog2);
            mTv = (TextView) view.findViewById(R.id.textView);
            mCheckMarkIv = (ImageView) view.findViewById(R.id.check_mark_iv);
            return view;
        }

        @Override
        public void setDataAndRefreshUI(ProvinceModel data, int position) {
            mTv.setText(data.getName());
            if (mSelectProvinceIndex != -1) {
                boolean checked = mProvinceDatas.get(mSelectProvinceIndex).getProviceId() == mProvinceDatas.get(position).getProviceId() ? true : false;

                if (checked) {
                    mCheckMarkIv.setVisibility(View.VISIBLE);
                    mTv.setEnabled(false);
                } else {
                    mCheckMarkIv.setVisibility(View.GONE);
                    mTv.setEnabled(true);
                }
            } else {
                mCheckMarkIv.setVisibility(View.GONE);
            }
        }
    }

    private class CityAdapter extends SuperBaseAdapter<CityModel> {

        public CityAdapter(List<CityModel> datas) {
            super(datas);
        }

        @Override
        protected BaseHolder getSpecialHolder() {
            return new CityHolder();
        }
    }


    private class CityHolder extends BaseHolder<CityModel> {

        private TextView  mTv;
        private ImageView mCheckMarkIv;

        @Override
        public View initHolderView() {
            View view = LayoutUtil.inflate(R.layout.item_address_dialog2);
            mTv = (TextView) view.findViewById(R.id.textView);
            mCheckMarkIv = (ImageView) view.findViewById(R.id.check_mark_iv);
            return view;
        }

        @Override
        public void setDataAndRefreshUI(CityModel data, int position) {
            mTv.setText(data.getName());
            if (mSelectCityIndex != -1) {
                boolean checked = mCityDatas.get(mSelectCityIndex).getCityId() == mCityDatas.get(position).getCityId() ? true : false;

                if (checked) {
                    mCheckMarkIv.setVisibility(View.VISIBLE);
                    mTv.setEnabled(false);
                } else {
                    mCheckMarkIv.setVisibility(View.GONE);
                    mTv.setEnabled(true);
                }
            } else {
                mCheckMarkIv.setVisibility(View.GONE);
            }
        }
    }

    private class AreaAdapter extends SuperBaseAdapter<DistrictModel> {

        public AreaAdapter(List<DistrictModel> datas) {
            super(datas);
        }

        @Override
        protected BaseHolder getSpecialHolder() {
            return new AreaHolder();
        }
    }


    private class AreaHolder extends BaseHolder<DistrictModel> {

        private TextView  mTv;
        private ImageView mCheckMarkIv;

        @Override
        public View initHolderView() {
            View view = LayoutUtil.inflate(R.layout.item_address_dialog2);
            mTv = (TextView) view.findViewById(R.id.textView);
            mCheckMarkIv = (ImageView) view.findViewById(R.id.check_mark_iv);
            return view;
        }

        @Override
        public void setDataAndRefreshUI(DistrictModel data, int position) {
            mTv.setText(data.getName());
            if (mSelectAreaIndex != -1) {
                boolean checked = mDistrictDatas.get(mSelectAreaIndex).getDistrictId() == mDistrictDatas.get(position).getDistrictId() ? true : false;
                if (checked) {
                    mCheckMarkIv.setVisibility(View.VISIBLE);
                    mTv.setEnabled(false);
                } else {
                    mCheckMarkIv.setVisibility(View.GONE);
                    mTv.setEnabled(true);
                }
            } else {
                mCheckMarkIv.setVisibility(View.GONE);
            }
        }
    }

    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(mIndicator, "X", mIndicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = mIndicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                mIndicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }


    private SelectFinishListener mListener;

    public void setSelectFinishListener(SelectFinishListener listener) {
        this.mListener = listener;
    }

    private interface SelectFinishListener {
        void setSelectAddressData(String addressData);
    }
}

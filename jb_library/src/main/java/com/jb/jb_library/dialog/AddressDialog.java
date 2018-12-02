package com.jb.jb_library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.jb.jb_library.R;
import com.jb.jb_library.adapter.BaseRefreshRecycleViewAdapter;
import com.jb.jb_library.address.CityModel;
import com.jb.jb_library.address.DistrictModel;
import com.jb.jb_library.address.ProvinceModel;
import com.jb.jb_library.address.XmlParserHandler;
import com.jb.jb_library.decoration.DividerItemDecoration;
import com.jb.jb_library.holder.RecycleCommonViewHolder;
import com.jb.jb_library.intf.OnItemClickListener;
import com.jb.jb_library.util.ScreenSizeUtil;
import com.jb.jb_library.util.ToastUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * @创建者： zhangbo
 * @创建时间： 2016/10/20 8:54
 * @描述： ${TODO} 地址选址器
 */

public class AddressDialog extends Dialog {

    private RecyclerView mRecyclerView;
    private Context      mContext;
    /**
     * key - 省 value - 市s
     */
    private Map<String, List<String>> mCitisDatasMap = new HashMap<String, List<String>>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, List<String>> mAreaDatasMap  = new HashMap<String, List<String>>();
    private List<String> mAreaDatas;
    private List<String> mCityDatas;
    private List<String> mProvinceDatas;
    private List<String> mDatas = new ArrayList();
    private AddressAdapterRefresh mAddressAdapter;
    private boolean mIsProvince = true;
    private boolean mIsCity;
    private boolean mIsArea;
    private List<String> mAreaList;
    private List<String> mCityList;
    private String mProvince;
    private String mCity;



    public AddressDialog(Context context) {
        this(context, R.style.DialogStyle);
    }

    public AddressDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_dialog);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        ScreenSizeUtil instance = ScreenSizeUtil.getInstance(mContext);
        lp.width = instance.getScreenWidth() * 4 / 5;
        lp.height = instance.getScreenHeight() * 7 / 10;

        lp.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(lp);
        mRecyclerView = (RecyclerView) findViewById(R.id.address_dialog_rv);

        initAddressData();
        initData();
    }



    private void initAddressData() {
        mProvinceDatas = new ArrayList<>();
        List<ProvinceModel> provinceList = null;
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
            provinceList = handler.getDataList();

            for (int i = 0; i < provinceList.size(); i++) {
                String province = provinceList.get(i).getName();
                mProvinceDatas.add(province);
                List<CityModel> cityList = provinceList.get(i).getCityList();
                mCityDatas = new ArrayList<>();
                for (int j = 0; j < cityList.size(); j++) {
                    CityModel cityModel = cityList.get(j);
                    String city = cityModel.getName();
                    mCityDatas.add(city);
                    List<DistrictModel> districtList = cityModel.getDistrictList();
                    mAreaDatas = new ArrayList<>();
                    for (int k = 0; k < districtList.size(); k++) {
                        DistrictModel districtModel = districtList.get(k);
                        String area = districtModel.getName();
                        mAreaDatas.add(area);
                    }

                    mAreaDatasMap.put(city, mAreaDatas);
                }

                mCitisDatasMap.put(province,mCityDatas);
            }
        }catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        mDatas.addAll(mProvinceDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST));
        mAddressAdapter = new AddressAdapterRefresh(mContext, mRecyclerView, mDatas, R.layout.item_address_dialog, null);
        mRecyclerView.setAdapter(mAddressAdapter);
    }

    @Override
    public void onBackPressed() {
        if(mIsArea){
            mDatas.clear();
            mDatas.addAll(mCityList);
            mAddressAdapter.notifyDataSetChanged();
            mIsArea = false;
            mIsCity = true;
        }else if(mIsCity){
            mDatas.clear();
            mDatas.addAll(mProvinceDatas);
            mAddressAdapter.notifyDataSetChanged();
            mIsCity = false;
            mIsProvince = true;
        }else if(mIsProvince){
            dismiss();
        }
    }

    private class AddressAdapterRefresh extends BaseRefreshRecycleViewAdapter<String> {

        public AddressAdapterRefresh(Context context, RecyclerView recyclerView, List<String> datas, int layoutId, SwipeRefreshLayout refreshLayout) {
            super(context, recyclerView, datas, layoutId, refreshLayout);
        }

        @Override
        public void convert(RecycleCommonViewHolder holder, String s) {
            holder.setText(R.id.item_address_tv, s);
            holder.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(int position) {
                    if(mIsProvince){
                        String province = mProvinceDatas.get(position);
                        mProvince = province;
                        mCityList = mCitisDatasMap.get(province);
                        mDatas.clear();
                        mDatas.addAll(mCityList);
                        mAddressAdapter.notifyDataSetChanged();
                        mIsProvince = false;
                        mIsCity = true;
                    }else if(mIsCity){
                        String city = mDatas.get(position);
                        mCity = city;
                        mAreaList = mAreaDatasMap.get(city);
                        mDatas.clear();
                        mDatas.addAll(mAreaList);
                        mAddressAdapter.notifyDataSetChanged();
                        mIsCity = false;
                        mIsArea = true;
                    }else if(mIsArea){
                        String area = mDatas.get(position);
                        ToastUtil.showToast(mProvince + mCity +area);
                        dismiss();
                    }

                }
            });
        }
    }

    private SelectFinishListener mListener;
    public void setSelectFinishListener(SelectFinishListener listener){
        this.mListener = listener;
    }

    private interface SelectFinishListener{
        void setSelectAddressData(String addressData);
    }
}

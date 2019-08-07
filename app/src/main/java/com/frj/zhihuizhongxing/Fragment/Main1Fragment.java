package com.frj.zhihuizhongxing.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.flyco.tablayout.SlidingTabLayout;
import com.frj.zhihuizhongxing.Base.BaseMvpFragment;
import com.frj.zhihuizhongxing.Data.AdPageInfo;
import com.frj.zhihuizhongxing.R;
import com.jorge.circlelibrary.ImageCycleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main1Fragment extends BaseMvpFragment {


    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.cycleView)
    ImageCycleView cycleView;
    @BindView(R.id.ll_lunbo)
    LinearLayout llLunbo;
    Unbinder unbinder;

    public Main1Fragment() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main1;
    }

    @Override
    protected void initView(View view) {
        llLunbo.getLayoutParams().height= (int) (getResources().getDisplayMetrics().heightPixels/3.5);
        Log.d("提示",getResources().getDisplayMetrics().heightPixels+"");
        initViewPage();
        initLunBo();
    }

    private void initViewPage() {
        Main1ViewPager main1ViewPager = new Main1ViewPager(getFragmentManager());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(main1ViewPager);
        tabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    private void initLunBo() {

        cycleView.setCycle_T(ImageCycleView.CYCLE_T.CYCLE_VIEW_ZOOM_IN);
        List<AdPageInfo> adPageInfos = new ArrayList<>();
        AdPageInfo adPageInfo1 = new AdPageInfo("测试", "http://pic1.win4000.com/wallpaper/2019-07-23/5d36b72c9d13d.jpg", "", 1);
        adPageInfos.add(adPageInfo1);
        AdPageInfo adPageInfo2 = new AdPageInfo("测试", "http://pic1.win4000.com/wallpaper/2019-07-23/5d36b72e84938.jpg", "", 1);
        adPageInfos.add(adPageInfo2);
        AdPageInfo adPageInfo3 = new AdPageInfo("测试", "http://pic1.win4000.com/wallpaper/2019-07-23/5d36b73073d7b.jpg", "", 1);
        adPageInfos.add(adPageInfo3);
        initCarsuelView(null, adPageInfos, cycleView);
    }


    @Override
    public void onError(Throwable throwable) {

    }

    public void setViewPageItem(int i) {
        if (viewPager != null) {
            viewPager.setCurrentItem(i);
        }
    }

    /**
     * 初始化轮播图
     */
    public void initCarsuelView(ArrayList<String> imageDescList, List<AdPageInfo> urlList, ImageCycleView imageCycleView) {
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                /**实现点击事件*/
                //Toast.makeText(this,"position="+position,Toast.LENGTH_SHORT).show();
                //openAdv(.getClickUlr());


            }

            @Override
            public void displayImage(String imageURL, ImageView mImageView, int position) {
                switch (position) {
                    case 0:
                        mImageView.setImageResource(R.drawable.baner1);
                        break;
                    case 1:
                        mImageView.setImageResource(R.drawable.baner2);
                        break;
                    case 2:
                        mImageView.setImageResource(R.drawable.baner3);
                        break;
                }
                //RoundImageView roundImageView=new RoundImageView(videoDataFragment.getContext());
                /**在此方法中，显示图片，可以用自己的图片加载库，也可以用本demo中的（Imageloader）*/
                //ImageLoaderHelper.getInstance().loadImage(imageURL, imageView);
                //Glide.with(getActivity()).load(imageURL).into(imageView);
            }
        };
        //List<String> strings=new ArrayList<>();
        ArrayList<String> strings = new ArrayList<String>();
        imageDescList = new ArrayList<>();
        for (AdPageInfo ad : urlList) {
            strings.add(ad.getPicUrl());
            imageDescList.add(ad.getTitle());
        }

        /**设置数据*/
        imageCycleView.setImageResources(imageDescList, strings, mAdCycleViewListener);
        // 是否隐藏底部
        imageCycleView.hideBottom(true);
        imageCycleView.startImageCycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    class Main1ViewPager extends FragmentPagerAdapter {
        private final String[] TITLES = {"待办任务", "发起任务"};
        private Fragment[] fragments;

        public Main1ViewPager(FragmentManager fm) {
            super(fm);
            fragments = new Fragment[TITLES.length];
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments[position] == null) {
                switch (position) {
                    case 0:
                        fragments[position] = new Main1undoneFragment();
                        break;
                    case 1:
                        fragments[position] = new Main1FinishFragment(Main1Fragment.this);
                        break;
                    default:
                        break;
                }
            }

            return fragments[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }

}

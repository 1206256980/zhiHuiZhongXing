package com.frj.zhihuizhongxing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.frj.zhihuizhongxing.Base.BaseMapActivity;
import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.Contract.MainContract;
import com.frj.zhihuizhongxing.Control.FastCatBar;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.Data.BaseBean;
import com.frj.zhihuizhongxing.Data.MainBean;
import com.frj.zhihuizhongxing.Data.UserBean;
import com.frj.zhihuizhongxing.Fragment.Main1Fragment;
import com.frj.zhihuizhongxing.Fragment.Main2Fragment;
import com.frj.zhihuizhongxing.Fragment.Main3Fragment;
import com.frj.zhihuizhongxing.Fragment.Main4Fragment;
import com.frj.zhihuizhongxing.Presenter.MainPresenter;
import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Web.RetrofitClient;
import com.frj.zhihuizhongxing.Web.RxScheduler;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseMapActivity<MainPresenter> implements MainContract.View, BaseMapActivity.GetMapPostion {

    Fragment curFragment;
    @BindView(R.id.fragment_main)
    FrameLayout fragmentMain;
    @BindView(R.id.fastcat)
    public FastCatBar fastcat;

    Main1Fragment main1Fragment;
    Main2Fragment main2Fragment;
    Main3Fragment main3Fragment;
    Main4Fragment main4Fragment;

    private String TAG = "MainActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    Disposable disposable;
    MainActivity mainActivity;

    @Override
    public void initView() {
        presenter = new MainPresenter(this);
        setSelectedItem(FastCatBar.FCB_ITEM_VIDEO);
        mainActivity = this;
        fastcat.setOnSelectedItemChanged(new FastCatBar.OnSelectedItemChanged() {
            @Override
            public void onChange(int itemIndex) {
                setSelectedItem(itemIndex);
            }
        });
        presenter.userInfo();

        setGetMapPostion(MainActivity.this);
        if (disposable != null) {
            disposable.dispose();
        }

        disposable = Observable.interval(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (CurrentUser.disposable != null && CurrentUser.disposable != disposable) {
                            CurrentUser.disposable.dispose();
                            Log.d("提示","进入");
                        }
                        Log.d("提示", aLong.intValue() + "///" + disposable + "///" + CurrentUser.disposable);//打印时间
                        CurrentUser.disposable = disposable;
                        startLocaion();
                    }
                });
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        CurrentUser.setIsLogin(false);
        toast("出现异常,请重新登录");
        CurrentUser.logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void setSelectedItem(int fcbItemFind) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (curFragment != null) transaction.hide(curFragment);
        if (fcbItemFind == FastCatBar.FCB_ITEM_VIDEO) {

            if (main1Fragment == null) {
                main1Fragment = new Main1Fragment();
//                videoFragment.setLogo(mLogo);
                transaction.add(R.id.fragment_main, main1Fragment);
            }
            curFragment = main1Fragment;
        } else if (fcbItemFind == FastCatBar.FCB_ITEM_FOLLOW) {
            if (main2Fragment == null) {
                main2Fragment = new Main2Fragment();
                transaction.add(R.id.fragment_main, main2Fragment);
            }
            curFragment = main2Fragment;
        } else if (fcbItemFind == FastCatBar.FCB_ITEM_UPLOAD) {

            if (main3Fragment == null) {
                main3Fragment = new Main3Fragment();
                transaction.add(R.id.fragment_main, main3Fragment);
            }
            curFragment = main3Fragment;
            Log.d("提示///2", "jf");
        } else if (fcbItemFind == FastCatBar.FCB_ITEM_APP) {


            if (main4Fragment == null) {
                main4Fragment = new Main4Fragment();
                transaction.add(R.id.fragment_main, main4Fragment);
            }
            curFragment = main4Fragment;
            Log.d("提示///2", "jf");

        }

        transaction.show(curFragment);
        transaction.commit();
    }


    @Override
    public void onSuccessCoordinate(MainBean mainBean) {

    }

    @Override
    public void onSuccessUserInfo(UserBean userBean) {
        try {

            if (userBean.getIsErr() != 0) {
                CurrentUser.setIsLogin(false);
                toast("登录失效,请重新登录");
                CurrentUser.logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                UserBean.DataBean dataBean = userBean.getData();
                CurrentUser.setIsLogin(true);
                CurrentUser.setAddress(dataBean.getAddress());
                CurrentUser.setBusiness(dataBean.getBusiness());
                CurrentUser.setCompany(dataBean.getCompany());
                CurrentUser.setEmail(dataBean.getEmail());
                CurrentUser.setFace(dataBean.getFace());
                CurrentUser.setName(dataBean.getName());
                CurrentUser.setPhone(dataBean.getPhone());
                CurrentUser.setPolice_no(dataBean.getPolice_no());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpdatePostion(BaseBean baseBean) {

    }

    @Override
    public void UpdatePostion(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void getPosition(double latitude, double longitude) {
        Log.e(TAG, latitude + "//" + longitude);
        CurrentUser.setLatitude(latitude);
        CurrentUser.setLongitude(longitude);
        presenter.updatePostion(latitude, longitude);
    }
}

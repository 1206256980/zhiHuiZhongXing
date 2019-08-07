package com.frj.zhihuizhongxing.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.frj.zhihuizhongxing.Activity.LoginActivity;
import com.frj.zhihuizhongxing.Activity.TaskIntoActivity;
import com.frj.zhihuizhongxing.Base.BaseMvpFragment;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main4Fragment extends BaseMvpFragment {


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.ll_loginout)
    LinearLayout llLoginout;
    @BindView(R.id.iv_photo)
    ImageView ivPhoto;

    public Main4Fragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView(View view) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main4;
    }

    @Override
    public void onError(Throwable throwable) {

    }

    private void setData() {
        tvName.setText(CurrentUser.getName() + "");
        tvUnit.setText(CurrentUser.getCompany() + "");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ico_user_photo);

        tvNumber.setText(CurrentUser.getPolice_no() + "");
        Glide.with(this).load(CurrentUser.getFace()).apply(requestOptions).into(ivPhoto);
       // Glide.with(this).load("http://pic1.win4000.com/tj/2018-12-12/5c10a2826be57.jpg").apply(requestOptions).into(ivPhoto);

    }

    @Override
    public void onStart() {
        super.onStart();
        setData();

    }


    @OnClick(R.id.ll_loginout)
    public void onViewClicked() {
        CurrentUser.logOut();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }


}

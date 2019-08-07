package com.frj.zhihuizhongxing.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.frj.zhihuizhongxing.Base.BaseMvpFragment;
import com.frj.zhihuizhongxing.R;
import com.wildma.pictureselector.PictureSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Main22Fragment extends BaseMvpFragment {

    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.iv_photo5)
    ImageView ivPhoto5;
    @BindView(R.id.iv_photo6)
    ImageView ivPhoto6;
    @BindView(R.id.iv_photo7)
    ImageView ivPhoto7;
    @BindView(R.id.iv_photo8)
    ImageView ivPhoto8;
    Unbinder unbinder;
    private String TAG = "Main2Fragment";

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.iv_photo1)
    ImageView ivPhoto1;
    @BindView(R.id.iv_photo2)
    ImageView ivPhoto2;
    @BindView(R.id.iv_photo3)
    ImageView ivPhoto3;
    @BindView(R.id.iv_photo4)
    ImageView ivPhoto4;

    public Main22Fragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main2;
    }

    @Override
    protected void initView(View view) {
        initAdapter();
    }


    Adapter adapter;

    private void initAdapter() {
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter();
        recycler.setAdapter(adapter);
        List list = new ArrayList();
        list.add(null);
        adapter.setNewData(list);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }


    @OnClick({R.id.iv_photo1, R.id.iv_photo2, R.id.iv_photo3, R.id.iv_photo4, R.id.iv_photo5, R.id.iv_photo6, R.id.iv_photo7, R.id.iv_photo8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo1:
                selectPhoto(1);
                break;
            case R.id.iv_photo2:
                selectPhoto(2);
                break;
            case R.id.iv_photo3:
                selectPhoto(3);
                break;
            case R.id.iv_photo4:
                selectPhoto(4);
            case R.id.iv_photo5:
                selectPhoto(5);
                break;
            case R.id.iv_photo6:
                selectPhoto(6);
                break;
            case R.id.iv_photo7:
                selectPhoto(7);
                break;
            case R.id.iv_photo8:
                selectPhoto(8);
                break;
        }
    }

    int postiton;

    private void selectPhoto(int i) {
        try {
            PictureSelector
                    .create(Main22Fragment.this, PictureSelector.SELECT_REQUEST_CODE)
                    .selectPicture(false, 0, 0, 0, 0);
            postiton = i;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPhoto(String picturePath) {
        ImageView imageView = new ImageView(getContext());
        switch (postiton) {
            case 1:
                imageView = ivPhoto1;
                break;
            case 2:
                imageView = ivPhoto2;
                break;
            case 3:
                imageView = ivPhoto3;
                break;
            case 4:
                imageView = ivPhoto4;
                break;
            case 5:
                imageView = ivPhoto5;
                break;
            case 6:
                imageView = ivPhoto6;
                break;
            case 7:
                imageView = ivPhoto7;
                break;
            case 8:
                imageView = ivPhoto8;
                break;
            default:
                break;
        }

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        Glide.with(this).load(picturePath).apply(requestOptions).into(imageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
                if (data != null) {
                    String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                    Log.e(TAG, picturePath);
                    loadPhoto(picturePath);
                }
            }
        }
    }

//获得照片的输出保存Uri


    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");

        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }



    /*@OnClick({R.id.iv_photo5, R.id.iv_photo6, R.id.iv_photo7, R.id.iv_photo8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_photo5:
                break;
            case R.id.iv_photo6:
                break;
            case R.id.iv_photo7:
                break;
            case R.id.iv_photo8:
                break;
        }
    }*/


    class Adapter extends BaseQuickAdapter {

        public Adapter() {
            super(R.layout.item_main2);
        }

        @Override
        protected void convert(BaseViewHolder helper, Object item) {

        }
    }


}

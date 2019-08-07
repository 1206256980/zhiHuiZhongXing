package com.frj.zhihuizhongxing.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.frj.zhihuizhongxing.Base.BaseMvpActivity;
import com.frj.zhihuizhongxing.Contract.LoginContract;
import com.frj.zhihuizhongxing.CurrentUser;
import com.frj.zhihuizhongxing.Data.LoginBean;
import com.frj.zhihuizhongxing.Presenter.LoginPresenter;
import com.frj.zhihuizhongxing.R;
import com.frj.zhihuizhongxing.Tools.DiaLogUtils;
import com.frj.zhihuizhongxing.Tools.SystemTools;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        quanxian();
        presenter = new LoginPresenter(this);
        /*sHA1(this);*/

    }


    @Override
    public void onError(Throwable throwable) {
        DiaLogUtils.getInstance().cancel();
        throwable.printStackTrace();
        toast("登录失败");
    }

    private static final String TAG = "LoginActivity";

    @Override
    public void onSuccessLogin(LoginBean loginBean) {
        DiaLogUtils.getInstance().cancel();
        if (loginBean.getIsErr() == 0) {
            toast("登录成功");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            toast("账号或密码错误");

        }
    }

    private boolean validateInput(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            toast("请输入账号");
            return false;
        } else if (TextUtils.isEmpty(password)) {
            toast("请输入密码");
            return false;
        }
        return true;
    }

    String getUserName() {
        return etUsername.getText().toString();
    }

    String getPssword() {
        return etPassword.getText().toString();
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        if(!validateInput(getUserName(), getPssword())){
            return;
        }
        DiaLogUtils.getInstance().show(this, false);
        presenter.login(getUserName(), getPssword(), SystemTools.getUniqueId(this));
        //startActivity(new Intent(this,MainActivity.class));
    }


    private void quanxian() {
        AndPermission.with(this)
                .permission(
                        // 多个权限，以数组的形式传入。
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        //init();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Toast.makeText(LoginActivity.this, "请先申请权限", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .start();
    }

}

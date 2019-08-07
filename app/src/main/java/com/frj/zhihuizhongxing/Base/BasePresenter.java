package com.frj.zhihuizhongxing.Base;

/**
 * @author oyt
 * @date 2019/6/11 16:52
 */
public class BasePresenter<V extends BaseView> {
    protected V view;

    /**
     * 绑定view
     * @param view
     */
    public void attachView(V view){
        this.view=view;
    }

    /**
     * 解除绑定view
     */
    public void detachView(){
        this.view=null;
    }

    /**
     * view是否绑定
     * @return
     */
    public boolean isViewAttached(){
        return view!=null;
    }
}

package com.qflbai.lib.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.qflbai.lib.R;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * @author WenXian Bai
 * @Date: 2018/11/2.
 * @Description:
 */
public abstract class BaseFragment extends Fragment {
    private View rootView;

    protected FragmentActivity mActivity;

    private View mContentView;
    private RelativeLayout mContainer;
    // fragment是否显示了
    private boolean mIsVisible;
    private Context mContext;
    /**
     * 加载view
     */
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mActivity = (FragmentActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        rootView = inflater.inflate(R.layout.fragment_base, null);

        mContentView = inflater.inflate(setContentResId(), null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mContentView.setLayoutParams(params);

        mContainer = rootView.findViewById(R.id.container);
        mContainer.addView(mContentView);

        ButterKnife.bind(this, rootView);
        initView(state);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isVis = isHidden() || getUserVisibleHint();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @LayoutRes
    protected abstract int setContentResId();

    /**
     * 初始化views
     *
     * @param state
     */
    protected void initView(Bundle state) {
    }

    ;

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    /**
     * 显示时加载数据,需要这样的使用
     * 注意声明 isPrepared，先初始化
     * 生命周期会先执行 setUserVisibleHint 再执行onActivityCreated
     * 在 onActivityCreated 之后第一次显示加载数据，只加载一次
     */
    protected void loadData() {
    }

    protected void onVisible() {
        loadData();
    }

    /**
     * 加载失败后点击后的操作
     */
    protected void onRefresh() {

    }

    /**
     * 显示加载中状态
     */
    protected void showLoading() {

        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

        if (mLoadingView == null) {
            mLoadingView = ((ViewStub) mContainer.findViewById(R.id.vs_loading)).inflate();

        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示加载中状态
     */
    protected void showLoading(String message) {

        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

        if (mLoadingView == null) {
            mLoadingView = ((ViewStub) mContainer.findViewById(R.id.vs_loading)).inflate();

        } else {
            mLoadingView.setVisibility(View.VISIBLE);
        }
        TextView tvTip = mLoadingView.findViewById(R.id.text_tip);
        tvTip.setText(message);
    }


    /**
     * 加载完成的状态
     */
    protected void showContentView() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mContentView == null) {
            mContentView.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 加载失败点击重新加载的状态
     */
    protected void showError() {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

        if (mErrorView == null) {
            mErrorView = ((ViewStub) mContainer.findViewById(R.id.vs_error_refresh)).inflate();

        } else {
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    protected void showError(boolean clickable, String message) {
        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

        if (mErrorView == null) {
            mErrorView = ((ViewStub) mContainer.findViewById(R.id.vs_error_refresh)).inflate();

        } else {
            mErrorView.setVisibility(View.VISIBLE);
        }

        if (clickable) {
            mErrorView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRefresh();
                }
            });
        }

        TextView tvTip = mLoadingView.findViewById(R.id.text_tip);
        tvTip.setText(message);
    }

    protected void showEmptyView() {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

        if (mEmptyView == null) {
            mEmptyView = ((ViewStub) mContainer.findViewById(R.id.vs_empty)).inflate();

        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    protected void showEmptyView(boolean clickable, String message) {
        if (mErrorView != null) {
            mErrorView.setVisibility(View.GONE);
        }

        if (mLoadingView != null) {
            mLoadingView.setVisibility(View.GONE);
        }

        if (mContentView != null) {
            mContentView.setVisibility(View.INVISIBLE);
        }

        if (mEmptyView == null) {
            mEmptyView = ((ViewStub) mContainer.findViewById(R.id.vs_empty)).inflate();

        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }

        if(clickable){
            mEmptyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }

        TextView tvTip = mEmptyView.findViewById(R.id.tv_tip_empty);
        tvTip.setText(message);

    }


    private CompositeDisposable mCompositeDisposable;

    public void addSubscription(Disposable disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = new CompositeDisposable();
        }
        this.mCompositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.clear();
        }
    }

    public void removeDisposable() {
        if (this.mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            this.mCompositeDisposable.dispose();
        }
    }
}

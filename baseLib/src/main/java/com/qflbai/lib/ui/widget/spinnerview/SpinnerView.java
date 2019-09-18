package com.qflbai.lib.ui.widget.spinnerview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qflbai.lib.R;
import com.qflbai.lib.utils.DensityUtil;

public class SpinnerView extends RelativeLayout implements OnClickListener {

    private TextView mEtInput;
    private ImageView mIvArrow;

    private PopupWindow mWindow;
    private ListAdapter mAdapter;
    private OnItemClickListener mListener;
    private ListView mContentView;
    private Context mContext;
    private RelativeLayout rlInput;
    private OnClickListener mOnClickListener1;

    public SpinnerView(Context context) {
        this(context, null);
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // xml和class 绑定
        View.inflate(context, R.layout.spinner_item, this);

        mEtInput = (TextView) findViewById(R.id.et_input);
        rlInput = findViewById(R.id.rl_input);
        rlInput.setOnClickListener(this);

        mContentView = new ListView(getContext());
    }

    @Override
    public void onClick(View v) {
        if (v == rlInput) {
            if(mOnClickListener1!=null){
                mOnClickListener1.onClick(v);
            }
            if(!isHaveData()){
                return;
            }
            haveData = true;
            clickArrow();
        }
    }

    public void setAdapter(ListAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private void clickArrow() {
        // 点击箭头，需要弹出显示list数据的层

        if (mAdapter == null) {
            throw new RuntimeException("请调用setAapter()去设置数据");
        }


        if (mWindow == null) {
            // contentView：显示的view
            // width height:popup宽和高

            //mContentView = new ListView(getContext());

            // 设置数据
            mContentView.setAdapter(mAdapter);// ---》adapter---》List《数据》

            int width = mEtInput.getWidth();
            //int height = mEtInput.getHeight();

            mWindow = new PopupWindow(mContentView, width, DensityUtil.dip2px(mContext, 260));
            // 设置获取焦点
            mWindow.setFocusable(true);

            // 设置边缘点击收起
            mWindow.setOutsideTouchable(true);
            mWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        }

        // 设置item的点击事件
        mContentView.setOnItemClickListener(mListener);

        mWindow.showAsDropDown(mEtInput);
    }

    public void setText(String data) {
        mEtInput.setText(data);
    }

    public TextView getTextView() {
        return mEtInput;
    }

    public String getText() {
        return mEtInput.getText().toString();
    }


    public void dismiss() {
        if (mWindow != null) {
            mWindow.dismiss();
        }
    }

    public ListView getListView() {
        return mContentView;
    }

    /**
     * 设置提示词
     *
     * @param hintText
     */
    public void setHintText(String hintText) {
        if (mEtInput != null) {
            mEtInput.setHint(hintText);
        }
    }

    /**
     * 设置输入框背景
     *
     * @param background
     */
    public void setInputBackground(int background) {
        if (mEtInput != null) {
            mEtInput.setBackgroundResource(background);
        }
    }

    /**
     * 设置右边图片样式
     *
     * @param drawable
     */
    public void setImageViewForeground(Drawable drawable) {
        if (mIvArrow != null) {
            mIvArrow.setBackground(drawable);
        }
    }

    /**
     * 设置背景
     *
     * @param background
     */
    public void setPopupBackground(int background) {
        if (mContentView != null) {
            mContentView.setBackgroundResource(background);
        }
    }

    private boolean haveData = true;
    public void setHaveData(boolean flag){
        haveData = flag;
    }

    private boolean isHaveData() {
        return haveData;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        mOnClickListener1 = onClickListener;
    }

    public interface OnClickListener {

        void onClick(View v);
    }
}

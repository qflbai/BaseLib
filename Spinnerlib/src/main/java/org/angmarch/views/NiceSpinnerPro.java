package org.angmarch.views;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import java.util.Arrays;
import java.util.List;


/*
 * Copyright (C) 2015 Angelo Marchesin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class NiceSpinnerPro extends RelativeLayout {

    private static final int MAX_LEVEL = 10000;
    private static final int VERTICAL_OFFSET = 1;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";
    private static final String IS_ARROW_HIDDEN = "is_arrow_hidden";
    private static final String ARROW_DRAWABLE_RES_ID = "arrow_drawable_res_id";
    private int selectedIndex;
    private Drawable arrowDrawable;
    private ListPopupWindow popupWindow;
    private NiceSpinnerBaseAdapter adapter;

    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private OnSpinnerItemSelectedListener onSpinnerItemSelectedListener;

    private boolean isArrowHidden;
    private int textColor;
    private int backgroundSelector;
    private int arrowDrawableTint;
    private int displayHeight;
    private int parentVerticalOffset;
    private int dropDownListPaddingBottom;
    private @DrawableRes
    int arrowDrawableResId;
    private SpinnerTextFormatter spinnerTextFormatter = new SimpleSpinnerTextFormatter();
    private SpinnerTextFormatter selectedTextFormatter = new SimpleSpinnerTextFormatter();
    private PopUpTextAlignment horizontalAlignment;

    @Nullable
    private ObjectAnimator arrowAnimator = null;
    public TextView editText;
    public ImageView imageView;


    public NiceSpinnerPro(Context context) {
        super(context);
        init(context, null);
    }

    public NiceSpinnerPro(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NiceSpinnerPro(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, selectedIndex);
        bundle.putBoolean(IS_ARROW_HIDDEN, isArrowHidden);
        bundle.putInt(ARROW_DRAWABLE_RES_ID, arrowDrawableResId);
        if (popupWindow != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, popupWindow.isShowing());
        }
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof Bundle) {
            Bundle bundle = (Bundle) savedState;
            selectedIndex = bundle.getInt(SELECTED_INDEX);
            if (adapter != null) {
                setTextInternal(selectedTextFormatter.format(adapter.getItemInDataset(selectedIndex)).toString());
                adapter.setSelectedIndex(selectedIndex);
            }

            if (bundle.getBoolean(IS_POPUP_SHOWING)) {
                if (popupWindow != null) {
                    // Post the show request into the looper to avoid bad token exception
                    post(this::showDropDown);
                }
            }
            isArrowHidden = bundle.getBoolean(IS_ARROW_HIDDEN, false);
            arrowDrawableResId = bundle.getInt(ARROW_DRAWABLE_RES_ID);
            savedState = bundle.getParcelable(INSTANCE_STATE);
        }
        super.onRestoreInstanceState(savedState);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NiceSpinnerPro);
        boolean isInput = typedArray.getBoolean(R.styleable.NiceSpinnerPro_isInput, false);
        if (isInput) {
            editText = new EditText(context);
        } else {
            editText = new TextView(context);
        }
        imageView = new ImageView(context);

        editText.setId(Integer.MAX_VALUE - 1000);
        imageView.setId(Integer.MAX_VALUE - 2000);


        boolean isWrapContent = typedArray.getBoolean(R.styleable.NiceSpinnerPro_isWrapContent, false);
        if (isWrapContent) {
            RelativeLayout.LayoutParams imageLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams etLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            etLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            etLp.addRule(RelativeLayout.CENTER_IN_PARENT);

            imageLp.addRule(RelativeLayout.RIGHT_OF, editText.getId());
            imageLp.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(editText, etLp);
            addView(imageView, imageLp);
        } else {

            RelativeLayout.LayoutParams imageLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams etLp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            int arrowGravity = typedArray.getInt(R.styleable.NiceSpinnerPro_arrowGravity, 1);
            if (arrowGravity == 0) {
                imageLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                etLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                etLp.addRule(RelativeLayout.RIGHT_OF, imageView.getId());

            } else {
                imageLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                etLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                etLp.addRule(RelativeLayout.LEFT_OF, imageView.getId());
            }

            addView(editText, etLp);
            addView(imageView, imageLp);
        }


        boolean isTextBold = typedArray.getBoolean(R.styleable.NiceSpinnerPro_isTextBold, false);
        if (isTextBold) {
            TextPaint paint = editText.getPaint();
            paint.setFakeBoldText(true);
        }

        int imagePadingLefet = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_imagePadingLeft, 0);
        int imagePadingRight = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_imagePadingRight, 0);
        int imagePadingTop = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_imagePadingTop, 0);
        int imagePadingBottom = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_imagePadingBottom, 0);
        imageView.setPadding(imagePadingLefet, imagePadingTop, imagePadingRight, imagePadingBottom);

        boolean imageClickable = typedArray.getBoolean(R.styleable.NiceSpinnerPro_imageClickable, false);
        int arrowBgDrawable = typedArray.getResourceId(R.styleable.NiceSpinnerPro_arrowBgDrawable, 0);


        if (imageClickable) {
            imageView.setBackgroundResource(arrowBgDrawable);
            imageView.setClickable(true);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter != null) {
                        if (!popupWindow.isShowing() && adapter.getCount() > 0) {
                            showDropDown();
                        } else {
                            dismissDropDown();
                        }
                    }
                }
            });
        } else {
            this.setBackgroundResource(arrowBgDrawable);
            imageView.setClickable(false);
            this.setClickable(true);
            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter != null) {
                        if (!popupWindow.isShowing() && adapter.getCount() > 0) {
                            showDropDown();
                        } else {
                            dismissDropDown();
                        }
                    }
                }
            });
        }

        //设置图片
        arrowDrawableResId = typedArray.getResourceId(R.styleable.NiceSpinnerPro_arrowDrawable, R.drawable.arrow);
        if (arrowDrawableResId != 0) {
            imageView.setImageResource(arrowDrawableResId);
        }


        //int defaultPadding = resources.getDimensionPixelSize(R.dimen.one_and_a_half_grid_unit);
        int defaultPadding = 0;
        int textPadingLeft =
                typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_textPadingLeft, defaultPadding);
        int textPadingRight =
                typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_textPadingRight, defaultPadding);
        int textPadingTop = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_textPadingTop, defaultPadding);
        int textPadingBottom = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_textPadingBottom, defaultPadding);
        editText.setPadding(textPadingLeft, textPadingTop, textPadingRight, textPadingBottom);
        editText.setBackgroundColor(Color.TRANSPARENT);


        //isNoEditView(isInput, editText);
        textColor = typedArray.getColor(R.styleable.NiceSpinnerPro_textColor, getDefaultTextColor(context));
        setTextColor(textColor);

        int textHintColor = typedArray.getColor(R.styleable.NiceSpinnerPro_hint, getDefaultTextColor(context));
        editText.setHintTextColor(textHintColor);

        int textSize = typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_textSize, 0);
        if (textSize != 0) {
            editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        String string = typedArray.getString(R.styleable.NiceSpinnerPro_hint);
        editText.setHint(string);

        String text = typedArray.getString(R.styleable.NiceSpinnerPro_text);
        editText.setText(text);

        popupWindow = new ListPopupWindow(context);
        popupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // The selected item is not displayed within the list, so when the selected position is equal to
                // the one of the currently selected item it gets shifted to the next item.
                /*if (position >= selectedIndex && position < adapter.getCount()) {
                    position++;
                }*/
                selectedIndex = position;

                if (onSpinnerItemSelectedListener != null) {
                    onSpinnerItemSelectedListener.onItemSelected(view, position, id);
                }

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }

                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(parent, view, position, id);
                }

                adapter.setSelectedIndex(position);

                setTextInternal(adapter.getItemInDataset(position));

                dismissDropDown();
            }
        });

        popupWindow.setModal(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (!isArrowHidden) {
                    animateArrow(false);
                }
            }
        });

        isArrowHidden = typedArray.getBoolean(R.styleable.NiceSpinnerPro_hideArrow, false);
        arrowDrawableTint = typedArray.getColor(R.styleable.NiceSpinnerPro_arrowTint, getResources().getColor(android.R.color.black));

        dropDownListPaddingBottom =
                typedArray.getDimensionPixelSize(R.styleable.NiceSpinnerPro_dropDownListPaddingBottom, 0);
        horizontalAlignment = PopUpTextAlignment.fromId(
                typedArray.getInt(R.styleable.NiceSpinnerPro_popupTextAlignment, PopUpTextAlignment.START.ordinal())
        );

        CharSequence[] entries = typedArray.getTextArray(R.styleable.NiceSpinnerPro_entries);
        if (entries != null) {
            attachDataSource(Arrays.asList(entries));
        }

        typedArray.recycle();

        measureDisplayHeight();

    }

    private void isNoEditView(boolean isEdit, EditText view) {
        if (isEdit) {
            view.setFocusableInTouchMode(true);
            view.setFocusable(true);
            view.requestFocus();
            view.setClickable(true);
        } else {
            view.setFocusableInTouchMode(false);
            view.setClickable(false);
            view.setFocusable(false);
            view.setEnabled(false);
            view.clearFocus();
            view.setCursorVisible(false);
        }
    }

    private void setTextColor(int color) {
        editText.setTextColor(color);
    }

    private void measureDisplayHeight() {
        displayHeight = getContext().getResources().getDisplayMetrics().heightPixels;
    }

    private int getParentVerticalOffset() {
        if (parentVerticalOffset > 0) {
            return parentVerticalOffset;
        }
        int[] locationOnScreen = new int[2];
        getLocationOnScreen(locationOnScreen);
        return parentVerticalOffset = locationOnScreen[VERTICAL_OFFSET];
    }

    @Override
    protected void onDetachedFromWindow() {
        if (arrowAnimator != null) {
            arrowAnimator.cancel();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            onVisibilityChanged(this, getVisibility());
        }
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        arrowDrawable = initArrowDrawable(arrowDrawableTint);

    }

    private Drawable initArrowDrawable(int drawableTint) {
        if (arrowDrawableResId == 0) return null;
        Drawable drawable = ContextCompat.getDrawable(getContext(), arrowDrawableResId);
        if (drawable != null) {
            // Gets a copy of this drawable as this is going to be mutated by the animator
            drawable = DrawableCompat.wrap(drawable).mutate();
            if (drawableTint != Integer.MAX_VALUE && drawableTint != 0) {
                DrawableCompat.setTint(drawable, drawableTint);
            }
        }
        return drawable;
    }


    private int getDefaultTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme()
                .resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data,
                new int[]{android.R.attr.textColorPrimary});
        int defaultTextColor = typedArray.getColor(0, Color.BLACK);
        typedArray.recycle();
        return defaultTextColor;
    }

    public Object getItemAtPosition(int position) {
        return adapter.getItemInDataset(position);
    }

    public Object getSelectedItem() {
        return adapter.getItemInDataset(selectedIndex);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setArrowDrawable(@DrawableRes @ColorRes int drawableId) {
        arrowDrawableResId = drawableId;
        arrowDrawable = initArrowDrawable(R.drawable.arrow);
    }

    public void setArrowDrawable(Drawable drawable) {
        arrowDrawable = drawable;
        imageView.setImageDrawable(drawable);
    }

    private void setTextInternal(Object item) {
        if (selectedTextFormatter != null) {
            setText(selectedTextFormatter.format(item));
        } else {
            setText(item.toString());
        }
    }

    public void setText(CharSequence text) {
        editText.setText(text);
    }

    public void setHintText(String hintText) {
        editText.setHint(hintText);
    }

    /**
     * Set the default spinner item using its index
     *
     * @param position the item's position
     */
    public void setSelectedIndex(int position) {
        if (adapter != null) {
            if (position >= 0 && position <= adapter.getCount()) {
                adapter.setSelectedIndex(position);
                selectedIndex = position;
                setTextInternal(selectedTextFormatter.format(adapter.getItemInDataset(position)).toString());
            } else {
                throw new IllegalArgumentException("Position must be lower than adapter count!");
            }
        }
    }


    /**
     * @deprecated use setOnSpinnerItemSelectedListener instead.
     */
    @Deprecated
    public void addOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * @deprecated use setOnSpinnerItemSelectedListener instead.
     */
    @Deprecated
    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public <T> void attachDataSource(@NonNull List<T> list) {
        adapter = new NiceSpinnerAdapter<>(getContext(), list, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment);
        setAdapterInternal(adapter);
    }

    public void setAdapter(ListAdapter adapter) {
        this.adapter = new NiceSpinnerAdapterWrapper(getContext(), adapter, textColor, backgroundSelector,
                spinnerTextFormatter, horizontalAlignment);
        setAdapterInternal(this.adapter);
    }

    public PopUpTextAlignment getPopUpTextAlignment() {
        return horizontalAlignment;
    }

    private <T> void setAdapterInternal(NiceSpinnerBaseAdapter<T> adapter) {
        if (adapter.getCount() >= 0) {
            // If the adapter needs to be set again, ensure to reset the selected index as well
            selectedIndex = 0;
            popupWindow.setAdapter(adapter);
        }
    }

    public boolean isEnabledTouch = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled() && event.getAction() == MotionEvent.ACTION_UP) {
            if (isEnabledTouch) {
                if (adapter != null) {
                    if (!popupWindow.isShowing() && adapter.getCount() > 0) {
                        showDropDown();
                    } else {
                        dismissDropDown();
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        arrowAnimator = ObjectAnimator.ofInt(arrowDrawable, "level", start, end);
        arrowAnimator.setInterpolator(new LinearOutSlowInInterpolator());
        arrowAnimator.start();
    }

    public void dismissDropDown() {
        if (!isArrowHidden) {
            animateArrow(false);
        }
        popupWindow.dismiss();
    }

    public void showDropDown() {
        if (!isArrowHidden) {
            animateArrow(true);
        }
        popupWindow.setAnchorView(this);
        popupWindow.show();
        final ListView listView = popupWindow.getListView();
        if (listView != null) {
            listView.setVerticalScrollBarEnabled(false);
            listView.setHorizontalScrollBarEnabled(false);
            listView.setVerticalFadingEdgeEnabled(false);
            listView.setHorizontalFadingEdgeEnabled(false);
        }
    }


    private int getPopUpHeight() {
        return Math.max(verticalSpaceBelow(), verticalSpaceAbove());
    }

    private int verticalSpaceAbove() {
        return getParentVerticalOffset();
    }

    private int verticalSpaceBelow() {
        return displayHeight - getParentVerticalOffset() - getMeasuredHeight();
    }

    public void setTintColor(@ColorRes int resId) {
        if (arrowDrawable != null && !isArrowHidden) {
            DrawableCompat.setTint(arrowDrawable, ContextCompat.getColor(getContext(), resId));
        }
    }

    public void setArrowTintColor(int resolvedColor) {
        if (arrowDrawable != null && !isArrowHidden) {
            DrawableCompat.setTint(arrowDrawable, resolvedColor);
        }
    }


    public boolean isArrowHidden() {
        return isArrowHidden;
    }

    public void setDropDownListPaddingBottom(int paddingBottom) {
        dropDownListPaddingBottom = paddingBottom;
    }

    public int getDropDownListPaddingBottom() {
        return dropDownListPaddingBottom;
    }

    public void setSpinnerTextFormatter(SpinnerTextFormatter spinnerTextFormatter) {
        this.spinnerTextFormatter = spinnerTextFormatter;
    }

    public void setSelectedTextFormatter(SpinnerTextFormatter textFormatter) {
        this.selectedTextFormatter = textFormatter;
    }


    public void performItemClick(int position, boolean showDropdown) {
        if (showDropdown) showDropDown();
        setSelectedIndex(position);
    }

    /**
     * only applicable when popup is shown .
     *
     * @param view
     * @param position
     * @param id
     */
    public void performItemClick(View view, int position, int id) {
        showDropDown();
        final ListView listView = popupWindow.getListView();
        if (listView != null) {
            listView.performItemClick(view, position, id);
        }
    }

    public OnSpinnerItemSelectedListener getOnSpinnerItemSelectedListener() {
        return onSpinnerItemSelectedListener;
    }

    public void setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener onSpinnerItemSelectedListener) {
        this.onSpinnerItemSelectedListener = onSpinnerItemSelectedListener;
    }


}


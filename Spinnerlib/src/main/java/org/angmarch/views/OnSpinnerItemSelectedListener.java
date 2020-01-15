package org.angmarch.views;

import android.view.View;

public interface OnSpinnerItemSelectedListener {
    void onItemSelected(NiceSpinner parent, View view, int position, long id);
    void onItemSelected( View view, int position, long id);
}

package com.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by baixiaokang on 16/12/23.
 */

public class TMVPFragment extends android.support.design.widget.BottomSheetDialogFragment {
    private static TMVPFragment mTMVPFragment;
    private BottomSheetBehavior mBehavior;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    public static TMVPFragment getInstance() {
        if (mTMVPFragment == null)
            mTMVPFragment = new TMVPFragment();
        return mTMVPFragment;
    }

    public void start(FragmentManager fm) {
        mTMVPFragment.show(fm, "");
    }
}

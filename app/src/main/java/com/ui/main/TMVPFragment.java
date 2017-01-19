package com.ui.main;

import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.base.util.SpUtil;

/**
 * Created by baixiaokang on 16/12/23.
 */

public class TMVPFragment extends android.support.design.widget.BottomSheetDialogFragment {
    private static TMVPFragment mTMVPFragment;
    private BottomSheetBehavior mBehavior;
    private ImageView imPay;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottom_sheet, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        imPay = (ImageView) view.findViewById(R.id.im_pay);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        imPay.setColorFilter(getContext().getResources().getColor(SpUtil.isNight() ? R.color.CoverColor : R.color.colorWhite), PorterDuff.Mode.MULTIPLY);
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

package com.example.foodfresh;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}

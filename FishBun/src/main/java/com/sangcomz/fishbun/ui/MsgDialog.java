package com.sangcomz.fishbun.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sangcomz.fishbun.R;


/**
 * 提醒Dialog
 *
 * @author 罗富清
 */
public class MsgDialog {
    private Context context;
    private OnClick onClick;
    private AlertDialog alertDialog;
    private String title;
    private String content;
    private String okText;
    private String cancelText;
    private boolean single;
    private int layout = R.layout.dialog_msg;

    public MsgDialog(Context context) {
        this.context = context;
    }

    public MsgDialog(Context context, String content) {
        this.context = context;
        this.content = content;
    }

    public MsgDialog(Context context, String title, String content) {
        this.context = context;
        this.content = content;
        this.title = title;
    }

    public MsgDialog(Context context, String title, boolean single) {
        this.context = context;
        this.title = title;
        this.single = single;
    }

    public MsgDialog(Context context, String title, String content, boolean single, String okText, int layout) {
        this.context = context;
        this.content = content;
        this.title = title;
        this.single = single;
        this.okText = okText;
        this.layout = layout;
    }

    public MsgDialog(Context context, String title, String content, String okText, String cancelText) {
        this.context = context;
        this.title = title;
        this.content = content;
        this.okText = okText;
        this.cancelText = cancelText;

    }



    public interface OnClick {
        /**
         * 确定点击
         */
        void yesClick();

        /**
         * 取消点击
         */
        void noClick();
    }

    public void setClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public void showDlg() {
        try {
            alertDialog = new AlertDialog.Builder(context, R.style.myDialogStyle_transparent).create();
            try {
                alertDialog.show();
            }catch (Exception e){
                e.printStackTrace();
            }

            Window window = alertDialog.getWindow();
            assert window != null;
            window.setContentView(layout);
            TextView cancel = window.findViewById(R.id.cancel);
            TextView agree = window.findViewById(R.id.ok);
            TextView titleView = window.findViewById(R.id.title);
            TextView contentView = window.findViewById(R.id.content);
            View verticalLine = window.findViewById(R.id.vertical_line);

            if (!TextUtils.isEmpty(okText)) {
                agree.setText(okText);
            }

            if (!TextUtils.isEmpty(cancelText)) {
                cancel.setText(cancelText);
            }

            if (!TextUtils.isEmpty(title)) {
                titleView.setText(title);
            }

            if (!TextUtils.isEmpty(content)) {
                contentView.setText(content);
            } else {
                contentView.setVisibility(View.GONE);
            }

            if (single) {
                cancel.setVisibility(View.GONE);
                verticalLine.setVisibility(View.GONE);
            }

            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick != null) {
                        onClick.yesClick();
                    }
                    try {
                        alertDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick != null) {
                        onClick.noClick();
                    }
                    try {
                        alertDialog.dismiss();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


}

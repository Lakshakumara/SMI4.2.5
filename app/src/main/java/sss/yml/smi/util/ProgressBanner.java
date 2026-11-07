package sss.yml.smi.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import sss.yml.smi.R;

public class ProgressBanner {
    private final Context context;
    private AlertDialog dialog;
    private View root;
    private String title;
    private String message = "";

    public ProgressBanner(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
        apply();
    }

    public void setMessage(String message) {
        this.message = message;
        apply();
    }

    public void show() {
        if (isShowing()) return;
        root = LayoutInflater.from(context).inflate(R.layout.progress_banner, null, false);
        dialog = new MaterialAlertDialogBuilder(context)
                .setView(root)
                .create();
        apply();
        dialog.show();
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void apply() {
        if (root == null) return;
        TextView titleView = root.findViewById(R.id.progress_title);
        if (title == null || title.isEmpty()) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setText(title);
            titleView.setVisibility(View.VISIBLE);
        }
        TextView messageView = root.findViewById(R.id.progress_message);
        messageView.setText(message);
    }
}

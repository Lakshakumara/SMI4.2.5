package sss.yml.smi.ui.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import sss.yml.smi.R;


public class NewsFragment extends Fragment {
    View view;
    AutoCompleteTextView newsType;
    TextInputEditText newsId;
    TextInputEditText title;
    TextInputEditText subTitle;
    TextInputEditText description;
    TextInputEditText nosView;
    TextView msg;
    FirebaseFirestore db;
    ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_news, container, false);
        mProgressDialog = new ProgressDialog(requireContext());

        newsType = view.findViewById(R.id.admin_news_type);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, new String[]{"Simple", "Icon", "Image"});
        newsType.setAdapter(adapter);

        newsId = view.findViewById(R.id.admin_news_id);
        title = view.findViewById(R.id.admin_news_tiitle);
        subTitle = view.findViewById(R.id.admin_news_subtitle);
        description = view.findViewById(R.id.admin_news_description);
        nosView = view.findViewById(R.id.admin_news_nosView);
        msg = view.findViewById(R.id.admin_news_msg);


        db = FirebaseFirestore.getInstance();
        showProgressDialog("Fetching Current News");
        db.collection("BMIS")
                .document("news")
                .addSnapshotListener((snapshot, e) -> {
                    if (snapshot != null && snapshot.exists()) {
                        Map<String, Object> data = snapshot.getData();
                        newsType.setText(data.get("newsType") == null ? null : data.get("newsType").toString());
                        String id = data.get("newsId").toString();
                        newsId.setText(id);
                        String newMessage = data.get("title") == null ? "" : data.get("title").toString();
                        title.setText(newMessage);
                        String newSubTitle = data.get("subTitle") == null ? "" : data.get("subTitle").toString();
                        subTitle.setText(newSubTitle);
                        String newDescription = data.get("description") == null ? "" : data.get("description").toString();
                        description.setText(newDescription);
                        String nos = data.get("nos").toString();
                        nosView.setText(nos);
                        hideProgressDialog();
                    }
                });
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_admin_news_save).setOnClickListener(view1 -> {
            msg.setText(null);
            showProgressDialog("Saving NEWS");
            Map<String, Object> news = new HashMap<>();
            news.put("newsType", newsType.getText().toString());
            news.put("newsId", newsId.getText().toString());
            news.put("title", title.getText().toString());
            news.put("subTitle", subTitle.getText().toString());
            news.put("description", description.getText().toString());
            news.put("nos", Integer.parseInt(nosView.getText().toString()));
            db.collection("BMIS")
                    .document("news").set(news)
                    .addOnSuccessListener(aVoid -> {
                        msg.setText("Uploaded");
                        hideProgressDialog();
                    })
                    .addOnFailureListener(e -> {
                        msg.setText(R.string.error);
                        hideProgressDialog();
                    });
        });
    }

    private void showProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

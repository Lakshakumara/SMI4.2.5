package sss.yml.smi.ui.child;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sss.yml.smi.R;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.util.DividerItemDecoration;

public class History extends Fragment implements HistoryAdapter.SingleClickListener {
    ChildData childData;
    private static SingleClickListener listener;
    public History() {
    }

    public History(ChildData childData) {
        this.childData = childData;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vertical, container, false);
        //setHasOptionsMenu(true);
        RecyclerView mRecyclerView = view.findViewById(R.id.vertical_fragment_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HistoryAdapter iAdapter = new HistoryAdapter((childData == null) ? null : childData.childWpData);
        mRecyclerView.setAdapter(iAdapter);
        Drawable dividerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        HistoryViewModel mViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        iAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClickListener(final ChildWpData childData) {
        if (listener != null) listener.onItemClickListener(childData);
    }

    public interface SingleClickListener {
        void onItemClickListener(ChildWpData childWpData);
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        listener = clickListener;
    }
}

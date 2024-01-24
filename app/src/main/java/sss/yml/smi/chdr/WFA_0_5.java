package sss.yml.smi.chdr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.snackbar.Snackbar;

import sss.yml.smi.R;
import sss.yml.smi.room.ChildData;

public class WFA_0_5 extends Fragment {
    private View view;
    private ChildData childData;
    private WFA05ViewModel mViewModel;

    public WFA_0_5() {
    }

    public WFA_0_5(ChildData childData) {
        this.childData = childData;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wfa_fragment, container, false);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(WFA05ViewModel.class);
        mViewModel.setChildData(childData);
        LineChart mChart = view.findViewById(R.id.wfaGrowthChart);
        mChart.setSaveEnabled(true);
        mViewModel.setChart(mChart);
        mViewModel.updateUserData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chart_menu_sd4_sd4neg, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        switch (item.getItemId()) {
            case R.id.SD4:
                mViewModel.addPercentile("SD4");
                break;
            case R.id.SD4neg:
                mViewModel.addPercentile("SD4neg");
                break;
            case R.id.save:
            if (mViewModel.saveChart())
                    Snackbar.make(view, "Image Saved..", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                else
                    Snackbar.make(view, "Error Saving Image..", Snackbar.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

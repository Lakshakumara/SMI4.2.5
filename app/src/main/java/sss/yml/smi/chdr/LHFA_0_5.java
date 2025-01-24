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

public class LHFA_0_5 extends Fragment {
    ChildData childData;
    View view;
    private LHFA05ViewModel mViewModel;
    public LineChart mChart;

    public LHFA_0_5() {
    }

    public LHFA_0_5(ChildData childData) {
        this.childData = childData;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wfa_fragment, container, false);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(LHFA05ViewModel.class);
        mViewModel.setChildData(childData);
        mChart = view.findViewById(R.id.wfaGrowthChart);
        mChart.setSaveEnabled(true);
        mViewModel.setChart(mChart);
        mViewModel.updateUserData();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chart_menu_htage, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        int id = item.getItemId();
        if (id == R.id.SD4) {
            mViewModel.addDataSet("SD4");
        } else if (id == R.id.SD3) {
            mViewModel.addDataSet("SD3");
        } else if (id == R.id.SD4neg) {
            mViewModel.addDataSet("SD4neg");
        } else if (id == R.id.save) {
            if (mViewModel.saveChart())
                Snackbar.make(view, "Image Saved..", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            else
                Snackbar.make(view, "Error Saving Image..", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

package sss.yml.smi.chdr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;

import sss.yml.smi.R;
import sss.yml.smi.room.Elder;

public class Adult extends Fragment {
    Elder elder;
    View view;
    public LineChart mChart;

    public Adult(Elder elder) {
        this.elder = elder;
    }

    public Adult() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wfa_fragment, container, false);
        AdultViewModel mViewModel = new ViewModelProvider(this).get(AdultViewModel.class);
        mChart = view.findViewById(R.id.wfaGrowthChart);
        mChart.setSaveEnabled(true);
        if(!mViewModel.setChart(mChart, elder)) onStop();
        return view;
    }
}

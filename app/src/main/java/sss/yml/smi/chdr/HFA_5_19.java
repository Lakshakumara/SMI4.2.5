package sss.yml.smi.chdr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import sss.yml.smi.R;
import sss.yml.smi.room.Student;

public class HFA_5_19 extends Fragment {
    View view;
    Student student;
    private HFA519ViewModel mViewModel;
    public LineChart mChart;

    public  HFA_5_19(){
    }

    public HFA_5_19(Student student) {
        this.student = student;
    }

    public static HFA_5_19 newInstance() {
        return new HFA_5_19();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wfa_fragment, container, false);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(HFA519ViewModel.class);
        mViewModel.setStudent(student);
        mChart = view.findViewById(R.id.wfaGrowthChart);
        mChart.setSaveEnabled(true);
        if(!mViewModel.setChart(mChart)) onStop();
        //mViewModel.updateUserData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HFA519ViewModel.class);

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chart_menu_htage, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        switch (item.getItemId()) {
            case R.id.SD4:
                mViewModel.addPercentile("SD4");
                break;
            case R.id.SD3:
                mViewModel.addPercentile("SD3");
                break;
            case R.id.SD4neg:
                mViewModel.addPercentile("SD4neg");
                break;
            case R.id.save:
               /* Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                File f = new File(Environment.getExternalStorageDirectory().getPath()
                        +  "/DCIM/BMISimulator/" + "finalFname" + ".png");
                sharingIntent.setType("image/jpeg");
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                startActivity(Intent.createChooser(sharingIntent, "BMISimulator Share image using"));*/
                if (mViewModel.saveChart()) {
                    Snackbar snackbar = Snackbar
                            .make(view, "Image Saved..",
                                    Snackbar.LENGTH_LONG)
                            .setAction("Share To", view -> {
                                Uri shareUri = mViewModel.getLastSavedUri();
                                if (shareUri == null) {
                                    File f = mViewModel.getLastSavedFile();
                                    if (f == null) {
                                        Snackbar.make(view, "Error Sharing Image..", Snackbar.LENGTH_SHORT).show();
                                        return;
                                    }
                                    shareUri = FileProvider.getUriForFile(requireContext(),
                                            requireContext().getPackageName() + ".fileprovider", f);
                                }
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("image/png");
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, shareUri);
                                sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(Intent.createChooser(sharingIntent, "BMISimulator Share image using"));
                            });
                    snackbar.show();
                } else {
                    Snackbar.make(view, "Error Saving Image..", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

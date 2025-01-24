package sss.yml.smi.smi;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.Objects;

import sss.yml.smi.R;
import sss.yml.smi.room.School;
import sss.yml.smi.util.DividerItemDecoration;

public class SchoolSelection extends Fragment implements SchoolAdapter.SingleClickListener, SchoolAdapter.LongClickListener,
        SchoolAdapter.DownloadClickListener {
    private View view;
    private ConstraintLayout layDown;
    private AutoCompleteTextView zone, division;
    private SchoolViewModel mViewModel;
    private ArrayAdapter<String> zoneAdapter, divisionAdapter;
    private RecyclerView mRecyclerView;
    private SchoolAdapter sAdapter;
    private String district;
    private MenuItem delete;
    private School schoolToDelete;

    public static SchoolSelection newInstance() {
        return new SchoolSelection();
    }

    private static final int sch_download = R.id.sch_download;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.school_fragment, container, false);
        setHasOptionsMenu(true);

        layDown = view.findViewById(R.id.download_school_lay);
        zone = view.findViewById(R.id.txtzonal);
        division = view.findViewById(R.id.txtdivision);
        district = getDistrict();
        zone.setOnItemClickListener((parent, arg1, pos, id) ->
                mViewModel.getList("SELECT distinct division FROM `School` where upper(district) = upper('" + district + "') and zone='"
                                + zoneAdapter.getItem(pos) + "' order by division",
                        Collections.singletonList("division")).observe(getViewLifecycleOwner(),
                        nameList -> {
                            divisionAdapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, nameList);
                            division.setAdapter(divisionAdapter);
                        }));
        division.setOnItemClickListener((parent, arg1, pos, id) ->
                sAdapter.setData(false,
                        mViewModel.downloadSchool("SELECT * FROM `School` where division='" + divisionAdapter.getItem(pos) + "' order by name")));
        mRecyclerView = view.findViewById(R.id.vertical_fragment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Drawable dividerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SchoolViewModel.class);
        sAdapter.setData(true, mViewModel.getSchoolList(true));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.school_select, menu);
        delete = menu.findItem(R.id.sch_delete);
        SearchView searchView = (SearchView) menu.findItem(R.id.sch_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint(getString(R.string.school_name));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sAdapter.setData(false, mViewModel.downloadSchool("SELECT * FROM `School` where upper(district) = upper('" + district + "') and upper(name) like upper('%" + query + "%') order by name"));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sch_download) {
            setAdapter();
            String district = getDistrict();
            if (layDown.getVisibility() == View.GONE) {
                if (zoneAdapter == null)
                    mViewModel.getList("SELECT distinct zone FROM `School` where upper(district) = upper('" + district + "') order by zone", Collections.singletonList("zone")).observe(getViewLifecycleOwner(), nameList -> {
                        zoneAdapter = new ArrayAdapter<>(requireContext(), R.layout.list_item, nameList);
                        zone.setAdapter(zoneAdapter);
                    });
                layDown.setVisibility(View.VISIBLE);
            } else layDown.setVisibility(View.GONE);
        } else if (id == R.id.sch_delete) {
            mViewModel.delete(schoolToDelete);
            sAdapter.setData(true, mViewModel.getSchoolList(true));
            delete.setVisible(false);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        sAdapter = new SchoolAdapter();
        mRecyclerView.setAdapter(sAdapter);
        sAdapter.setOnItemClickListener(this);
        sAdapter.setOnItemLongClickListener(this);
        sAdapter.setOnDownloadClickListener(this);
    }

    private String getDistrict() {
        if (district == null) {
            if (getActivity() == null)
                Snackbar.make(Objects.requireNonNull(requireActivity().getCurrentFocus()), "Filter based on profile District", Snackbar.LENGTH_LONG).show();
            district = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("district", "");
        }
        return district;
    }

    @Override
    public void onItemClick(School school) {
        if (layDown.getVisibility() == View.VISIBLE) return;
        if (school == null) return;
        NavDirections navDirections = SchoolSelectionDirections.actionNavSmiToSmi(school.getCensusNo(), school.getName(), null);
        Navigation.findNavController(view).navigate(navDirections);
    }

    @Override
    public void onItemLongClick(School school) {
        delete.setVisible(true);
        schoolToDelete = school;
    }

    @Override
    public void onDownloadClick(School school) {
        mViewModel.insert(school);
        Snackbar.make(mRecyclerView, "Downloaded " + school.getName(), Snackbar.LENGTH_SHORT).show();
    }
}
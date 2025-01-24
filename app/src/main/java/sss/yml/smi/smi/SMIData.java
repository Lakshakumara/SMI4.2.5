package sss.yml.smi.smi;

import static sss.yml.smi.smi.SMIDataArgs.*;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textview.MaterialTextView;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sss.yml.smi.Home.HomePage;
import sss.yml.smi.R;
import sss.yml.smi.room.School;
import sss.yml.smi.room.Student;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SMIData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SMIData extends Fragment implements SMIDataAdapter.SingleClickListener,
        SMIDataAdapter.LongClickListener, SMIDataAdapter.CardPOPListener {
    private SmiDataEntryViewModel mViewModel;
    private RecyclerView mRecyclerView;
    private SMIDataAdapter sAdapter;
    private int censusNo;
    private int year;
    private String grade;
    private Student selectedStudent;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SMIData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SMIData.
     */
    // TODO: Rename and change types and number of parameters
    public static SMIData newInstance(String param1, String param2) {
        SMIData fragment = new SMIData();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_m_i_data, container, false);
        mViewModel = new ViewModelProvider(this).get(SmiDataEntryViewModel.class);
        mRecyclerView = view.findViewById(R.id.item_viewer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //Drawable dividerDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider_drawable);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setAdapter();
        if (getArguments() != null) {
            censusNo = SMIDataArgs.fromBundle(getArguments()).getCensusNo();
            grade = SMIDataArgs.fromBundle(getArguments()).getGrade();
            ((HomePage) requireActivity()).setActionBarTitle(grade);
            year = Calendar.getInstance().get(Calendar.YEAR);// - 1900;
            //getStudents(year, grade);
        }
        mViewModel.getStudentList(censusNo, year, grade, 1).observe(getViewLifecycleOwner(), students ->
                sAdapter.setData(false, students, requireContext()));

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.smi_data, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.filter) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Filter By");
                    builder.setSingleChoiceItems(R.array.filter, -1, (dialog, which) -> {
                        mViewModel.getStudentList(censusNo, year, grade, which).observe(getViewLifecycleOwner(), students ->
                                sAdapter.setData(false, students, requireContext()));
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else if (id == R.id.sc_export) {
                    exportToCSV();
                } else if (id == R.id.sc_chart) {
                    /*Intent i = new Intent(getContext(), ChartShow.class);
                        i.putExtra("student", new Student());
                        startActivity(i);*/
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        return view;
    }


    private void setAdapter() {
        sAdapter = new SMIDataAdapter();
        mRecyclerView.setAdapter(sAdapter);
        sAdapter.setOnItemClickListener(this);
        sAdapter.setOnPopupMenuClick(this);
        //sAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(Student student) {
        selectedStudent = student;
    }

    @Override
    public void onItemLongClick(Student student) {
        //Snackbar.make(mRecyclerView, "onItemLongClick", Snackbar.LENGTH_SHORT).show();
    }

    private void Filter(boolean edit, @StringRes int resId) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.smi_data_edit);
        MaterialTextView title = bottomSheetDialog.findViewById(R.id.txtTitle);
        Spinner sch = bottomSheetDialog.findViewById(R.id.schList);
        Spinner spGrade = bottomSheetDialog.findViewById(R.id.gradeList);
        List<School> schoolList = mViewModel.getSchoolList(true);
        ArrayList<String> regSch = new ArrayList<>();
        final int[] newCens = {0};
        if (edit) {
            title.setText(R.string.edit);
            int index = 0;
            int tot = schoolList.size();
            for (int j = 0; j < tot; j++) {
                if (schoolList.get(j).getCensusNo() == censusNo) index = j;
                regSch.add(schoolList.get(j).getName());
            }
            sch.setAdapter(new ArrayAdapter<>(requireContext(),
                    R.layout.list_item, regSch));
        }

        sch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newCens[0] = (schoolList.get(position)).getCensusNo();
                mViewModel.getGradeList(schoolList.get(position).getCensusNo(), year)
                        .observe(getViewLifecycleOwner(), grades ->
                                spGrade.setAdapter(new ArrayAdapter<>(requireContext(),
                                        R.layout.list_item, grades)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        bottomSheetDialog.findViewById(R.id.schEditAll).setOnClickListener(v -> {
            if (selectedStudent == null) return;
            //Navigation.findNavController(view).navigate(R.id.Smi);
            //NavDirections navDirections = SMIDataDirections.actionSMIDataToSmi2(0, "cc");
            //Navigation.findNavController(view).navigate(navDirections);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.getPreviousBackStackEntry().getSavedStateHandle().set("student", "selectedStudent");
        });
        bottomSheetDialog.findViewById(R.id.schEdit).setOnClickListener(v -> {
            if (selectedStudent == null) return;
            selectedStudent.setCensusNo(newCens[0]);
            selectedStudent.setGrade(spGrade.getSelectedItem().toString());
            mViewModel.update(selectedStudent);
        });

        //MaterialTextView msg = bottomSheetDialog.findViewById(R.id.msg);
        //msg.setText(resId);
        bottomSheetDialog.show();
    }

    @Override
    public void onPopupMenuClick(View view, Student student) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.smi_data_card, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.edit) {

                try {
                    NavDirections navDirections = SMIDataDirections.actionSMIDataToSmi2(censusNo, grade, student);
                    Navigation.findNavController(view).navigate(navDirections);
                } catch (Exception ignored) {
                }
            } else if (id == R.id.delete) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(requireContext());
                builder2.setTitle("Delete ?");
                builder2.setPositiveButton(getString(R.string.delete), (dialogInterface, i) -> {
                    mViewModel.delete(student);
                });
                builder2.setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> {
                });
                AlertDialog dialog2 = builder2.create();
                dialog2.show();

            }
            return true;
        });
        popup.show();
    }

    private void exportToCSV() {
        final int[] selectedCensusNo = {0};
        final int[] selectedYear = {0};
        final String[] selectedGrade = {""};
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.smi_data_export);
        MaterialTextView title = bottomSheetDialog.findViewById(R.id.txtTitle);
        assert title != null;
        title.setText("Export Data");
        Spinner sch = bottomSheetDialog.findViewById(R.id.schList);
        Spinner spYear = bottomSheetDialog.findViewById(R.id.yearList);
        Spinner spGrade = bottomSheetDialog.findViewById(R.id.gradeList);
        List<School> schoolList = mViewModel.getSchoolList(true);
        ArrayList<String> regSch = new ArrayList<>();
        int index = 0;
        int tot = schoolList.size();
        for (int j = 0; j < tot; j++) {
            if (schoolList.get(j).getCensusNo() == censusNo) index = j;
            regSch.add(schoolList.get(j).getName());
        }
        assert sch != null;
        sch.setAdapter(new ArrayAdapter<>(requireContext(),
                R.layout.list_item, regSch));

        sch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCensusNo[0] = (schoolList.get(position)).getCensusNo();
                assert spGrade != null;
                spGrade.setAdapter(new ArrayAdapter<>(requireContext(),
                        R.layout.list_item, new String[]{}));

                mViewModel.getYearList(selectedCensusNo[0])
                        .observe(getViewLifecycleOwner(), years ->
                        {
                            assert spYear != null;
                            spYear.setAdapter(new ArrayAdapter<>(requireContext(),
                                    R.layout.list_item, years));

                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        assert spYear != null;
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear[0] = (int) spYear.getAdapter().getItem(position);
                mViewModel.getGradeList(selectedCensusNo[0], selectedYear[0])
                        .observe(getViewLifecycleOwner(), grades ->
                        {
                            grades.add(0, "All");
                            assert spGrade != null;
                            spGrade.setAdapter(new ArrayAdapter<>(requireContext(),
                                    R.layout.list_item, grades));
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        assert spGrade != null;
        spGrade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGrade[0] = (String) spGrade.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        bottomSheetDialog.findViewById(R.id.btnExport).setOnClickListener(v -> {
            /*Logger.setMsg("Exported Click");
            String fileName = "smi_data";
            File exportDir = new File(Environment.getExternalStorageDirectory().getPath()+ "/BMISimulator");
            //File exportDir = new File(Environment.getExternalStorageDirectory(), "bmiExport");
            if (!exportDir.exists()) {
                boolean mkdirs = exportDir.mkdirs();
                Logger.setMsg("not exists dir",mkdirs);
            }


            // Create a new file in the Downloads folder
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);


            //
            //File file = new File(exportDir, fileName + ".csv");
            try {
                //file.createNewFile();

                // Insert the file into MediaStore
                OutputStream outputStream = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    outputStream = context.getContentResolver().openOutputStream(
                            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), "w");
                }


                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                Cursor curCSV = mViewModel.getcursor("SELECT * FROM student", null);
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    //Which column you want to exprort
                    String arrStr[] = new String[curCSV.getColumnCount()];
                    for (int i = 0; i < curCSV.getColumnCount() - 1; i++)
                        arrStr[i] = curCSV.getString(i);
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
                curCSV.close();
                Toast.makeText(getContext(), "Exported", Toast.LENGTH_LONG).show();
                //ToastHelper.showToast(this, "Exported", Toast.LENGTH_SHORT);
            } catch (Exception sqlEx) {
                Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
            }*/

            mViewModel.getStudentList(selectedCensusNo[0], selectedYear[0], selectedGrade[0], 0)
                    .observe(getViewLifecycleOwner(), students ->
                    {
// CSV File Content
                        StringBuilder csvBuilder = new StringBuilder();
                        csvBuilder.append("ID,Name,BMI,Grade\n"); // Add headers

                        for (Student student : students) {
                            csvBuilder.append(student.getId()).append(",")
                                    .append(student.getUserName()).append(",")
                                    .append(student.getBmi()).append(",")
                                    .append(student.getGrade()).append("\n");
                        }
                        String csvContent = csvBuilder.toString();

                        // Save to Downloads folder
                        String fileName = "students.csv";
                        try {
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                            contentValues.put(MediaStore.Downloads.MIME_TYPE, "text/csv");
                            contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                            // Get content URI
                            ContentResolver contentResolver = requireContext().getContentResolver();
                            Uri uri;
                            OutputStream outputStream = null;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                uri = contentResolver.insert(MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues);
                                if (uri == null) {
                                    throw new Exception("Failed to create new file entry.");
                                }
                                outputStream = requireContext().getContentResolver().openOutputStream(
                                        MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY));
                            }
                            if (outputStream != null) {
                                outputStream.write(csvContent.getBytes());
                                outputStream.close();
                                Toast.makeText(requireContext(), "File saved to Downloads folder", Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.hide();
                            } else {
                                throw new Exception("Failed to create output stream.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(requireContext(), "Error saving file: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });


            // Create Workbook and Sheet
           /* Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Students");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Age");
            headerRow.createCell(3).setCellValue("Grade");

            // Save to Downloads folder
            String fileName = "students.xlsx";
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                OutputStream outputStream = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    outputStream = requireContext().getContentResolver().openOutputStream(
                            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY));
                }
                if (outputStream != null) {
                    workbook.write(outputStream);
                    outputStream.close();
                    workbook.close();
                    Toast.makeText(requireContext(), "Excel file saved to Downloads folder", Toast.LENGTH_SHORT).show();
                } else {
                    throw new Exception("Failed to create output stream.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Error saving Excel file: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }*/
        });

        bottomSheetDialog.show();
    }
}
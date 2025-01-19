package sss.yml.smi.smi;

import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import sss.yml.smi.room.DbRepository;
import sss.yml.smi.room.School;
import sss.yml.smi.room.Student;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SmiDataEntryViewModel extends AndroidViewModel {
    private final DbRepository repository;

    public SmiDataEntryViewModel(@NonNull Application application) {
        super(application);
        repository = new DbRepository();
    }

    public int insert(Student student) throws ExecutionException, InterruptedException {
        DocumentReference fsData = FirebaseFirestore.getInstance().collection("data")
                .document(String.valueOf(student.getMeasureYear()))
                .collection(String.valueOf(student.getCensusNo())).document();
        student.setDocRef(fsData.getId());
        student.setUid(FirebaseAuth.getInstance().getUid());
        fsData.set(student);
        return repository.insert(student).intValue();
    }

    public void update(Student student) {
        student.setUid(FirebaseAuth.getInstance().getUid());
        if (student.getDocRef() != null)
            FirebaseFirestore.getInstance().collection("data")
                    .document(String.valueOf(student.getMeasureYear()))
                    .collection(String.valueOf(student.getCensusNo())).document(student.getDocRef()).set(student);
        repository.update(student);
    }

    public void delete(Student student) {
        if (student.getDocRef() != null)
            FirebaseFirestore.getInstance().collection("data")
                    .document(String.valueOf(student.getMeasureYear()))
                    .collection(String.valueOf(student.getCensusNo())).document(student.getDocRef()).delete();
        repository.delete(student);
    }

    public LiveData<List<Student>> getStudentList(int censusNo, int year, String grade, int order) {
        return repository.getStudentList(censusNo, year, grade, order);
    }

    public void delete(School school) {
        repository.delete(school);
    }

    public List<School> getSchoolList(boolean selected) {
        return repository.getSchoolList(selected);
    }

    public LiveData<List<Integer>> getYearList(int censusNo) {
        return repository.getYears(censusNo);
    }

    public LiveData<List<String>> getGradeList(int censusNo, int year) {
        return repository.getGradeList(censusNo, year);
    }

    public Cursor getcursor(String query, Objects[] args) {
        return repository.getCursor(query, args);
    }
}

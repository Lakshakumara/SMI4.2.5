package sss.yml.smi.ui.child;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import sss.yml.smi.room.Child;
import sss.yml.smi.room.DbRepository;

import java.util.concurrent.ExecutionException;

public class ChildRegViewModel extends AndroidViewModel {
    private final DbRepository repository;

    public ChildRegViewModel(@NonNull Application application) {
        super(application);
        repository = new DbRepository();
    }

    public int insert(Child child) throws ExecutionException, InterruptedException {
        return repository.insert(child).intValue();
    }

    public void update(Child child) {
        repository.update(child);
    }

    public void delete(Child child) {
        repository.delete(child);
    }
    public void deleteData(int childId) {
        repository.deleteData(childId);
    }
    public Integer getChildId(String name) {
        return repository.getChildId(name);
    }

    public LiveData<Child> getChild(int childId) {
        return repository.getChild(childId);
    }

    public Child getChild(String... child_BI_name) {
        return repository.getChild(child_BI_name);
    }
}

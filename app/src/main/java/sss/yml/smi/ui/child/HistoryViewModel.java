package sss.yml.smi.ui.child;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.room.DbRepository;

public class HistoryViewModel extends AndroidViewModel {
    private DbRepository repository;

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        repository = new DbRepository();
    }

    public void insert(ChildWpData childWpData) {
        repository.insert(childWpData);
    }

    public void update(ChildWpData childWpData) {
        repository.update(childWpData);
    }

    public LiveData<List<ChildData>> getChild(String name) {
        return repository.getChildData(name, false);
    }
}

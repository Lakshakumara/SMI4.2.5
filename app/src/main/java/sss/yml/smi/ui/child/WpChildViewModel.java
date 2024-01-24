package sss.yml.smi.ui.child;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import sss.yml.smi.room.Child;
import sss.yml.smi.room.ChildData;
import sss.yml.smi.room.ChildWpData;
import sss.yml.smi.room.DbRepository;

import java.util.List;

public class WpChildViewModel extends AndroidViewModel {
    private final DbRepository repository;

    public WpChildViewModel(@NonNull Application application) {
        super(application);
        repository = new DbRepository();
    }
    public void insert(ChildWpData childWpData){repository.insert(childWpData);}
    public void update(Child child){repository.update(child);}
    public void update(ChildWpData childWpData){repository.update(childWpData);}
    public void delete(ChildWpData childWpData){repository.delete(childWpData);}
    public LiveData<List<ChildData>> getChild(String name){
        return repository.getChildData(name, false);
    }
    public Integer getChildId(String name){
        return repository.getChildId(name);
    }

    public LiveData<List<String>> getStringList(String field){
        return repository.getStringList(field);
    }
}

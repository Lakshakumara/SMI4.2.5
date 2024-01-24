package sss.yml.smi.cloud;

import java.util.List;

import sss.yml.smi.room.Child;
import sss.yml.smi.room.ChildWpData;

public class CompleteChild extends Child {
    private List<ChildWpData> childWpData;

    public CompleteChild(Child child, List<ChildWpData> childWpData) {
        super(child);
        this.childWpData = childWpData;
    }

    public List<ChildWpData> getChildWpData() {
        return childWpData;
    }

    public void setChildWpData(List<ChildWpData> childWpData) {
        this.childWpData = childWpData;
    }
}

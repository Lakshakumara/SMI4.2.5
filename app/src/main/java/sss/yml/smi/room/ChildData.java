package sss.yml.smi.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
public class ChildData {
    @Embedded public Child child;
    @Relation(
            parentColumn = "id",
            entityColumn = "childId"
    )
    public List<ChildWpData> childWpData;
}

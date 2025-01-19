package sss.yml.smi.room;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "childWpData", indices = {@Index("childId")})
@TypeConverters({DateConverter.class})
public class ChildWpData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int childId;
    Date mDate;
    private double wt;
    private double ht;
    private String note;
    private float ageDays;
    private int ageYY;
    private int ageMM;
    private int ageDD;

    private double wfaZ;
    private double hfaZ;
    private double wfhZ;
    private String wfaStatus;
    private String hfaStatus;
    private String wfhStatus;

    public ChildWpData() {
    }
@Ignore
    public ChildWpData(int childId) {
        this.childId = childId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setWt(double wt) {
        this.wt = wt;
    }

    public void setHt(double ht) {
        this.ht = ht;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAgeDays(float ageDays) {
        this.ageDays = ageDays;
    }

    public void setAgeYY(int ageYY) {
        this.ageYY = ageYY;
    }

    public void setAgeMM(int ageMM) {
        this.ageMM = ageMM;
    }

    public void setAgeDD(int ageDD) {
        this.ageDD = ageDD;
    }

    public void setWfaZ(double wfaZ) {
        this.wfaZ = wfaZ;
    }

    public void setHfaZ(double hfaZ) {
        this.hfaZ = hfaZ;
    }

    public void setWfhZ(double wfhZ) {
        this.wfhZ = wfhZ;
    }

    public void setWfaStatus(String wfaStatus) {
        this.wfaStatus = wfaStatus;
    }

    public void setHfaStatus(String hfaStatus) {
        this.hfaStatus = hfaStatus;
    }

    public void setWfhStatus(String wfhStatus) {
        this.wfhStatus = wfhStatus;
    }

    public int getId() {
        return id;
    }

    public int getChildId() {
        return childId;
    }

    public Date getmDate() {
        return mDate;
    }

    public double getWt() {
        return wt;
    }

    public double getHt() {
        return ht;
    }

    public String getNote() {
        return note;
    }

    public float getAgeDays() {
        return ageDays;
    }

    public int getAgeYY() {
        return ageYY;
    }

    public int getAgeMM() {
        return ageMM;
    }

    public int getAgeDD() {
        return ageDD;
    }

    public double getWfaZ() {
        return wfaZ;
    }

    public double getHfaZ() {
        return hfaZ;
    }

    public double getWfhZ() {
        return wfhZ;
    }

    public String getWfaStatus() {
        return wfaStatus;
    }

    public String getHfaStatus() {
        return hfaStatus;
    }

    public String getWfhStatus() {
        return wfhStatus;
    }

    @Ignore
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildWpData that = (ChildWpData) o;
        return id == that.id &&
                childId == that.childId &&
                Double.compare(that.wt, wt) == 0 &&
                Double.compare(that.ht, ht) == 0 &&
                Float.compare(that.ageDays, ageDays) == 0 &&
                ageYY == that.ageYY &&
                ageMM == that.ageMM &&
                ageDD == that.ageDD &&
                mDate.equals(that.mDate);// &&
                //note.equals(that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, childId, mDate, wt, ht, note, ageDays, ageYY, ageMM, ageDD);
    }

    @Override
    public String toString() {
        return "ChildWpData{" +
                "id=" + id +
                ", childId=" + childId +
                ", mDate=" + mDate +
                ", wt=" + wt +
                ", ht=" + ht +
                ", note='" + note + '\'' +
                ", ageDays=" + ageDays +
                ", ageYY=" + ageYY +
                ", ageMM=" + ageMM +
                ", ageDD=" + ageDD +
                '}';
    }
}

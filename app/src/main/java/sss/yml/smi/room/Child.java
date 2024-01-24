package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "child", indices = {@Index("name")})
//, @Index(value = {"last_name", "address"}), unique = true
@TypeConverters({DateConverter.class})
public class Child {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String regNo;
    @NonNull
    private String name;
    @NonNull
    private String sex;
    @NonNull
    private Date dob;
    private int maturity;
    private String guardian;
    private String tp;
    @ColumnInfo(defaultValue = "")
    private String note;
    @ColumnInfo(defaultValue = "0.0")
    private double fht;
    @ColumnInfo(defaultValue = "0.0")
    private double mht;
    boolean deleted = false;
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private Date accessTime;

    public Child() {
    }

    @Ignore
    public Child(Child child) {
        this.id = child.getId();
        this.regNo = child.getRegNo();
        this.name = child.getName();
        this.sex = child.getSex();
        this.dob = child.getDob();
        this.maturity = child.getMaturity();
        this.guardian = child.getGuardian();
        this.tp = child.getTp();
        this.note = child.getNote();
        this.fht = child.getFht();
        this.mht = child.getMht();
        this.deleted = child.isDeleted();
    }

    @Ignore
    public Child(String name) {
        this.name = name;
    }

    public Child(String regNo, String name, String sex, Date dob, int maturity, String guardian, String tp, String note, double fatherHt, double motherHt) {
        this.regNo = regNo;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.maturity = maturity;
        this.guardian = guardian;
        this.tp = tp;
        this.note = note;
        this.fht = fatherHt;
        this.mht = motherHt;
    }
    @Ignore
    public Child(@NonNull String sex, @NonNull Date dob) {
        this.sex = sex;
        this.dob = dob;
    }

    public int getId() {
        return id;
    }

    public String getRegNo() {
        return regNo;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public Date getDob() {
        return dob;
    }

    public int getMaturity() {
        return maturity;
    }

    public String getGuardian() {
        return guardian;
    }

    public String getTp() {
        return tp;
    }

    public String getNote() {
        return note;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public double getFht() {
        return fht;
    }

    public double getMht() {
        return mht;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegNo(@NonNull String regNo) {
        this.regNo = regNo;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setSex(@NonNull String sex) {
        this.sex = sex;
    }

    public void setDob(@NonNull Date dob) {
        this.dob = dob;
    }

    public void setMaturity(int maturity) {
        this.maturity = maturity;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public void setTp(String tp) {
        this.tp = tp;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setFht(double fht) {
        this.fht = fht;
    }

    public void setMht(double mht) {
        this.mht = mht;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", regNo='" + regNo + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", dob=" + dob +
                ", maturity=" + maturity +
                ", guardian='" + guardian + '\'' +
                ",\n tp='" + tp + '\'' +
                ", note='" + note + '\'' +
                ", fht=" + fht +
                ", mht=" + mht +
                ", deleted=" + deleted +
                ", accessTime=" + accessTime +
                '}';
    }
}
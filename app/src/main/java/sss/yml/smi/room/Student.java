package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "student", indices = {@Index("id")})
//, @Index(value = {"last_name", "address"}), unique = true
@TypeConverters({DateConverter.class})
public class Student implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int censusNo;
    @ColumnInfo(defaultValue = "0")
    private int refId;
    @ColumnInfo(defaultValue = "")
    private String userName;
    @ColumnInfo(defaultValue = "")
    private String grade;
    @NonNull
    private Date dob;
    @NonNull
    private Date mdate;
    @NonNull
    private String sex;
    @NonNull
    private double height;
    @NonNull
    private double weight;
    @NonNull
    private int ageY;
    @NonNull
    private int ageM;
    @NonNull
    private int ageD;
    @NonNull
    private double bmi;
    @NonNull
    private double wtZ;
    @NonNull
    private double htZ;
    @NonNull
    private String wtStatus;
    private int wtColor;
    @NonNull
    private String htStatus;
    private int htColor;
    @NonNull
    private String wtPos;
    @NonNull
    private String htPos;

    private String docRef = "";

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }
    @Ignore
    private String uid = "";

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public Student() { }

    @NonNull
    public String getWtPos() {
        return wtPos;
    }

    public void setWtPos(@NonNull String wtPos) {
        this.wtPos = wtPos;
    }

    @NonNull
    public String getHtPos() {
        return htPos;
    }

    public void setHtPos(@NonNull String htPos) {
        this.htPos = htPos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCensusNo() {
        return censusNo;
    }

    public void setCensusNo(int censusNo) {
        this.censusNo = censusNo;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getMdate() {
        return mdate;
    }

    public void setMdate(Date mdate) {
        this.mdate = mdate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getAgeY() {
        return ageY;
    }

    public void setAgeY(int ageY) {
        this.ageY = ageY;
    }

    public int getAgeM() {
        return ageM;
    }

    public void setAgeM(int ageM) {
        this.ageM = ageM;
    }

    public int getAgeD() {
        return ageD;
    }

    public void setAgeD(int ageD) {
        this.ageD = ageD;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getWtStatus() {
        return wtStatus;
    }

    public void setWtStatus(String wtStatus) {
        this.wtStatus = wtStatus;
    }

    public String getHtStatus() {
        return htStatus;
    }

    public void setHtStatus(String htStatus) {
        this.htStatus = htStatus;
    }

    public int getWtColor() {
        return wtColor;
    }

    public void setWtColor(int wtColor) {
        this.wtColor = wtColor;
    }

    public int getHtColor() {
        return htColor;
    }

    public void setHtColor(int htColor) {
        this.htColor = htColor;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getWtZ() {
        return wtZ;
    }

    public void setWtZ(double wtZ) {
        this.wtZ = wtZ;
    }

    public double getHtZ() {
        return htZ;
    }

    public void setHtZ(double htZ) {
        this.htZ = htZ;
    }

    @Ignore
    public float getAgeMonths() {
        int months = getAgeY() * 12 + getAgeM();
        return (float) months;
    }
    public int getMeasureYear() {
        Calendar c = Calendar.getInstance();
        c.setTime(mdate);
        return c.get(Calendar.YEAR);
    }

    @Ignore
    public void clear() {
        setAgeD(0);
        setAgeM(0);
        setAgeY(0);
        setBmi(0);
        setCensusNo(0);
        setDob(null);
        setGrade("");
        setHeight(0);
        setHtColor(0);
        setHtPos("");
        setHtStatus("");
        setHtZ(0);
        setId(0);
        setMdate(null);
        setRefId(0);
        setSex("");
        setUserName("");
        setWeight(0);
        setWtColor(0);
        setWtPos("");
        setWtStatus("");
        setWtZ(0);
    }

    @Override
    public String toString() {
        return "\nStudent{" +
                "id=" + id +
                ", censusNo=" + censusNo +
                ", refId=" + refId +
                ", userName='" + userName + '\'' +
                ", grade='" + grade + '\'' +
                ", dob=" + dob +
                ", mdate=" + mdate +
                ", sex='" + sex + '\'' +
                ", height=" + height +
                ", \nweight=" + weight +
                ", ageY=" + ageY +
                ", ageM=" + ageM +
                ", ageD=" + ageD +
                ", bmi=" + bmi +
                ", wtZ=" + wtZ +
                ", htZ=" + htZ +
                ", wtStatus='" + wtStatus + '\'' +
                ", htStatus='" + htStatus + '\'' +
                ", wtPos='" + wtPos + '\'' +
                ", htPos='" + htPos + '\'' +
                '}';
    }
}

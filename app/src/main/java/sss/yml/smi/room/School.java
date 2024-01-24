package sss.yml.smi.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "school", indices = {@Index("censusNo")})
public class School {
    @PrimaryKey
    private int censusNo;
    @NonNull
    private String name;
    @NonNull
    private String address;
    @NonNull
    private String province;
    @NonNull
    private String district;
    @NonNull
    private String zone;
    @NonNull
    private String division;
    @NonNull
    private String level;
    @NonNull
    private String span;
    private int male;
    private int female;
    private boolean selected;

    public School(int censusNo, @NonNull String name, @NonNull String address, @NonNull String province, @NonNull String district,
                  @NonNull String zone, @NonNull String division, @NonNull String level, @NonNull String span, int male, int female, boolean selected) {
        this.censusNo = censusNo;
        this.name = name;
        this.address = address;
        this.province = province;
        this.district = district;
        this.zone = zone;
        this.division = division;
        this.level = level;
        this.span = span;
        this.male = male;
        this.female = female;
        this.selected = selected;
    }

    public int getCensusNo() {
        return censusNo;
    }

    public void setCensusNo(int censusNo) {
        this.censusNo = censusNo;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getProvince() {
        return province;
    }

    public void setProvince(@NonNull String province) {
        this.province = province;
    }

    @NonNull
    public String getDistrict() {
        return district;
    }

    public void setDistrict(@NonNull String district) {
        this.district = district;
    }

    @NonNull
    public String getZone() {
        return zone;
    }

    public void setZone(@NonNull String zone) {
        this.zone = zone;
    }

    @NonNull
    public String getDivision() {
        return division;
    }

    public void setDivision(@NonNull String division) {
        this.division = division;
    }

    @NonNull
    public String getLevel() {
        return level;
    }

    public void setLevel(@NonNull String level) {
        this.level = level;
    }

    @NonNull
    public String getSpan() {
        return span;
    }

    public void setSpan(@NonNull String span) {
        this.span = span;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "School{" +
                "censusNo=" + censusNo +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", province='" + province + '\'' +
                ", district='" + district + '\'' +
                ", zone='" + zone + '\'' +
                ", division='" + division + '\'' +
                ", level='" + level + '\'' +
                ", span='" + span + '\'' +
                ", male=" + male +
                ", female=" + female +
                ", selected=" + selected +
                '}';
    }
}

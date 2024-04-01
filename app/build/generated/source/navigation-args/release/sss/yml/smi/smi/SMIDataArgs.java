package sss.yml.smi.smi;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class SMIDataArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SMIDataArgs() {
  }

  @SuppressWarnings("unchecked")
  private SMIDataArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SMIDataArgs fromBundle(@NonNull Bundle bundle) {
    SMIDataArgs __result = new SMIDataArgs();
    bundle.setClassLoader(SMIDataArgs.class.getClassLoader());
    if (bundle.containsKey("censusNo")) {
      int censusNo;
      censusNo = bundle.getInt("censusNo");
      __result.arguments.put("censusNo", censusNo);
    } else {
      throw new IllegalArgumentException("Required argument \"censusNo\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("grade")) {
      String grade;
      grade = bundle.getString("grade");
      if (grade == null) {
        throw new IllegalArgumentException("Argument \"grade\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("grade", grade);
    } else {
      throw new IllegalArgumentException("Required argument \"grade\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("year")) {
      int year;
      year = bundle.getInt("year");
      __result.arguments.put("year", year);
    } else {
      throw new IllegalArgumentException("Required argument \"year\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SMIDataArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    SMIDataArgs __result = new SMIDataArgs();
    if (savedStateHandle.contains("censusNo")) {
      int censusNo;
      censusNo = savedStateHandle.get("censusNo");
      __result.arguments.put("censusNo", censusNo);
    } else {
      throw new IllegalArgumentException("Required argument \"censusNo\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("grade")) {
      String grade;
      grade = savedStateHandle.get("grade");
      if (grade == null) {
        throw new IllegalArgumentException("Argument \"grade\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("grade", grade);
    } else {
      throw new IllegalArgumentException("Required argument \"grade\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("year")) {
      int year;
      year = savedStateHandle.get("year");
      __result.arguments.put("year", year);
    } else {
      throw new IllegalArgumentException("Required argument \"year\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getCensusNo() {
    return (int) arguments.get("censusNo");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getGrade() {
    return (String) arguments.get("grade");
  }

  @SuppressWarnings("unchecked")
  public int getYear() {
    return (int) arguments.get("year");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("censusNo")) {
      int censusNo = (int) arguments.get("censusNo");
      __result.putInt("censusNo", censusNo);
    }
    if (arguments.containsKey("grade")) {
      String grade = (String) arguments.get("grade");
      __result.putString("grade", grade);
    }
    if (arguments.containsKey("year")) {
      int year = (int) arguments.get("year");
      __result.putInt("year", year);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("censusNo")) {
      int censusNo = (int) arguments.get("censusNo");
      __result.set("censusNo", censusNo);
    }
    if (arguments.containsKey("grade")) {
      String grade = (String) arguments.get("grade");
      __result.set("grade", grade);
    }
    if (arguments.containsKey("year")) {
      int year = (int) arguments.get("year");
      __result.set("year", year);
    }
    return __result;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    SMIDataArgs that = (SMIDataArgs) object;
    if (arguments.containsKey("censusNo") != that.arguments.containsKey("censusNo")) {
      return false;
    }
    if (getCensusNo() != that.getCensusNo()) {
      return false;
    }
    if (arguments.containsKey("grade") != that.arguments.containsKey("grade")) {
      return false;
    }
    if (getGrade() != null ? !getGrade().equals(that.getGrade()) : that.getGrade() != null) {
      return false;
    }
    if (arguments.containsKey("year") != that.arguments.containsKey("year")) {
      return false;
    }
    if (getYear() != that.getYear()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getCensusNo();
    result = 31 * result + (getGrade() != null ? getGrade().hashCode() : 0);
    result = 31 * result + getYear();
    return result;
  }

  @Override
  public String toString() {
    return "SMIDataArgs{"
        + "censusNo=" + getCensusNo()
        + ", grade=" + getGrade()
        + ", year=" + getYear()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull SMIDataArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int censusNo, @NonNull String grade, int year) {
      this.arguments.put("censusNo", censusNo);
      if (grade == null) {
        throw new IllegalArgumentException("Argument \"grade\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("grade", grade);
      this.arguments.put("year", year);
    }

    @NonNull
    public SMIDataArgs build() {
      SMIDataArgs result = new SMIDataArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setCensusNo(int censusNo) {
      this.arguments.put("censusNo", censusNo);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setGrade(@NonNull String grade) {
      if (grade == null) {
        throw new IllegalArgumentException("Argument \"grade\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("grade", grade);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setYear(int year) {
      this.arguments.put("year", year);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getCensusNo() {
      return (int) arguments.get("censusNo");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getGrade() {
      return (String) arguments.get("grade");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getYear() {
      return (int) arguments.get("year");
    }
  }
}

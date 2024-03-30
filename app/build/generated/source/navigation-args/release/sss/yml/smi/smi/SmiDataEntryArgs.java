package sss.yml.smi.smi;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sss.yml.smi.room.Student;

public class SmiDataEntryArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private SmiDataEntryArgs() {
  }

  @SuppressWarnings("unchecked")
  private SmiDataEntryArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings({
      "unchecked",
      "deprecation"
  })
  public static SmiDataEntryArgs fromBundle(@NonNull Bundle bundle) {
    SmiDataEntryArgs __result = new SmiDataEntryArgs();
    bundle.setClassLoader(SmiDataEntryArgs.class.getClassLoader());
    if (bundle.containsKey("censusNo")) {
      int censusNo;
      censusNo = bundle.getInt("censusNo");
      __result.arguments.put("censusNo", censusNo);
    } else {
      throw new IllegalArgumentException("Required argument \"censusNo\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("school")) {
      String school;
      school = bundle.getString("school");
      if (school == null) {
        throw new IllegalArgumentException("Argument \"school\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("school", school);
    } else {
      throw new IllegalArgumentException("Required argument \"school\" is missing and does not have an android:defaultValue");
    }
    if (bundle.containsKey("student")) {
      Student student;
      if (Parcelable.class.isAssignableFrom(Student.class) || Serializable.class.isAssignableFrom(Student.class)) {
        student = (Student) bundle.get("student");
      } else {
        throw new UnsupportedOperationException(Student.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
      __result.arguments.put("student", student);
    } else {
      throw new IllegalArgumentException("Required argument \"student\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static SmiDataEntryArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    SmiDataEntryArgs __result = new SmiDataEntryArgs();
    if (savedStateHandle.contains("censusNo")) {
      int censusNo;
      censusNo = savedStateHandle.get("censusNo");
      __result.arguments.put("censusNo", censusNo);
    } else {
      throw new IllegalArgumentException("Required argument \"censusNo\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("school")) {
      String school;
      school = savedStateHandle.get("school");
      if (school == null) {
        throw new IllegalArgumentException("Argument \"school\" is marked as non-null but was passed a null value.");
      }
      __result.arguments.put("school", school);
    } else {
      throw new IllegalArgumentException("Required argument \"school\" is missing and does not have an android:defaultValue");
    }
    if (savedStateHandle.contains("student")) {
      Student student;
      student = savedStateHandle.get("student");
      __result.arguments.put("student", student);
    } else {
      throw new IllegalArgumentException("Required argument \"student\" is missing and does not have an android:defaultValue");
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getCensusNo() {
    return (int) arguments.get("censusNo");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public String getSchool() {
    return (String) arguments.get("school");
  }

  @SuppressWarnings("unchecked")
  @Nullable
  public Student getStudent() {
    return (Student) arguments.get("student");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("censusNo")) {
      int censusNo = (int) arguments.get("censusNo");
      __result.putInt("censusNo", censusNo);
    }
    if (arguments.containsKey("school")) {
      String school = (String) arguments.get("school");
      __result.putString("school", school);
    }
    if (arguments.containsKey("student")) {
      Student student = (Student) arguments.get("student");
      if (Parcelable.class.isAssignableFrom(Student.class) || student == null) {
        __result.putParcelable("student", Parcelable.class.cast(student));
      } else if (Serializable.class.isAssignableFrom(Student.class)) {
        __result.putSerializable("student", Serializable.class.cast(student));
      } else {
        throw new UnsupportedOperationException(Student.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    if (arguments.containsKey("school")) {
      String school = (String) arguments.get("school");
      __result.set("school", school);
    }
    if (arguments.containsKey("student")) {
      Student student = (Student) arguments.get("student");
      if (Parcelable.class.isAssignableFrom(Student.class) || student == null) {
        __result.set("student", Parcelable.class.cast(student));
      } else if (Serializable.class.isAssignableFrom(Student.class)) {
        __result.set("student", Serializable.class.cast(student));
      } else {
        throw new UnsupportedOperationException(Student.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
      }
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
    SmiDataEntryArgs that = (SmiDataEntryArgs) object;
    if (arguments.containsKey("censusNo") != that.arguments.containsKey("censusNo")) {
      return false;
    }
    if (getCensusNo() != that.getCensusNo()) {
      return false;
    }
    if (arguments.containsKey("school") != that.arguments.containsKey("school")) {
      return false;
    }
    if (getSchool() != null ? !getSchool().equals(that.getSchool()) : that.getSchool() != null) {
      return false;
    }
    if (arguments.containsKey("student") != that.arguments.containsKey("student")) {
      return false;
    }
    if (getStudent() != null ? !getStudent().equals(that.getStudent()) : that.getStudent() != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getCensusNo();
    result = 31 * result + (getSchool() != null ? getSchool().hashCode() : 0);
    result = 31 * result + (getStudent() != null ? getStudent().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SmiDataEntryArgs{"
        + "censusNo=" + getCensusNo()
        + ", school=" + getSchool()
        + ", student=" + getStudent()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull SmiDataEntryArgs original) {
      this.arguments.putAll(original.arguments);
    }

    @SuppressWarnings("unchecked")
    public Builder(int censusNo, @NonNull String school, @Nullable Student student) {
      this.arguments.put("censusNo", censusNo);
      if (school == null) {
        throw new IllegalArgumentException("Argument \"school\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("school", school);
      this.arguments.put("student", student);
    }

    @NonNull
    public SmiDataEntryArgs build() {
      SmiDataEntryArgs result = new SmiDataEntryArgs(arguments);
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
    public Builder setSchool(@NonNull String school) {
      if (school == null) {
        throw new IllegalArgumentException("Argument \"school\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("school", school);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setStudent(@Nullable Student student) {
      this.arguments.put("student", student);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getCensusNo() {
      return (int) arguments.get("censusNo");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @NonNull
    public String getSchool() {
      return (String) arguments.get("school");
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    @Nullable
    public Student getStudent() {
      return (Student) arguments.get("student");
    }
  }
}

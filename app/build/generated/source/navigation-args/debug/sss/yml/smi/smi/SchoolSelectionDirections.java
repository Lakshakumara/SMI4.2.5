package sss.yml.smi.smi;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sss.yml.smi.R;
import sss.yml.smi.room.Student;

public class SchoolSelectionDirections {
  private SchoolSelectionDirections() {
  }

  @NonNull
  public static ActionNavSmiToSmi actionNavSmiToSmi(int censusNo, @NonNull String school,
      @Nullable Student student) {
    return new ActionNavSmiToSmi(censusNo, school, student);
  }

  @NonNull
  public static NavDirections actionNavSmiToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_nav_smi_to_homeView);
  }

  public static class ActionNavSmiToSmi implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionNavSmiToSmi(int censusNo, @NonNull String school, @Nullable Student student) {
      this.arguments.put("censusNo", censusNo);
      if (school == null) {
        throw new IllegalArgumentException("Argument \"school\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("school", school);
      this.arguments.put("student", student);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavSmiToSmi setCensusNo(int censusNo) {
      this.arguments.put("censusNo", censusNo);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavSmiToSmi setSchool(@NonNull String school) {
      if (school == null) {
        throw new IllegalArgumentException("Argument \"school\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("school", school);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavSmiToSmi setStudent(@Nullable Student student) {
      this.arguments.put("student", student);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
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

    @Override
    public int getActionId() {
      return R.id.action_nav_smi_to_smi;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavSmiToSmi that = (ActionNavSmiToSmi) object;
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
      if (getActionId() != that.getActionId()) {
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
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavSmiToSmi(actionId=" + getActionId() + "){"
          + "censusNo=" + getCensusNo()
          + ", school=" + getSchool()
          + ", student=" + getStudent()
          + "}";
    }
  }
}

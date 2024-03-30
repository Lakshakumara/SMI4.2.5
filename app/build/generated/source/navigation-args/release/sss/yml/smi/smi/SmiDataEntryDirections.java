package sss.yml.smi.smi;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sss.yml.smi.R;

public class SmiDataEntryDirections {
  private SmiDataEntryDirections() {
  }

  @NonNull
  public static NavDirections actionSmiToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_smi_to_homeView);
  }

  @NonNull
  public static ActionSmiToSMIData actionSmiToSMIData(int censusNo, @NonNull String grade,
      int year) {
    return new ActionSmiToSMIData(censusNo, grade, year);
  }

  public static class ActionSmiToSMIData implements NavDirections {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    private ActionSmiToSMIData(int censusNo, @NonNull String grade, int year) {
      this.arguments.put("censusNo", censusNo);
      if (grade == null) {
        throw new IllegalArgumentException("Argument \"grade\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("grade", grade);
      this.arguments.put("year", year);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSmiToSMIData setCensusNo(int censusNo) {
      this.arguments.put("censusNo", censusNo);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSmiToSMIData setGrade(@NonNull String grade) {
      if (grade == null) {
        throw new IllegalArgumentException("Argument \"grade\" is marked as non-null but was passed a null value.");
      }
      this.arguments.put("grade", grade);
      return this;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSmiToSMIData setYear(int year) {
      this.arguments.put("year", year);
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

    @Override
    public int getActionId() {
      return R.id.action_smi_to_SMIData;
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

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionSmiToSMIData that = (ActionSmiToSMIData) object;
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
      if (getActionId() != that.getActionId()) {
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
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionSmiToSMIData(actionId=" + getActionId() + "){"
          + "censusNo=" + getCensusNo()
          + ", grade=" + getGrade()
          + ", year=" + getYear()
          + "}";
    }
  }
}

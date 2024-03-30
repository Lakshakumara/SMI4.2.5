package sss.yml.smi.person;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sss.yml.smi.R;

public class GeneralDirections {
  private GeneralDirections() {
  }

  @NonNull
  public static NavDirections actionNavGeneralToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_nav_general_to_homeView);
  }

  @NonNull
  public static ActionNavGeneralToChildReg actionNavGeneralToChildReg() {
    return new ActionNavGeneralToChildReg();
  }

  @NonNull
  public static NavDirections actionNavGeneralToSetting() {
    return new ActionOnlyNavDirections(R.id.action_nav_general_to_setting);
  }

  public static class ActionNavGeneralToChildReg implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionNavGeneralToChildReg() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionNavGeneralToChildReg setChildId(int childId) {
      this.arguments.put("childId", childId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("childId")) {
        int childId = (int) arguments.get("childId");
        __result.putInt("childId", childId);
      } else {
        __result.putInt("childId", 0);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_nav_general_to_childReg;
    }

    @SuppressWarnings("unchecked")
    public int getChildId() {
      return (int) arguments.get("childId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavGeneralToChildReg that = (ActionNavGeneralToChildReg) object;
      if (arguments.containsKey("childId") != that.arguments.containsKey("childId")) {
        return false;
      }
      if (getChildId() != that.getChildId()) {
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
      result = 31 * result + getChildId();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavGeneralToChildReg(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }
}

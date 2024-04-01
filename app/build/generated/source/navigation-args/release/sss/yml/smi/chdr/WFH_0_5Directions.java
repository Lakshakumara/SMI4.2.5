package sss.yml.smi.chdr;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sss.yml.smi.R;

public class WFH_0_5Directions {
  private WFH_0_5Directions() {
  }

  @NonNull
  public static ActionWFH02ToWtpost actionWFH02ToWtpost() {
    return new ActionWFH02ToWtpost();
  }

  public static class ActionWFH02ToWtpost implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionWFH02ToWtpost() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionWFH02ToWtpost setChildId(int childId) {
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
      return R.id.action_WFH_0_2_to_wtpost;
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
      ActionWFH02ToWtpost that = (ActionWFH02ToWtpost) object;
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
      return "ActionWFH02ToWtpost(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }
}

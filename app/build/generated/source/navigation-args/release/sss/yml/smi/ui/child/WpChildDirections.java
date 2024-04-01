package sss.yml.smi.ui.child;

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

public class WpChildDirections {
  private WpChildDirections() {
  }

  @NonNull
  public static NavDirections actionWtpostToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_wtpost_to_homeView);
  }

  @NonNull
  public static NavDirections actionWtpostToHistory() {
    return new ActionOnlyNavDirections(R.id.action_wtpost_to_history);
  }

  @NonNull
  public static NavDirections actionWtpostToWFH02() {
    return new ActionOnlyNavDirections(R.id.action_wtpost_to_WFH_0_2);
  }

  @NonNull
  public static ActionWtpostToChildReg actionWtpostToChildReg() {
    return new ActionWtpostToChildReg();
  }

  @NonNull
  public static NavDirections actionWtpostToSetting() {
    return new ActionOnlyNavDirections(R.id.action_wtpost_to_setting);
  }

  public static class ActionWtpostToChildReg implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionWtpostToChildReg() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionWtpostToChildReg setChildId(int childId) {
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
      return R.id.action_wtpost_to_childReg;
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
      ActionWtpostToChildReg that = (ActionWtpostToChildReg) object;
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
      return "ActionWtpostToChildReg(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }
}

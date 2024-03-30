package sss.yml.smi.ui.setting;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sss.yml.smi.R;

public class SettingsDirections {
  private SettingsDirections() {
  }

  @NonNull
  public static ActionSettingToWtpost actionSettingToWtpost() {
    return new ActionSettingToWtpost();
  }

  @NonNull
  public static ActionSettingToNavGeneral actionSettingToNavGeneral() {
    return new ActionSettingToNavGeneral();
  }

  public static class ActionSettingToWtpost implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionSettingToWtpost() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSettingToWtpost setChildId(int childId) {
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
      return R.id.action_setting_to_wtpost;
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
      ActionSettingToWtpost that = (ActionSettingToWtpost) object;
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
      return "ActionSettingToWtpost(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }

  public static class ActionSettingToNavGeneral implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionSettingToNavGeneral() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionSettingToNavGeneral setChildId(int childId) {
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
      return R.id.action_setting_to_nav_general;
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
      ActionSettingToNavGeneral that = (ActionSettingToNavGeneral) object;
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
      return "ActionSettingToNavGeneral(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }
}

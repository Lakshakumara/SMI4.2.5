package sss.yml.smi.Home;

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

public class HomeViewDirections {
  private HomeViewDirections() {
  }

  @NonNull
  public static NavDirections actionHomeViewToAboutFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeView_to_aboutFragment);
  }

  @NonNull
  public static ActionHomeViewToWtpost actionHomeViewToWtpost() {
    return new ActionHomeViewToWtpost();
  }

  @NonNull
  public static NavDirections actionHomeViewToAdminFragment() {
    return new ActionOnlyNavDirections(R.id.action_homeView_to_adminFragment);
  }

  @NonNull
  public static NavDirections actionHomeViewToRate() {
    return new ActionOnlyNavDirections(R.id.action_homeView_to_rate);
  }

  @NonNull
  public static NavDirections actionHomeViewToNavDonate() {
    return new ActionOnlyNavDirections(R.id.action_homeView_to_nav_donate);
  }

  @NonNull
  public static NavDirections actionHomeViewToNavSmi() {
    return new ActionOnlyNavDirections(R.id.action_homeView_to_nav_smi);
  }

  @NonNull
  public static NavDirections actionHomeViewToNavProfile() {
    return new ActionOnlyNavDirections(R.id.action_homeView_to_nav_profile);
  }

  @NonNull
  public static ActionHomeViewToNavGeneral actionHomeViewToNavGeneral() {
    return new ActionHomeViewToNavGeneral();
  }

  public static class ActionHomeViewToWtpost implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionHomeViewToWtpost() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionHomeViewToWtpost setChildId(int childId) {
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
      return R.id.action_homeView_to_wtpost;
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
      ActionHomeViewToWtpost that = (ActionHomeViewToWtpost) object;
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
      return "ActionHomeViewToWtpost(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }

  public static class ActionHomeViewToNavGeneral implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionHomeViewToNavGeneral() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionHomeViewToNavGeneral setChildId(int childId) {
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
      return R.id.action_homeView_to_nav_general;
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
      ActionHomeViewToNavGeneral that = (ActionHomeViewToNavGeneral) object;
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
      return "ActionHomeViewToNavGeneral(actionId=" + getActionId() + "){"
          + "childId=" + getChildId()
          + "}";
    }
  }
}

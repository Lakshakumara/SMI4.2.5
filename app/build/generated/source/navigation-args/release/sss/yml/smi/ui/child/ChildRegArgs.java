package sss.yml.smi.ui.child;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavArgs;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class ChildRegArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private ChildRegArgs() {
  }

  @SuppressWarnings("unchecked")
  private ChildRegArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChildRegArgs fromBundle(@NonNull Bundle bundle) {
    ChildRegArgs __result = new ChildRegArgs();
    bundle.setClassLoader(ChildRegArgs.class.getClassLoader());
    if (bundle.containsKey("childId")) {
      int childId;
      childId = bundle.getInt("childId");
      __result.arguments.put("childId", childId);
    } else {
      __result.arguments.put("childId", 0);
    }
    return __result;
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static ChildRegArgs fromSavedStateHandle(@NonNull SavedStateHandle savedStateHandle) {
    ChildRegArgs __result = new ChildRegArgs();
    if (savedStateHandle.contains("childId")) {
      int childId;
      childId = savedStateHandle.get("childId");
      __result.arguments.put("childId", childId);
    } else {
      __result.arguments.put("childId", 0);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getChildId() {
    return (int) arguments.get("childId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
    Bundle __result = new Bundle();
    if (arguments.containsKey("childId")) {
      int childId = (int) arguments.get("childId");
      __result.putInt("childId", childId);
    } else {
      __result.putInt("childId", 0);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public SavedStateHandle toSavedStateHandle() {
    SavedStateHandle __result = new SavedStateHandle();
    if (arguments.containsKey("childId")) {
      int childId = (int) arguments.get("childId");
      __result.set("childId", childId);
    } else {
      __result.set("childId", 0);
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
    ChildRegArgs that = (ChildRegArgs) object;
    if (arguments.containsKey("childId") != that.arguments.containsKey("childId")) {
      return false;
    }
    if (getChildId() != that.getChildId()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getChildId();
    return result;
  }

  @Override
  public String toString() {
    return "ChildRegArgs{"
        + "childId=" + getChildId()
        + "}";
  }

  public static final class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(@NonNull ChildRegArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public ChildRegArgs build() {
      ChildRegArgs result = new ChildRegArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setChildId(int childId) {
      this.arguments.put("childId", childId);
      return this;
    }

    @SuppressWarnings({"unchecked","GetterOnBuilder"})
    public int getChildId() {
      return (int) arguments.get("childId");
    }
  }
}

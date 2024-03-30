package sss.yml.smi.ui.admin;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class AdminFragmentDirections {
  private AdminFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAdminFragmentToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_adminFragment_to_homeView);
  }

  @NonNull
  public static NavDirections actionAdminFragmentToNewsFragment() {
    return new ActionOnlyNavDirections(R.id.action_adminFragment_to_newsFragment);
  }

  @NonNull
  public static NavDirections actionAdminFragmentToUsersFragment() {
    return new ActionOnlyNavDirections(R.id.action_adminFragment_to_usersFragment);
  }

  @NonNull
  public static NavDirections actionNavAdminToRequestFragment() {
    return new ActionOnlyNavDirections(R.id.action_nav_admin_to_requestFragment);
  }
}

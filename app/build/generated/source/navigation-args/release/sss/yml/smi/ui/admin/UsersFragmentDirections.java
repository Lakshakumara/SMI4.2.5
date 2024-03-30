package sss.yml.smi.ui.admin;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class UsersFragmentDirections {
  private UsersFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionUsersFragmentToAdminFragment() {
    return new ActionOnlyNavDirections(R.id.action_usersFragment_to_adminFragment);
  }
}

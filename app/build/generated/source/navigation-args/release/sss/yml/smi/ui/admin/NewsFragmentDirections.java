package sss.yml.smi.ui.admin;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class NewsFragmentDirections {
  private NewsFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionNewsFragmentToAdminFragment() {
    return new ActionOnlyNavDirections(R.id.action_newsFragment_to_adminFragment);
  }
}

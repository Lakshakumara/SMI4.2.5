package sss.yml.smi.ui.admin;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class RequestFragmentDirections {
  private RequestFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionRequestFragmentToNavAdmin() {
    return new ActionOnlyNavDirections(R.id.action_requestFragment_to_nav_admin);
  }
}

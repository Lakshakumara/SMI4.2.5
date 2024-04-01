package sss.yml.smi.support;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class SupportDirections {
  private SupportDirections() {
  }

  @NonNull
  public static NavDirections actionNavDonateToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_nav_donate_to_homeView);
  }
}

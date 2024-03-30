package sss.yml.smi.ui.about;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class RateDirections {
  private RateDirections() {
  }

  @NonNull
  public static NavDirections actionRateToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_rate_to_homeView);
  }
}

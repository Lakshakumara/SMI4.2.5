package sss.yml.smi.ui.about;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import sss.yml.smi.R;

public class AboutFragmentDirections {
  private AboutFragmentDirections() {
  }

  @NonNull
  public static NavDirections actionAboutFragmentToHomeView() {
    return new ActionOnlyNavDirections(R.id.action_aboutFragment_to_homeView);
  }
}

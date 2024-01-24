package sss.yml.smi.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import sss.yml.smi.R;
import sss.yml.smi.util.Logger;
import sss.yml.smi.util.SMIConstants;


public class AdminFragment extends Fragment {
    Switch drawValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setMenuProvider();
        return inflater.inflate(R.layout.admin_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_admin_user).setOnClickListener(view1 -> NavHostFragment.findNavController(AdminFragment.this)
                .navigate(R.id.action_adminFragment_to_usersFragment));
        view.findViewById(R.id.btn_admin_news).setOnClickListener(view1 -> NavHostFragment.findNavController(AdminFragment.this)
                .navigate(R.id.action_adminFragment_to_newsFragment));
        view.findViewById(R.id.btn_admin_request).setOnClickListener(view1 -> NavHostFragment.findNavController(AdminFragment.this)
                .navigate(R.id.action_nav_admin_to_requestFragment));

        drawValues = view.findViewById(R.id.swDrawValue);
        drawValues.setChecked(SMIConstants.DRAW_VALUE);
        drawValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SMIConstants.DRAW_VALUE = drawValues.isChecked();
                Logger.setMsg(SMIConstants.DRAW_VALUE);
            }
        });

    }
   /* private void setMenuProvider(){
        MenuHost menuHost= requireActivity();
        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onPrepareMenu(@NonNull Menu menu) {
                MenuProvider.super.onPrepareMenu(menu);
            }

            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.search, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return true;
            }

            @Override
            public void onMenuClosed(@NonNull Menu menu) {
                MenuProvider.super.onMenuClosed(menu);
            }
        });
    }
*/
}

package abhinay.firstapp.project;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.google.android.material.navigation.NavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

    Button b;
    private DrawerLayout mDrawer;
    private NavigationView nvDrawer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this,callback);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_welcome, container, false);
        b = v.findViewById(R.id.explore);
        mDrawer = getActivity().findViewById(R.id.drawer_layout);
        b.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View view) {
        mDrawer.openDrawer(Gravity.LEFT);
    }
}
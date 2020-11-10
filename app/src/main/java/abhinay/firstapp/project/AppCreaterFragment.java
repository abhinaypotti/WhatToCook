package abhinay.firstapp.project;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;




/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class AppCreaterFragment extends Fragment {

    TextView abhi,kishore,praneeth,kousik;
    private DrawerLayout mDrawer;


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

        View v = inflater.inflate(R.layout.fragment_app_creater, container, false);
        mDrawer = getActivity().findViewById(R.id.drawer_layout);
        abhi = v.findViewById(R.id.textViewLink);
        abhi.setMovementMethod(LinkMovementMethod.getInstance());
        kishore = v.findViewById(R.id.textViewLink2);
        kishore.setMovementMethod(LinkMovementMethod.getInstance());
        praneeth = v.findViewById(R.id.textViewLink3);
        praneeth.setMovementMethod(LinkMovementMethod.getInstance());
        kousik = v.findViewById(R.id.textViewLink4);
        kousik.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }
}
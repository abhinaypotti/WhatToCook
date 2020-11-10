package abhinay.firstapp.project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class InstructionsFragment extends Fragment {


    TextView tv1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_instructions, container, false);
        tv1 = v.findViewById(R.id.instructions);
        String ins = getArguments().getString("instructions");
        ins = ins.replace("�"," ");
        tv1.setText(ins);
        return v;
    }
}
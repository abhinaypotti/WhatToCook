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
public class IngredientsFragment extends Fragment {


    TextView tv1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_ingredients, container, false);
        tv1 = v.findViewById(R.id.ingredients);
        String ing = getArguments().getString("ingredients");
        ing = ing.replace("�"," ");
        tv1.setText(ing);
        return v;
    }
}
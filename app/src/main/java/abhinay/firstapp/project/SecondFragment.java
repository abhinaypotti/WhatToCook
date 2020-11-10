package abhinay.firstapp.project;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ToggleButton;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {


    ToggleButton t1;//,t2;
    private ListView lv;
    private DrawerLayout mDrawer;
    //private EditText editText;
    private ArrayAdapter<String> adapter;
    DatabaseReference Rootref;
    HashMap<String,String> hm;
    private ArrayList<String> itemsarray;
    final int MYREQUEST = 11;

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
        View v = inflater.inflate(R.layout.fragment_second, container, false);
        t1 = v.findViewById(R.id.toggle1);
        mDrawer = getActivity().findViewById(R.id.drawer_layout);
       // t2 = v.findViewById(R.id.toggle2);
        hm = new HashMap<>();
        lv = v.findViewById(R.id.listView);

        t1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //t2.setChecked(false);
                    itemsarray = new ArrayList<>();
                   // t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_bg));
                    t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg));
                    Rootref = FirebaseDatabase.getInstance().getReference();
                    Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(int i=0;i<snapshot.getChildrenCount()-1;i++){
                                if(snapshot.child(String.valueOf(i)).exists()){
                                    Recipe r = snapshot.child(String.valueOf(i)).getValue(Recipe.class);
                                    //items[i] = r.getRecipe();
                                    String[] name = r.getRecipe().split("\\|");
                                    itemsarray.add(name[0]);
                                    hm.put(name[0],String.valueOf(i));
                                }
                            }
                            Collections.sort(itemsarray);
                            adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.itemname, itemsarray);
                            lv.setAdapter(adapter);
                            lv.setVisibility(View.VISIBLE);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                                    // TODO Auto-generated method stub
                                    final String[] value = new String[1];

                                    Rootref = FirebaseDatabase.getInstance().getReference();
                                    Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            String ix = adapter.getItem(position);
                                            String index = hm.get(ix);

                                            if (
                                                    snapshot.child(index).exists()) {
                                                Recipe r = snapshot.child(index).getValue(Recipe.class);
                                                //items[i] = r.getRecipe();
                                                value[0] = r.getInstructions();
                                                String[] ins = value[0].split("\\.");
                                                String in = r.getIngredients();
                                                String[] ing = in.split("/");
                                                Intent i = new Intent(getContext(), RecipeViewActivity.class);
                                                i.putExtra("instructions", ins);
                                                i.putExtra("ingredients", ing);
                                                startActivity(i);

//                                Toast.makeText(getApplicationContext(), value[0],Toast.LENGTH_SHORT).show();
//                                editText.setText(r.getRecipe());
                                            }

                                        }


                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });


                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });




                }
                else {
                    lv.setVisibility(View.INVISIBLE);
                    t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_bg));
                }
            }
        });


//        t2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    t1.setChecked(false);
//                    lv.setVisibility(View.INVISIBLE);
//                    t1.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_bg));
//                    t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg));
//                }
//                else {
//                    lv.setVisibility(View.INVISIBLE);
//                    t2.setBackgroundDrawable(getResources().getDrawable(R.drawable.toggle_bg));
//                }
//            }
//        });

        return v;
    }


}
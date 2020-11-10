package abhinay.firstapp.project;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    private ListView lv;
    private EditText editText;
    private ArrayAdapter<String> adapter;
    DatabaseReference Rootref;
    HashMap<String, String> hm;
    private ArrayList<String> itemsarray;
    final int MYREQUEST = 11;
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
        View v = inflater.inflate(R.layout.fragment_first, container, false);
        lv = v.findViewById(R.id.listView);
        editText = v.findViewById(R.id.editText);
        mDrawer = getActivity().findViewById(R.id.drawer_layout);
        itemsarray = new ArrayList<>();
        hm = new HashMap<>();
        initialize();
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.itemname, itemsarray);
        lv.setAdapter(adapter);



        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Log.d("Test", "on text changed");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                Log.d("test", "before text changed");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                Log.d("Test", "after text changed");
                adapter.getFilter().filter(arg0);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                        @Override
                        public void onItemClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                            // TODO Auto-generated method stub
                            final String[] value = new String[1];
                            Rootref = FirebaseDatabase.getInstance().getReference();
                            Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String ix = adapter.getItem(position);
                                    //Toast.makeText(getContext(), ""+ix, Toast.LENGTH_SHORT).show();
                                    String index = hm.get(ix);
                                    if (snapshot.child(index).exists()) {
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

        return v;

    }


    private void initialize(){
        Rootref = FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(int i=0;i<snapshot.getChildrenCount() - 1;i++){
                    if(snapshot.child(String.valueOf(i)).exists()){
                        Recipe r = snapshot.child(String.valueOf(i)).getValue(Recipe.class);
                        //items[i] = r.getRecipe();

                        String[] name = r.getRecipe().split("\\|");
                        itemsarray.add(name[0]);
                        hm.put(name[0],String.valueOf(i));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}

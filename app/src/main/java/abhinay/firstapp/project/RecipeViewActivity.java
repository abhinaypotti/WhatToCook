package abhinay.firstapp.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import com.google.android.material.tabs.TabLayout;

public class RecipeViewActivity extends AppCompatActivity implements View.OnClickListener {

    //TextView tv1,tv2;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageButton b1,b2;
    String instruc = "";
    String ingre = "";
    IngredientsFragment igf;
    InstructionsFragment inf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        Bundle b = getIntent().getExtras();
        String[] ing = b.getStringArray("ingredients");
        String[] ins = b.getStringArray("instructions");
        for(int i=1;i<=ing.length;i++){
            ingre = ingre + i +"."+ing[i-1]+"\n\n";

        }
//        Bundle data = new Bundle();
//        igf = new IngredientsFragment();
//        data.putString("ingredients",ingre);
//        igf.setArguments(data);

        for(int i=1;i<=ins.length;i++){
            Log.d("Recipe",instruc+" ");
            instruc = instruc + i +"."+ins[i-1]+"\n\n";
        }
//        Bundle data2 = new Bundle();
//        inf = new InstructionsFragment();
//        data2.putString("instructions",instruc);
//        inf.setArguments(data2);
        tabLayout.addTab(tabLayout.newTab().setText("Ingredients"));
        tabLayout.addTab(tabLayout.newTab().setText("Instructions"));
//        tabLayout.addTab(tabLayout.newTab().setText("NBA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(),
                tabLayout.getTabCount(),ingre,instruc);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

//        Bundle b = getIntent().getExtras();
//        String[] ing = b.getStringArray("ingredients");
//        String[] ins = b.getStringArray("instructions");
//        tv1 = findViewById(R.id.ingredients);
//        tv2 = findViewById(R.id.instructions);
//        b1 = findViewById(R.id.ing_btn);
//        b2 = findViewById(R.id.ins_btn);
//        b1.setOnClickListener(this);
//        b2.setOnClickListener(this);



//        for(int i=1;i<=ing.length;i++){
//            ingre = ingre + i +"."+ing[i-1]+"\n\n";
//
//        }
//
//        for(int i=1;i<=ins.length;i++){
//            Log.d("Recipe",instruc+" ");
//            instruc = instruc + i +"."+ins[i-1]+"\n\n";
//        }
//        Toast.makeText(this, instruc+ " ", Toast.LENGTH_SHORT).show();
//        tv1.setText(ingre);
//        tv2.setText(instruc);

    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == R.id.ing_btn){
//            Bundle data = new Bundle();
//            igf = new IngredientsFragment();
//            data.putString("ingredients",ingre);
//            igf.setArguments(data);
//            loadFragment(igf);
//
//        }
//        if(view.getId() == R.id.ins_btn){
//            Bundle data = new Bundle();
//            inf = new InstructionsFragment();
//            data.putString("instructions",instruc);
//            inf.setArguments(data);
//            loadFragment(inf);
//        }

    }

    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
//        FragmentManager fm = getSupportFragmentManager();
//
//        // create a FragmentTransaction to begin the transaction and replace the Fragment
//        FragmentTransaction fragmentTransaction =
//                fm.beginTransaction();
//
//        // replace the FrameLayout with new Fragment
//        fragmentTransaction.replace(R.id.recipe_fragment, fragment);
//
//        //fragmentTransaction.add(R.id.firstFragment,fragment);
//        fragmentTransaction.commit(); // save the changes
    }


}
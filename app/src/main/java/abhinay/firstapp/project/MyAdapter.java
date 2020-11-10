package abhinay.firstapp.project;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    String ingre;
    String instruc;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs, String ing, String ins) {
        super(fm);
        context = c;
        ingre = ing;
        instruc = ins;
        this.totalTabs = totalTabs;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                IngredientsFragment tab1 = new IngredientsFragment();
                Bundle data = new Bundle();
                data.putString("ingredients",ingre);
                tab1.setArguments(data);
                return tab1;
            case 1:
                InstructionsFragment tab2 = new InstructionsFragment();
                Bundle data2 = new Bundle();
                data2.putString("instructions",instruc);
                tab2.setArguments(data2);

                return tab2;
//            case 2:
//                NBA nbaFragment = new NBA();
//                return nbaFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}

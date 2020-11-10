package abhinay.firstapp.project;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    RecyclerView recyclerView;
//    Button b;
    Vector<YouTubeVideos> youtubeVideos;// = new Vector<YouTubeVideos>();
//    DatabaseReference Rootref;
    private ListView lv;
    private EditText editText;
    private DrawerLayout mDrawer;
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
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        lv = v.findViewById(R.id.listView);
        mDrawer = getActivity().findViewById(R.id.drawer_layout);
        editText = v.findViewById(R.id.editText);
        itemsarray = new ArrayList<>();
        hm = new HashMap<>();
        initialize();
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
        adapter = new ArrayAdapter<String>(getContext(), R.layout.list_item, R.id.itemname, itemsarray);
        lv.setAdapter(adapter);

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                Log.d("Test", "on text changed");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // Toast.makeText(getApplicationContext(), "before text change", Toast.LENGTH_LONG).show();
                Log.d("test", "before text changed");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // Toast.makeText(getApplicationContext(), "after text change", Toast.LENGTH_LONG).show();
                Log.d("Test", "after text changed");
                adapter.getFilter().filter(arg0);
            }
        });

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
                        //Toast.makeText(getContext(), ""+ix, Toast.LENGTH_SHORT).show();
                        String index = hm.get(ix);
                        if (snapshot.child(index).exists()) {
                            youtubeVideos = new Vector<>();
                            VideoUrl u = snapshot.child(index).getValue(VideoUrl.class);
                            //items[i] = r.getRecipe();
                            String[] uarray = u.getUrl().split("/");

                            String urllink = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + uarray[uarray.length - 1] + "\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
                            //youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src="+urllink+" frameborder=\"0\" allowfullscreen></iframe>") );
                            youtubeVideos.add(new YouTubeVideos(urllink));


                        }
                        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

                        recyclerView.setAdapter(videoAdapter);
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
                for(int i=0;i<snapshot.getChildrenCount() -1 ;i++){
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





//        recyclerView = v.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager( new LinearLayoutManager(getActivity()));
//        b = v.findViewById(R.id.button);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Rootref = FirebaseDatabase.getInstance().getReference();
//
//                Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(int i=0;i<snapshot.getChildrenCount() -1 ;i++){
//                            if(snapshot.child(String.valueOf(i)).exists()){
//                                VideoUrl u = snapshot.child(String.valueOf(i)).getValue(VideoUrl.class);
//                                //items[i] = r.getRecipe();
//                                String[] uarray = u.getUrl().split("/");
//
//                                String urllink = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/"+uarray[uarray.length -1]+"\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
//                                //youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src="+urllink+" frameborder=\"0\" allowfullscreen></iframe>") );
//                                youtubeVideos.add(new YouTubeVideos(urllink));
//
//                            }
//                        }
//                        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
//
//                        recyclerView.setAdapter(videoAdapter);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//            }
//        });
//        Rootref = FirebaseDatabase.getInstance().getReference();
//
//        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(int i=0;i<snapshot.getChildrenCount() -1 ;i++){
//                    if(snapshot.child(String.valueOf(i)).exists()){
//                        VideoUrl u = snapshot.child(String.valueOf(i)).getValue(VideoUrl.class);
//                        //items[i] = r.getRecipe();
//                        String urllink = u.getUrl();
//                        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src="+urllink+" frameborder=\"0\" allowfullscreen></iframe>") );
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });



//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/eWEF1Zrmdow\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KyJ71G2UxTQ\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/y8Rr39jKFKU\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/8Hg1tqIwIfI\" frameborder=\"0\" allowfullscreen></iframe>") );
//        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/uhQ7mh_o_cM\" frameborder=\"0\" allowfullscreen></iframe>") );

//        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);
//
//        recyclerView.setAdapter(videoAdapter);

       // return v;
    }
}
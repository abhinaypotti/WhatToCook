package abhinay.firstapp.project;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;



/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class IndianHealthyFragment extends Fragment {

    private WebView webView;
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
        View v = inflater.inflate(R.layout.fragment_indian_healthy, container, false);
        CustomWebViewClient client = new CustomWebViewClient(getActivity());
        mDrawer = getActivity().findViewById(R.id.drawer_layout);
        webView = v.findViewById(R.id.webView);
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://www.indianhealthyrecipes.com/");

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction()!=KeyEvent.ACTION_DOWN)
                    return true;
                if (i == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    } else {
                        //((HomeNavigationActivity)getActivity()).onBackPressed();
                        mDrawer.openDrawer(Gravity.LEFT);
                        return true;

                    }

                }
                return false;
            }
        });

        return v;
    }


}

class CustomWebViewClient extends WebViewClient {
    private Activity activity;
    public CustomWebViewClient(Activity activity){
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url){
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request){
        return false;
    }
}


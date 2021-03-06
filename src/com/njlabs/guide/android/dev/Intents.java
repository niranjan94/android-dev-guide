package com.njlabs.guide.android.dev;

import java.io.IOException;
import java.io.InputStream;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.SubMenu;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.AssetManager;

public class Intents extends SherlockActivity {
	
	String title=null;
	String codesnippet=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intents);
		
		Bundle extras = getIntent().getExtras();
		title = extras.getString("title");
		codesnippet = extras.getString("codesnippet");
		ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
        
        TextView textView = (TextView) findViewById(R.id.textViewIntents);
        if(title.equals("What is an Intent?"))
        {
        	textView.setText("Intents are asynchronous messages which allow Android components to request functionality from other components of the Android system. Intents can be used to signal to the Android system that a certain event has occurred. Other components in Android can register to this event via an intent filter.\n\n Intents are send to the Android system via a method call, e.g. via the startActivity() method you can start activities. Depending on how the Intent was constructed the Android system will run an receiver determination and determine possible components which can be started. If several components have registered for the same intents the user can decide which component should be started.");
        }
        else if(title.equals("Opening a new screen"))
        {
        	textView.setText("In an Android application, Each new screen is an Activity class. And to open the screen you need to use an Intent.\n\nIntents can also be used to send data/paramenter to new Activities or to get the result back from the newly opened Activity.\n\n The below examples clearly explain how to use a basic intent.\n\n NOTE: all new Activities must be declared in the Manifest Before being used.");
        }
        else if(title.equals("Intents Quick reference"))
        {
        	textView.setText("Here is a list of some common intents that can be quite useful while developing Android Applications.");
        }
        WebView webView = (WebView) findViewById(R.id.webViewJava);
        webView.setWebViewClient(new WebViewClient(){
        	@TargetApi(11)
        	@Override
        	public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        	    Log.d("shouldInterceptRequest", url);

        	    InputStream stream = inputStreamForAndroidResource(url);
        	    if (stream != null) {
        	        return new WebResourceResponse("text/javascript", "utf-8", stream);
        	    }
        	    return super.shouldInterceptRequest(view, url);
        	}

        	private InputStream inputStreamForAndroidResource(String url) {
        	    final String ANDROID_ASSET = "file:///android_asset/";

        	    if (url.contains(ANDROID_ASSET)) {
        	        url = url.replaceFirst(ANDROID_ASSET, "");
        	        try {
        	            AssetManager assets = getAssets();
        	            Uri uri = Uri.parse(url);
        	            return assets.open(uri.getPath(), AssetManager.ACCESS_STREAMING);
        	        } catch (IOException e) {
        	            e.printStackTrace();
        	        }
        	    }
        	    return null;
        	}        	
        	
        });
        Button button = (Button) findViewById(R.id.demo_btn_Intents);
        if(title.equals("What is an Intent?"))
        {
        	TextView textView2 = (TextView) findViewById(R.id.textViewIntents2);
        	textView2.setVisibility(View.GONE); 
        	webView.setVisibility(View.GONE);        	
        	button.setVisibility(View.GONE);        	      	
        }
        else if(title.equals("Opening a new screen"))
        {
        	button.setVisibility(View.GONE);  
        	webView.getSettings().setJavaScriptEnabled(true);		
        	webView.loadUrl("file:///android_asset/code_snippets/"+codesnippet+"_java.html");
        }
        else
        {
        	webView.getSettings().setJavaScriptEnabled(true);	
        	webView.loadUrl("file:///android_asset/code_snippets/"+codesnippet+"_java.html");
        }
	}
	public void DemoIntents(View view)
	{
		Intent intent1 = new Intent(this, BitmapCanvas.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
	}
    private Menu mainMenu;
    private SubMenu subMenu1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        mainMenu = menu;

        subMenu1 = menu.addSubMenu("Options");
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.main, subMenu1);

        MenuItem subMenu1Item = subMenu1.getItem();
        subMenu1Item.setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_dark);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }  
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
            	finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                return true;
            case R.id.AboutAppOption:
                Intent intent1 = new Intent(this, AboutApp.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                return true;
            case R.id.BugReportOption:
                Intent intent2 = new Intent(this, BugReport.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
        
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
                mainMenu.performIdentifierAction(subMenu1.getItem().getItemId(), 0);
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }
}

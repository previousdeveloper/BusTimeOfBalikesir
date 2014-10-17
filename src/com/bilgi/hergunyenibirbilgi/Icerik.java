package com.bilgi.hergunyenibirbilgi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.startapp.android.publish.StartAppAd;

public class Icerik extends Activity{
ImageView image;
String SCAN_PATH;
File[] allFiles ;

private StartAppAd startAppAd = new StartAppAd(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.icerik);
		
		 StartAppAd startAppAd = new StartAppAd(this);
			StartAppAd.init(this, " 104746054", "206587471");
			
		image=(ImageView) findViewById(R.id.itemImage);
        Intent in = getIntent();
        
       
     
        
        // Get JSON values from previous intent
        
        String email = in.getStringExtra("url");
        
        Picasso.with(getApplicationContext()).load(email).into(image);

	}
	@Override
	public void onResume() {
	    super.onResume();
	    startAppAd.onResume();
	}
	@Override
	public void onBackPressed() {
	    startAppAd.onBackPressed();
	    super.onBackPressed();
	}
	@Override
	public void onPause() {
	    super.onPause();
	    startAppAd.onPause();
	}
	public void btnOpenActivity (View view){
	    startAppAd.showAd();
	    startAppAd.loadAd();
	    Intent nextActivity = new Intent(this, Icerik.class);
	    startActivity(nextActivity);
	}
	
	

	
	
	
	
	
	
	
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	    
	     switch (item.getItemId()) {
	     case R.id.indir:
				

	    Random r=new Random();
	    	 int randomSayi=r.nextInt(1000);
	    	 
	    	 String yazdir="Foto"+randomSayi+".jpg";

	    	    image.buildDrawingCache();
	    	    
			    Bitmap bm=image.getDrawingCache();
			    OutputStream fOut = null;
			    Uri outputFileUri;
			     try {
			    File root = new File(Environment.getExternalStorageDirectory()
			      + File.separator + "Hergunbilgi" + File.separator);
			    root.mkdirs();
			   File sdImageMainDirectory = new File(root,yazdir);
			    outputFileUri = Uri.fromFile(sdImageMainDirectory);
			    fOut = new FileOutputStream(sdImageMainDirectory);
			    
			   } catch (Exception e) {
			   
			   }

			   try {
			    bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
			    fOut.flush();
			    fOut.close();
			    
			   } catch (Exception e) {
			   }
			   
		    	 Toast.makeText(getApplicationContext(), "KAYDEDILDI", Toast.LENGTH_LONG).show();
		    	 galleryAddPic();
	         return true;
	         
	     case R.id.paylas:
	    	paylas();
	    	 return true;
	    	 
	    	
	    	 
	     default:
	         return super.onOptionsItemSelected(item);
	     }
	 }
	private void galleryAddPic() {
	    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
	    File f = new File(Environment.getExternalStorageDirectory()
			      + File.separator + "Hergunbilgi" + File.separator);
	    Uri contentUri = Uri.fromFile(f);
	    mediaScanIntent.setData(contentUri);
	    this.sendBroadcast(mediaScanIntent);
	}
	
	 public void paylas(){
    	 Uri uri = Uri.parse("market://details?id=" + getApplicationContext().getPackageName());
    	 Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
    	 try {
    	   startActivity(goToMarket);
    	 } catch (ActivityNotFoundException e) {
    	   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
    	 }
    	 }
	

}

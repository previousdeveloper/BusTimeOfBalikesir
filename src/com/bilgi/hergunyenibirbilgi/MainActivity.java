package com.bilgi.hergunyenibirbilgi;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActivity extends ListActivity {
	private ProgressDialog pDialog;
	ListAdapter adapter;
	String baslik2,urlFoto,tarih;
	private static String url = "http://www.gokhankaradas.com/Webservice/bilgi.php";
	JSONArray contacts = null;
	ArrayList<HashMap<String,String>> contactList;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<HashMap<String, String>>();

        
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),Icerik.class);
				in.putExtra("baslik",contactList.get(+position).get("baslik"));
				in.putExtra("url",contactList.get(+position).get("url"));
				startActivity(in);

			}
		});
		
		new GetContacts().execute();
		
		
        
    }

   
   
    
    private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Lütfen Bekleyiniz !");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					
					
					
					
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					contacts = jsonObj.getJSONArray("beykent");

					// looping through All Contacts
					for (int i = 0; i < contacts.length(); i++) {
						JSONObject c = contacts.getJSONObject(i);
						
						baslik2 = c.getString("baslik");
						urlFoto = c.getString("url");
						tarih = c.getString("tarih");

						
						

						// tmp hashmap for single contact
						HashMap<String, String> contact = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						contact.put("baslik",baslik2);
						contact.put("url",urlFoto);
                        contact.put("tarih",tarih);
						// adding contact to contact list
						contactList.add(contact);
						
						
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
				
					
				
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			adapter = new SimpleAdapter(
					MainActivity.this, contactList,
					R.layout.list_item, new String[] { "baslik","tarih"}, new int[] { R.id.baslik,R.id.tarihTxt});

			setListAdapter(adapter);
		}

	}
}

package com.example.pruebawebservice;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		 String opcion=null;
	     switch(position){
	     case 0:
	    	 opcion="Opcion_0";
	    	 break;
	     case 1:
	    	 opcion="Opcion_1";
	    	 break;
	     case 2:
	    	 opcion="Opcion_2";
	    	 break;
	     case 3:
	    	 opcion="Opcion_3";
	    	 break;
	     case 4:
	    	 opcion="Opcion_4";
	    	 break;
	     case 5:
	    	 opcion="Opcion_5";
	    	 break;
	     }
		    try {
		    	Toast.makeText(
						this,opcion ,Toast.LENGTH_LONG)
						.show();
		    	Class<?> clase=Class.forName("com.example.pruebawebservice."+opcion);
				Intent intent = new Intent(this,clase);
				ArrayList<String> datos = new ArrayList<String>();
				datos.add(countryList.get(position).getName());
				datos.add(countryList.get(position).getContinent());
				Bundle envio = new Bundle();
				envio.putStringArrayList("envio",datos);
				intent.putExtras(envio);
				
				startActivity(intent);
				
		    } catch (Exception e) {
		    	Toast.makeText(
						this,e.getMessage(),Toast.LENGTH_LONG)
						.show();
				// TODO: handle exception
			}
	}

	private static final String LOG_TAG = "JSONStreamReader";
	private ArrayList<Country> countryList = new ArrayList<Country>();
	private boolean success = false;
	private MyCustomAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);

		// create an ArrayAdaptar from the String Array
		dataAdapter = new MyCustomAdapter(this, R.layout.country_row,
				countryList);
		//ListView listView = (ListView) findViewById(R.id.countrylistView);
		// Assign adapter to ListView
		
		
		setListAdapter(dataAdapter);

		// URI to get the JSON stream data array of countries
		String url = "http://demo.mysamplecode.com/Servlets_JSP/CountryJSONData";
		new MyAsyncTask().execute(url);

	}
	
	

	private void displayCountries() {
		// if the request was successful then notify the adapter to display the
		// data
		if (success) {
			dataAdapter.notifyDataSetChanged();
		    Log.d("Fin","Fin");
		}
	}


  // custom array adapter to display our custom row layout for the listview
	private class MyCustomAdapter extends ArrayAdapter<Country> {

		public MyCustomAdapter(Context context, int textViewResourceId,
				ArrayList<Country> countryList) {
			super(context, textViewResourceId, countryList);
		}

		private class ViewHolder {
			TextView code;
			TextView name;
			TextView continent;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.country_row, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView.findViewById(R.id.code);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.continent = (TextView) convertView
						.findViewById(R.id.continent);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Country country = countryList.get(position);
			holder.code.setText(country.getCode());
			holder.name.setText(country.getName());
			holder.continent.setText(country.getContinent());

			return convertView;

		}

	}

	// asynchronous task to get our JSON data with holding up the main thread
	private class MyAsyncTask extends AsyncTask<String, Void, Void> {

		private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		private static final int WAIT_TIMEOUT = 30 * 1000;
		private final HttpClient httpclient = new DefaultHttpClient();

		final HttpParams params = httpclient.getParams();
		private boolean error = false;

		protected Void doInBackground(String... urls) {

			String URL = null;

			try {

				// URL passed to the AsyncTask
				URL = urls[0];
				HttpConnectionParams.setConnectionTimeout(params,
						REGISTRATION_TIMEOUT);
				HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
				ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

				HttpPost httpPost = new HttpPost(URL);

				// Response from the Http Request
				HttpResponse response = httpclient.execute(httpPost);

				// Check the Http Request for success
				StatusLine statusLine = response.getStatusLine();
				if (statusLine.getStatusCode() == HttpStatus.SC_OK) {

					Gson gson = new Gson();
					// create a new JSON reader from the response input stream
					JsonReader jsonReader = new JsonReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					// begin parsing
					jsonReader.beginObject();
					// stay in loop as long as there are more data elements
					while (jsonReader.hasNext()) {
						// get the element name
						String name = jsonReader.nextName();

						if (name.equals("success")) {
							success = jsonReader.nextBoolean();
						}
						// if the element name is the list of countries then
						// start the array
						else if (name.equals("countryList")) {
							jsonReader.beginArray();
							while (jsonReader.hasNext()) {
								// parse every element and convert that to a
								// country object
								Country country = gson.fromJson(jsonReader,
										Country.class);
								// add the country object to the list
								countryList.add(country);
							}
							jsonReader.endArray();
						}
					}
					// end reader and close the stream
					jsonReader.endObject();
					jsonReader.close();

				} else {
					// Closes the connection.
					Log.w(LOG_TAG, statusLine.getReasonPhrase());
					response.getEntity().getContent().close();
					throw new IOException(statusLine.getReasonPhrase());
				}

			} catch (Exception e) {
				Log.w(LOG_TAG, e);
				error = true;
				cancel(true);
			}

			return null;

		}

		protected void onCancelled() {
			Log.e(LOG_TAG, "Error occured during data download");
		}

		protected void onPostExecute(Void unused) {
			if (error) {
				Log.e(LOG_TAG, "Data download ended abnormally!");
			} else {
				displayCountries();
			}
		}

	}
}
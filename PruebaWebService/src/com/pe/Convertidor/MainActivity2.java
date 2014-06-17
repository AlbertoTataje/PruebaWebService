package com.pe.Convertidor;

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
import com.example.pruebawebservice.R;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class MainActivity2 extends Activity{

	/**
	 * @param args
	 */
	
	private static final String LOG_TAG = "JSONStreamReader";
	private ArrayList<Volume> volumeList = new ArrayList<Volume>();
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
         new MyAsyncTask().execute("http://10.0.2.2/android/servicio.json");
	}
	
	
	private class MyAsyncTask extends AsyncTask<String, Void, Void> {

		private static final int REGISTRATION_TIMEOUT = 3 * 1000;
		private static final int WAIT_TIMEOUT = 30 * 1000;
		private final HttpClient httpclient = new DefaultHttpClient();
		final HttpParams params = httpclient.getParams();
		private boolean error = false;
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Log.d("Fin", "23354544646");
		   
			Log.d("Resultado",volumeList.get(1).getSupport().getReasons().get(0));
			
			/*
			HashMap<String, String> hm = new HashMap<String,String>();
			for(Volume v: vc.getVolumes()){
			  hm.put(v.getId(), v.getName());  
			}
			*/
		}

		@Override
		protected Void doInBackground(String... urls) {
			// TODO Auto-generated method stub
			
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
					
					JsonReader jsonReader = new JsonReader(
							new InputStreamReader(response.getEntity()
									.getContent(), "UTF-8"));
					    Log.d("Conexio","Buena Cponexion");
					 	
					 	
					 	jsonReader.beginObject();
					 	while(jsonReader.hasNext()){
					 		
					 		String name = jsonReader.nextName();
					 		
					 		if(name.equals("volumes")){
					 			jsonReader.beginArray();
					 			while(jsonReader.hasNext()){
					 				Volume vo = gson.fromJson(jsonReader,
											Volume.class);
					 				//agregamos al arrayList
					 				volumeList.add(vo);
					 			     
					 			}
					 			jsonReader.endArray();
					 		}
					 		
					 	}
					 	
					 	jsonReader.endObject();
						jsonReader.close();
					 
						return null;
					 
					
				

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
	          
	}
}

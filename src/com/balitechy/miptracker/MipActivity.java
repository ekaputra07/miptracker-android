package com.balitechy.miptracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MipActivity extends Activity {
	
	private Button check_btn;
	private TextView my_ip;
	private final String url = "http://miptracker-app.appspot.com/plain/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mip);
		
		check_btn = (Button) findViewById(R.id.check_btn);
		my_ip = (TextView) findViewById(R.id.ip_text);
		
		check_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetIP().execute(url);
			}
		});
	}
	
	private class GetIP extends AsyncTask<String, Void, String>{
		private String btn_text;
		
		
		@Override
		protected void onPreExecute() {
			btn_text = MipActivity.this.check_btn.getText().toString();
			MipActivity.this.check_btn.setText("Loading...");
		}

		@Override
		protected String doInBackground(String... urls) {
			String ip = "Failed!";
			
			try {
				URL url = new URL(urls[0]);
				
				URLConnection conn = url.openConnection();
				conn.setRequestProperty("User-Agent", "Miptracker for Android v0.1.0.");
				
				InputStreamReader isreader = new InputStreamReader(conn.getInputStream());
				BufferedReader bfreader = new BufferedReader(isreader);
				
				ip = bfreader.readLine();
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return ip;
		}

		@Override
		protected void onPostExecute(String result) {
			MipActivity.this.my_ip.setText(result);
			MipActivity.this.check_btn.setText(this.btn_text);
		}
	}
}


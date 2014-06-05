package com.myproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Diff_Post_Http extends Activity implements OnClickListener {
	private String mTitle = "Write.My.Action";
	private static final String LOGTAG = "tag";
	public EditText username, password;
	private Button login, register; 
	private ProgressDialog mDialog;
	
	JSONParser jsonParser = new JSONParser();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_sucess);
		getActionBar().setTitle(mTitle);
		
		username = (EditText) findViewById(R.id.email);
	
		password = (EditText) findViewById(R.id.password);
		
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		
		//login.setOnClickListener(this);
		//register.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.login:
			if((username.getText().toString().length() < 1) || (password.getText().toString().length() < 1)){
				
				
				
			}else{
				//new AttemptLogin().execute();
				break;
			}
		case R.id.register:
			//Intent intent = new Intent(MainActivity.this, Register.class);
			//startActivity(intent);
			break;
			default:
				break;
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	class AttemptLogin extends AsyncTask<String, String, String>{
		
		boolean failure = false;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mDialog = new ProgressDialog(Diff_Post_Http.this);
			mDialog.setMessage("Attempting to Login...");
			mDialog.setIndeterminate(false);
			mDialog.setCancelable(false);
			mDialog.show();
			
		}
		@Override
		protected String doInBackground(String... arg0) {
			
			try {
				String input_username = username.getText().toString();
				String input_password = password.getText().toString();
				
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
				nameValuePair.add(new BasicNameValuePair("username", input_username));
				nameValuePair.add(new BasicNameValuePair("password", input_password));
				
				String link = "http://psmovers.com.au/login.php";
				
				String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(input_username, "UTF-8");
				data+="&" +URLEncoder.encode("password", "UTF-8")+ "=" + URLEncoder.encode(input_password, "UTF-8");
				//Log.i(LOGTAG, "Data is "+ data);
				URL url = new URL(link);
				
				URLConnection connection = url.openConnection();
			
				
				connection.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
//				Log.i(LOGTAG, "Wr value is " + wr);
				wr.write(data);
				wr.flush();
				wr.close();
				String line = null; 
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
				StringBuilder sb = new StringBuilder();
				
				//Read server response
				
				while((line=reader.readLine()) != null){
					
					sb.append(line + "\n");
					
				}
				reader.close();
				return sb.toString();
				
			} catch (Exception e) {
				Log.i(LOGTAG, "Exception:" + e.toString());
			}
			
			
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			mDialog.setTitle("Login Success");
			mDialog.dismiss();
			
			Toast.makeText(Diff_Post_Http.this, "Result is :" + result, Toast.LENGTH_LONG).show();
		
		}
		
	}

}

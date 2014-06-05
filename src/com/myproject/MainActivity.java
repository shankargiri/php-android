package com.myproject;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private String mTitle = "Write.My.Action";
	private static final String LOGTAG = "tag";
	public EditText email, password;
	private Button login, register,checkBtn; 
	private ProgressDialog mDialog;
	
	static private final String DEVELOPER_KEY = "AIzaSyBHifwJPB8PpLC9kE6IEdSp0tSCAPpTNLo";
    static private final String VIDEO = "4SK0cUNMnMM";
    
	private MainFragment mainFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 if (savedInstanceState == null) {
		        // Add the fragment on initial activity setup
		        mainFragment = new MainFragment();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, mainFragment)
		        .commit();
		        
		        setContentView(R.layout.activity_main);
				getActionBar().setTitle(mTitle);
				email = (EditText) findViewById(R.id.email);
			
				password = (EditText) findViewById(R.id.password);
				login = (Button) findViewById(R.id.login);
				register = (Button) findViewById(R.id.register);
				
				
		    } else {
		        // Or set the fragment from restored state info
		        mainFragment = (MainFragment) getSupportFragmentManager()
		        .findFragmentById(android.R.id.content);
		    }
		
		checkBtn = (Button) findViewById(R.id.check);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		checkBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.login:
			if((email.getText().toString().length() < 1) || (password.getText().toString().length() < 1)){
				
				Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_LONG).show();
				
			}else{
				mDialog = new ProgressDialog(MainActivity.this);
				mDialog.setMessage("Attempting to Login...");
				mDialog.setIndeterminate(false);
				mDialog.setCancelable(false);
				mDialog.show();
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						login();
						
					}
				}).start();
				break;
			}
		case R.id.register:
			Intent intent = new Intent(MainActivity.this, Register.class);
			startActivity(intent);
			break;
			default:
				break;
			case R.id.check:
				Toast.makeText(MainActivity.this, "I m lcicked", Toast.LENGTH_LONG).show();
			
		}
	
	}

	void login(){
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("your url");
				
			String email_input = email.getText().toString();
			String password_input = password.getText().toString();
			//add your data here
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("email", email_input));
			nameValuePair.add(new BasicNameValuePair("password", password_input));
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			
			//execute http post request
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpClient.execute(httpPost, responseHandler);
			
			System.out.println("Response: "+ response);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					//responseDisplay.setText("Resonse: " + response);
					mDialog.dismiss();
				}
			});
			
			if(response.equalsIgnoreCase("User Found")){
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						startActivity(new Intent(MainActivity.this, Login_Success.class));
					}
				});
				
			}else{
				showAlert();
			}
			
		} catch (Exception e) {
			mDialog.dismiss();
			Log.i(LOGTAG, "Exception found"+ e.getMessage());
			
		}
	}
	public void showAlert(){
		MainActivity.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Login Error");
				builder.setMessage("User Not Found")
						.setCancelable(false)
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
	}

//	class AttemptLogin extends AsyncTask<String, String, String>{
//		
//		boolean failure = false;
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			mDialog = new ProgressDialog(MainActivity.this);
//			mDialog.setMessage("Attempting to Login...");
//			mDialog.setIndeterminate(false);
//			mDialog.setCancelable(false);
//			mDialog.show();
//			
//		}
//		@Override
//		protected String doInBackground(String... arg0) {
//			
//			try {
//				String input_username = username.getText().toString();
//				String input_password = password.getText().toString();
//				
//				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
//				nameValuePair.add(new BasicNameValuePair("username", input_username));
//				nameValuePair.add(new BasicNameValuePair("password", input_password));
//				
//				String link = "http://psmovers.com.au/login.php";
//				
//				String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(input_username, "UTF-8");
//				data+="&" +URLEncoder.encode("password", "UTF-8")+ "=" + URLEncoder.encode(input_password, "UTF-8");
//				//Log.i(LOGTAG, "Data is "+ data);
//				URL url = new URL(link);
//				
//				URLConnection connection = url.openConnection();
//			
//				
//				connection.setDoOutput(true);
//				OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
////				Log.i(LOGTAG, "Wr value is " + wr);
//				wr.write(data);
//				wr.flush();
//				wr.close();
//				String line = null; 
//				
//				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			
//				StringBuilder sb = new StringBuilder();
//				
//				//Read server response
//				
//				while((line=reader.readLine()) != null){
//					
//					sb.append(line + "\n");
//					
//				}
//				reader.close();
//				return sb.toString();
//				
//			} catch (Exception e) {
//				Log.i(LOGTAG, "Exception:" + e.toString());
//			}
//			
//			
//			
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
//			mDialog.setTitle("Login Success");
//			mDialog.dismiss();
//			
//			Toast.makeText(MainActivity.this, "Result is :" + result, Toast.LENGTH_LONG).show();
//		
//		}
//		
//	}

}

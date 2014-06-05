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
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener{
	
	private String mTitle = "Write.My.Action";
	
	private static final String LOGTAG = "tag";
	public EditText fullname, email, password;
	private Button register; 
	private ProgressDialog mDialog; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		getActionBar().setTitle(mTitle);
		
		fullname = (EditText) findViewById(R.id.fullname);
		email = (EditText) findViewById(R.id.editText2);
		password = (EditText) findViewById(R.id.editText1);
		
		register = (Button) findViewById(R.id.button1);
		
		register.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			mDialog = new ProgressDialog(Register.this);
			mDialog.setMessage("Attempting to Register...");
			mDialog.setIndeterminate(false);
			mDialog.setCancelable(false);
			mDialog.show();
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					register();
					
				}
			}).start();
		}
		
	}
	
	 void register() {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://psmovers.com.au/__android_registration_success.php");
			
			System.out.println("httpPost is: " + httpPost);
			
			String fullname_input = fullname.getText().toString().trim();
			String email_input = email.getText().toString().trim();
			String password_input = password.getText().toString().trim();
			
			//adding data into list view so we can make post over the server
			
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
			nameValuePair.add(new BasicNameValuePair("fullname",  fullname_input));
			nameValuePair.add(new BasicNameValuePair("email",  email_input));
			nameValuePair.add(new BasicNameValuePair("password",  password_input));
			
			System.out.println("namevaluepair is: " + nameValuePair);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			
			//execute http post resquest
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			final String response = httpClient.execute(httpPost, responseHandler);
			
			System.out.println("Response is: " + response);
			
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					mDialog.dismiss();
					
				}
			});
			if(response.equalsIgnoreCase("Signed Up")){
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						startActivity(new Intent(Register.this, MainActivity.class));
						finish();
						Toast.makeText(Register.this, "Thank you for registering with us! Please, Sign in now!", Toast.LENGTH_LONG).show();
						
					}
				});
			}else {
				showAlert();
			}
			
		} catch (Exception e) {
			mDialog.dismiss();
			Log.i(LOGTAG, "Exception found"+ e.getMessage());
		}
		
	}
	 public void showAlert(){
		 Register.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
				builder.setTitle("Registration Error");
				builder.setMessage("Please, try registration again!")
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
}







package br.com.datasol;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReplicarEstoqueOff extends Activity implements Runnable{
	
	private Cursor c = null;
	private int totalDB = 0;
	private int total = 0;
	private ProgressDialog pg;
	private SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replicarestoqueoff);
		
		Button btnIniciar = (Button)findViewById(R.id.btnIniciar);
		TextView txvTotal = (TextView)findViewById(R.id.txvTotal);
		
		db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		c = db.rawQuery("SELECT * FROM estoq", null);
		totalDB = c.getCount();
		txvTotal.setText("Total Registros: " + String.valueOf(totalDB));
		
		if(totalDB == 0)
			btnIniciar.setEnabled(false);
		
		btnIniciar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					Builder msg = new Builder(ReplicarEstoqueOff.this);
					msg.setMessage("Deseja iniciar a replicação?");
					msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							pg = ProgressDialog.show(ReplicarEstoqueOff.this, "Aguarde...", "Replicando dados", true, false, null);
							Thread t = new Thread(ReplicarEstoqueOff.this);
							t.start();
						}
					});
					msg.setNegativeButton("NÃO", null);
					msg.show();
			}
		});
	}

	@Override
	public void run() {
		while(c.moveToNext()){
			// URL
			StringBuilder strURL = new StringBuilder();
			strURL.append("http://192.168.1.101/inserir.php?nome=");
			strURL.append(c.getString(c.getColumnIndex("nome")));
			strURL.append("&email=");
			strURL.append(c.getString(c.getColumnIndex("email")));
			
			// Transforma String em URL
			try {
				URL url = new URL(strURL.toString());
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				InputStreamReader ipr = new InputStreamReader(http.getInputStream());
				BufferedReader bf = new BufferedReader(ipr);
				
				if(bf.readLine().equals("Y")){
					total += 1;
					db.execSQL("DELETE FROM clientes WHERE id = " + c.getInt(c.getColumnIndex("id")));
				}
					
			} catch (Exception e) {
				//Toast.makeText(getBaseContext(), "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		handler.sendEmptyMessage(0);
	}
	
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg){
			pg.dismiss();
			if(total == totalDB){
				Toast.makeText(getBaseContext(), "Sucesso: total de " + total + "/" + totalDB, Toast.LENGTH_LONG).show();
			}
			finish();
		}
	};
}

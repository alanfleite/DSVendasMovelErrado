package br.com.datasol;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.com.datasol.auxilio.FormatarCampos;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReplicarVendasOff extends Activity implements Runnable{
	
	private Cursor rec = null;
	private Cursor prodvend = null;
	private int totalDBRec = 0;
	private int total = 0;
	private ProgressDialog pg;
	private SQLiteDatabase db;
	
	FormatarCampos fc = new FormatarCampos();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.replicarestoqueoff);
		
		Button btnIniciar = (Button)findViewById(R.id.btnIniciar);
		TextView txvTotalRec = (TextView)findViewById(R.id.txvTotal);
		
		db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		rec = db.rawQuery("SELECT FANTASIA, RAZAO, CNPJ, TOT, DATAEMS, VENDEDOR FROM rec where TOT not null and DATAEMS not null", null);
		totalDBRec = rec.getCount();
		txvTotalRec.setText("Total Registros: " + String.valueOf(totalDBRec));
		
		if(totalDBRec == 0)
			btnIniciar.setEnabled(false);
		
		btnIniciar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
					Builder msg = new Builder(ReplicarVendasOff.this);
					msg.setMessage("Deseja iniciar a replicação?");
					msg.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							pg = ProgressDialog.show(ReplicarVendasOff.this, "Aguarde...", "Replicando dados", true, false, null);
							Thread t = new Thread(ReplicarVendasOff.this);
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
		while(rec.moveToNext()){
			// URL
/*			
			String data;
			if(rec.getString(rec.getColumnIndex("dataems")).equals("")){
				data = fc.dataAtualTela();
			}else {
				data = rec.getString(rec.getColumnIndex("dataems"));
			}
	*/			
				//String data1 = fc.dataSalvarFirebird(data); 
/*			
			Log.d("Replicar venda", "fantasia " + rec.getString(rec.getColumnIndex("fantasia")) + " razao " + rec.getString(rec.getColumnIndex("razao")) +
					" cnpj " +  rec.getString(rec.getColumnIndex("cnpj")) + " tot " + rec.getString(rec.getColumnIndex("tot")) +
					" totg " + rec.getString(rec.getColumnIndex("tot")) + " dataems " + rec.getString(rec.getColumnIndex("dataems")) +
					" vendedor " + rec.getString(rec.getColumnIndex("vendedor")));
*/					
			//fc.dataSalvarFirebird(data)
			
			StringBuilder strURL = new StringBuilder();
			
			strURL.append("http://192.168.1.3:8080/AndroidWeb/ReplicarVendaCIn.jsp?fantasia=");
			//strURL.append("http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ReplicarVendaCIn.jsp?fantasia=");
			strURL.append(rec.getString(rec.getColumnIndex("fantasia")));
			strURL.append("&razao=");
			strURL.append(rec.getString(rec.getColumnIndex("razao")));
			strURL.append("&cnpj=");
			strURL.append(rec.getString(rec.getColumnIndex("cnpj")));
/*			
			strURL.append("&tot=");
			strURL.append(rec.getString(rec.getColumnIndex("tot")));
			strURL.append("&totg=");
			strURL.append(rec.getString(rec.getColumnIndex("tot")));
			strURL.append("&dataems=");
*/			
			//String data = fc.dataSalvarFirebird(rec.getString(rec.getColumnIndex("dataems")));
			//strURL.append(fc.dataSalvarFirebird(rec.getString(rec.getColumnIndex("dataems"))));
			//strURL.append(rec.getString(rec.getColumnIndex("dataems")));
			strURL.append("&vendedor=");
			strURL.append(rec.getString(rec.getColumnIndex("vendedor")));
			
//			http://localhost:8080/AndroidWeb/ReplicarVendaCIn.jsp?fantasia=alan&razao=alan&cnpj=11.123.132/0001-15&tot=15.15&totg=15.15&dataems=14.03.2014&vendedor=testevendedor
			
			// Transforma String em URL
			try {
				URL url = new URL(strURL.toString());
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				InputStreamReader ipr = new InputStreamReader(http.getInputStream());
				BufferedReader bf = new BufferedReader(ipr);
/*				
				if(bf.readLine().equals("Y")){
					total += 1;
					db.execSQL("DELETE FROM clientes WHERE id = " + rec.getInt(rec.getColumnIndex("id")));
				}
*/					
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
			if(total == totalDBRec){
				Toast.makeText(getBaseContext(), "Sucesso: total de " + total + "/" + totalDBRec, Toast.LENGTH_LONG).show();
			}
			finish();
		}
	};
}

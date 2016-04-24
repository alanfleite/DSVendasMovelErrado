package br.com.datasol;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import br.com.datasol.DB.DB;
import br.com.datasol.auxilio.FormatarCampos;
import br.com.datasol.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.dao.ProdVendDAO;
import br.com.datasol.dao.RecDAO;
import br.com.datasol.vo.RecVO;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ReplicarVendasOff1 extends ListActivity {
//	EditText etUsuario;
	String[] listaVendaC;
	String[] listaVendaD;
	String garcom;
	String garcomLogado;
	
	private Cursor rec = null;
	private Cursor prodvend = null;
	private int totalDBRec = 0;
	private int total = 0;
	private ProgressDialog pg;
	private SQLiteDatabase db;
	FormatarCampos fc = new FormatarCampos();
	
	
//	Button btVoltar;

	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

		//listarMesas();
        iniciarReplicacaoVendaC();
	}

	public void iniciarReplicacaoVendaC(){
		//db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		rec = db.rawQuery("SELECT FANTASIA, RAZAO, CNPJ, TOT, DATAEMS, VENDEDOR FROM rec where TOT not null and DATAEMS not null", null);		
		totalDBRec = rec.getCount();
		//txvTotalRec.setText("Total Registros: " + String.valueOf(totalDBRec));
		
		//String url = "http://192.168.1.3:8080/AndroidWeb/ReplicarVendaCIn.jsp";
/*		
		ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			paramentosPost.add(new BasicNameValuePair("fantasia", "teste2"));
			paramentosPost.add(new BasicNameValuePair("razao", "teste2"));
			paramentosPost.add(new BasicNameValuePair("cnpj", "12123123000121"));
			paramentosPost.add(new BasicNameValuePair("tot", "12.3"));
			paramentosPost.add(new BasicNameValuePair("totg", "12.3"));
			paramentosPost.add(new BasicNameValuePair("dataems", "15.03.2014"));
			paramentosPost.add(new BasicNameValuePair("vendedor", "teste"));

			//http://localhost:8080/AndroidWeb/ReplicarVendaCIn.jsp?fantasia=alan&razao=alan&cnpj=11.123.132/0001-15&vendedor=testevendedor
		
			String respostaRetornada = null;
			Log.i("abrir", "2");
			try {
				Log.i("abrir", "3 " + paramentosPost);
				respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
				Log.i("abrir", "3.5");
				String resposta = respostaRetornada.toString();
				resposta = resposta.replaceAll("\\s+", "");
				Log.i("abrir", "4" + resposta);
				if (resposta.equals("1")) {
					mensagemExibir("Salvar no servidor", "Salvo com sucesso!!");
					Log.i("abrir", "5");
					
					//iniciarVenda(mesa, garcom);
				} else
					mensagemExibir("Salvar no servidor", "Não foi Salvo!!");

			} catch (Exception e) {
				mensagemExibir("Salvar no servidor erro", "Erro " + e);
			}
*/
		
		
		
		//ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
		String respostaRetornada = null;
		//Log.i("Replicação das Vendas", "1");
		//ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
		//Log.i("Replicação das Vendas", "2");
		while(rec.moveToNext()){
			Log.i("Replicação das Vendas", "1");
			String url = "http://192.168.1.3:8080/AndroidWeb/ReplicarVendaCIn.jsp";
//			String url = "http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ReplicarVendaCIn.jsp";
			Log.i("Replicação das Vendas", "2");
			ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			Log.i("Replicação das Vendas", "3");
			paramentosPost.add(new BasicNameValuePair("fantasia", rec.getString(rec.getColumnIndex("fantasia"))));
			paramentosPost.add(new BasicNameValuePair("razao", rec.getString(rec.getColumnIndex("razao"))));
			paramentosPost.add(new BasicNameValuePair("cnpj", rec.getString(rec.getColumnIndex("cnpj"))));
			paramentosPost.add(new BasicNameValuePair("tot", rec.getString(rec.getColumnIndex("tot"))));
			paramentosPost.add(new BasicNameValuePair("totg", rec.getString(rec.getColumnIndex("tot"))));
			Log.i("Replicação das Vendas", "4");
			paramentosPost.add(new BasicNameValuePair("dataems", rec.getString(rec.getColumnIndex("dataems"))));
			Log.i("Replicação das Vendas", "5");
			paramentosPost.add(new BasicNameValuePair("vendedor", rec.getString(rec.getColumnIndex("vendedor"))));
			Log.i("Replicação das Vendas", "6 " + paramentosPost);
			try {
				respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mensagemExibir("Replicação do cliente", "Erro " + e);
			}

		}
		Log.i("Replicação das Vendas", "7");
//		respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
		Log.i("Replicação das Vendas", "8");
		String resposta = respostaRetornada.toString();
		resposta = resposta.replaceAll("\\s+", "");
		Log.i("Replicação das Vendas", "9" + resposta);
//		if (resposta.equals("1")) {
		if (resposta != "0") {
			//mensagemExibir("Replicação das Vendas", "Realizada com sucesso!!");
			//Log.i("Replicação das Vendas", "10");
			//startActivity(new Intent(this,MenuPrincipal.class));
			iniciarReplicacaoVendaD();
		} else
			mensagemExibir("Replicação das Vendas", "Não foi realizada!!");
	}

	public void iniciarReplicacaoVendaD(){
		//db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
		
		prodvend = db.rawQuery("SELECT prod, q1, vl_u, vl_t, codvend, vendedor FROM prod_vend", null);		
		String respostaRetornada = null;
		while(prodvend.moveToNext()){
			Log.i("Replicação das Vendas", "1");
			String url = "http://192.168.1.3:8080/AndroidWeb/ReplicarVendaDIn.jsp";
			Log.i("Replicação das Vendas", "2");
			ArrayList<NameValuePair> paramentosPost = new ArrayList<NameValuePair>();
			Log.i("Replicação das Vendas", "3");
			paramentosPost.add(new BasicNameValuePair("prod", prodvend.getString(prodvend.getColumnIndex("prod"))));
			paramentosPost.add(new BasicNameValuePair("q1", prodvend.getString(prodvend.getColumnIndex("q1"))));
			paramentosPost.add(new BasicNameValuePair("vl_u", prodvend.getString(prodvend.getColumnIndex("vl_u"))));
			paramentosPost.add(new BasicNameValuePair("vl_t", prodvend.getString(prodvend.getColumnIndex("vl_t"))));
			paramentosPost.add(new BasicNameValuePair("codvend", prodvend.getString(prodvend.getColumnIndex("codvend"))));
			Log.i("Replicação das Vendas", "4");
			paramentosPost.add(new BasicNameValuePair("vendedor", prodvend.getString(prodvend.getColumnIndex("vendedor"))));
			Log.i("Replicação das Vendas", "6 " + paramentosPost);
			try {
				respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				mensagemExibir("Replicação do cliente", "Erro " + e);
			}
		}
		Log.i("Replicação das Vendas", "7");
//		respostaRetornada = ConexaoHTTPClient.executaHttpPost(url, paramentosPost);
		Log.i("Replicação das Vendas", "8");
		String resposta = respostaRetornada.toString();
		resposta = resposta.replaceAll("\\s+", "");
		Log.i("Replicação das Vendas", "9" + resposta);
//		if (resposta.equals("1")) {
		if (resposta != "0") {
			mensagemExibir("Replicação das Vendas", "Realizada com sucesso!!");
			Log.i("Replicação das Vendas", "10");
			//startActivity(new Intent(this,MenuPrincipal.class));
			deleteVendaC();
			deleteVendaD();
			Log.i("Replicação das Vendas", "11 delecao");
			//startActivity(new Intent(this,Principal.class));
		} else
			mensagemExibir("Replicação das Vendas", "Não foi realizada!!");
	}
	
	public void deleteVendaC() {
		RecDAO dao = new RecDAO(getBaseContext());
		dao.deleteAll();
	}
	
	public void deleteVendaD() {
		ProdVendDAO dao = new ProdVendDAO(getBaseContext());
		dao.deleteAll();
		//db.rawQuery("DELETE from REC", null);
	}	
	
	public void mensagemExibir(String titulo, String mensagemm) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(ReplicarVendasOff1.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(mensagemm);
		mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}
			
}
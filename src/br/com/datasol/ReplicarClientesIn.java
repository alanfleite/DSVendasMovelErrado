package br.com.datasol;

import br.com.datasol.R;
import br.com.datasol.DB.DB;
import br.com.datasol.conexaoweb.ConexaoHTTPClient;
import br.com.datasol.dao.Cad_cliDAO;
import br.com.datasol.dao.EstoqueDAO;
import br.com.datasol.vo.Cad_cliVO;
import br.com.datasol.vo.EstoqueVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

public class ReplicarClientesIn extends Activity{
	int posicao=0;
	String [] listaClientes;
	EditText etQt;
	
	private Cursor c = null;
	private String cliente;
	private String usuario;
	private String razao;
	private String ende;
	private String ende_num;
	private String fone;
	private String cel;
	private String bairro;
	private String cida;
	private String uf;
	private String cnpj;
	private String cpf;

	private SQLiteDatabase db;
	private static Context ctx;	
	
	@SuppressLint("NewApi")
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        
	        //etQt = (EditText) findViewById(R.id.txtQt);
	        
	        //Log.d("delete", "1");
	        Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
	        dao.deleteAll();
	        //Log.d("delete", "2");
	        
	        setContentView(R.layout.listarreplicacaocad_cli);
	        //Log.d("RepCli", "1");
	        //db.delete("cad_cli", null,null);
	        //Log.d("RepCli", "2");
			String url="http://192.168.1.3:8080/AndroidWeb/ListarClientes.jsp";
			//String url="http://rpsutilidades.no-ip.biz:8080/AndroidWeb/ListarClientes.jsp";
			//Log.d("RepCli", "3");
			String respostaRetornada = null;
			//Log.d("RepCli", "4");
			try {
				//Log.d("RepCli", "5");
				respostaRetornada = ConexaoHTTPClient.executaHttpGet(url);
				String resposta = respostaRetornada.toString();
				
				char separador='#';
				int contUsuarios = 0;
				for(int i=0;i < resposta.length();i++){
					if (separador == resposta.charAt(i))
						contUsuarios++;
				}
				
				db = openOrCreateDatabase("datasol.db", Context.MODE_PRIVATE, null);
				
				
				listaClientes = new String[contUsuarios];
				
				char caracter_lido=resposta.charAt(0);
				String nome="";
				//String sql = "insert into CAD_CLI (usuario) values (" + nome + ");";
				int contador=0;
				
				for (int i=0; caracter_lido != '^'; i++){
					caracter_lido = resposta.charAt(i);
					
					if (caracter_lido != '#'){
						nome += caracter_lido;
					}else {
						listaClientes[posicao] = "" + nome;
						posicao++;
						
						int us = nome.indexOf("|"); 
	                    usuario = nome.substring(0, us);
	                    int r = nome.indexOf("@");
	                    us = us + 1;
	                    razao = nome.substring(us, r);
	                    //int e = nome.indexOf("|");
	                    //r = r + 1;
	                    //ende = nome.substring(r, e);
	                    //int en = nome.indexOf("¨");
	                    //e = e + 1;
	                    //ende_num = nome.substring(e, en);
	                    //int f = nome.indexOf("/");
	                    //en = en + 1;
	                    //fone = nome.substring(en, f);
	                    //int c = nome.indexOf("{");
	                    //f = f + 1;
	                    //cel = nome.substring(f, c);
	                    //int b = nome.indexOf("*");
	                    //c = c + 1;
	                    //bairro = nome.substring(c, b);
	                    //int ci = nome.indexOf("%");
	                    //b = b + 1;
	                    //cida = nome.substring(b, ci);
	                    //int uf1 = nome.indexOf("$");
	                    //ci = ci + 1;
	                    //uf = nome.substring(ci, uf1);
	                    //int cn = nome.indexOf("@");
	                    int cn = nome.indexOf("$");
	                    //uf1 = uf1 + 1;
	                    r = r + 1;
	                    //cnpj = nome.substring(uf1, cn);
	                    cnpj = nome.substring(r, cn);
	                    //int cp = nome.indexOf("}");
	                    //cn = cn + 1;
	                    //cpf = nome.substring(cn, cp);						
						
	                    //Log.d("cli 4", "usuario " + usuario + " - razao " + razao + cnpj +" Contador " + String.valueOf(contador));
	                    
	                    //Log.d("cli 4", "usuario " + usuario + " - razao " + razao + " - ende " + ende + " - ende n " + ende_num + " - fone " + fone + " - cel " + cel + " - bairro " + bairro + " - cida " + cida + " - uf " + uf + " - cnpj " + cnpj + " - cpf " + cpf +" Contador " + String.valueOf(contador));
						
						//Log.d("RepCli", "6" + nome);
						Cad_cliVO vo = new Cad_cliVO();
						
						vo.setUsuario(usuario.toString());
						vo.setRazao(razao.toString());
//						vo.setEnde(ende.toString());	
//						vo.setEnde_num(ende_num.toString());
//						vo.setBairro(bairro.toString());
//						vo.setCidade(cida.toString());
//						vo.setUf(uf.toString());
						vo.setCnpj(cnpj.toString());
						//vo.setCpf(cpf.toString());
						
						//Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
						dao = new Cad_cliDAO(getBaseContext());
						dao.insert(vo);
						
						//String sql = "insert into CAD_CLI (usuario) values ('" + nome + "');";
						//Log.d("sql cliente ", sql);
						//c = db.rawQuery(sql, null);
						//Log.d("RepCli", "7");
						contador = contador + 1;
						//Log.d("RepCli contador ", String.valueOf(contador));
						nome = "";
					}
				}
			} catch (Exception e) {
				mensagemExibir("Clientes", "Erro " + e);
			}

	        ArrayAdapter<String> aaProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaClientes);
            AutoCompleteTextView autoCompletarProdutos = (AutoCompleteTextView) findViewById(R.id.autoCompleteClientes);
            autoCompletarProdutos.setAdapter(aaProdutos);
            
            Spinner spinnerUsuarios = (Spinner) findViewById(R.id.spinnerClientes);
            ArrayAdapter<String> todosProdutos;
            todosProdutos = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaClientes);
            todosProdutos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerUsuarios.setAdapter(todosProdutos);
            
            mensagemExibir("Dados", "Importado com sucesso!!");
	 }
	 
	   public void mensagemExibir(String titulo, String texto)
	   {
			AlertDialog.Builder mensagem = new AlertDialog.Builder(ReplicarClientesIn.this);
			mensagem.setTitle(titulo);
			mensagem.setMessage(texto);
			mensagem.setNeutralButton("OK",null);
			mensagem.show();
	   }
}
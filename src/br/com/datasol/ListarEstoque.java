package br.com.datasol;

import java.util.List;

import br.com.datasol.adapters.EstoqueAdapter;
import br.com.datasol.dao.EstoqueDAO;
import br.com.datasol.vo.EstoqueVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ListarEstoque extends Activity{
	ListView ltw;
	List<EstoqueVO> lista = null;
	int idItem = 0;
	private static int MENU_EDITAR = 1;
//	private static int MENU_APAGAR = 2;
//	private static int MENU_CALL = 3;
	private String nome;
	
	//@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
		
		setContentView(R.layout.estoquelistar);
		ltw = (ListView)findViewById(R.id.ltvDados);
		ltw.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		final Button btnApagar = (Button)findViewById(R.id.btnApagar);
		
		
		registerForContextMenu(ltw);
		
		ltw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//btnApagar.setVisibility(0);
			}
		});
	}
	
	@Override
	public void onResume(){
		super.onResume();
		EstoqueDAO dao = new EstoqueDAO(getBaseContext());
		lista = dao.getAll();
		ltw.setAdapter(new EstoqueAdapter(getBaseContext(), lista));
	}
	
	public void Apagar_click(View v){
		String nomes = "";
		SparseBooleanArray checkeds = ltw.getCheckedItemPositions();
		
		for(int i = 0; i < checkeds.size(); i++){
			nomes += lista.get(checkeds.keyAt(i)).getProd() + ", ";
		}
		
		Toast.makeText(getBaseContext(), nomes, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
		
		menu.setHeaderTitle(lista.get(info.position).getProd());
		menu.add(Menu.NONE, MENU_EDITAR, 0, "Carregar");
//		menu.add(Menu.NONE, MENU_APAGAR, 0, "Apagar");
//		menu.add(Menu.NONE, MENU_CALL, 0, "Telefonar");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
		
		idItem = lista.get(info.position).getCod();
		nome = lista.get(info.position).getProd();
		
		if(item.getItemId() == MENU_EDITAR){
			finish();
			Log.d("listar estoque", String.valueOf(idItem));
			Intent it = new Intent(getBaseContext(), Venda.class);
			it.putExtra("codProd", idItem);
			startActivity(it);
//			setResult(RESULT_OK, new Intent().putExtra("nome", nome));
			Log.d("listar estoque 1", it + " - " + String.valueOf(idItem));
		} 
/*		
		else if(item.getItemId() == MENU_APAGAR){
			Builder msg = new  Builder(ListarClientes.this);
			msg.setMessage("Deseja excluir este cliente?");
			msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
					Cad_cliVO cliente = dao.getById(idItem);
					if(dao.delete(cliente) == true){
						Toast.makeText(getBaseContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
						ltw.setAdapter(new Cad_cliAdapter(getBaseContext(), dao.getAll()));
					}
				}
			});
			msg.setNegativeButton("N�o", null);
			
			msg.show();
		} else if(item.getItemId() == MENU_CALL){
			Uri uri = Uri.parse("tel:" + lista.get(info.position).getFone());
			Intent it = new Intent(Intent.ACTION_DIAL, uri);
			startActivity(it);
		}
*/		
		return super.onContextItemSelected(item);
	}
	
}

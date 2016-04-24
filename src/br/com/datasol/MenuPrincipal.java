package br.com.datasol;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MenuPrincipal extends ListActivity{
//	EditText etUsuario;
	String garcomLogado;
	
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        String [] menu = new String[] {"Cadastrar Clientes", "Listar Clientes", "Vendas", "Atualizar Clientes", "Atualizar Estoque", "Salvar no Servidor", "Produtos", "m", "Sair"};
	        ArrayAdapter<String> aaCursos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu);
	        setListAdapter(aaCursos);
        	
//	        etUsuario=(EditText) findViewById(R.id.etUsuario);
	 }
	 
	 protected void onListItemClick(ListView l, View v, int position, long id)
	 {
		 super.onListItemClick(l, v, position, id);	 
		 Object objetoSelecionado = this.getListAdapter().getItem(position);
		 String menuSelecionado = objetoSelecionado.toString();
		 
		 switch(position) {
		 case 0:
			 startActivity(new Intent(this,Cad_cli.class)); 
			 break;
		 case 1:
			 startActivity(new Intent(this,Cad_cliListar.class)); 
			 break;		 		 
		 case 2:
			 startActivity(new Intent(this,Venda1.class)); 
			 break;		 
		 case 3:
			 startActivity(new Intent(this,ReplicarClientesIn.class)); break;
		 case 4:
			 startActivity(new Intent(this,ReplicarEstoqueIn.class)); break;
		 case 5:
			 startActivity(new Intent(this,ReplicarVendasOff1.class)); 
			 break;			 
		 case 6:
			 startActivity(new Intent(this,Produtos.class)); break;
		 case 7:
			 startActivity(new Intent(this,MesasLivres.class)); break;
		 case 8:
			 finish(); break;
			 
		default: finish();
		 }
	 }
	 
	 public void recebeGarcom(String garcom){
		 garcomLogado = garcom;
	 }
}

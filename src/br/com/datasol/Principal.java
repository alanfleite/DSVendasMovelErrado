package br.com.datasol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		
		ImageButton imbCadastrar = (ImageButton)findViewById(R.id.buttonok);
		ImageButton imbListar = (ImageButton)findViewById(R.id.ImageButton01);
		ImageButton imbVendas = (ImageButton)findViewById(R.id.ImageButton02);
		ImageButton imbAtClientes = (ImageButton)findViewById(R.id.ImageButton03);
		ImageButton imbAtEst = (ImageButton)findViewById(R.id.ImageButton04);
		ImageButton imbSavServ = (ImageButton)findViewById(R.id.ImageButton05);
		ImageButton imbProdutos = (ImageButton)findViewById(R.id.ImageButton06);
		ImageButton imbSair = (ImageButton)findViewById(R.id.ImageButton07);
		
		imbCadastrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//startActivity(new Intent(getBaseContext(), Cad_cli.class)); 	
				startActivity(new Intent(getBaseContext(), Vendedor.class));
			}
		});
		
		imbListar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),Cad_cliListar.class)); 
				
			}
		});
		
		imbVendas.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),VendaC.class)); 
			}
		});
		
		imbProdutos.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),VendaD.class));
			}
		});		
		
		imbAtClientes.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext() ,ReplicarClientesIn.class)); 
				
			}
		});
		
		imbAtEst.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),ReplicarEstoqueIn.class));
				
			}
		});
		
		imbSavServ.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),ReplicarVendasOff1.class)); 
				
			}
		});
				
		imbSair.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
		
		
	}
}

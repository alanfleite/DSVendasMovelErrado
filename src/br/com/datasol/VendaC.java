package br.com.datasol;

import java.util.List;

import br.com.datasol.adapters.ProdVendAdapter;
import br.com.datasol.auxilio.FormatarCampos;
import br.com.datasol.dao.Cad_cliDAO;
import br.com.datasol.vo.Cad_cliVO;
import br.com.datasol.dao.EstoqueDAO;
import br.com.datasol.vo.EstoqueVO;
import br.com.datasol.dao.RecDAO;
import br.com.datasol.vo.RecVO;
import br.com.datasol.dao.ProdVendDAO;
import br.com.datasol.vo.ProdVendVO;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class VendaC extends Activity {
	
	FormatarCampos fc = new FormatarCampos();
	
	private double vlTotal=0;
	
	private String codigo;
	private String fantasia;
	private String razao;
	private String cnpj;
	private String TotalGeral;
	
	EditText txtID;
	EditText txtUsuario;
	EditText txtRazao;
	EditText txtCnpj;

	EditText txtTotalGeral;
	EditText txtData;

	Button btNovo;
	Button btSalvarVendaC;
	Button btAtualizarVendaC;
	Button btApagar;
	Button btBuscarCliente;
	Button btVoltarCad;

	private int codCliente = 0;
	String idVendaC = "";
	private static int RETORNO_NOME = 1;
	private static int RETORNO_COR = 1;
	private static int buscarCliente = 0;
	private String codUnid;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("venda 1", " - ");
	
		setContentView(R.layout.vendac);
		
		Log.d("venda 2", " - ");
		
		if (buscarCliente > 0) {
			Log.d("venda it", " - ");
			Intent it = getIntent();
			codCliente = it.getIntExtra("codigo", 1);
			Log.d("venda it", String.valueOf(codCliente));
			final Cad_cliDAO dao = new Cad_cliDAO(getBaseContext());
			final Cad_cliVO vo = dao.getById(codCliente);

			txtID = (EditText) findViewById(R.id.txtCod);
			txtUsuario = (EditText) findViewById(R.id.txtFantasia);
			txtRazao = (EditText) findViewById(R.id.txtRazao);
			txtCnpj = (EditText) findViewById(R.id.txtCnpj);

			txtID.setText(vo.getCod().toString());
			txtUsuario.setText(vo.getUsuario());
			txtRazao.setText(vo.getRazao());
			txtCnpj.setText(vo.getCnpj());
			
			Log.d("venda it", "nome " + vo.getUsuario());
		}
		Log.d("venda 3", " - ");
		
		Log.d("venda 4", " - ");
		txtTotalGeral = (EditText) findViewById(R.id.txtTotalVendaC);
		txtTotalGeral.setText("0.00");
		txtData = (EditText) findViewById(R.id.txtData);
		txtData.setText(fc.dataAtualTela());

		btNovo = (Button) findViewById(R.id.btNovoVendaC);
		btSalvarVendaC = (Button) findViewById(R.id.btSalvar);
		btApagar = (Button) findViewById(R.id.btApagar);
		btBuscarCliente = (Button) findViewById(R.id.btBuscarcliente);
		btVoltarCad = (Button) findViewById(R.id.btVoltarCad);
		Log.d("venda 5", " - ");
		
		btVoltarCad.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		Log.d("venda 6", " - ");
		btNovo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				limparTela();
			}
		});
		Log.d("venda 7", " - ");
		btBuscarCliente.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getBaseContext(),
						ListarClientes.class), RETORNO_COR);
				
				buscarCliente = buscarCliente + 1; 
			}
		});
		
		btSalvarVendaC.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				salvarVendaC();
			}
		});

		btApagar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				buscarUltimaVenda();
			}
		});
		Log.d("venda 8", " - ");
	}

	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		if (RETORNO_COR == requestCode) {
			if (resultCode == RESULT_OK)
				txtUsuario.setText(data.getStringExtra("nome"));
		}
	}

	protected void salvarVendaC() {
		RecVO vo = new RecVO();
		vo.setFantasia(txtUsuario.getText().toString());
		vo.setRazao(txtRazao.getText().toString());
		if (txtTotalGeral.getText().toString().equals("")) {
			vo.setTot("0.00");
		} else {
			vo.setTot(txtTotalGeral.getText().toString());
		}
		vo.setDataems(fc.dataSalvarSQLite());
		vo.setCnpj(txtCnpj.getText().toString());

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.insert(vo)) {
			buscarUltimaVenda1();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}
	
	protected void atualizarVendaC() {
		RecVO vo = new RecVO();
		vo.setCod(Integer.parseInt(txtID.getText().toString()));
		vo.setFantasia(txtUsuario.getText().toString());
		vo.setRazao(txtRazao.getText().toString());
		
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		txtTotalGeral.setText(pvdao.getSomaProdutos(txtID.getText().toString()));
		
		Log.d("at VC", pvdao.getSomaProdutos(txtID.getText().toString()));
		
		if (txtTotalGeral.getText().toString().equals("") || txtTotalGeral.getText().toString().equals("0.00")) {
			vo.setTot("0.00");
		} else {
			vo.setTot(txtTotalGeral.getText().toString());
		}
		vo.setDataems(fc.dataSalvarSQLite());
		vo.setCnpj(txtCnpj.getText().toString());

		RecDAO dao = new RecDAO(getBaseContext());
		if (dao.update(vo)) {
			buscarUltimaVenda();
			
			Toast.makeText(getBaseContext(), "Sucesso!", Toast.LENGTH_SHORT).show();
		}
	}		
		
	protected void buscarUltimaVenda() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();
		
		idVendaC = vo.getCod().toString();
		txtID.setText(vo.getCod().toString());
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		
		ProdVendDAO pvdao = new ProdVendDAO(getBaseContext());
		
		txtTotalGeral.setText(pvdao.getSomaProdutos(txtID.getText().toString()));
	}
	
	protected void buscarUltimaVenda1() {
		final RecDAO dao = new RecDAO(getBaseContext());
		final RecVO vo = dao.getUltimo();
		
		txtID.setText(vo.getCod().toString());
		txtUsuario.setText(vo.getFantasia());
		txtRazao.setText(vo.getRazao());
		txtCnpj.setText(vo.getCnpj());
		txtTotalGeral.setText(vo.getTot());
	}
	
	protected void limparTela(){
		txtID.setText("");
		txtUsuario.setText("");
		txtRazao.setText("");
		txtCnpj.setText("");
		txtTotalGeral.setText("");
	}
}
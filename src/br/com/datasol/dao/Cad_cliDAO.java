package br.com.datasol.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.DB.DB;
import br.com.datasol.vo.Cad_cliVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Cad_cliDAO {
	
	private static String table_name = "cad_cli";
	private static Context ctx;
	private static String[] columns = {"cod", "usuario", "razao", "ende", "ende_num", "fone", "cel", "bairro",
                                       "cidade", "uf", "cnpj", "rg", "inscest", "resp", "email", "contato", "cpf"};

	public Cad_cliDAO(Context ctx){
		this.ctx = ctx;
	}
	
	public boolean insert(Cad_cliVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("usuario", vo.getUsuario());
		ctv.put("razao", vo.getRazao());
		ctv.put("ende", vo.getEnde());
		ctv.put("ende_num", vo.getEnde_num());
		ctv.put("fone", vo.getFone());
		ctv.put("cel", vo.getCel());
		ctv.put("bairro", vo.getBairro());
		ctv.put("cidade", vo.getCidade());
		ctv.put("uf", vo.getUf());
		ctv.put("cnpj", vo.getCnpj());
		ctv.put("rg", vo.getRg());
		ctv.put("inscest", vo.getInscest());
		ctv.put("resp", vo.getResp());
		ctv.put("email", vo.getEmail());
		ctv.put("contato", vo.getContato());
		ctv.put("cpf", vo.getCpf());
		
		return (db.insert(table_name, null, ctv) > 0 );
	}
	
	public boolean delete(Cad_cliVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "cod=?", new String[]{vo.getCod().toString()}) > 0);
		//db.delete(table_name, null, null);
		//return true;
	}
	
	public void deleteAll(){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		db.delete(table_name, null, null);
	}
	
	public boolean update(Cad_cliVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("usuario", vo.getUsuario());
		ctv.put("razao", vo.getRazao());
		ctv.put("ende", vo.getEnde());
		ctv.put("ende_num", vo.getEnde_num());
		ctv.put("fone", vo.getFone());
		ctv.put("cel", vo.getCel());
		ctv.put("bairro", vo.getBairro());
		ctv.put("cidade", vo.getCidade());
		ctv.put("uf", vo.getUf());
		ctv.put("cnpj", vo.getCnpj());
		ctv.put("rg", vo.getRg());
		ctv.put("inscest", vo.getInscest());
		ctv.put("resp", vo.getResp());
		ctv.put("email", vo.getEmail());
		ctv.put("contato", vo.getContato());
		ctv.put("cpf", vo.getCpf());
		return (db.update(table_name, ctv, "cod=?", new String[]{vo.getCod().toString()}) > 0);
	}	
	
	public Cad_cliVO getById(Integer COD){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "cod=?", new String[]{COD.toString()}, null, null, null);
		
		Cad_cliVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new Cad_cliVO();
			vo.setCod(rs.getInt(rs.getColumnIndex("cod")));
			vo.setUsuario(rs.getString(rs.getColumnIndex("usuario")));
			vo.setRazao(rs.getString(rs.getColumnIndex("razao")));
			vo.setEnde(rs.getString(rs.getColumnIndex("ende")));
			vo.setEnde_num(rs.getString(rs.getColumnIndex("ende_num")));
			vo.setFone(rs.getString(rs.getColumnIndex("fone")));
			vo.setCel(rs.getString(rs.getColumnIndex("cel")));
			vo.setBairro(rs.getString(rs.getColumnIndex("bairro")));
			vo.setCidade(rs.getString(rs.getColumnIndex("cidade")));
			vo.setUf(rs.getString(rs.getColumnIndex("uf")));
			vo.setCnpj(rs.getString(rs.getColumnIndex("cnpj")));
			vo.setRg(rs.getString(rs.getColumnIndex("rg")));
			vo.setInscest(rs.getString(rs.getColumnIndex("inscest")));
			vo.setResp(rs.getString(rs.getColumnIndex("resp")));
			vo.setEmail(rs.getString(rs.getColumnIndex("email")));			
			vo.setContato(rs.getString(rs.getColumnIndex("contato")));
			vo.setCpf(rs.getString(rs.getColumnIndex("cpf")));
		}
		
		return vo;
	}
	
	public List<Cad_cliVO> getAll(){
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM cad_cli", null);
		
		List<Cad_cliVO> lista = new ArrayList<Cad_cliVO>();
		
		while(rs.moveToNext()){
			Cad_cliVO vo = new Cad_cliVO(rs.getInt(0), rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
					 rs.getString(5),  rs.getString(6),  rs.getString(7),  rs.getString(8),  rs.getString(9),
					 rs.getString(10),  rs.getString(11),  rs.getString(12),  rs.getString(13),  rs.getString(14),
					 rs.getString(15), rs.getString(16));
			lista.add(vo);
		}

		return lista;
	}
	
	public List<String> getAllLista(){
        Log.d("1", null);		
        SQLiteDatabase db = new DB(ctx).getReadableDatabase();
        Log.d("2", null);
		Cursor rs = db.rawQuery("SELECT * FROM cad_cli", null);
		Log.d("3", null);
//		List lista;
		
		ArrayList<String> lista2= null;
		Log.d("4", null);
		while(rs.moveToNext()){
			lista2.add(rs.getString(1));
			Log.d("5", rs.getString(1));
		}
		
		return lista2;
	}
}
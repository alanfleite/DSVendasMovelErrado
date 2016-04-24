package br.com.datasol.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.datasol.DB.DB;
import br.com.datasol.vo.VendedorVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class VendedorDAO {

	private static String table_name = "vendedor";
	private static Context ctx;
	private static String[] columns = {"id", "nome"};
	
	public VendedorDAO(Context ctx){
		this.ctx = ctx;
	}
	
	public boolean insert(VendedorVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());
		return (db.insert(table_name, null, ctv) > 0 );
	}
	
	public boolean delete(VendedorVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "id=?", new String[]{vo.getId().toString()}) > 0);
	}
	
	public boolean update(VendedorVO vo){
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		
		ContentValues ctv = new ContentValues();
		ctv.put("nome", vo.getNome());
		
		return (db.update(table_name, ctv, "id=?", new String[]{vo.getId().toString()}) > 0);
	}
	
	public VendedorVO getById(Integer ID){
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.query(table_name, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		VendedorVO vo = null;
		
		if(rs.moveToFirst()){
			vo = new VendedorVO();
			vo.setId(rs.getInt(rs.getColumnIndex("id")));
			vo.setNome(rs.getString(rs.getColumnIndex("nome")));
		}		
		return vo;
	}
	
	public List<VendedorVO> getAll(){
		
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		
		Cursor rs = db.rawQuery("SELECT * FROM vendedor", null);
		
		List<VendedorVO> lista = new ArrayList<VendedorVO>();
		
		while(rs.moveToNext()){
			VendedorVO vo = new VendedorVO(rs.getInt(0), rs.getString(1));
			lista.add(vo);
		}		
		return lista;
	}
}
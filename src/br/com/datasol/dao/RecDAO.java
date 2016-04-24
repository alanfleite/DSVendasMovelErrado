package br.com.datasol.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.datasol.DB.DB;
import br.com.datasol.auxilio.FormatarCampos;
import br.com.datasol.vo.RecVO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecDAO {
	
	FormatarCampos fc = new FormatarCampos();
	private static String table_name = "rec";
	private static Context ctx;
	private static String[] columns = { "cod", "fantasia", "razao", "tot",
			"cnpj", "condpg", "dataems", "vendedor" };

	public RecDAO(Context ctx) {
		this.ctx = ctx;
	}

	public boolean insert(RecVO vo) {
		
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("fantasia", vo.getFantasia());
		ctv.put("razao", vo.getRazao());
		ctv.put("tot", vo.getTot());
		ctv.put("cnpj", vo.getCnpj());
		ctv.put("condpg", vo.getCondpg());
		//ctv.put("dataems", vo.getDataems());
		ctv.put("dataems", vo.getDataems());
		ctv.put("vendedor", vo.getVendedor());

		return (db.insert(table_name, null, ctv) > 0);
	}

	public boolean delete(RecVO vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();
		return (db.delete(table_name, "cod=?", new String[] { vo.getCod()
				.toString() }) > 0);
	}
	
	public void deleteAll() {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.rawQuery("SELECT * FROM rec", null);

		List<RecVO> lista = new ArrayList<RecVO>();

		while (rs.moveToNext()) {
			
			RecVO vo = new RecVO(rs.getInt(0), rs.getString(1),
					rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7));
			//lista.add(vo);
			
			delete(vo);
		}

		
		//SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		//db.rawQuery("DELETE from REC", null);
		
		//Log.d("deleteall dao", "rec");
	}

	public boolean update(RecVO vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("fantasia", vo.getFantasia());
		ctv.put("razao", vo.getRazao());
		ctv.put("tot", vo.getTot());
		ctv.put("cnpj", vo.getCnpj());
		ctv.put("condpg", vo.getCondpg());
		ctv.put("dataems", vo.getDataems());
		ctv.put("vendedor", vo.getVendedor());

		return (db.update(table_name, ctv, "cod=?", new String[] { vo.getCod()
				.toString() }) > 0);
	}
	
	public boolean updateTotalG(RecVO vo) {
		SQLiteDatabase db = new DB(ctx).getWritableDatabase();

		ContentValues ctv = new ContentValues();
		ctv.put("tot", vo.getTot());

		return (db.update(table_name, ctv, "cod=?", new String[] { vo.getCod()
				.toString() }) > 0);
	}	

	public RecVO getById(Integer COD) {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.query(table_name, columns, "cod=?",
				new String[] { COD.toString() }, null, null, null);

		RecVO vo = null;

		if (rs.moveToFirst()) {
			vo = new RecVO();
			vo.setCod(rs.getInt(rs.getColumnIndex("cod")));
			vo.setFantasia(rs.getString(rs.getColumnIndex("fantasia")));
			vo.setRazao(rs.getString(rs.getColumnIndex("razao")));
			vo.setTot(rs.getString(rs.getColumnIndex("tot")));
			vo.setCnpj(rs.getString(rs.getColumnIndex("cnpj")));
			vo.setCondpg(rs.getString(rs.getColumnIndex("condpg")));
			vo.setDataems(rs.getString(rs.getColumnIndex("dataems")));
			vo.setVendedor(rs.getString(rs.getColumnIndex("vendedor")));
		}

		return vo;
	}

	public List<RecVO> getAll() {

		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.rawQuery("SELECT * FROM rec", null);

		List<RecVO> lista = new ArrayList<RecVO>();

		while (rs.moveToNext()) {
			RecVO vo = new RecVO(rs.getInt(0), rs.getString(1),
					rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5), rs.getString(6), rs.getString(7));
			lista.add(vo);
		}

		return lista;
	}

	public List<String> getAllLista() {
		SQLiteDatabase db = new DB(ctx).getReadableDatabase();
		Cursor rs = db.rawQuery("SELECT * FROM rec", null);

		ArrayList<String> lista2 = null;
		while (rs.moveToNext()) {
			lista2.add(rs.getString(1));
		}

		return lista2;
	}

	public RecVO getUltimo() {

		SQLiteDatabase db = new DB(ctx).getReadableDatabase();

		Cursor rs = db.rawQuery("SELECT * FROM rec", null);

		RecVO vo = null;

		// rs.isLast();
		while (rs.moveToNext()) {
			// if(rs.moveToLast()){
			if (rs.isLast()) {
				vo = new RecVO();

				vo.setCod(rs.getInt(rs.getColumnIndex("cod")));
				vo.setFantasia(rs.getString(rs.getColumnIndex("fantasia")));
				vo.setRazao(rs.getString(rs.getColumnIndex("razao")));
				vo.setTot(rs.getString(rs.getColumnIndex("tot")));
				vo.setCnpj(rs.getString(rs.getColumnIndex("cnpj")));
				vo.setCondpg(rs.getString(rs.getColumnIndex("condpg")));
				vo.setDataems(rs.getString(rs.getColumnIndex("dataems")));
				vo.setVendedor(rs.getString(rs.getColumnIndex("vendedor")));
			}
		}
		
		return vo;
		

		/*
		 * List<RecVO> lista = new ArrayList<RecVO>();
		 * 
		 * while(rs.moveToNext()){ RecVO vo = new RecVO(rs.getInt(0),
		 * rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
		 * rs.getString(5), rs.getString(6), rs.getString(7)); lista.add(vo); }
		 * 
		 * return lista;
		 */
	}

}

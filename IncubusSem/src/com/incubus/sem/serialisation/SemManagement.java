package com.incubus.sem.serialisation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import com.incubus.sem.ParsedSem;
import com.incubus.sem.serialisation.db.ConnectionManager;

public class SemManagement {

	
	private final static String SQL_MAX_ID = "select MAX(idDico) from Dico";
	
	private final static String SQL_SELECT_NAME = "select idDico, name from Dico where name in ({0})";
	
	private final static String SQL_SELECT_RELATION_ID = "select idDico1, idDico2 from LienDico where idDico1 = ? and idDico2 = ?";
	
	private final static String SQL_INSERT_RELATION_ID = "insert into LienDico (idDico1, idDico2) values (?,?)";
	
	private final static String SQL_INSERT_NAME = "insert into Dico (idDico, name) values (?,?)";
	
	private static SemManagement INSTANCE;
	
	private int lastId;
	
	private SemManagement() {
		
		try {
			loadSemManagement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public final static SemManagement getInstance(){
		if (INSTANCE == null){
			INSTANCE = new SemManagement();
		}
		return INSTANCE;
	}
	
	private void loadSemManagement() throws SQLException{
		
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Connection connection = connectionManager.getConnection();
		PreparedStatement ps = connection.prepareStatement(SQL_MAX_ID);
		ResultSet rs = ps.executeQuery();
		rs.next();
		lastId = rs.getShort(1);
		connection.close();
	}
	
	public long getIds(ParsedSem sem){
		long id = 0;
		try {
			id = innerGetId(sem);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return id;
	}
	
	public long innerGetId(ParsedSem sem) throws SQLException{
		
		ConnectionManager connectionManager = ConnectionManager.getInstance();
		Connection connection = connectionManager.getConnection();
		StringBuilder parameter = new StringBuilder();
		
		char separator = ' ';
		while (sem.hasMoreName()) {
			String name = sem.nextName();
			parameter.append(separator);
			parameter.append("'");
			parameter.append(name);
			parameter.append("'");
			separator = ',';
		}
		
		PreparedStatement ps = connection.prepareStatement(MessageFormat.format(SQL_SELECT_NAME, parameter.toString()));
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			short id = rs.getShort(1);
			String name = rs.getString(2);
			sem.setId(name, id);
		}
		ps.close();
		while (sem.hasMoreUnregisteredName()) {
			String name = sem.nextUnregisteredName();
			ps = connection.prepareStatement(SQL_INSERT_NAME);
			lastId = lastId + 1;
			ps.setInt(1, lastId);
			ps.setString(2, name);
			sem.setId(name, (short)lastId);
		}
		ps.close();
		
		while (sem.hasMoreRelation()) {
			ps = connection.prepareStatement(SQL_SELECT_RELATION_ID);
			short[] relation = sem.nextRelation();
			ps.setLong(1, relation[0]);
			ps.setLong(2, relation[1]);
			rs = ps.executeQuery();
			if (!rs.next()){
				PreparedStatement psInsert = connection.prepareStatement(SQL_INSERT_RELATION_ID);
				psInsert.setLong(1, relation[0]);
				psInsert.setLong(2, relation[1]);
				psInsert.executeQuery();
				psInsert.close();
			}
			ps.close();
		}
		
		
		return sem.getId();
	}
	
	

}

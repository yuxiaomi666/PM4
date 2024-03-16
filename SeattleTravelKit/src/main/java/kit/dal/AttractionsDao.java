package kit.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kit.model.Attractions;

public class AttractionsDao {
	protected ConnectionManager connectionManager;
	
	private static AttractionsDao instance = null;
	

	public AttractionsDao() {
		connectionManager = new ConnectionManager();
	}

	
	public static AttractionsDao getInstance() {
		if(instance == null) {
			instance = new AttractionsDao();
		}
		return instance;
	}
	
	public Attractions create(Attractions attraction) throws SQLException {
		Connection connection = null;
		PreparedStatement insertStmt = null;
		String insertAttraction = "INSERT INTO Hotels(AttractionsName, Phone, Website, ZipCode, Area) " +
							"VALUES(?,?,?,?,?);";
		ResultSet resultKey = null;
		
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertAttraction);
			insertStmt.setString(1,attraction.getAttractionsName());
			insertStmt.setInt(2,attraction.getPhone());
			insertStmt.setString(3,attraction.getWebsite());
			insertStmt.setInt(4,attraction.getZipCode());
			insertStmt.setString(5,attraction.getArea());
		
			insertStmt.executeUpdate();
			
			resultKey = insertStmt.getGeneratedKeys();
			int attractionId = -1;
			if(resultKey.next()) {
				attractionId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			attraction.setAttractionId(attractionId);
			return attraction;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	
	public Attractions findAttractionById(int attractionId) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStmt = null;
		String selectAttraction = "SELECT AttractionId, AttractionsName, Phone, Website, ZipCode, Area " +
								"FROM Attractions WHERE AttractionId=?;";
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAttraction);
			selectStmt.setInt(1, attractionId);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				String attractionName = results.getString("AttractionsName");
				int phone = results.getInt("Phone");
				String website = results.getString("Website");
				int zipCode = results.getInt("ZipCode");
				String area = results.getString("Area");
				
				Attractions attraction = new Attractions(attractionId, attractionName, phone, website, zipCode, area);
				return attraction;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		
		return null;
	}
	
	public Attractions findAttractionByName(String attractionName) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStmt = null;
		String selectAttraction = "SELECT AttractionId, AttractionsName, Phone, Website, ZipCode, Area " +
								"FROM Attractions WHERE AttractionName=?;";
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAttraction);
			selectStmt.setString(1, selectAttraction);
			results = selectStmt.executeQuery();
			
			if (results.next()) {
				int attractionId = results.getInt("AttractionsId");
				int phone = results.getInt("Phone");
				String website = results.getString("Website");
				int zipCode = results.getInt("ZipCode");
				String area = results.getString("Area");
				
				Attractions attraction = new Attractions(attractionId, attractionName, phone, website, zipCode, area);
				return attraction;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		
		return null;
	}
	
	
	public List<Attractions> findAttractionByZipCode(int zipCode) throws SQLException {
		Connection connection = null;
		PreparedStatement selectStmt = null;
		String selectAttraction = "SELECT AttractionId, AttractionsName, Phone, Website, ZipCode, Area " +
								"FROM Attractions WHERE AttractionName=?;";
		ResultSet results = null;
		List<Attractions> attractions = new ArrayList<>()
;		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectAttraction);
			selectStmt.setString(1, selectAttraction);
			results = selectStmt.executeQuery();
			
			while (results.next()) {
				int attractionId = results.getInt("AttractionsId");
				String attractionName = results.getString("AttractionsName");
				int phone = results.getInt("Phone");
				String website = results.getString("Website");
				String area = results.getString("Area");
				
				Attractions attraction = new Attractions(attractionId, attractionName, phone, website, zipCode, area);
				attractions.add(attraction);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
		
		return null;
	}
	
	public Attractions updateAttractionPhone(Attractions attraction, int phone) throws SQLException {
		Connection connection = null;
		PreparedStatement updateStmt = null;
		String updateAttraction = "UPDATE Attractions SET Phone=? WHERE AttractionId=?;";
		
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateAttraction);
			updateStmt.setInt(1,phone);
			updateStmt.setInt(2,attraction.getAttractionId());
			
			updateStmt.executeUpdate();
			
			attraction.setPhone(phone);
			return attraction;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
	
	public Attractions deleteAttraction(Attractions attraction) throws SQLException {
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		String deleteAttraction = "DELETE FROM Attractions WHERE AttractionId=?;";
		
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteAttraction);
			deleteStmt.setInt(1, attraction.getAttractionId());
			
			deleteStmt.executeUpdate();
			return null;
		}catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
}

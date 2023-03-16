package com.redstream.smdatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SocialMediaDB implements IDatabase {

	private static String driverName;
	private static String databaseConnectionLink;
	private static String databaseUser;
	private static String databasePassword;

	public SocialMediaDB() {
		this.driverName = "com.mysql.cj.jdbc.Driver";
		this.databaseConnectionLink = "jdbc:mysql://localhost:3306/SocialMediaDB?characterEncoding=latin1&useConfigs=maxPerformance";
		this.databaseUser = "root";
		this.databasePassword = "";
	}

	@Override
	public Connection connection() {
		Connection connection = null;
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(databaseConnectionLink, databaseUser, databasePassword);
			System.out.println("Database Connected!");
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}

		return connection;
	}

}

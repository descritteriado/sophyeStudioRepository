package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

public class Database {

	final static Logger logger = Logger.getLogger(Database.class);

	public static Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/skeleton", "postgres",
					"postgres");
			return con;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
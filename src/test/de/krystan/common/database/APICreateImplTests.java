package de.krystan.common.database;

import org.apache.commons.lang.NotImplementedException;
import org.junit.Test;
import org.junit.Assert;

/**
 * This suite tests the basic behavior of the API in terms of the fundamental creation of the implementations
 * 
 * @author Robert Duck
 * @since 09.01.2017
 */
public class APICreateImplTests {
	/**
	 * This will test how the API behaves when the argument for the database system is not provided.<br>
	 * The expected behavior is an IllegalArgumentException because without the argument the API is not able to create 
	 * the correct implementation
	 */
	@Test(expected=IllegalArgumentException.class)
	public void createImplTest1() {
		DatabaseAPI databaseAPI = new DatabaseAPI();
		databaseAPI.createDatabaseImpl();
	}
	
	/**
	 * This will test how the API behaves when the argument for the database system is provided but without a value.<br>
	 * The expected behavior is an IllegalArgumentException because without the value of the argument the API is not able to
	 * create the correct implementation
	 */
	@Test(expected=IllegalArgumentException.class)
	public void createImplTest2() {
		System.setProperty("Database_System", "");
		DatabaseAPI databaseAPI = new DatabaseAPI();
		databaseAPI.createDatabaseImpl();
	}
	
	/**
	 * This will test how the API behaves when the argument for the database system is set with a value which is not
	 * implemented yet.<br>
	 * The expected behavior is a NotImplementedException because there is actually no implementation available to create
	 */
	@Test(expected=NotImplementedException.class)
	public void createImplTest3() {
		System.setProperty("Database_System", "NoDatabaseSystemAtAll");
		DatabaseAPI databaseAPI = new DatabaseAPI();
		databaseAPI.createDatabaseImpl();
	}
	
	/**
	 * This will test how the API behaves when the argument for the database system is set correctly.<br>
	 * Expected behavior is the name of the SQLite3Impl class
	 */
	@Test
	public void createImplTest4() {
		System.setProperty("Database_System", "SQLITE");
		DatabaseAPI databaseAPI = new DatabaseAPI();
		databaseAPI.createDatabaseImpl();
		Assert.assertEquals("de.krystan.common.database.impl.SQLite3Impl", databaseAPI.getImplementationName());
	}
	
	/**
	 * This will test how the API behaves when the argument for the database system is set correctly but the name
	 * is requested before the actual implementation was created.<br>
	 * Expected behavior is an IllegalStateExecption because without implementation no class name can be determined
	 */
	@Test(expected=IllegalStateException.class)
	public void createImplTest5() {
		System.setProperty("Database_System", "SQLITE3");
		DatabaseAPI databaseAPI = new DatabaseAPI();
		databaseAPI.getImplementationName();
	}
}

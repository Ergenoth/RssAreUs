package de.krystan.common.database;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import de.krystan.common.database.impl.IDatabaseImpl;
import de.krystan.common.database.impl.SQLite3Impl;

/**
 * API for accessing the database connection layer.<br>
 * The API is intended to be accessed from both components, the UI and the server part
 * 
 * @author Robert Duck
 * @since 09.01.2017
 */
public class DatabaseAPI {
	/* ----------------------- *
	 * Private members
	 * ----------------------- */
	private IDatabaseImpl currentDatabaseImpl;
	
	/* ----------------------- *
	 * Constructor
	 * ----------------------- */
	public DatabaseAPI() {}
	
	/* ----------------------- *
	 * Public methods
	 * ----------------------- */
	/**
	 * Opens a connection to the database for further requests to the database
	 * 
	 * @return {@code true} when the connection could be successfully established; otherwise {@code false}
	 */
	public boolean openConnection() {
		return currentDatabaseImpl.openConnection();
	}
	
	/**
	 * Closes a already opened connection. <br>
	 * If the connection is closed, this method will immediately return without doing anything
	 * 
	 * @return {@code true} when the connection could be closed successfully or the method returned immediately; otherwise {@code false}
	 */
	public boolean closeConnection() {
		return currentDatabaseImpl.closeConnection();
	}
	
	/**
	 * Executes a given SQL statement in the database
	 * 
	 * @param sqlStatement the SQL statement to be executed
	 * @return the result set of the SQL statement or {@code null} if there was no result set or an error occured
	 */
	public Object executeStatement(String sqlStatement) {
		return currentDatabaseImpl.executeStatement(sqlStatement);
	}
	
	/**
	 * Begins a transaction in the database in order to commit or rollback the modified or added records.<br>
	 * Necessary to call this method before committing changes or rollback changes
	 * 
	 * @return {@code true} if the transaction could be created successfully; otherwise {@code false}
	 */
	public boolean beginTransaction() {
		return currentDatabaseImpl.beginTransaction();
	}
	
	/**
	 * Commits the before created transaction.<br>
	 * Everything changed in the transaction will be committed.<br>
	 * The transaction will be closed after committing.<br>
	 * If no transaction was created before, this method does nothing
	 * 
	 * @return {@code true} if the commit was successful or no transaction was created before; otherwise {@code false}
	 */
	public boolean commitTransaction() {
		return currentDatabaseImpl.commitTransaction();
	}
	
	/**
	 * Rollback the before created transaction.<br>
	 * Everything changed in the transaction will be reverted and not persisted in the database.<br>
	 * The transaction will be closed after the rollback.<br>
	 * If no transaction was created before, this method does nothing
	 * 
	 * @return {@code true} if the rollback was successful or no transaction was created before; otherwise {@code false}
	 */
	public boolean rollbackTransaction() {
		return currentDatabaseImpl.rollbackTransaction();
	}
	
	/**
	 * Provides the name of the current database implementation.<br>
	 * This can be used for determining which statements can be used or something like this
	 * 
	 * @return the name of the class as {@link String}
	 */
	public String getImplementationName() {
		if (currentDatabaseImpl == null) {
			throw new IllegalStateException("Please create the database implementation first");
		}
		return currentDatabaseImpl.getClass().getName();
	}

	/* ----------------------- *
	 * Protected methods
	 * ----------------------- */
	/**
	 * Creates an instance of the implementation of the requested database system.<br>
	 * The database system is provided via a VM-Argument: "-dDatabase_System".<br>
	 * Currently supported database systems:
	 * <ul>
	 * <li>SQLite3</li>
	 * </ul>
	 * If a database system is requested which is not supported, the application throws a {@link NotImplementedException}.<br>
	 * If the argument is missing completely or no value is provided for the argument, the application throws a {@link IllegalArgumentException}
	 */
	protected void createDatabaseImpl() {
		String property = System.getProperty("Database_System");
		if (property != null && property.length() > 0) {
			if (StringUtils.equalsIgnoreCase(property, "SQLITE3") == true) {
				currentDatabaseImpl = new SQLite3Impl();
			} else {
				throw new NotImplementedException("The requested database system: " + property + " is not yet implemented");
			}
		} else {
			throw new IllegalArgumentException("The property \"Database_System\" has to be defined and provided with the name of the database system");
		}
	}
}

package de.krystan.common.database.impl;

/**
 * Interface for database implementations.<br>
 * This interface describes the methods each database implementation have to provide in order to be called via the API.<br>
 * This also enables different implementations for different database systems
 * 
 * @author Robert Duck
 * @since 09.01.2017
 */
public interface IDatabaseImpl {
	/**
	 * Opens a connection to the database for further requests to the database
	 * 
	 * @return {@code true} when the connection could be successfully established; otherwise {@code false}
	 */
	public boolean openConnection();
	
	/**
	 * Closes a already opened connection. <br>
	 * If the connection is closed, this method will immediately return without doing anything
	 * 
	 * @return {@code true} when the connection could be closed successfully or the method returned immediately; otherwise {@code false}
	 */
	public boolean closeConnection();
	
	/**
	 * Executes a given SQL statement in the database
	 * 
	 * @param sqlStatement the SQL statement to be executed
	 * @return the result set of the SQL statement or {@code null} if there was no result set or an error occured
	 */
	public Object executeStatement(String sqlStatement);
	
	/**
	 * Begins a transaction in the database in order to commit or rollback the modified or added records.<br>
	 * Necessary to call this method before committing changes or rollback changes
	 * 
	 * @return {@code true} if the transaction could be created successfully; otherwise {@code false}
	 */
	public boolean beginTransaction();
	
	/**
	 * Commits the before created transaction.<br>
	 * Everything changed in the transaction will be committed.<br>
	 * The transaction will be closed after committing.<br>
	 * If no transaction was created before, this method does nothing
	 * 
	 * @return {@code true} if the commit was successful or no transaction was created before; otherwise {@code false}
	 */
	public boolean commitTransaction();
	
	/**
	 * Rollback the before created transaction.<br>
	 * Everything changed in the transaction will be reverted and not persisted in the database.<br>
	 * The transaction will be closed after the rollback.<br>
	 * If no transaction was created before, this method does nothing
	 * 
	 * @return {@code true} if the rollback was successful or no transaction was created before; otherwise {@code false}
	 */
	public boolean rollbackTransaction();
}

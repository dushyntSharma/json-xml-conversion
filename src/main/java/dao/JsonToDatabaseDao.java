package dao;

import java.sql.SQLException;
import java.util.Set;

import model.Book;

public interface JsonToDatabaseDao {

	void storeBooks(int id, String title, int price) throws Exception;

	void storeAuthors(int id, String name) throws Exception;

	void storeBooksAuthors(int BookId, int AuthorId) throws SQLException, Exception;

	Set<Book> getBooks() throws Exception;

}

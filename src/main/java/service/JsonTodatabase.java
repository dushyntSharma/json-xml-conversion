package service;

import java.util.Set;

import model.Book;

public interface JsonTodatabase {

	void storeJsonData(Set<Book> books) throws Exception;

	void getBooks() throws Exception;
}

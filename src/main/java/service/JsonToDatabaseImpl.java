package service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dao.JsonToDatabaseDao;
import dao.JsonToDatabaseDaoImpl;
import model.Author;
import model.Book;

public class JsonToDatabaseImpl implements JsonTodatabase {
	JsonToDatabaseDao jsonTodatabaseDao = new JsonToDatabaseDaoImpl();
	List<Author> authorsService = new LinkedList<Author>();

	List<Author> authorsServiceNew = new LinkedList<Author>();

	public void storeJsonData(Set<Book> books) throws Exception {

		for (Book book : books) {
			for (Author author : book.getAuthors()) {
				boolean flag = checkList(author.getId());
				if (flag) {
					continue;
				} else {
					authorsService.add(new Author(author.getId(), author.getName()));
				}
			}
		}

		insertBooks(books);
		insertAuthors(authorsService);
		insertBooksAuthors(books);

	}

	private boolean checkList(int i) {

		boolean flag = false;

		Iterator<Author> itr = authorsService.iterator();
		while (itr.hasNext()) {
			if (itr.next().getId() == i) {
				flag = true;
				break;
			} else {
				flag = false;
			}
		}

		return flag;

	}

	private void insertBooksAuthors(Set<Book> books) throws SQLException, Exception {
		for (Book book : books) {
			for (Author author : book.getAuthors()) {
				int bookId = book.getId();
				int authorId = author.getId();
				jsonTodatabaseDao.storeBooksAuthors(bookId, authorId);
			}
		}

	}

	private void insertAuthors(List<Author> authorsService) throws Exception {

		for (Author author : authorsService) {
			int id = author.getId();
			String name = author.getName();
			jsonTodatabaseDao.storeAuthors(id, name);
		}

	}

	private void insertBooks(Set<Book> books) throws Exception {

		for (Book book : books) {
			int id = book.getId();
			String title = book.getTitle();
			int price = book.getPrice();

			jsonTodatabaseDao.storeBooks(id, title, price);
		}
	}

	public void getBooks() throws Exception {

		Set<Book> books = jsonTodatabaseDao.getBooks();

		String xmlData = "";
		if (books != null) {
			for (Book book : books) {
				xmlData += readObjToXml(book) + "\n";

			}
			System.out.println("***************************");
			System.out.println(xmlData);
			File file = new File("books.xml");
			try {

				Writer fw = new FileWriter(file);
				fw.write(xmlData);
				fw.flush();

			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(xmlData);

		} else {
			System.out.println("Data Generation failed");
		}

	}

	private String readObjToXml(Book book) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Book.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter sw = new StringWriter();

			marshaller.marshal(book, sw);
			String bookData = sw.toString();
			return bookData;
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}

}

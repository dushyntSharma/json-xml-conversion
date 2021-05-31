package client;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import model.Author;
import model.Book;
import service.JsonToDatabaseImpl;
import service.JsonTodatabase;

public class Application {
	public static void main(String[] args) {

		System.out.println("===============Welcome to the Application=================");

		String file = "D:/Script Workspace/XMLandJSON/books.json";

		Set<Book> books = readJson(file);
//		displayBooks(books);

		JsonTodatabase jsonTodatabase = new JsonToDatabaseImpl();

//		try {
//			jsonTodatabase.storeJsonData(books);
//			System.out.println("Json data stored to the database");
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//		try {
//			jsonTodatabase.getBooks();
//			System.out.println("Data Written to XML File");
//		} catch (Exception e1) {
//
//			e1.printStackTrace();
//		}

	}

	private static void displayBooks(Set<Book> books) {
		for (Book book : books) {
			System.out.println(book.getId());
			System.out.println(book.getTitle());
			System.out.println(book.getPrice());
			System.out.println("************************");
			for (Author author : book.getAuthors()) {
				System.out.println(author.getId());
				System.out.println(author.getName());

			}
		}
	}

	private static Set<Book> readJson(String file) {

		// Creating a JSONParser object
		JSONParser jsonParser = new JSONParser();
		List<Book> books = new ArrayList<Book>();
//		Set<Book> books = new HashSet<Book>();
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(file));
			// Retrieving the array
			JSONArray jsonArrayBooks = (JSONArray) jsonObject.get("books");
//			System.out.println(jsonArrayBooks);

			for (Object object : jsonArrayBooks) {

				JSONObject record = (JSONObject) object;

				int booksId = Integer.parseInt((String) record.get("id"));
				String title = (String) record.get("title");
				int price = Integer.parseInt(record.get("price").toString());

				JSONArray jsonArrayAuthors = (JSONArray) record.get("authors");
				Set<Author> authors = new HashSet<Author>();
				for (Object object2 : jsonArrayAuthors) {

					JSONObject record2 = (JSONObject) object2;

					int authorId = Integer.parseInt((String) record2.get("id"));
					String authorName = (String) record2.get("name");

					authors.add(new Author(authorId, authorName));

				}
				books.add(new Book(booksId, title, price, authors));

			}
			System.out.println("===================");
			System.out.println("USing the Comparable");
			System.out.println("==========================");
			Collections.sort(books);
			for (Book bk : books) {
				System.out.println(bk.getId() + " " + bk.getTitle() + " " + bk.getPrice());

			}
			System.out.println("===================");
			System.out.println("USing the comparator");
			System.out.println("==========================");

			Collections.sort(books, new Comparator<Book>() {

				@Override
				public int compare(Book o1, Book o2) {
					// TODO Auto-generated method stub
					return o1.getTitle().compareToIgnoreCase(o2.getTitle());
				}
			});
			for (Book bk : books) {
				System.out.println(bk.getId() + " " + bk.getTitle() + " " + bk.getPrice());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

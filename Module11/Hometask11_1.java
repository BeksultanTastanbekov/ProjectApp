import java.util.ArrayList;
import java.util.List;

class Book {
    String title;
    String author;
    String isbn;
    boolean available;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    @Override
    public String toString() {
        return "Книга: " + title + ", Автор: " + author + ", ISBN: " + isbn + ", Доступна: " + available;
    }
}

class Reader {
    String name;
    List<Book> borrowedBooks;

    public Reader(String name) {
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    public void borrowBook(Book book) {
        if (book.available) {
            borrowedBooks.add(book);
            book.available = false;
            System.out.println(name + " взял(а) книгу: " + book.title);
        } else {
            System.out.println("Книга " + book.title + " недоступна.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.available = true;
            System.out.println(name + " вернул(а) книгу: " + book.title);
        } else {
            System.out.println(name + " не брал(а) эту книгу.");
        }
    }
}

class Librarian {
    String name;

    public Librarian(String name) {
        this.name = name;
    }

    public void manageBooks(List<Book> books) {
        // Здесь можно добавить функционал управления книгами библиотекарем
    }
}

class Library {
    List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayAvailableBooks() {
        System.out.println("Доступные книги:");
        for (Book book : books) {
            if (book.available) {
                System.out.println(book);
            }
        }
    }
}


public class Hometask11_1 {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("1984", "Джордж Оруэлл", "978-5-17-097611-2"));
        library.addBook(new Book("Война и мир", "Лев Толстой", "978-5-17-090488-2"));

        Reader reader = new Reader("Арман");
        Librarian librarian = new Librarian("Алия");

        library.displayAvailableBooks();
        reader.borrowBook(library.books.get(0));
        library.displayAvailableBooks();
        reader.returnBook(library.books.get(0));
        library.displayAvailableBooks();
    }
}

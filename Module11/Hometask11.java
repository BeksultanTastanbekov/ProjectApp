import java.util.ArrayList;
import java.util.List;

// Класс книги
class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean status; // true - в наличии, false - арендована

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.status = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return status;
    }

    public void rentBook() {
        this.status = false;
    }

    public void returnBook() {
        this.status = true;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (ISBN: " + isbn + ", Available: " + (status ? "Yes" : "No") + ")";
    }
}

// Класс читателя
class Reader {
    private String name;
    private List<Book> rentedBooks;

    public Reader(String name) {
        this.name = name;
        this.rentedBooks = new ArrayList<>();
    }

    public void rentBook(Book book) {
        if (book.isAvailable()) {
            rentedBooks.add(book);
            book.rentBook();
            System.out.println(name + " rented " + book.getTitle());
        } else {
            System.out.println(book.getTitle() + " is not available.");
        }
    }

    public void returnBook(Book book) {
        if (rentedBooks.contains(book)) {
            rentedBooks.remove(book);
            book.returnBook();
            System.out.println(name + " returned " + book.getTitle());
        } else {
            System.out.println("This book is not rented by " + name);
        }
    }

    @Override
    public String toString() {
        return "Reader: " + name + ", Rented books: " + rentedBooks;
    }
}

// Класс библиотекаря
class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    public void manageLibrary(Library library) {
        System.out.println(name + " is managing the library.");
        library.displayBooks();
    }
}

// Класс библиотеки
class Library {
    private List<Book> books;

    public Library() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void displayBooks() {
        System.out.println("\nBooks in the library:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    // Метод getBooks() для получения списка книг
    public List<Book> getBooks() {
        return books;
    }
}

// Главный класс
public class Hometask11 {
    public static void main(String[] args) {
        Library library = new Library();

        // Добавляем книги в библиотеку
        library.addBook(new Book("1984", "George Orwell", "123456789"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "987654321"));
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "192837465"));

        // Создаем читателя и библиотекаря
        Reader reader = new Reader("Alice");
        Librarian librarian = new Librarian("Bob");

        // Управление библиотекой
        librarian.manageLibrary(library);

        // Аренда книги
        reader.rentBook(library.getBooks().get(0));
        reader.rentBook(library.getBooks().get(1));

        // Отображение после аренды
        librarian.manageLibrary(library);

        // Возврат книги
        reader.returnBook(library.getBooks().get(0));

        // Отображение после возврата
        librarian.manageLibrary(library);
    }
}

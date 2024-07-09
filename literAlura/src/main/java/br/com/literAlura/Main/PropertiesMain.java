package br.com.literAlura.Main;

import br.com.literAlura.Model.Author;
import br.com.literAlura.Model.AuthorData;
import br.com.literAlura.Model.Book;
import br.com.literAlura.Model.BookData;
import br.com.literAlura.Repository.AuthorRepository;
import br.com.literAlura.Repository.BookRepository;
import br.com.literAlura.Service.ClientSolicitation;
import br.com.literAlura.Service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class PropertiesMain {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientSolicitation clientSolicitation = new ClientSolicitation();
    private final String address =  "https://gutendex.com/books?search=";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private List<Book> books = new ArrayList<>();
    private final ConvertData convertData = new ConvertData();

    @Autowired
    public PropertiesMain(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void menu() {
        int choiceUser = -1;
        while (choiceUser != 6) {
            String menu = """
                -------------------------------------------------------------
                [pt-br] Bem-vindo ao LiterAlura. Escolha uma das opções:
                [eng] Welcome to LiterAlura. Choice an option:
                
                1 - Search book by title.
                2 - List books already registed.
                3 - List already registed authors.
                4 - List living authors in a given year.
                5 - List books in a given language.
                6 - Exit the application.
                -------------------------------------------------------------
                """;
            System.out.println(menu);
            choiceUser = scanner.nextInt();
            scanner.nextLine();
            switch (choiceUser) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    listBooksAlreadyRegisted();
                    break;
                case 3:
                    listAlreadyRegistedAuthors();
                    break;
                case 4:
                    listLivingAuthorsInAGivenYear();
                    break;
                case 5:
                    listBooksInAGivenLanguage();
                    break;
                case 6:
                    System.out.println("Goodbye." +
                            "<LiterAlura.Application>");
                    break;
                default:
                    System.out.println("Choice an valid option.");
            }
        }
    }

    private void searchBookByTitle() {
        System.out.println("Which book do you want to research?");
        var book = scanner.nextLine();
        var data = clientSolicitation.solicitation(address + book.replace(" ", "%20"));
        saveToDataBase(data);
    }

    private void saveToDataBase(String data) {
        try {
            System.out.println("Raw JSON Data: " + data); //Retorna o Json por inteiro
        BookData bookData = convertData.getData(data, BookData.class);
        AuthorData authorData = convertData.getData(data, AuthorData.class);

        System.out.println("Converted Book: " + bookData);
        System.out.println("Converted Author: " + authorData);

        if (bookData == null || authorData == null) {
            throw new NullPointerException("Book or Author conversion returned null");
        }

        //Pega o primeiro idioma da Lista
        String language = bookData.language().isEmpty() ? null : bookData.language().get(0);

        Author author = new Author(authorData.name(), authorData.birthYear(), authorData.deathYear());
        Book book = new Book(bookData.id(), bookData.title(), bookData.language(), bookData.downloadCount());
        Optional<Author> authorDataBaseOptional;

        if (!authorRepository.existsByName(author.getName())) {
            authorRepository.save(author);
            authorDataBaseOptional = Optional.of(author);
            System.out.println("Author saved: " + authorDataBaseOptional.get());
        } else {
            authorDataBaseOptional = authorRepository.findByName(author.getName());
            if (authorDataBaseOptional.isPresent()) {
                author = authorDataBaseOptional.get();
                System.out.println("Author found in database: " + author);
            } else {
                System.out.println("Multiple authors found with name: " + author.getName());
            }
        }
        //associação book -> author para salvar no database
        book.setAuthor(author);
        Optional<Book> bookDataBaseOptional;

        if (!bookRepository.existsByTitle(book.getTitle())) {
            bookRepository.save(book);
            bookDataBaseOptional = Optional.of(book);
            System.out.println("Book saved: " + bookDataBaseOptional.get());
        } else {
            bookDataBaseOptional = bookRepository.findByTitle(book.getTitle());
            if (bookDataBaseOptional.isPresent()) {
                book = bookDataBaseOptional.get();
                System.out.println("Book found in database: " + book);
            } else {
                System.out.println("Multiple books found with title: " + book.getTitle());
            }
        }

        bookDataBaseOptional.ifPresentOrElse(
                b -> System.out.println(b),
                () -> System.out.println("No book found.")
        );
    } catch (NullPointerException e) {
        System.out.println("Error. Book or Author not found.");
        e.printStackTrace(); // Log the stack trace to identify where the null pointer is happening
    } catch (Exception e) {
        System.out.println("An unexpected error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}
    @Transactional
    private void listBooksAlreadyRegisted() {
        var catchBook = bookRepository.findAll();
        if (!catchBook.isEmpty()) {
            System.out.println("Books Already Registed: ");
            catchBook.forEach(System.out::println);
        } else {
            System.out.println("No registered books.");
        }
    }

    private void listAlreadyRegistedAuthors() {
    }

    private void listLivingAuthorsInAGivenYear() {
    }

    private void listBooksInAGivenLanguage() {
    }
}

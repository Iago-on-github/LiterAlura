package br.com.literAlura;

import br.com.literAlura.Main.PropertiesMain;
import br.com.literAlura.Model.Author;
import br.com.literAlura.Repository.AuthorRepository;
import br.com.literAlura.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class DemoApplication implements CommandLineRunner {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		PropertiesMain main =  new PropertiesMain(bookRepository, authorRepository);
		main.menu();
	}
}

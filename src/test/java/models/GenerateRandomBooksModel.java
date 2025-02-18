package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

public class GenerateRandomBooksModel {

    public String generateBooks(int count) {
        BooksModels booksModels = new BooksModels();
        booksModels.setBooks(generateRandomBooks(count)); // Генерируем 5 случайных книг

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(booksModels);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }

    public List<BooksModels.Book> generateRandomBooks(int count) {
        Faker faker = new Faker();
        List<BooksModels.Book> books = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            BooksModels.Book book = new BooksModels.Book();
            book.setIsbn(faker.number().digits(13));
            book.setTitle(faker.book().title());
            book.setSubTitle(faker.book().title());
            book.setAuthor(faker.book().author());
            book.setPublish_date(faker.date().birthday().toString());
            book.setPublisher(faker.book().publisher());
            book.setPages(faker.number().numberBetween(100, 500));
            book.setDescription(faker.lorem().sentence());
            book.setWebsite(faker.internet().url());
            books.add(book);
        }
        return books;
    }
}

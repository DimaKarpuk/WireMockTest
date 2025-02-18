package models;

import lombok.Data;

import java.util.List;

@Data
public class BooksModels {
    private List<Book> books;

    @Data
    public static class Book {
        private String isbn;
        private String title;
        private String subTitle;
        private String author;
        private String publish_date;
        private String publisher;
        private int pages;
        private String description;
        private String website;
    }
}


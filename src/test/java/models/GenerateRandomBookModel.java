package models;

import com.github.javafaker.Faker;

public class GenerateRandomBookModel {
    public String jsonBodyBook() {
        Faker faker = new Faker();
        String isbn = faker.number().digits(13);
        String title = faker.book().title();
        String subTitle = faker.book().title();
        String author = faker.book().author();
        String publish_date = faker.date().birthday().toString();
        String publisher = faker.book().publisher();
        int pages = faker.number().numberBetween(100, 500);
        String description = faker.lorem().sentence();
        String website = faker.internet().url();

        String jsonBook = String.format("""
                {
                  "isbn": "%s",
                  "title": "%s",
                  "subTitle": "%s",
                  "author": "%s",
                  "publish_date": "%s",
                  "publisher": "%s",
                  "pages": %d,
                  "description": "%s",
                  "website": "%s"
                }
                """, isbn, title, subTitle, author, publish_date, publisher, pages, description, website);

        return jsonBook;
    }
}

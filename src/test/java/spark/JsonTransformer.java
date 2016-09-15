package spark;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Semih Okan Pehlivan
 */
public class JsonTransformer {

    private static Logger logger = LoggerFactory.getLogger(JsonTransformer.class.getName());

    @Test
    public void jsonTransformerTest() {
        spark.template.freemaker.JsonTransformer transformer = new spark.template.freemaker.JsonTransformer();
        Book book = new Book("Java for beginner", "Semih Okan Pehlivan");
        logger.info(book.toString());
        String json = null;
        try {
            json = transformer.render(book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("After to GSon");
        logger.info(json);
    }

    public static class Book {

        private String title, author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAutor(String author) {
            this.author = author;
        }

        @Override
        public String toString() {
            return "title: " + getTitle() + " \n" + "author: " + getAuthor();
        }
    }


}

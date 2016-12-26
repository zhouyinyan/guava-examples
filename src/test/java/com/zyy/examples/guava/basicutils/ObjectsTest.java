package com.zyy.examples.guava.basicutils;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zhouyinyan on 16/12/26.
 */
public class ObjectsTest {

    @Test
    public void test(){
        String expected = "hello";

        Assert.assertEquals(expected, Objects.firstNonNull(expected,"aaa"));
        Assert.assertEquals(expected, Objects.firstNonNull(null,expected));


        Book book1 = new Book();
        book1.setIsbn("123");
        book1.setPrice(20d);
        book1.setPublisher("zyy");
        book1.setTitle("title");

        Book book2 = new Book();
        book2.setIsbn("123");
        book2.setPrice(20d);
        book2.setPublisher("zyy");
        book2.setTitle("title");

        Assert.assertEquals(book1, book2);
    }


    class Book implements Comparable<Book> {
        private String title;
        private String publisher;
        private String isbn;
        private double price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return Objects.toStringHelper(this)
                    .add("title", title)
                    .add("publisher", publisher)
                    .add("isbn", isbn)
                    .add("price", price)
                    .toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return Double.compare(book.price, price) == 0 &&
                    Objects.equal(title, book.title) &&
                    Objects.equal(publisher, book.publisher) &&
                    Objects.equal(isbn, book.isbn);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(title, publisher, isbn, price);
        }

        @Override
        public int compareTo(Book o) {
            return ComparisonChain.start()
                    .compare(this.title, o.getTitle())
                    .compare(this.isbn, o.getIsbn())
                    .compare(this.price, o.getPrice())
                    .compare(this.publisher, o.getPublisher())
                    .result();
        }


    }
}

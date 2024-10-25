package org.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
class Book {
    private String title;
    private String author;
    private int year;
    private String isbn;
    private String publisher;
}

@Data
class Visitor {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private List<Book> favoriteBooks;
    private boolean subscribed;
}

public class Library {
    public static void main(String[] args) {
        Gson gson = new Gson();
        Type visitorListType = new TypeToken<List<Visitor>>(){}.getType();

        try (FileReader reader = new FileReader("C:/path/to/your/books.json")) { // Укажите правильный путь к файлу
            List<Visitor> visitors = gson.fromJson(reader, visitorListType);

            // Задание 1: Вывод списка посетителей и их количества
            System.out.println("Список посетителей:");
            visitors.forEach(visitor ->
                    System.out.println(visitor.getFirstName() + " " + visitor.getLastName())
            );
            System.out.println("Общее количество посетителей: " + visitors.size());

            // Задание 2: Вывод списка и количества книг, добавленных в избранное, без повторений
            Set<Book> uniqueFavoriteBooks = visitors.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .collect(Collectors.toSet());

            System.out.println("\nСписок уникальных книг в избранном:");
            uniqueFavoriteBooks.forEach(book ->
                    System.out.println(book.getTitle() + " by " + book.getAuthor())
            );
            System.out.println("Общее количество уникальных книг в избранном: " + uniqueFavoriteBooks.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
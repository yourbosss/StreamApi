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

public class Books {
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

            // Задание 3: Сортировка по году издания и вывод списка книг
            List<Book> sortedBooks = uniqueFavoriteBooks.stream()
                    .sorted((b1, b2) -> Integer.compare(b1.getYear(), b2.getYear()))
                    .collect(Collectors.toList());

            System.out.println("\nСписок книг, отсортированных по году издания:");
            sortedBooks.forEach(book ->
                    System.out.println(book.getTitle() + " by " + book.getAuthor() + " (" + book.getYear() + ")")
            );

            // Задание 4: Проверка на наличие книг автора "Jane Austen"
            boolean hasJaneAustenBook = visitors.stream()
                    .flatMap(visitor -> visitor.getFavoriteBooks().stream())
                    .anyMatch(book -> book.getAuthor().equalsIgnoreCase("Jane Austen"));

            if (hasJaneAustenBook) {
                System.out.println("\nЕсть книги автора 'Jane Austen' в избранном у посетителей.");
            } else {
                System.out.println("\nНет книг автора 'Jane Austen' в избранном у посетителей.");
            }

            // Задание 5: Вывод максимального числа добавленных в избранное книг
            int maxFavoriteBooksCount = visitors.stream()
                    .mapToInt(visitor -> visitor.getFavoriteBooks().size())
                    .max()
                    .orElse(0); // Возвращает 0, если нет ни одного посетителя

            System.out.println("\nМаксимальное количество добавленных в избранное книг: " + maxFavoriteBooksCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
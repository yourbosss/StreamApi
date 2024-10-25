package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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

public class LibraryVisitors {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
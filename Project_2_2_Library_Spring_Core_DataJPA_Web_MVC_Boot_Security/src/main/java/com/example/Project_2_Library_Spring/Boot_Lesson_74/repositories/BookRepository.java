package com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleStartingWith(String title);

    Optional<Book> findByTitleAndAuthor(String title, String author);
}

package org.example.repositories;

import org.example.models.Book;
import org.example.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    List<Book> findByReader(Person reader);
    List<Book> findByTitleIgnoreCaseStartingWith(String startingWith);
}

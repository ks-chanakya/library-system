package com.aeon.library.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aeon.library.entity.Book;

public interface BookRepo extends JpaRepository<Book, Integer>{
	
	@Query("SELECT b FROM Book b WHERE b.id NOT IN (SELECT bt.book.id FROM BookTransactions bt WHERE bt.borrowed = false)")
	List<Book> findAvailableBooks();

}

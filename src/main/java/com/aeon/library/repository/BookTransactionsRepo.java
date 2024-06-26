package com.aeon.library.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aeon.library.entity.BookTransactions;

public interface BookTransactionsRepo extends JpaRepository<BookTransactions, Integer> {

	@Query("SELECT bt FROM BookTransactions bt WHERE bt.borrower.id = :borrowerId AND bt.book.id = :bookId AND bt.borrowed = false")
    Optional<BookTransactions> findBorrowedBooks(@Param("borrowerId") Integer borrowerId, @Param("bookId") Integer bookId);

	//BookTransactions findBorrowedBooks(Integer id, UUID id2);

}

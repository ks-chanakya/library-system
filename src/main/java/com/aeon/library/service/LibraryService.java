package com.aeon.library.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.aeon.library.entity.Book;
import com.aeon.library.entity.BookTransactions;
import com.aeon.library.entity.Borrower;
import com.aeon.library.model.BookRequest;
import com.aeon.library.repository.BookRepo;
import com.aeon.library.repository.BookTransactionsRepo;
import com.aeon.library.repository.BorrowerRepo;

public class LibraryService {
	
	@Autowired
	BookRepo bookRepo;
	
	@Autowired
	BorrowerRepo borrowerRepo;
	
	@Autowired
	BookTransactionsRepo transactionsRepo;

	public ResponseEntity<Book> saveBook(Book book) {
		
		return new ResponseEntity<Book>(bookRepo.save(book), HttpStatus.CREATED);
	}

	public ResponseEntity<Borrower> saveBorrower(Borrower user) {
		
		return new ResponseEntity<Borrower>(borrowerRepo.save(user), HttpStatus.CREATED);
	}

	public ResponseEntity<List<Book>> getBookList() {
		
		return new ResponseEntity<List<Book>>((List<Book>) bookRepo.findAll(),HttpStatus.OK);
	}

	public ResponseEntity<Book> getParticularBook(Integer id) {
		
		return new ResponseEntity<Book>(bookRepo.findById(id).get(), HttpStatus.OK);
	}

	public ResponseEntity<BookTransactions> borrowParticularBook(BookRequest bookRequest) {
		
		Borrower borrower = borrowerRepo.findById(bookRequest.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));
		
        Book book = bookRepo.findById(bookRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        
        boolean transaction = transactionsRepo.findBorrowedBooks(borrower.getId(),book.getId()).isPresent();
		
        if(transaction) {
        	throw new IllegalStateException("book already borrowed");
        }
        
        BookTransactions bt = new BookTransactions();
		bt.setBook(book);
		bt.setBorrower(borrower);
		bt.setBorrowed(true);
		return new ResponseEntity<BookTransactions>(transactionsRepo.save(bt), HttpStatus.CREATED);
	}
	
	public ResponseEntity<BookTransactions> returnParticularBook(BookRequest bookRequest) {
		Borrower borrower = borrowerRepo.findById(bookRequest.getBorrowerId())
                .orElseThrow(() -> new ResourceNotFoundException("Borrower not found"));
		
        Book book = bookRepo.findById(bookRequest.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        
        boolean transaction = transactionsRepo.findBorrowedBooks(borrower.getId(),book.getId()).isPresent();
        BookTransactions bt = null;
        if(transaction) {
        	 bt = transactionsRepo.findBorrowedBooks(borrower.getId(),book.getId()).get();
        	bt.setBorrowed(false);
        	transactionsRepo.save(bt);
        }else {
        	throw new ResourceNotFoundException("No record of borrowing of the book");
        }
		
		return new ResponseEntity<BookTransactions>(transactionsRepo.save(bt), HttpStatus.OK);
    }

	public ResponseEntity<List<Book>> getAvailableBookList() {
		
		return new ResponseEntity<List<Book>>(bookRepo.findAvailableBooks(),HttpStatus.OK);
	}

}

package com.aeon.library.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aeon.library.entity.Book;
import com.aeon.library.entity.BookTransactions;
import com.aeon.library.entity.Borrower;
import com.aeon.library.model.BookRequest;
import com.aeon.library.service.LibraryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Library", description = "Endpoints for managing books and services")
public class LibraryController {
	
	@Autowired
	LibraryService service;
	
	@PostMapping("/saveBook")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		return service.saveBook(book);
	}
	
	@Operation(summary = "Register a new borrower")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Borrower registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
	@PostMapping("/saveBorrower")
	public ResponseEntity<Borrower> saveBorrower(@RequestBody Borrower user) {
		return service.saveBorrower(user);
	}
	
	@GetMapping("/Books")
	public ResponseEntity<List<Book>> getAllBooks() {
		return service.getBookList();
	}
	
	@GetMapping("/Book")
	public ResponseEntity<Book> getABook(@RequestParam Integer id) {
		return service.getParticularBook(id);
	}
	
	@GetMapping("/borrowBook")
	public ResponseEntity<BookTransactions> borrowBook(@RequestBody BookRequest bookRequest) {
		return service.borrowParticularBook(bookRequest);
	}
	
	@GetMapping("/returnBook")
	public ResponseEntity<BookTransactions> returnBook(@RequestBody BookRequest bookRequest) {
		return service.returnParticularBook(bookRequest);
	}
	
	@GetMapping("/availableBooks")
	public ResponseEntity<List<Book>> getAvailableBooks() {
		return service.getAvailableBookList();
	}

}

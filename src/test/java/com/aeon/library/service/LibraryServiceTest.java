package com.aeon.library.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.aeon.library.entity.Borrower;
import com.aeon.library.repository.BookRepo;
import com.aeon.library.repository.BorrowerRepo;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LibraryServiceTest {

    @InjectMocks
    private LibraryService libraryService;

    @Mock
    private BorrowerRepo borrowerRepo;
    
    @Mock
    private BookRepo bookRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerBorrower() {
        Borrower borrower = new Borrower();
        borrower.setId(123132);
        borrower.setName("John Doe");
        borrower.setEmail("john.doe@example.com");

        when(borrowerRepo.save(any(Borrower.class))).thenReturn(borrower);

        ResponseEntity<Borrower> savedBorrower = libraryService.saveBorrower(borrower);
        assertNotNull(savedBorrower);
        assertEquals("John Doe", savedBorrower.getBody().getName());
    }
}

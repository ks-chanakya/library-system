package com.aeon.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.aeon.library.entity.Borrower;

public interface BorrowerRepo extends JpaRepository<Borrower, Integer> {

}

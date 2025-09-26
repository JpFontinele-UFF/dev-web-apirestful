package com.fontineleantunes.apirestful.repository;

import com.fontineleantunes.apirestful.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}

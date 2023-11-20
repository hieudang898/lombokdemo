package com.example.lombokdemo.repository;

import com.example.lombokdemo.models.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepo extends JpaRepository<SanPham,Integer> {

}

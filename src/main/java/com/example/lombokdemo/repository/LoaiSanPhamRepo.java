package com.example.lombokdemo.repository;

import com.example.lombokdemo.models.LoaiSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiSanPhamRepo extends JpaRepository<LoaiSanPham, Integer> {
}

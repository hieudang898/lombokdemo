package com.example.lombokdemo.repository;

import com.example.lombokdemo.models.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepo extends JpaRepository<KhachHang,Integer> {
}

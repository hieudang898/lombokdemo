package com.example.lombokdemo.repository;

import com.example.lombokdemo.models.ChiTietHoaDon;
import com.example.lombokdemo.models.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietHoaDonRepo extends JpaRepository<ChiTietHoaDon, Integer> {
public List<ChiTietHoaDon> findChiTietHoaDonsByHoaDon(HoaDon hoaDon);
}


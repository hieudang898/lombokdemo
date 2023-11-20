package com.example.lombokdemo.repository;

import com.example.lombokdemo.models.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Integer> {
    @Query(value = "SELECT COUNT(hoadonid) FROM hoadon WHERE DATE(thoigiantao) = :currentDate", nativeQuery = true)
    public int countHoaDonByHoaDonId(@Param("currentDate") LocalDate currentDate);
    @Query(value = "select * from hoadon where thoigiantao between :thoiGianBatDau and :thoiGianKetThuc order by hoadonid desc",nativeQuery = true)
    public Page<HoaDon> timKiemHoaDonTrongKhoangThoiGian(LocalDate thoiGianBatDau,LocalDate thoiGianKetThuc,Pageable pageable);
    @Query(value = "select * from hoadon where tongtien between :giaThap and :giaCao order by hoadonid desc",nativeQuery = true)
    public  Page<HoaDon> timHoaDonTrongKhoangTienPaged(@Param("giaThap") double giaThap,
                                                       @Param("giaCao") double giaCao,
                                                       Pageable pageable);


}

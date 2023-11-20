package com.example.lombokdemo.services.iservices;

import com.example.lombokdemo.models.HoaDon;
import com.example.lombokdemo.repository.HoaDonRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.function.EntityResponse;


import java.time.LocalDate;
import java.util.List;

public interface IHoaDonService {
    public HoaDon themHoaDon(String hoaDonJson);
    public HoaDon xoaHoaDon(int idHoaDonXoa);
    public HoaDon suaHoaDon(String hoaDonSuaJson);
    public Page<HoaDon> timHoaDonTheoNgay(LocalDate ngayTimKiem,Pageable pageable);
    public Page<HoaDon> timHoaDonTrongKhoang(LocalDate ngayBatDau,LocalDate ngayKetThuc,Pageable pageable);
    public Page<HoaDon> timHoaDonTrongKhoangTienPaged(double giaThap, double giaCao, Pageable pageable);

}

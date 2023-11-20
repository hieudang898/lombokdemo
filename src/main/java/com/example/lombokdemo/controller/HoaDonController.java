package com.example.lombokdemo.controller;

import com.example.lombokdemo.models.HoaDon;
import com.example.lombokdemo.services.cservices.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.List;

@RestController
public class HoaDonController {
    @Autowired
    HoaDonService hoaDonService;

    @RequestMapping(value = "themhoadon", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    public HoaDon themHoaDon(@RequestBody String hoaDonMoi) {
//        return hoaDonService.themHoaDon(hoaDonMoi);
//    }
    public ResponseEntity<HoaDon> themHoaDon(@RequestBody String hoaDonMoi) {
        return ResponseEntity.ok(hoaDonService.themHoaDon(hoaDonMoi));
    }

    @RequestMapping(value = "xoahoadon", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public HoaDon themHoaDon(@RequestParam int idHoaDonXoa) {
        return hoaDonService.xoaHoaDon(idHoaDonXoa);
    }

    @GetMapping(value = "timhoadontheongay", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HoaDon> timHoaDonTheoNgay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayTimKiem,
                                          @RequestParam int pageNumber,
                                          @RequestParam int pageSize) {
        Pageable page = (Pageable) PageRequest.of(pageNumber, pageSize, Sort.by("hoaDonId").descending());
        return hoaDonService.timHoaDonTheoNgay(ngayTimKiem, page);
    }

    @PutMapping(value = "suahoadon", produces = MediaType.APPLICATION_JSON_VALUE)
    public HoaDon suaHoaDon(@RequestBody String hoaDonSuaJson) {
        return hoaDonService.suaHoaDon(hoaDonSuaJson);
    }

    @GetMapping(value = "timhoadontrongkhoangthoigian", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<HoaDon> hoaDonsTrongKhoang(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayBatDau,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayKetThuc,
                                           @RequestParam int pageNumber,
                                           @RequestParam int pageSize) {
        Pageable page = (Pageable) PageRequest.of(pageNumber, pageSize);

        return hoaDonService.timHoaDonTrongKhoang(ngayBatDau, ngayKetThuc, page);
    }

    @GetMapping(value = "timhoadontrongkhoangtien", produces = MediaType.APPLICATION_JSON_VALUE)

    public Page<HoaDon> hoaDonsTrongKhoangTien(@RequestParam double giaThap,
                                               @RequestParam double giaCao,
                                               @RequestParam int pageNumber,
                                               @RequestParam int pageSize) {
        Pageable page = (Pageable) PageRequest.of(pageNumber, pageSize);
        return hoaDonService.timHoaDonTrongKhoangTienPaged(giaThap, giaCao, page);
    }

}

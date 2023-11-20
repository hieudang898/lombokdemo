package com.example.lombokdemo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "hoadon")
@Getter
@Setter
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hoadonid")
    private int hoaDonId;
    @Column(name = "tenhoadon")
    private String tenHoaDon;
    @Column(name = "magiaodich")
    private String maGiaoDich;
    @Column(name = "thoigiantao")
    private LocalDate thoigiantao;
    @Column(name = "thoigiancapnhat")
    private LocalDate thoiGianCapNhat;
    @Column(name = "ghichu")
    private String ghiChu;
    @Column(name = "tongtien")
    private double tongTien;
    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL)
    private List<ChiTietHoaDon> chiTietHoaDons;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "khachhangid")
    private KhachHang khachHang;
}

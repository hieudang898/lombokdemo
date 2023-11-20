package com.example.lombokdemo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chitiethoadon")
public class ChiTietHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chitietid")
    private int chiTietId;
    @Column(name = "soluong")
    private int soLuong;
    @Column(name = "donvitinh")
    private String donViTinh;
    @Column(name = "thanhtien")
    private double thanhTien;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "hoadonid")
    private HoaDon hoaDon;


    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sanphamid", referencedColumnName = "sanphamid")  // Added referencedColumnName
    private SanPham sanPham;
}

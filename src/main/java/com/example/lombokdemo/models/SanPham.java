package com.example.lombokdemo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sanpham")
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sanphamid")
    private int sanPhamId;
    @Column(name = "tensanpham")
    private String tenSanPham;
    @Column(name = "giathanh")
    private double giaThanh;
    @Column(name = "mota")
    private String moTa;
    @Column(name = "ngayhethan")
    private LocalDate ngayHetHan;
    @Column(name = "kyhieusanpham")
    private String kyHieuSanPham;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "loaisanphamid")
    private LoaiSanPham loaiSanPham;
    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ChiTietHoaDon> chiTietHoaDons;
}

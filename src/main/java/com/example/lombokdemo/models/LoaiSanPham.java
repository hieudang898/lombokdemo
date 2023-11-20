package com.example.lombokdemo.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "loaisanpham")
public class LoaiSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loaisanphamid")
    private int loaiSanPhamId;
    @Column(name = "tenloai")
    private String tenLoai;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiSanPham", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SanPham> sanPhams;
}

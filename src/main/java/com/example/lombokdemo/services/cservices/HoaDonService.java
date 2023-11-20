package com.example.lombokdemo.services.cservices;

import com.example.lombokdemo.models.ChiTietHoaDon;
import com.example.lombokdemo.models.HoaDon;
import com.example.lombokdemo.models.SanPham;
import com.example.lombokdemo.repository.ChiTietHoaDonRepo;
import com.example.lombokdemo.repository.HoaDonRepo;
import com.example.lombokdemo.repository.SanPhamRepo;
import com.example.lombokdemo.services.iservices.IHoaDonService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HoaDonService implements IHoaDonService {
    //Autowired Repo
    @Autowired
    HoaDonRepo hoaDonRepo;
    @Autowired
    ChiTietHoaDonRepo chiTietHoaDonRepo;
    @Autowired
    SanPhamRepo sanPhamRepo;

    // tạo mã sản phẩm tự tăng
    private LocalDate currentDate = LocalDate.now();
    private DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private double tinhTongTienCuaChiTiet(ChiTietHoaDon chiTietHoaDon) {
        return chiTietHoaDon.getSoLuong() * chiTietHoaDon.getSanPham().getGiaThanh();
    }

    public String taoMaGiaoDich() {
        LocalDate currentDate = LocalDate.now();
        int counter = hoaDonRepo.countHoaDonByHoaDonId(currentDate);
        ++counter;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = currentDate.format(formatter);
        String counterPart = String.format("%03d", counter);

        return datePart + "-" + counterPart;
    }

    //tính tổng tiền của 1 list chi tiết hóa đơn
    private double tinhTongTien(List<ChiTietHoaDon> chiTietHoaDons) {
        double tongTien = chiTietHoaDons.stream().mapToDouble(ChiTietHoaDon::getThanhTien).sum();
        return tongTien;
    }

    //thêm hóa đơn
    @Override
    public HoaDon themHoaDon(String hoaDonJson) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer() {
                    @Override
                    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
                        return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString());
                    }
                }
        ).create();
        HoaDon hoaDonMoi = gson.fromJson(hoaDonJson, HoaDon.class);
        hoaDonMoi.setMaGiaoDich(taoMaGiaoDich());
        hoaDonMoi.setThoigiantao(currentDate);
        hoaDonRepo.save(hoaDonMoi);

        for (ChiTietHoaDon cthd : hoaDonMoi.getChiTietHoaDons()
        ) {
            Optional<SanPham> sanPhamOptional = sanPhamRepo.findById(cthd.getSanPham().getSanPhamId());
            if (sanPhamOptional.isEmpty()) {
                hoaDonRepo.delete(hoaDonMoi);
                return null;

            }
            SanPham sanPham = sanPhamOptional.get();
            cthd.setThanhTien(cthd.getSoLuong() * sanPham.getGiaThanh());
            cthd.setHoaDon(hoaDonMoi);
            chiTietHoaDonRepo.save(cthd);

        }

        hoaDonMoi.setTongTien(tinhTongTien(hoaDonMoi.getChiTietHoaDons()));
        hoaDonRepo.save(hoaDonMoi);
        return hoaDonMoi;
    }

    //Xóa hóa đơn theo id
    @Override
    public HoaDon xoaHoaDon(int idHoaDonXoa) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(idHoaDonXoa);
        if (hoaDonOptional.isEmpty()) return null;
        hoaDonRepo.delete(hoaDonOptional.get());
        return hoaDonOptional.get();
    }

//    sửa hóa đơn
    @Override
    public HoaDon suaHoaDon(String hoaDonSuaJson) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer() {
                    @Override
                    public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
                        return LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString());
                    }
                }
        ).create();
        HoaDon updatedHoaDon = gson.fromJson(hoaDonSuaJson, HoaDon.class);

        Optional<HoaDon> hoaDonOptional = hoaDonRepo.findById(updatedHoaDon.getHoaDonId());
        if (hoaDonOptional.isPresent()) {
            HoaDon existingHoaDon = hoaDonOptional.get();
            for (ChiTietHoaDon updatedCT : updatedHoaDon.getChiTietHoaDons()) {
                // Find the corresponding ChiTietHoaDon in the existing HoaDon
                Optional<ChiTietHoaDon> existingCTOptional = chiTietHoaDonRepo.findById(updatedCT.getChiTietId());

                if (existingCTOptional.isPresent()) {
                    ChiTietHoaDon existingCT = existingCTOptional.get();
                    existingCT.setHoaDon(updatedCT.getHoaDon());

                    // Update SanPhamId in SanPham of ChiTietHoaDon
                    existingCT.getSanPham().setSanPhamId(updatedCT.getSanPham().getSanPhamId());

                    // Update SoLuong and ThanhTien of ChiTietHoaDon
                    existingCT.setSoLuong(updatedCT.getSoLuong());
                    existingCT.setThanhTien(updatedCT.getSoLuong() * updatedCT.getSanPham().getGiaThanh());

                    // Save the updated ChiTietHoaDon
                    chiTietHoaDonRepo.save(existingCT);
                }
            }

            // Update ChiTietHoaDons of the existing HoaDon
            existingHoaDon.setChiTietHoaDons(updatedHoaDon.getChiTietHoaDons());
            existingHoaDon.setThoiGianCapNhat(LocalDate.now());
            existingHoaDon.setTongTien(tinhTongTien(updatedHoaDon.getChiTietHoaDons()));
            hoaDonRepo.save(existingHoaDon);
            return existingHoaDon;
        }
        return null;

    }



    //tìm kiếm hóa đơn theo ngày
    @Override
    public Page<HoaDon> timHoaDonTheoNgay(LocalDate ngayTimKiem, Pageable pageable) {
//        Page<HoaDon> hoaDonTimKiemTheoNgay = hoaDonRepo.findAll(pageable)
//                .stream()
//                .filter(hd -> hd.getThoigiantao().equals(ngayTimKiem))
//                .collect(Collectors.toList());
//        hoaDonTimKiemTheoNgay.sort(Comparator.comparingInt(HoaDon::getHoaDonId).reversed());
//        return hoaDonTimKiemTheoNgay;
        Page<HoaDon> hoaDonTimKiemTheoNgay = hoaDonRepo.findAll(pageable);
        return hoaDonTimKiemTheoNgay;
    }


    //tìm kiếm trong khoảng thời gian

    @Override
    public Page<HoaDon> timHoaDonTrongKhoang(LocalDate ngayBatDau, LocalDate ngayKetThuc, Pageable pageable) {
        return hoaDonRepo.timKiemHoaDonTrongKhoangThoiGian(ngayBatDau, ngayKetThuc, pageable);
    }

    //tìm hóa đơn trong khoảng tiền

    @Override
    public Page<HoaDon> timHoaDonTrongKhoangTienPaged(double giaThap, double giaCao, Pageable pageable) {
        return hoaDonRepo.timHoaDonTrongKhoangTienPaged(giaThap, giaCao, pageable);
    }
}

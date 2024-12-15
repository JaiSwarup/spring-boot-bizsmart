package com.jai.bizsmart.Repository;

import com.jai.bizsmart.Model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDetail_Repository extends JpaRepository<BillDetail, Integer> {
    @Query(value = "SELECT bd FROM BillDetail bd WHERE bd.bill.id = :billId")
    List<BillDetail> findAllByBillId(@Param("billId") Integer billId);

    @Query(value = "SELECT bd.bill.id FROM BillDetail bd WHERE bd.id = :idBillDetail")
    public Integer idBillSearchByIdBillDetail(@Param("idBillDetail") Integer idBillDetail);
}

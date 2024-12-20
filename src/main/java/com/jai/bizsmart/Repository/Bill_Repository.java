package com.jai.bizsmart.Repository;

import com.jai.bizsmart.Model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface Bill_Repository extends JpaRepository<Bill, Integer> {
    @Query(value = "SELECT b FROM Bill b WHERE b.status = :status")
    List<Bill> findBillByStatus(@Param("status") Integer status);

    @Query(value = "SELECT b FROM Bill b WHERE b.user.name_User like :keyWord")
    Bill findBillByUser(@Param("keyWord") String keyWord);

    @Query(value = "SELECT b FROM Bill b WHERE b.status = 1")
    List<Bill> getBillActive();


}

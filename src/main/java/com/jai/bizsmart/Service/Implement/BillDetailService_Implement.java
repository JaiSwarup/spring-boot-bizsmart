package com.jai.bizsmart.Service.Implement;

import com.jai.bizsmart.Model.BillDetail;
import com.jai.bizsmart.Model.ProductDetail;
import com.jai.bizsmart.Repository.BillDetail_Repository;
import com.jai.bizsmart.Repository.ProductDetail_Repository;
import com.jai.bizsmart.Service.BillDetail_Service;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BillDetailService_Implement implements BillDetail_Service {
    private final BillDetail_Repository billDetailRepository;
    private final ProductDetail_Repository productDetailRepository;

    @Override
    public List<BillDetail> findAll() {
        return billDetailRepository.findAll();
    }

    @Override
    public List<BillDetail> findAllByBillId(Integer billId) {
        return billDetailRepository.findAllByBillId(billId);
    }

    @Override
    public BillDetail findById(Integer id) throws ChangeSetPersister.NotFoundException {
        return billDetailRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    @Override
    public BillDetail save(BillDetail billDetail) throws ChangeSetPersister.NotFoundException {
        ProductDetail productDetail = productDetailRepository.findById(billDetail.getProductDetail().getId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (productDetail.getQuanlity_ProductDetail() < billDetail.getQuanlity_BillDetail()) {
            return null;
        } else {
            productDetail.setQuanlity_ProductDetail(productDetail.getQuanlity_ProductDetail() - billDetail.getQuanlity_BillDetail());
            productDetailRepository.save(productDetail);
        }

        BigDecimal total = productDetail.getUnitPrice_ProductDetail().multiply(BigDecimal.valueOf(billDetail.getQuanlity_BillDetail()));
        billDetail.setTotalAmount_BillDetail(total);
        return billDetailRepository.save(billDetail);
    }

    @Override
    public void update(Integer id, BillDetail billDetailDto) throws ChangeSetPersister.NotFoundException {
        BillDetail billDetailFind = billDetailRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        ProductDetail productDetail = productDetailRepository.findById(billDetailDto.getProductDetail().getId())
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());

        // Restore the previous quantity to the product's inventory
        ProductDetail previousProductDetail = billDetailFind.getProductDetail();
        if (previousProductDetail != null) {
            previousProductDetail.setQuanlity_ProductDetail(previousProductDetail.getQuanlity_ProductDetail() + billDetailFind.getQuanlity_BillDetail());
            productDetailRepository.save(previousProductDetail);
        }

        if (productDetail.getQuanlity_ProductDetail() < billDetailDto.getQuanlity_BillDetail()) {
            System.out.println("Không thể thực hiện UPDATE");
            return;
        }

        // Update the product's quantity in the inventory
        productDetail.setQuanlity_ProductDetail(productDetail.getQuanlity_ProductDetail() - billDetailDto.getQuanlity_BillDetail());
        productDetailRepository.save(productDetail);

        // Update bill details
        billDetailFind.setProductDetail(billDetailDto.getProductDetail());
        billDetailFind.setQuanlity_BillDetail(billDetailDto.getQuanlity_BillDetail());
        billDetailFind.setStatic(billDetailDto.getStatic());

        // Recalculate the total amount
        BigDecimal total = productDetail.getUnitPrice_ProductDetail().multiply(BigDecimal.valueOf(billDetailDto.getQuanlity_BillDetail()));
        billDetailFind.setTotalAmount_BillDetail(total);

        billDetailRepository.save(billDetailFind);
    }


    @Override
    public void removeOrRever(Integer id) throws ChangeSetPersister.NotFoundException {
        BillDetail billDetailFind = billDetailRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        if (billDetailFind != null) {
            billDetailFind.setStatic(0);
        }
    }


}

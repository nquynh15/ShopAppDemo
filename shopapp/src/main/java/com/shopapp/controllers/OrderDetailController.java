package com.shopapp.controllers;

import com.shopapp.dtos.OrderDTO;
import com.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8080/api/v1/order_detail
@RequestMapping("api/v1/order_detail")
public class OrderDetailController {

    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
                    @Valid @RequestBody OrderDetailDTO newOrderDetail){
        return ResponseEntity.ok("create order detail successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(
                    @Valid @PathVariable("id") Long id){
        return ResponseEntity.ok("get order detail");
    }

    //Lấy ra danh sách các order_details của 1 order
    @GetMapping("/order/{orderId}")
//    public ResponseEntity<List<?>> getOrderDetails
    public ResponseEntity<?> getOrderDetails(
                @Valid @PathVariable("orderId") Long orderId){
//        List<OrderDetailDTO> orderDetailDTOList = orderDetailService.getOrderDetails(orderId);
        return ResponseEntity.ok("getOrderDetails with orderID " + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updategetOrderDetail(
            @Valid @PathVariable("id") Long id,
            @RequestBody OrderDetailDTO newOrderDetailData){
        return ResponseEntity.ok("update orderdetail with id = " + id +
                " , new OrderTail: " + newOrderDetailData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteOrderDetail(@Valid @PathVariable Long id){
        //xóa  mềm => cập nhật active = false
        return ResponseEntity.ok("Xóa thành công orderdetail: " + String.valueOf(id));
    }
}

package com.shopapp.controllers;

import com.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//http://localhost:8080/api/v1/orders ${api.prefix}
@RequestMapping("api/v1/orders")
public class OrderController {

    @PostMapping("")
    public ResponseEntity<?> createUser(@Valid @RequestBody OrderDTO orderDTO,
                                        BindingResult result){
        try{
            if(result.hasErrors()){
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            return ResponseEntity.ok("create order successfully");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?>  getOrders(@Valid @PathVariable("user_id") Long userId){
        try{
            return ResponseEntity.ok("Lay ra danh sach order tu user_id "  + String.valueOf(userId));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    //công việc admin
    public ResponseEntity<?>  updateOrder(@Valid @PathVariable Long id,
                                          @Valid @RequestBody OrderDTO orderDTO){
        try{
            return ResponseEntity.ok("Cap nhat thanh cong order " + String.valueOf(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>  deleteOrder(@Valid @PathVariable Long id){
        //xóa  mềm => cập nhật active = false
        return ResponseEntity.ok("Xóa thành công order " + String.valueOf(id));
    }
}

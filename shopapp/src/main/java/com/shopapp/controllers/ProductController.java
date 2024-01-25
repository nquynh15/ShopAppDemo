package com.shopapp.controllers;

import com.shopapp.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
//http://localhost:8080/api/v1/products
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        return ResponseEntity.ok("Page " + page + " limit " +limit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable("id") String productId){
        return ResponseEntity.ok("Product with id " + productId);
    }

    //Upload 1 file anh
//    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO,
////                                            @RequestPart("file") MultipartFile file,
//                                           BindingResult result){
//        try{
//            if(result.hasErrors()){
//                //tao ra mot ds loic
//                List<String> errorMessages = result.getFieldErrors()
//                        .stream()
//                        .map(FieldError::getDefaultMessage) //~ fieldError -> fieldError.getDefaultMessage()
//                        .toList();
//                return ResponseEntity.badRequest().body(errorMessages);
//            }
//
//            MultipartFile file = productDTO.getFile();
//            if(file != null){
//                //Kiểm tra kích thước file và định dạng chung
//                if(file.getSize() > 10 *1024 * 1024){
//                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
//                            .body("File is too large!, Maxium size is 10MB");
//                }
//                String contentType = file.getContentType();
//                if(contentType == null || !contentType.startsWith("image/")){
//                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                            .body("File must be an image");
//                }
//                //Lưu file và cập nhật thumbnail trong DTO
//                String filename = storeFile(file);
//                //Luu vao db
//            }
//
//            return ResponseEntity.ok("Product creates successfully!");
//        }catch(Exception e){
//            return  ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    "product_name" : "Macbook air 15 2023",
//    "price": 1200,
//    "thumbnail" : "",
//    "product_description": "This is test product",
//    "category_id": 1

    //upload nhieu file
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute ProductDTO productDTO,
//                                            @RequestPart("file") MultipartFile file,
                                           BindingResult result){
        try{
            if(result.hasErrors()){
                //tao ra mot ds loic
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage) //~ fieldError -> fieldError.getDefaultMessage()
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() : files;
            for (MultipartFile file: files) {
                if(file.getSize() == 0)
                    continue;
                //Kiểm tra kích thước file và định dạng chung
                if(file.getSize() > 10 *1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large!, Maxium size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                //Lưu file và cập nhật thumbnail trong DTO
                String filename = storeFile(file);
            }
            return ResponseEntity.ok("Product creates successfully!");
        }catch(Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //Thêm UUID vào trước tên file để đảm bảo tên file là suy nhất
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        //Đường dẫn đến thư mục lưu file
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Đường dẫn đầy đủ ến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable long id){
        return ResponseEntity.ok("Update the category " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable long id){
        return ResponseEntity.ok(String.format("Product with id = %d deleted successfully", id));
    }
}


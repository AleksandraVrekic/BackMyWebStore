package com.clothesShop.mypcg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clothesShop.mypcg.dto.OrderDetailRequestDTO;
import com.clothesShop.mypcg.dto.OrderDetailResponseDTO;
import com.clothesShop.mypcg.entity.OrderDetail;
import com.clothesShop.mypcg.mapper.OrderDetailMapper;
import com.clothesShop.mypcg.service.OrderDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-details")
public class OrderDetailController {

	@Autowired
    private OrderDetailsService orderDetailService;
    
    @Autowired
    private OrderDetailMapper orderDetailMapper;


    @Autowired
    public OrderDetailController(OrderDetailsService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponseDTO>> getAllOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailService.getAllOrderDetails();
        List<OrderDetailResponseDTO> responseDTOs = orderDetails.stream()
                .map(orderDetailMapper::mapToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDTO> getOrderDetailById(@PathVariable int id) {
        Optional<OrderDetail> orderDetailOptional = orderDetailService.getOrderDetailsById(id);
        if (orderDetailOptional.isPresent()) {
            OrderDetail orderDetail = orderDetailOptional.get();
            OrderDetailResponseDTO responseDTO = orderDetailMapper.mapToDTO(orderDetail);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetailRequestDTO requestDTO) {
        // Convert the requestDTO to an OrderDetail entity and save it
        OrderDetail orderDetail = orderDetailMapper.mapToEntity(requestDTO);
        OrderDetail createdOrderDetail = orderDetailService.createOrderDetails(orderDetail);
        return new ResponseEntity<>(createdOrderDetail, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable int id, @RequestBody OrderDetailRequestDTO requestDTO) {
        Optional<OrderDetail> orderDetailOptional = orderDetailService.getOrderDetailsById(id);
        if (orderDetailOptional.isPresent()) {
            OrderDetail orderDetail = orderDetailOptional.get();
            orderDetail.setAmount(requestDTO.getAmount());
            orderDetail.setPrice(requestDTO.getPrice());
            orderDetail.setQuantity(requestDTO.getQuantity());
            
            OrderDetail savedOrderDetail = orderDetailService.updateOrderDetail(orderDetail);
            return new ResponseEntity<>(savedOrderDetail, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable int id) {
        boolean deleted = orderDetailService.deleteOrderDetails(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.clothesShop.mypcg.mapper;

import org.springframework.stereotype.Component;

import com.clothesShop.mypcg.dto.OrderDetailRequestDTO;
import com.clothesShop.mypcg.dto.OrderDetailResponseDTO;
import com.clothesShop.mypcg.entity.OrderDetail;

/*@Component
public class OrderDetailMapper {

    public OrderDetailResponseDTO mapToDTO(OrderDetail orderDetail) {
        OrderDetailResponseDTO dto = new OrderDetailResponseDTO();
        dto.setId(orderDetail.getId());
        dto.setAmount(orderDetail.getAmount());
        dto.setPrice(orderDetail.getPrice());
        dto.setQuantity(orderDetail.getQuantity());
        dto.setOrderId(orderDetail.getOrder().getId());
        dto.setProductId(orderDetail.getProduct().getId());
        return dto;
    }
    
    public OrderDetail mapToEntity(OrderDetailRequestDTO requestDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setAmount(requestDTO.getAmount());
        orderDetail.setPrice(requestDTO.getPrice());
        orderDetail.setQuantity(requestDTO.getQuantity());
        // You can set other properties of the entity based on the requestDTO
        return orderDetail;
    }
}*/

package com.zky.infrastructure.adapter.port;


import com.zky.domain.order.adapter.port.IProductPort;
import com.zky.domain.order.model.entity.ProductEntity;
import com.zky.infrastructure.gateway.ProductRPC;
import com.zky.infrastructure.gateway.dto.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public class ProductPort implements IProductPort {

    private ProductRPC productRPC;

    public ProductPort(ProductRPC productRPC) {
        this.productRPC = productRPC;
    }

    @Override
    public ProductEntity queryProductByProductId(String productId) {
        ProductDTO productDTO = productRPC.queryProductByProductId(productId);
        return ProductEntity.builder()
                .productId(productDTO.getProductId())
                .productName(productDTO.getProductName())
                .productDesc(productDTO.getProductDesc())
                .price(productDTO.getPrice())
                .build();
    }
}


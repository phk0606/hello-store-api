package com.hellostore.ecommerce.entity;

import com.hellostore.ecommerce.dto.CartProductDto;
import com.hellostore.ecommerce.dto.CartProductOptionDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "cartProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductOption> cartProductOptions = new ArrayList<>();

    private int quantity;

    public static CartProduct createCartProduct(Cart cart, Product product, CartProductDto cartProductDto) {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(cartProductDto.getQuantity());

        List<CartProductOptionDto> cartProductOptionDtos = cartProductDto.getProductOptions();
        List<CartProductOption> cartProductOptions = new ArrayList<>();
        for (CartProductOptionDto cartProductOption : cartProductOptionDtos) {
            cartProductOptions.add(
                    CartProductOption.builder()
                            .cartProduct(cartProduct)
                            .optionGroupNumber(cartProductOption.getOptionGroupNumber())
                            .optionName(cartProductOption.getOptionName())
                            .optionValue(cartProductOption.getOptionValue())
                            .optionId(cartProductOption.getOptionId())
                            .build());
        }
        cartProduct.setCartProductOptions(cartProductOptions);

        return cartProduct;
    }
}

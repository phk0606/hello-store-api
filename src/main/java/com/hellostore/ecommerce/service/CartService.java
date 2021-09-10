package com.hellostore.ecommerce.service;

import com.hellostore.ecommerce.dto.CartProductDto;
import com.hellostore.ecommerce.entity.Cart;
import com.hellostore.ecommerce.entity.CartProduct;
import com.hellostore.ecommerce.entity.Product;
import com.hellostore.ecommerce.entity.User;
import com.hellostore.ecommerce.repository.CartProductRepository;
import com.hellostore.ecommerce.repository.CartRepository;
import com.hellostore.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final UserService userService;
    private final ProductRepository productRepository;

    @Transactional
    public void addCartProduct(CartProductDto cartProductDto) {

        log.debug("cartProductDto: {}", cartProductDto);
        Long userNo = userService.getLoginUserInfo().getUserNo();
        Cart cart = cartRepository.getCart(userNo);

        if (ObjectUtils.isEmpty(cart)) {
            cart = cartRepository.save(new Cart(User.builder().id(userNo).build()));
        }

        Product product = productRepository.getProduct(cartProductDto.getProductId());

        cartProductRepository.save(
                CartProduct.builder()
                        .cart(cart)
                        .product(product)
                        .quantity(cartProductDto.getQuantity())
                        .firstOptionName(cartProductDto.getFirstOptionName())
                        .firstOptionValue(cartProductDto.getFirstOptionValue())
                        .secondOptionName(cartProductDto.getSecondOptionName())
                        .secondOptionValue(cartProductDto.getSecondOptionValue())
                        .build());
    }

    public List<CartProductDto> getCartProducts(String username) {
        return cartProductRepository.getCartProducts(username);
    }
}

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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public List<CartProductDto> getCartProducts(String username) throws IOException {
        List<CartProductDto> cartProducts = cartProductRepository.getCartProducts(username);

        for (CartProductDto cartProduct : cartProducts) {
            cartProduct.setImage(Files.readAllBytes(
                    Paths.get(cartProduct.getFilePath(), cartProduct.getFileName())));
        }

        return cartProducts;
    }

    @Transactional
    public void modifyQuantity(Long cartProductId, int quantity) {
        cartProductRepository.modifyQuantity(cartProductId, quantity);
    }

    @Transactional
    public void removeCartProducts(List<Long> cartProductIds, Long cartId) {
        cartProductRepository.removeCartProducts(cartProductIds);
        boolean existCartProducts = cartProductRepository.existCartProducts(cartId);
        if (!existCartProducts) {
            cartRepository.removeCart(cartId);
        }
    }
}

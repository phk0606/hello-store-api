package com.hellostore.ecommerce.controller;

import com.hellostore.ecommerce.dto.CartProductDto;
import com.hellostore.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/addCartProduct")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void addCartProduct(@RequestBody CartProductDto cartProductDto) {

        cartService.addCartProduct(cartProductDto);
    }

    @GetMapping("/getCartProducts")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public List<CartProductDto> getCartProducts(@RequestParam String username) throws IOException {
        return cartService.getCartProducts(username);
    }
}

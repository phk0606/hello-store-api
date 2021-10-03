package com.hellostore.ecommerce.config;

import com.hellostore.ecommerce.jwt.JwtAccessDeniedHandler;
import com.hellostore.ecommerce.jwt.JwtAuthenticationEntryPoint;
import com.hellostore.ecommerce.jwt.JwtSecurityConfig;
import com.hellostore.ecommerce.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("h2-console/**", "/favicon.ico", "/error", "/resources/**")
                .antMatchers("/images/products/**")
                .antMatchers("/api/getCategories")
                .antMatchers("/api/getChildCategories")
                .antMatchers("/api/getCategory")
                .antMatchers("/api/shop/getProductsPageCondition")
                .antMatchers("/api/shop/getProductById")
                .antMatchers("/api/shop/getListImage")
                .antMatchers("/api/notice/getNotices")
                .antMatchers("/api/notice/getNotice")
                .antMatchers("/api/community/getCommunities")
                .antMatchers("/api/community/getCommunity")
                .antMatchers("/api/productComment/getProductComments")
                .antMatchers("/api/bankAccount/getBankAccount")
                .antMatchers("/api/bankAccount/getBankAccounts")
                .antMatchers("/api/paymentMethod/getPaymentMethodTypes")
                .antMatchers("/api/paymentMethod/getPaymentMethodTypesWithValues")
                .antMatchers("/api/user/getUsername")
                .antMatchers("/api/user/createTempPassword")
                .antMatchers("/api/faq/getCategories")
                .antMatchers("/api/faq/getFaqs")
                .antMatchers("/api/faq/getFaq")

        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()

                .antMatchers("/api/getProductsPageCondition").permitAll()
                .antMatchers("/api/getProductById").permitAll()
                .antMatchers("/api/productQnA/getProductQnA").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}

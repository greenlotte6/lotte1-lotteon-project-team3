package kr.co.lotteOn.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 로그인 설정
        http.formLogin(login -> login
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .failureUrl("/member/login?code=100")
                .usernameParameter("id")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler)
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/member/logout")
                .invalidateHttpSession(true)
                //.logoutSuccessUrl("/member/login")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .invalidateHttpSession(true)
        );

        // 인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/","/member/login","/member/logout","/admin/admin").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/shop/list").permitAll()  // <- 상점 등록 처리하는 POST
                .requestMatchers("/admin/shop/list").permitAll() //상점 등록
                .requestMatchers("/Community/write**").authenticated()
                .requestMatchers("/Community/modify**").authenticated()
                .requestMatchers("/product/cart", "/product/addToCart").authenticated()
                .anyRequest().permitAll()
        );

        // 예외 처리 설정
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthenticationEntryPoint)
        );

        // CSRF 비활성화
        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}

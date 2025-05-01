package kr.co.lotteOn.security;

import jakarta.servlet.http.HttpSession;
import kr.co.lotteOn.oauth2.OAuth2UserService;
import kr.co.lotteOn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
    private final OAuth2UserService oAuth2UserService;
    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

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

        /*
        // OAuth2 설정
        // 주입받은 customOAuth2UserService를 바로 사용
        http.oauth2Login(oauth -> oauth
                .loginPage("/member/login") // 로그인 페이지 경로 설정
                .userInfoEndpoint(info -> info
                        .userService(customOAuth2UserService)
                )
                .failureHandler((request, response, exception) -> {
                    if (exception.getMessage().contains("추가 정보 필요")) {
                        response.sendRedirect("/member/signup-extra");
                    } else {
                        response.sendRedirect("/member/login?code=100");
                    }
                })
        );

         */

        http
                .oauth2Login(config -> config
                .loginPage("/member/login")
                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
        );

        // 인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/","/member/login","/member/logout","/admin/admin").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/shop/list").permitAll()  // <- 상점 등록 처리하는 POST
                .requestMatchers("/admin/shop/list").permitAll() //상점 등록
                .requestMatchers("/Community/write**").authenticated()
                .requestMatchers("/Community/modify**").authenticated()
                .requestMatchers("/cust/cust_question_write").authenticated()
                .requestMatchers("/product/cart", "/product/addToCart").authenticated()
                .requestMatchers("/myPage/**").authenticated()
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

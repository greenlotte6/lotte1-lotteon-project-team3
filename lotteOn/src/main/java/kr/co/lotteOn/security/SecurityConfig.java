package kr.co.lotteOn.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        //로그인 설정
        http.formLogin(login -> login
                .loginPage("/user/login")
                .defaultSuccessUrl("/")
                .failureUrl("/user/login?code=100")
                .usernameParameter("id")
                .passwordParameter("password"));

        //로그아웃 설정
        http.logout(logout -> logout.logoutUrl("/user/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/user/login"));

      /*
            인가 설정
             - MyUserDetails 권한 목록 생성하는 메서드 (getAuthorities)
             에서 접두어로 ROLE_ 입력해야 haRole, hasAnyRole 권한 처리됨
             - Spring Security는 기본적으로 인가 페이지에 대해
                                login 페이지로 redirect 수행


       */

        //인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/Management/**").hasRole("ADMIN")
                .requestMatchers("/support/**").hasAnyRole("ADMIN","STUDENT")
                .requestMatchers("/staff/**").hasAnyRole("ADMIN","MANAGER","STAFF")
                .requestMatchers("/Community/write**").authenticated()
                .requestMatchers("/Community/modify**").authenticated()
                .anyRequest().permitAll()
        );

        // 예외 처리 설정 - 인증 안 된 사용자 접근 시 JS alert로 리다이렉트
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint(customAuthenticationEntryPoint)
        );

        //기타 보안 설정
        http.csrf(AbstractHttpConfigurer::disable);



        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //Security 암호화 인코더 설정
        return new BCryptPasswordEncoder();
    }
}

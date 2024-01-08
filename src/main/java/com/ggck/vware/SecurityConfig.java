package com.ggck.vware;

import com.ggck.vware.user.CustomSiteUserDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
            .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
        .formLogin((formLogin) -> formLogin.loginPage("/user/login").defaultSuccessUrl("/")
            .successHandler(loginSuccessHandler()))
        .logout((logout) -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true))
    ;
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager(); // AuthenticationManager는 스프링 시큐리티의 인증을 담당한다. AuthenticationManager는 사용자 인증시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 사용한다.
  }


  private AuthenticationSuccessHandler loginSuccessHandler() {
    return (request, response, authentication) -> {
      CustomSiteUserDetails userDetails = (CustomSiteUserDetails) authentication.getPrincipal();
      String userNickName = userDetails.getUserNickName();
      int point = userDetails.getPoint();

      // 로그인 성공 시 JavaScript 코드를 실행하여 alert 창을 띄움
      String script =
          "<script>alert('환영합니다. " + userNickName + "님! 현재 포인트는 " + point
              + "pt입니다." + "'); window.location='/';</script>";
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(script);
    };
  }
}

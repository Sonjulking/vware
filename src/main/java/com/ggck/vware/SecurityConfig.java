package com.ggck.vware;

import com.ggck.vware.user.CustomSiteUserDetails;
import com.ggck.vware.user.SiteUserRole;
import com.ggck.vware.user.entity.SiteUserEntity;
import com.ggck.vware.user.repository.SiteUserRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
// securedEnabled => Secured 애노테이션 사용 여부, prePostEnabled => PreAuthorize 어노테이션 사용 여부.
public class SecurityConfig {

  @Autowired
  private SiteUserRepository siteUserRepository;
  @Autowired
  private CustomAccessDeniedHandler accessDeniedHandler;

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable() // 이거 안하면 시큐리티에서 post 막음 -> TODO : 보안 취약해질 수 있으니 다른 방법 구글링 해서 해결할 것 : 고강찬 담당
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .and()
        .authorizeHttpRequests((authorizeHttpRequests) ->
            authorizeHttpRequests
                .requestMatchers("/EggSetAdmin/**")
                .hasRole(
                    "ADMIN") //어드민 권한일때만 접속가능 부여 SiteUserRole.ADMIN.getValue() //기본적으로 ROLE_ ~~라고 이름이 붙어있는 상태래요.
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
        .formLogin((formLogin) -> formLogin.loginPage("/user/login").defaultSuccessUrl("/")
            .successHandler(loginSuccessHandler()))
        .logout((logout) -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            /*  .logoutRequestMatcher(new AntPathRequestMatcher("/user/MyPage/withdrawal"))*/
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
      //int point = userDetails.getPoint();
      int point = 0;
      Optional<SiteUserEntity> userEntityOptional = siteUserRepository.findByUserId(
          userDetails.getUsername()); //아이디를 매개변수로, 유저정보를 담아줌.
      if (userEntityOptional.isPresent()) { //유저정보가 존재한다면.
        SiteUserEntity userEntity = userEntityOptional.get(); //userEntity에 담아준다
        userEntity.setLastAccessTime(LocalDateTime.now()); //접속시간 등록

        if (!Objects.equals(userEntity.getPaymentStatus(), "1")) {
          userEntity.setPaymentStatus("1");
          userEntity.setPoint(userEntity.getPoint() + 5);
        }

        siteUserRepository.save(userEntity);
        point = userEntity.getPoint();
      }

      // 로그인 성공 시 JavaScript 코드를 실행하여 alert 창을 띄움
      String script =
          "<script>alert('환영합니다. " + userNickName + "님! 현재 포인트는 " + point
              + "pt입니다." + "'); window.location='/';</script>";
      response.setContentType("text/html;charset=UTF-8");
      response.getWriter().write(script);
    };
  }
}
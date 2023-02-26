package farm.board.config.security;

import farm.board.config.token.TokenHelper;
import farm.board.service.sign.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //Spring Security 를 사용하는 웹 어플리케이션을 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenHelper accessTokenHelper;
    private final CustomUserDetailsService userDetailsService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/exception/**","/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs/**"); // /exception/** 패턴의 요청은 보안 검사를 거치지 않는다
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .httpBasic().disable() // HTTP Basic 인증을 사용하지
                .formLogin().disable() // Form 로그인을 사용하지 않음
                .csrf().disable() // CSRF 공격으로부터 보호하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않음
                    .and()
                    .authorizeRequests() // 요청에 대한 인가를 구성
                    .antMatchers(HttpMethod.POST, "/api/sign-in", "/api/sign-up", "/api/refresh-token").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                    .antMatchers(HttpMethod.DELETE,"/api/members/{id}/**").access("@memberGuard.check(#id)")
                    .antMatchers(HttpMethod.POST, "/api/categories/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/categories/**").hasRole("ADMIN")
                    .anyRequest().hasAnyRole("ADMIN")
                    .and()
                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) // 엑세스 거부 예외
                .and()
                    .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                    .addFilterBefore( // JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 앞에 등록
                            new JwtAuthenticationFilter(accessTokenHelper, userDetailsService) //JWT 토큰 기반 인증
                            , UsernamePasswordAuthenticationFilter.class
                    );
    }

    @Bean
    public PasswordEncoder passwordEncoder()    {
        return new BCryptPasswordEncoder();
    }
}
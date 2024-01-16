package com.example.testsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //config 등록
@EnableWebSecurity
public class SecurityConfig {

    //암호화 메서드 구현
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    //계층 권한 등록 메서드
    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        //ADMIN이 USER보다 권한이 높다
        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //authorizeHttpRequests : 특정 경로에 요청 거부,허용을 정할 수 있음, 람다식으로 작성

        authorizeHttpRequests(http);


        // .formLogin의 loginPage에서 URL을 지정하면 /admin 등으로 접속하여 permit이 막힐경우 자동적으로 /login으로 리다이렉션 해준다.
        // loginProcessingUrl에서 /loginProc에서 로그인 프로세싱을 자동적으로 진행하도록 해준다.
//        setFormLogin(http);

        //별다른 코드를 넣지 않으면 디폴트로 csrf설정이 enable된다.
//        http
//                .csrf((auth) -> auth.disable());

        setHttpBasic(http);

        //다중 로그인 통제 설정
        setOnlyOneLoginSession(http);

        //세선 고정 공격 보호
        protectSessionAttack(http);

        //
        return http.build();
    }

    private static void protectSessionAttack(HttpSecurity http) throws Exception {
        http
                .sessionManagement((auth)-> auth
                        .sessionFixation().changeSessionId());
    }

    private static void setOnlyOneLoginSession(HttpSecurity http) throws Exception {
        http
                .sessionManagement((auth)-> auth
                        .maximumSessions(1) //maximumSession : 하나의 아이디에 대한 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true)); //로그인 개수를 초과하였을 경우 처리 방법 (true : 초과시 새로운 로그인 차단)
    }

    private static void setHttpBasic(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults());
    }

    private static void setFormLogin(HttpSecurity http) throws Exception {
        http
                .formLogin((auth)-> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc").permitAll()
                );
    }

    private static void authorizeHttpRequests(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login","/join","/").permitAll()
                        .requestMatchers("/welcome").hasAnyRole("USER")
                        .requestMatchers("/admin").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                );
    }
}

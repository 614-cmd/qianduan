package com.jeeplus.config;

import com.jeeplus.security.exception.JwtAccessDeniedHandler;
import com.jeeplus.security.exception.JwtAuthenticationEntryPoint;
import com.jeeplus.security.jwt.JWTFilter;
import com.jeeplus.security.jwt.TokenProvider;
import com.jeeplus.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Order(0)
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启方法级安全验证
public class WebSecurityConfig  {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationConfiguration authConfiguration;


    /**
     * 核心配置
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        http.csrf(csrf -> csrf.disable())

                .sessionManagement ( httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy ( SessionCreationPolicy.STATELESS ) )
                // 处理请求
                .authorizeHttpRequests(authorize -> {
                    // 放开哪些接口
                    authorize
                            .requestMatchers (
//                                    AntPathRequestMatcher.antMatcher ("/service/*"),
                                    AntPathRequestMatcher.antMatcher ("/401"),
                                    AntPathRequestMatcher.antMatcher ("/404"),
                                    AntPathRequestMatcher.antMatcher ("/app/rest/**"),
                                    AntPathRequestMatcher.antMatcher ("/v1/**"),
                                    AntPathRequestMatcher.antMatcher ( "/v2/**"),
                                    AntPathRequestMatcher.antMatcher ( "/v3/**"),
                                    AntPathRequestMatcher.antMatcher ( "/weboffice/**"),
                                    AntPathRequestMatcher.antMatcher ("/ReportServer/**"),
                                    AntPathRequestMatcher.antMatcher ( "/sys/login"),
                                    AntPathRequestMatcher.antMatcher ( "/app/sys/login"),
                                    AntPathRequestMatcher.antMatcher ( "/sys/sysConfig/getConfig"),
                                    AntPathRequestMatcher.antMatcher ( "/captcha"),
                                    AntPathRequestMatcher.antMatcher ( "/sys/captcha"),
                                    AntPathRequestMatcher.antMatcher ( "/sys/login/captcha"),
                                    AntPathRequestMatcher.antMatcher ("/getAppFlowChart"),
                                    AntPathRequestMatcher.antMatcher ( "/sys/getCode"),
                                    AntPathRequestMatcher.antMatcher ( "/app/sys/getCode"),
                                    AntPathRequestMatcher.antMatcher ( "/app/file/download"),
                                    AntPathRequestMatcher.antMatcher ("/file/download"),
                                    AntPathRequestMatcher.antMatcher ("/sys/language/getLanguageMap"),
                                    AntPathRequestMatcher.antMatcher (  "/sys/casLogin"),
                                    AntPathRequestMatcher.antMatcher ( "/doc.html"),
                                    AntPathRequestMatcher.antMatcher ( "/swagger-ui.html"),
                                    AntPathRequestMatcher.antMatcher ( "/userfiles/**"),
                                    AntPathRequestMatcher.antMatcher ( "/static/**"),
                                    AntPathRequestMatcher.antMatcher ( "/swagger**/**"),
                                    AntPathRequestMatcher.antMatcher ( "/webjars/**")
                                    )
                            .permitAll();
                    // 其他的都需要认证
                    authorize.anyRequest().authenticated();
                })
                // 错误处理
                .exceptionHandling(m -> {
                    m.authenticationEntryPoint(unauthorizedHandler);
                    m.accessDeniedHandler(accessDeniedHandler);
                })

                // 这个不配置也会生效
                .authenticationProvider(authenticationProvider())
                .headers(headers->headers
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.disable ()
                        )
                );
                // 下面这个两个不待测效果了
//                .httpBasic( Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults());

        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtFilter (), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 配置全局的某些通用事物，例如静态资源等
     * @return
     */
    @Bean
    public WebSecurityCustomizer securityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/doc.html",
                "/swagger-ui.html",
                "/userfiles/**",
                "/static/**",
                "/swagger**/**",
                "/webjars/**",
                "/captcha",
                "/sys/captcha",
                "/sys/login/captcha"
        );
    }

    /**
     * 描述: 密码加密算法 BCrypt 推荐使用
     **/
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ( );
    }

    /**
     * 描述: 注入token
     **/
    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider ( );
    }

    private JWTFilter jwtFilter() throws Exception {
        return new  JWTFilter (  tokenProvider ( ), authConfiguration.getAuthenticationManager() );
    }

    // 身份认证提供程序（加密方式，用户查询）
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 这个主要是为了其他地方可以使用认证管理器
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
        return authConfiguration.getAuthenticationManager();
    }

}

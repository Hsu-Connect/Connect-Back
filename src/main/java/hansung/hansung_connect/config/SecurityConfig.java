package hansung.hansung_connect.config;

import hansung.hansung_connect.auth.token.JwtAuthFilter;
import hansung.hansung_connect.auth.token.TokenProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // REST API 기본 세팅
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        // 프리플라이트 전부 허용 (Swagger/브라우저용)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 문서/로그인/디버그 허용
                        .requestMatchers(
                                "/auth/login",
                                "/auth/debug/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 개발 중 테스트할 API 임시 허용
                        .requestMatchers("/users/me/careers/**").permitAll()
                        .requestMatchers("/links/**").permitAll()
                        .requestMatchers("/users/**").permitAll()
                        // 그 외는 인증 필요
                        .anyRequest().authenticated()
                )

                // JWT 필터는 UsernamePasswordAuthenticationFilter 앞에
                .addFilterBefore(new JwtAuthFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 전역 CORS 설정: 개발 중에는 * 로 두고, 실제 배포에서는 프론트 도메인으로 제한.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // 개발 중: 전체 허용 (배포 시 특정 Origin으로 교체)
        cfg.setAllowedOriginPatterns(List.of("*"));
        cfg.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        cfg.setExposedHeaders(List.of("Location"));
        cfg.setAllowCredentials(true); // 쿠키/인증 필요 시

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}



package ru.shuman.Project_Aibolit_Server.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shuman.Project_Aibolit_Server.security.JWTUtil;
import ru.shuman.Project_Aibolit_Server.services.ProfileDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
Фильтр по работе с jwt токеном
 */
@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final ProfileDetailsService profileDetailsService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public JWTFilter(JWTUtil jwtUtil, ProfileDetailsService profileDetailsService) {
        this.jwtUtil = jwtUtil;
        this.profileDetailsService = profileDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, AuthenticationException {

        //получаем хедер Authorization из запроса
        String authHeader = request.getHeader("Authorization");

        //если хедер не null и не пустой и начинается с "Bearer ", то сохраняем токен в переменную
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            //если токен пустой возвращаем ошибку
            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Пустой JWT токен!");

            } else {
                try {
                    //валидируем токен, в случае успешной валидации получаем имя пользователя из токена, далее получаем
                    // по данному имени пользователя в обертке UserDetails
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = profileDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    //если в контексте getAuthentication null кладем в контекст
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                    //обрабатываем ошибку верификации токена
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный или устаревший JWT токен!");
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;

// этот класс полностью идентичен конфигурационному файлу applicationContextMVC.xml
// для того, чтобы подключить шаблолнизатор Thymeleaf вместо стандартного, мы имплеминтируем интерфейс WebMvcConfigurer
// с реализацией метода configureViewResolvers, в котором задаем шаблонизатор Thymeleaf
@Configuration
@ComponentScan("org.example")
@EnableWebMvc   //аналогично <mvc:annotation-driven/>, которая включает необх аннотации для spring mvc
@PropertySource("classpath:database.properties")
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final Environment environment;      //с англ "окружение", через него получаем доступ к свойствам, которые подгрузили из файла database.properties

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    // используем ApplicationContext для настройки Thymeleaf, указываем где лежат шаблоны и в каком формате
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

    // создаем бин источника данных, указываем драйвер для БД, URL, username и пароль
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("psql_driver")));
        dataSource.setUrl(environment.getProperty("psql_url"));
        dataSource.setUsername(environment.getProperty("psql_username"));
        dataSource.setPassword(environment.getProperty("psql_password"));
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/project_1_library?autoReconnect=true&useSSL=false");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("qwerty");
        return dataSource;
    }

    // создаем бин JdbcTemplate, который будет возвращать экземпляр JdbcTemplate с источником данных dataSource
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}

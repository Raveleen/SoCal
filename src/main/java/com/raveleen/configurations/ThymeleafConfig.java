package com.raveleen.configurations;

import com.raveleen.services.ImageService;
import com.raveleen.services.UtilsService;
import com.raveleen.services.ProfileImageService;
import com.raveleen.services.UserService;
import com.raveleen.services.PostService;
import com.raveleen.services.CommentService;
import com.raveleen.services.DialogService;
import com.raveleen.services.MessageService;
import com.raveleen.services.impl.ImageServiceImpl;
import com.raveleen.services.impl.ProfileImageServiceImpl;
import com.raveleen.services.impl.UserServiceImpl;
import com.raveleen.services.impl.PostServiceImpl;
import com.raveleen.services.impl.CommentServiceImpl;
import com.raveleen.services.impl.DialogServiceImpl;
import com.raveleen.services.impl.MessageServiceImpl;
import com.raveleen.services.impl.UserDetailsServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Created by Святослав on 05.01.2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.raveleen")
public class ThymeleafConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    @Bean
    public ImageService imageService() {
        return new ImageServiceImpl();
    }

    @Bean
    public UtilsService utilsService() {
        return new UtilsService();
    }

    @Bean
    public ProfileImageService profileImageService() {
        return new ProfileImageServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PostService postService() {
        return new PostServiceImpl();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceImpl();
    }

    @Bean
    public DialogService dialogService() {
        return new DialogServiceImpl();
    }

    @Bean
    public MessageService messageService() {
        return new MessageServiceImpl();
    }

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ThymeleafConfig() {
        super();
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(true);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        engine.setEnableSpringELCompiler(true);
        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        return resolver;
    }

    @Bean
    public CommonsMultipartResolver setMultipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(16777215);
        return resolver;
    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/resources/", "classpath:/public/"};

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
        //super.addResourceHandlers(registry);
        //registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}

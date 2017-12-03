package pr.study.springboot.configure.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import pr.study.springboot.aop.web.interceptor.Interceptor1;
import pr.study.springboot.aop.web.interceptor.Interceptor2;

@Configuration
public class SpringMvcConfigure extends WebMvcConfigurerAdapter {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        // viewResolver.setViewClass(JstlView.class); // 这个属性通常并不需要手动配置，高版本的Spring会自动检测
        return viewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor1()).addPathPatterns("/**");
        registry.addInterceptor(new Interceptor2()).addPathPatterns("/users").addPathPatterns("/users/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler指的是访问路径，addResourceLocations指的是文件放置的目录  
        registry.addResourceHandler("/**").addResourceLocations("classpath:/res/");
    }
}

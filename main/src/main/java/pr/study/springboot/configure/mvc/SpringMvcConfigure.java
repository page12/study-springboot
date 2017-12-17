package pr.study.springboot.configure.mvc;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

import pr.study.springboot.configure.mvc.converter.JavaSerializationConverter;

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

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new Interceptor1()).addPathPatterns("/**");
//        registry.addInterceptor(new Interceptor2()).addPathPatterns("/users").addPathPatterns("/users/**");
//        super.addInterceptors(registry);
//    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler指的是访问路径，addResourceLocations指的是文件放置的目录  
        registry.addResourceHandler("/**").addResourceLocations("classpath:/res/");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 仅仅添加一种新的converter，不删除默认添加的
        // 如果要删除可以使用 converters.clear()
        // 仅仅只有一种converter时，代表请求和响应默认都是这个converter代表的mediatype
        // 推荐使用这个方法添加converter
        converters.add(new JavaSerializationConverter());

        // 使用fastJson代替jackson
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue); // 序列化null属性
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss"); // 默认的时间序列化格式
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(fastJsonConverter);
        System.err.println(converters);
    }

//    // 添加converter的第二种方式，会删除原来的converter
//    @Bean
//    public HttpMessageConverter<Object> javaSerializationConverter() {
//        return new JavaSerializationConverter();
//    }

//    // 添加converter的第三种方式，会删除原来的converter
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new JavaSerializationConverter());
//    }

}

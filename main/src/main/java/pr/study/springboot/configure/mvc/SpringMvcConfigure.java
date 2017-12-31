package pr.study.springboot.configure.mvc;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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

//    // 添加converter的第一种方式，代码很简单，也是推荐的方式
//    // 这样做springboot会把我们自定义的converter放在顺序上的最高优先级（List的头部）
//    // 即有多个converter都满足Accpet/ContentType/MediaType的规则时，优先使用我们这个
//    @Bean
//    public JavaSerializationConverter javaSerializationConverter() {
//        JavaSerializationConverter c = new JavaSerializationConverter();
//        System.err.println("javaSerializationConverter: " + c);
//        return c;
//    }
//
//    // 添加converter的第二种方式
//    // 通常在只有一个自定义WebMvcConfigurerAdapter时，会把这个方法里面添加的converter依次放在最高优先级（List的头部）
//    // 虽然第一种方式的代码先执行，但是bean的添加比这种方式晚，所以方式二的优先级 大于 方式一
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        JavaSerializationConverter c = new JavaSerializationConverter();
//        System.err.println("configureMessageConverters: " + c);
//        // add方法可以指定顺序，有多个自定义的WebMvcConfigurerAdapter时，可以改变相互之间的顺序
//        // 但是都在springmvc内置的converter前面
//        converters.add(c);
//    }

    // 添加converter的第三种方式
    // 同一个WebMvcConfigurerAdapter中的configureMessageConverters方法先于extendMessageConverters方法执行
    // 可以理解为是三种方式中最后执行的一种，可以通过add指定顺序来调整优先级，也可以使用remove/clear来删除converter，功能强大
    // 使用converters.add(xxx)会放在最低优先级（List的尾部）
    // 使用converters.add(0,xxx)会放在最高优先级（List的头部）
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        JavaSerializationConverter c = new JavaSerializationConverter();
        System.err.println("extendMessageConverters: " + c);
        converters.add(c);

        // 添加FastJsonHttpMessageConverter
        FastJsonHttpMessageConverter fastJsonConverter = new FastJsonHttpMessageConverter();

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue); // 序列化null属性
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss"); // 默认的时间序列化格式
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
//      converters.add(fastJsonConverter); // 最低优先级
        converters.add(0, fastJsonConverter); // 最高优先级

        System.err.println(converters);
        for (HttpMessageConverter<?> converter : converters) {
            System.err.println(converter.getClass().getSimpleName() + ":" + converter.getSupportedMediaTypes());
            for (MediaType mediaType : converter.getSupportedMediaTypes()) {
                System.err.println(mediaType + ": " + mediaType.getQualityValue());
            }
        }
    }
}

package pr.study.springboot.configure.mvc.json;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * jackson的核心是ObjectMapper，在这里配置ObjectMapper来控制springboot使用的jackson的某些功能
 */
@Configuration
public class MyObjectMpper {

    @Bean
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL); // 不序列化null的属性
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")); // 默认的时间序列化格式
        return mapper;
    }
}

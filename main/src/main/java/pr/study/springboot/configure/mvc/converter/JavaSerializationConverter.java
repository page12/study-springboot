package pr.study.springboot.configure.mvc.converter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import pr.study.springboot.bean.BaseBean;

public class JavaSerializationConverter extends AbstractHttpMessageConverter<Object> {
    private Logger LOGGER = LoggerFactory.getLogger(JavaSerializationConverter.class);

    public JavaSerializationConverter() {
        // 构造方法中指明consumes（req）和produces（resp）的类型，指明这个类型才会使用这个converter
        super(new MediaType("application", "x-java-serialization", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return BaseBean.class.isAssignableFrom(clazz);
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        byte[] bytes = StreamUtils.copyToByteArray(inputMessage.getBody());
        // base64使得二进制数据可视化，便于测试
        ByteArrayInputStream bytesInput = new ByteArrayInputStream(Base64.getDecoder().decode(bytes));
        ObjectInputStream objectInput = new ObjectInputStream(bytesInput);
        try {
            return objectInput.readObject();
        } catch (ClassNotFoundException e) {
            LOGGER.error("exception when java deserialize, the input is:{}", new String(bytes, "UTF-8"), e);
            return null;
        }
    }

    @Override
    protected void writeInternal(Object t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
        ObjectOutputStream objectOutput = new ObjectOutputStream(bytesOutput);
        objectOutput.writeObject(t);
        // base64使得二进制数据可视化，便于测试
        outputMessage.getBody().write(Base64.getEncoder().encode(bytesOutput.toByteArray()));
    }

}

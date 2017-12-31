package boot.test.mediatype;

import org.junit.Test;
import org.springframework.http.MediaType;

public class MediaTypeTest {

    @Test
    public void testMediaTypeCompare() {
        int result = MediaType.SPECIFICITY_COMPARATOR.compare(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM);
        System.err.println("result: " + result);
        result = MediaType.QUALITY_VALUE_COMPARATOR.compare(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM);
        System.err.println("result: " + result);
    }
}

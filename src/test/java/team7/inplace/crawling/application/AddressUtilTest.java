package team7.inplace.crawling.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressUtilTest {

    @DisplayName("주소 추출 테스트 1")
    @Test
    void extractAddressFromJsonNodeTest1() {
        // given
        final String description = """
                [참조은 생고기]
                대구 북구 복현로 78 (복현2동 266-9)
                """;
        final String address = "대구 북구 복현로 78";

        // when
        String result = AddressUtil.extractAddressFromString(description);

        // then
        assertEquals(address, result);
    }

}
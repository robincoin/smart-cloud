package org.smartframework.cloud.mask.test.unit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.mask.util.JacksonMaskUtil;

class JacksonMaskUtilTest {

    @Test
    void testMask() {
        Assertions.assertThat(JacksonMaskUtil.mask(null)).isNull();

        LoginBO loginBO = new LoginBO("zhangsan", "123456");
        Assertions.assertThat(JacksonMaskUtil.mask(loginBO)).isEqualTo(loginBO.toString());
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    static class LoginBO {
        private String username;
        private String password;
    }

}
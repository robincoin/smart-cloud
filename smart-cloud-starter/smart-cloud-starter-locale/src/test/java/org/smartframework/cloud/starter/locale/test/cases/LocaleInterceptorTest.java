package org.smartframework.cloud.starter.locale.test.cases;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.smartframework.cloud.starter.locale.test.prepare.Application;
import org.smartframework.cloud.starter.locale.test.prepare.controller.OrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
class LocaleInterceptorTest {

    @Autowired
    private OrderController orderController;

    @Test
    void testSubmit() {
        Response<String> response = orderController.submit();
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getHead()).isNotNull();
        Assertions.assertThat(response.getHead().getCode()).isEqualTo(CommonReturnCodes.SUCCESS.getCode());
        Assertions.assertThat(StringUtils.containsAny(response.getHead().getMessage(), "Success", "成功")).isTrue();
    }

}
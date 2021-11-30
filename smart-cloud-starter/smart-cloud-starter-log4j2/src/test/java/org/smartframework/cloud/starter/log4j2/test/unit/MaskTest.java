package org.smartframework.cloud.starter.log4j2.test.unit;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.mask.MaskLog;
import org.smartframework.cloud.mask.MaskRule;
import org.smartframework.cloud.mask.util.MaskUtil;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
class MaskTest {

    @Test
    void testMask() throws IOException, InterruptedException {
        User user = new User();
        user.setId(9L);
        user.setName("zhangsan");
        user.setMobile("13112345678");
        log.info("user={}", user);

        // 判断日志中是否脱敏
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        String appenderName = "console";
        ConsoleAppender appender = ctx.getConfiguration().getAppender(appenderName);
        ByteBuffer byteBuffer = appender.getManager().getByteBuffer().asReadOnlyBuffer();
        String logContent = StandardCharsets.UTF_8.decode(byteBuffer).toString();
        byteBuffer.flip();
        String maskName = MaskUtil.mask(user.getName(), MaskRule.NAME);
        String maskMobile = MaskUtil.mask(user.getMobile(), MaskRule.MOBILE);
        Assertions.assertThat(logContent).contains(maskName);
        Assertions.assertThat(logContent).contains(maskMobile);
    }

    @Getter
    @Setter
    @ToString
    static class User implements Serializable {
        private Long id;
        @MaskLog(MaskRule.NAME)
        private String name;
        @MaskLog(MaskRule.MOBILE)
        private String mobile;
    }

}
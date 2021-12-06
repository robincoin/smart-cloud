/*
 * Copyright © 2019 collin (1634753825@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.smartframework.cloud.starter.rpc.test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.starter.core.constants.PackageConfig;
import org.smartframework.cloud.starter.rpc.feign.annotation.SmartFeignClient;
import org.smartframework.cloud.starter.rpc.feign.condition.SmartFeignClientCondition;
import org.smartframework.cloud.starter.rpc.test.SuiteTest;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

class SmartFeignClientConditionUnitTest {

    @Test
    void test() {
        PackageConfig.setBasePackages(new String[]{SuiteTest.class.getPackage().getName()});
        SmartFeignClientCondition smartFeignClientCondition = new SmartFeignClientCondition();

        AnnotationMetadata annotationMetadata1 = AnnotationMetadata.introspect(TestFeign1.class);
        boolean result1 = smartFeignClientCondition.matches(null, annotationMetadata1);
        Assertions.assertThat(result1).isTrue();


        AnnotationMetadata annotationMetadata2 = AnnotationMetadata.introspect(TestFeign2.class);
        boolean result2 = smartFeignClientCondition.matches(null, annotationMetadata2);
        Assertions.assertThat(result2).isFalse();
    }

    @SmartFeignClient(url = "test")
    public interface TestFeign1 {
        @GetMapping
        String get();
    }

    @SmartFeignClient(url = "test")
    public interface TestFeign2 {
        @PostMapping
        String get();
    }

    @RestController
    public class TestFeignImpl implements TestFeign2 {

        @Override
        public String get() {
            return null;
        }
    }

}
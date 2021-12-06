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
package org.smartframework.cloud.utility.test.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.smartframework.cloud.utility.NonceUtil;

class NonceUtilUnitTest {

    @Test
    void test() {
        String id1 = NonceUtil.getInstance().nextId();
        Assertions.assertThat(id1).isNotBlank();

        String id2 = NonceUtil.getInstance().nextId();
        Assertions.assertThat(id2).isNotBlank();

        Assertions.assertThat(id1).isNotEqualTo(id2);
    }

}
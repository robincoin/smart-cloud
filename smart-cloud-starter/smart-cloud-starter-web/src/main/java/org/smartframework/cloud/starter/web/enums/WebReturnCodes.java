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
package org.smartframework.cloud.starter.web.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;

/**
 * starter-web模块返回码
 *
 * @author collin
 * @date 2021-10-31
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum WebReturnCodes implements IBaseReturnCodes {

    /**
     * 待校验参数object不能为null
     */
    VALIDATE_IN_PARAMS_NULL("2001");

    /**
     * 状态码
     */
    private String code;

}
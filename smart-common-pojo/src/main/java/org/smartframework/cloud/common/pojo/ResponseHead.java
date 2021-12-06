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
package org.smartframework.cloud.common.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.smartframework.cloud.common.pojo.enums.IBaseReturnCodes;
import uk.co.jemos.podam.common.PodamStringValue;

/**
 * 响应头部
 *
 * @author liyulin
 * @date 2020-05-07
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ResponseHead extends Base {

    private static final long serialVersionUID = 1L;

    /**
     * 全局唯一交易流水号
     */
    private String nonce;

    /**
     * 响应状态码
     */
    @PodamStringValue(strValue = "200")
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 响应时间戳
     */
    private long timestamp;

    public ResponseHead(IBaseReturnCodes returnCodes) {
        setReturnCode(returnCodes);
    }

    public ResponseHead(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setReturnCode(IBaseReturnCodes returnCodes) {
        if (returnCodes == null) {
            return;
        }

        this.code = returnCodes.getCode();
    }

}
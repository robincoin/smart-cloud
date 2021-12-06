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
package org.smartframework.cloud.starter.web.aspect.interceptor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.ResponseHead;
import org.smartframework.cloud.common.web.pojo.LogAspectDO;
import org.smartframework.cloud.common.web.util.WebServletUtil;
import org.smartframework.cloud.mask.util.LogUtil;
import org.smartframework.cloud.starter.configure.constants.OrderConstant;
import org.smartframework.cloud.starter.configure.properties.LogProperties;
import org.smartframework.cloud.starter.web.exception.ExceptionHandlerContext;
import org.springframework.core.Ordered;
import org.springframework.core.io.InputStreamSource;
import org.springframework.validation.DataBinder;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 接口日志切面
 *
 * @author liyulin
 * @date 2019-04-08
 */
@Slf4j
@AllArgsConstructor
public class ServletApiLogInterceptor implements MethodInterceptor, Ordered {

    private LogProperties logProperties;

    @Override
    public int getOrder() {
        return OrderConstant.API_LOG;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (RequestContextHolder.getRequestAttributes() == null) {
            return invocation.proceed();
        }

        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
            result = invocation.proceed();
            long cost = System.currentTimeMillis() - startTime;
            if (cost >= logProperties.getSlowApiMinCost()) {
                log.warn(LogUtil.truncate("api.slow=>{}", buildLogAspectDO(invocation.getArguments(), result, cost)));
            } else if (log.isInfoEnabled()) {
                log.info(LogUtil.truncate("api.info=>{}", buildLogAspectDO(invocation.getArguments(), result, cost)));
            }
            return result;
        } catch (Exception e) {
            long cost = System.currentTimeMillis() - startTime;
            log.error(LogUtil.truncate("api.error=>{}", buildLogAspectDO(invocation.getArguments(), result, cost)), e);

            ResponseHead head = ExceptionHandlerContext.transRespHead(e);
            return new Response<>(head);
        }
    }

    private LogAspectDO buildLogAspectDO(Object[] args, Object result, long cost) {
        HttpServletRequest request = WebServletUtil.getHttpServletRequest();
        return LogAspectDO.builder()
                .url(request.getPathInfo())
                .method(request.getMethod())
                .head(getHeaders(request))
                .args(getRequestArgs(args))
                .cost(cost)
                .result(result)
                .build();
    }

    /**
     * 获取http header部分数据
     *
     * @param request
     * @return
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Enumeration<String> enumerations = request.getHeaderNames();
        if (enumerations == null) {
            return null;
        }

        Map<String, String> headers = new HashMap<>(8);
        while (enumerations.hasMoreElements()) {
            String name = enumerations.nextElement();
            headers.put(name, request.getHeader(name));
        }

        return headers;
    }


    /**
     * 获取有效的请求参数（过滤掉不能序列化的）
     *
     * @param args
     * @return
     */
    private static Object getRequestArgs(Object[] args) {
        if (ArrayUtils.isEmpty(args)) {
            return args;
        }

        boolean needFilter = false;
        for (Object arg : args) {
            if (needFilter(arg)) {
                needFilter = true;
                break;
            }
        }

        if (!needFilter) {
            return args.length == 1 ? args[0] : args;
        }

        Object[] tempArgs = Stream.of(args).filter(arg -> !needFilter(arg)).toArray();

        return getValidArgs(tempArgs);
    }

    /**
     * 是否需要过滤
     *
     * @param object
     * @return
     */
    private static boolean needFilter(Object object) {
        return object instanceof ServletRequest
                || object instanceof ServletResponse
                || object instanceof DataBinder
                || object instanceof InputStreamSource;
    }

    /**
     * 获取有效的参数（如果是request对象，则优先从ParameterMap里取）
     *
     * @param args
     * @return
     */
    private static Object getValidArgs(Object[] args) {
        if (ArrayUtils.isEmpty(args)) {
            return args;
        }

        if (args.length == 1 && args[0] instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest) args[0];
            return request.getParameterMap();
        }

        return args;
    }

}
package org.smartframework.cloud.starter.locale.test.prepare.controller;

import org.smartframework.cloud.common.pojo.Response;
import org.smartframework.cloud.common.pojo.ResponseHead;
import org.smartframework.cloud.common.pojo.enums.CommonReturnCodes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("order")
public class OrderController {

    @PostMapping("submit")
    public Response<String> submit() {
        Response response = new Response(new ResponseHead(CommonReturnCodes.SUCCESS));
        response.setBody(UUID.randomUUID().toString());
        return response;
    }

}
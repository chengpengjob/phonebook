package com.cp.controller;

import com.cp.constant.SystemConstant;
import com.cp.entity.R;
import com.cp.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author feipeng
 * @site www.gcp168.cn
 * @create 2019-09-22 12:26
 */

@RestController
@RequestMapping("/")
public class TokenController {

    private final Logger logger = LoggerFactory.getLogger(TokenController.class);

    @GetMapping(value="/refreshToken")
    public R refreshToken(HttpServletRequest request){
        Claims claims = JwtUtils.validateJWT(request.getHeader("token")).getClaims();
        String newToken = JwtUtils.createJWT(claims.getId(),claims.getSubject(),SystemConstant.JWT_TTL);
        R r = new R();
        r.put("token",newToken);
        logger.info("新token:"+newToken);
        return r;
    }
}

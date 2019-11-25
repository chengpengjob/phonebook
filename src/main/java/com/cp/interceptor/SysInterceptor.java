package com.cp.interceptor;

import com.cp.constant.SystemConstant;
import com.cp.entity.CheckResult;
import com.cp.entity.R;
import com.cp.util.JwtUtils;
import com.cp.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author feipeng
 * @site www.gcp168.cn
 * @create 2019-09-21 17:28
 */
public class SysInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(SysInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath=request.getRequestURI();
        logger.info("路径："+contextPath);
        String token=request.getHeader("token");
        if(handler instanceof HandlerMethod){
            if(StringUtil.isEmpty(token)){
                logger.info("签名签证不存在");
                print(response, R.error(SystemConstant.JWT_ERRCODE_NULL,"签名验证不存在"));
                return false;
            }else{
                CheckResult checkResult = JwtUtils.validateJWT(token);
                if(checkResult.isSuccess()){
                    logger.info("签名验证通过");
                    return true;
                }else{
                    switch (checkResult.getErrCode()){
                        case SystemConstant.JWT_ERRCODE_FAIL:
                            logger.info("签名验证不通过");
                            print(response, R.error(SystemConstant.JWT_ERRCODE_FAIL,"签名验证不通过"));
                            break;
                        case SystemConstant.JWT_ERRCODE_EXPIRE:
                            logger.info("签名验证过期");
                            print(response, R.error(SystemConstant.JWT_ERRCODE_EXPIRE,"签名验证过期"));
                            break;
                    }
                    return false;
                }
            }
        }else{
            return true;
        }
    }

    private void print(HttpServletResponse response, Object message){
        try {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setHeader("Cache-Control", "no-cache, must-revalidate");
            PrintWriter writer = response.getWriter();
            writer.write(message.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package com.example.modules.interceptor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.example.modules.jwt.JavaWebToken;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 拦截器
 * @author CaoRui
 * @date 2019年9月9日
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {       //请求进入这个拦截器
        //预检请求直接放行
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
//        String ipAddress =IpUtil.getIpAddr(request);
//        System.out.println(ipAddress);
//        System.out.println(request.getRemoteAddr());
//        System.out.println(request.getRemoteHost());
        //从header中获取token
        String token = request.getHeader("token");
        //String accessToken = request.getHeader("accessToken");
        //如果header中不存在token，则从参数中获取token
        //if(StringUtils.isBlank(token)){
        //   token = request.getParameter("token");
        //}
        JSONObject resultData = new JSONObject();
        if(StringUtils.isNotBlank(token)){
            Map<String, Object> jwtClaims = JavaWebToken.parserJavaWebToken(token);
            if(jwtClaims != null){//表示token合法
                Calendar now = Calendar.getInstance();
                Date nowDate = now.getTime();//当前时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //if(jwtClaims.get("exp") == null || StringUtils.isBlank(jwtClaims.get("exp").toString())) {
                //    throw new RRException("Token不合法");
                //}
                Long longTime = Long.valueOf((long) jwtClaims.get("exp"));
                String stringExp = format.format(longTime);
                Date exp = null;
                try {
                    exp = format.parse(stringExp);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(nowDate.before(exp)) {//比较当前时间和过期时间，过期时间大于当前时间则有效
                    
                    return true;
                }else { //如果已经失效则删除token信息
                    resultData.put("code", -1);
                    resultData.put("msg", "Token已过期");
                }
            }else{  //token不合法或者过期
                resultData.put("code", -1);
                resultData.put("msg", "Token不合法或者过期");
            }
        }else {
            resultData.put("flag", false);
            resultData.put("msg", "Token不合法或者过期");
        }
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        httpResponse.getWriter().write(mapper.writeValueAsString(resultData));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

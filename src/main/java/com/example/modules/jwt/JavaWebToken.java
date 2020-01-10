package com.example.modules.jwt;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT操作类
 * @see <a href="http://blog.csdn.net/csdn_blog_lcl/article/details/73485463">JWT token心得与使用实例</a>
 * @see <a href="https://juejin.im/post/58c29e0b1b69e6006bce02f4">使用JWT和Spring Security保护REST API（JWT部分）</a>
 * @date 2017年11月7日
 */
@Slf4j
public class JavaWebToken {

	/**
	 * 该方法使用HS256算法和Secret:caorui生成signKey
	 * @return signKey
	 */
    private static Key getKeyInstance() {
    	//我们将用我们的ApiKey密钥(bicon)签署我们的JavaWebToken
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("caorui");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        return signingKey;
    }

    /**
     * 使用HS256签名算法和生成的signingKey以及参数生成最终的Token.<hr/>
     * 方法中已经添加签发时间（iat，当前时间）和过期时间（exp，当前时间推后一小时）<br/>
     * 传入参数型如：<br/> 
     * Map<String,Object> m = new HashMap<String,Object>();<br/>
	 * &nbsp&nbsp&nbsp&nbsp m.put("userid", userInfo.getId());<br/>
	 * &nbsp&nbsp&nbsp&nbsp m.put("userName", userInfo.getUserName());<hr/>
     * @param claims 载荷内容，即传入的参数.
     * @return token
     */
    public static String createJavaWebToken(Map<String, Object> claims) {
    	claims.put("iss", "caorui");//签发者
    	claims.put("iat", iat());//签发时间
    	claims.put("exp", exp());//过期时间
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance()).compact();
    }

    /**
     * 解析Token，同时也能验证Token，当验证失败返回null
     * @param jwt JWT Token
     * @return
     */
    public static Map<String, Object> parserJavaWebToken(String jwt) {
        try {
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            log.error("json web token verify failed");
            return null;
        }
    }
    
    /**
	 * 获取当前时间并增加一个小时作为token的过期时间.<br>
	 * * 当设置了时间参数后，其他相关的数值都会重新计算，例如当你把日期设为 11 号后，周几就会作对应变化.<br>
	 * * 获得的月份加 1 才是实际月份.<br>
	 * * 在 Calendar 类中，周日是 1，周一是 2，以此类推.
	 * @return 过期时间
	 */
	public static Date exp() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());//当前时间
		//DAY_OF_MONTH=日期，同DATE；HOUR_OF_DAY=24小时制；DAY_OF_WEEK=周几
		//YEAR=年；MONTH=月；DATE=日；HOUR=时；MINUTE=分；SECOND=秒；
		now.add(Calendar.HOUR,1);//加一小时；
    	Date expDate = now.getTime();//时间
		return expDate;
	}
	/**
	 * 获取当前时间
	 * @return 当前时间
	 */
	public static Date iat() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());//当前时间
    	Date nowDate = now.getTime();//时间
		return nowDate;
	}
}

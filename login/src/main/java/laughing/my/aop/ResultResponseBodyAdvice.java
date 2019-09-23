package laughing.my.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author huang.xiaolong
 * @create 2019-09-19 23:35:37
 * @desc 需要尝试一下  https://www.jianshu.com/p/376c8cc199ca
 * @problem 现在一个问题打印返回数据的时候缺少 request 开始时间（ServerHttpRequest 无法拿到params数据）
 * todo？？？？？？？？？？？？？？？？？
 **/
@ControllerAdvice
@Slf4j
public class ResultResponseBodyAdvice implements ResponseBodyAdvice<Object> {


    @Value("print.response.body:false")
    private boolean printResponseBody;
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse response) {
        // 响应值转JSON串输出到日志系统
        String uri = serverHttpRequest.getURI().toString();
        ServletServerHttpRequest sshr = (ServletServerHttpRequest) serverHttpRequest;
        HttpServletRequest request = sshr.getServletRequest();
        Object a = request.getParameter("userName");
        if (body instanceof RsResult) {

        } else {
            body = new RsResult(ErrorEnum.SUCCESS, body);
        }
//        log.info("{}: {}", request.getURI(), JSON.toJSONString(body, SerializerFeature.UseSingleQuotes));

        return body;
    }
//    private
}

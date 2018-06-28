package laughing.permission.exception;

import laughing.utils.exception.LaughingMappingExceptionResolver;
import laughing.utils.global.ErrorEnum;
import laughing.utils.net.response.bean.RsResult;
import org.apache.shiro.authz.AuthorizationException;

/**
 * @author laughing
 * @create 2018-06-28 19:15:04
 * @desc 系统统一异常处理
 **/
public class SysMappingExceptionResolver extends LaughingMappingExceptionResolver {
    @Override
    public RsResult dowithException(Exception e) {
        if (AuthorizationException.class.isAssignableFrom(e.getClass())) {
            return new RsResult(ErrorEnum.UNAUTH);
        }
        return null;
    }
}

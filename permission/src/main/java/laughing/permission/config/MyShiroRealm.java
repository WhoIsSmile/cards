package laughing.permission.config;

import laughing.permission.entity.UserInfoEntity;
import laughing.permission.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author laughing
 * @create 2018-04-07 15:54:18
 * @desc
 **/
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfoEntity userInfoEntity = (UserInfoEntity)principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //获取功能列表
        List<String> permissionNames = userInfoService.findFuncActionByUserId(userInfoEntity.getUserAccount());
        Set set = new HashSet(Arrays.asList(permissionNames));
        authorizationInfo.setStringPermissions(set);
        return authorizationInfo;
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfoEntity userInfo = userInfoService.findUserInfoByUserAccount(userName);
        if (userInfo == null) {
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getUserPassword(),
                getName()
        );
        return authenticationInfo;
    }
}

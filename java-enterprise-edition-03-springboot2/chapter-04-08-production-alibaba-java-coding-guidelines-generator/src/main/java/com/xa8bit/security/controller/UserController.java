package com.xa8bit.security.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.xa8bit.common.enums.ResponseStatusEnum;
import com.xa8bit.security.domain.User;
import com.xa8bit.security.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author lingwh
 * @desc
 * @date 2025/11/22 17:30
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public SaResult login(@RequestBody User user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean isLogin = userService.login(user);
        if (isLogin) {
            // 第1步，先登录上
            StpUtil.login(user.getUsername());
            // 第2步，获取 Token  相关参数
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            // 第3步，返回给前端
            return SaResult.data(tokenInfo);
        } else {
            ResponseStatusEnum invalidCredentials = ResponseStatusEnum.INVALID_CREDENTIALS;
            return SaResult.error(invalidCredentials.getMessage()).setCode(invalidCredentials.getCode());
        }
    }

    @SaCheckLogin
    @GetMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

//    @SaCheckLogin
//    @GetMapping("/login")
//    public SaResult getLoginUser() {
//        String operateUser = (String) StpUtil.getLoginId();
//        Map<String, String> operateUserMap = new HashMap<>();
//        operateUserMap.put("user", operateUser);
//        return SaResult.ok(JsonUtil.toJsonString(operateUserMap));
//    }

}

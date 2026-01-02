package org.bluebridge.guard.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.bluebridge.common.domain.response.Result;
import org.bluebridge.common.enums.OperationTypeEnum;
import org.bluebridge.common.enums.ResponseStatusEnum;
import org.bluebridge.guard.domain.dto.UserLoginDTO;
import org.bluebridge.guard.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
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

    @SaIgnore
    @PostMapping("/login")
    public Result login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        boolean isLogin = userService.login(userLoginDTO);
        if (isLogin) {
            // 第1步，先登录上
            StpUtil.login(userLoginDTO.getUsername());
            // 第2步，获取 Token  相关参数
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            // 第3步，返回给前端
            return Result.buildDataResult(tokenInfo, OperationTypeEnum.USER_LOGIN);
        } else {
            ResponseStatusEnum invalidCredentials = ResponseStatusEnum.INVALID_CREDENTIALS;
            return Result.error(invalidCredentials.getCode(), invalidCredentials.getMessage());
        }
    }

    @SaCheckLogin
    @GetMapping("/logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

}

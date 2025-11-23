package org.bluebridge.controller;

import com.github.pagehelper.PageInfo;
import org.bluebridge.domain.PageEntity;
import org.bluebridge.domain.User;
import org.bluebridge.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lingwh
 * @desc 
 * @date 2025/11/23 13:10
 */
@Controller
public class UserController {

    @Resource
    private IUserService userService;

    /**
     * 访问   http://localhost:8080/user
     * @return
     */
    @ResponseBody
    @GetMapping("/user")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 访问   http://localhost:8080/user/page
     * @return
     */
    @ResponseBody
    @GetMapping("/user/page")
    public PageInfo<User> listPage() {
        // 手动模拟从前台获取分页信息
        int currentPage = 1;
        int pageSize = 10;
        PageEntity<User> pageEntity = new PageEntity<>();
        pageEntity.setCurrentPage(currentPage);
        pageEntity.setPageSize(pageSize);

        return userService.listPage(pageEntity);
    }

    /**
     * 访问   http://localhost:8080/user/1
     * @return
     */
    @ResponseBody
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getById(id);
    }

}

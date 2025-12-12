package org.bluebridge.controller;

/**
 * @author lingwh
 * @desc
 * @date 2025/12/12 18:33
 */
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")  // 根路径：复数资源
public class UserController {

    // 1. 单条查询（主键）
    @GetMapping("/{id}")
    public Result<UserVO> getUserById(@PathVariable Long id) {
        // 业务逻辑
        return Result.success(userService.getById(id));
    }

    // 2. 条件单查（非主键）
    @GetMapping("/by-phone")
    public Result<UserVO> getUserByPhone(@RequestParam String phone) {
        return Result.success(userService.getByPhone(phone));
    }

    // 3. 分页查询
    @GetMapping("/page")
    public Result<PageResult<UserVO>> pageUser(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String username,  // 可选筛选条件
            @RequestParam(required = false) Integer status) {
        PageResult<UserVO> page = userService.pageQuery(pageNum, pageSize, username, status);
        return Result.success(page);
    }

    // 4. 列表查询（不分页）
    @GetMapping("/list")
    public Result<List<UserVO>> listUser(@RequestParam(required = false) Integer status) {
        return Result.success(userService.listByStatus(status));
    }

    // 5. 新增
    @PostMapping
    public Result<Void> createUser(@Valid @RequestBody UserCreateDTO userDTO) {
        userService.create(userDTO);
        return Result.success();
    }
    // 6. 批量新增
    @PostMapping("/batch")
    public Result<Void> createUserBatch(@Valid @RequestBody List<UserCreateDTO> userDTOList) {
        userService.create(userDTO);
        return Result.success();
    }

    // 6. 全量更新
    @PutMapping("/{id}")
    public Result<Void> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO userDTO) {
        userService.update(id, userDTO);
        return Result.success();
    }

    // 7. 局部更新（如仅改手机号）
    @PatchMapping("/{id}")
    public Result<Void> patchUser(
            @PathVariable Long id,
            @RequestBody UserPatchDTO patchDTO) {
        userService.patch(id, patchDTO);
        return Result.success();
    }

    // 8. 删除（主键）
    @DeleteMapping("/{id}")
    public Result<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteById(id);
        return Result.success();
    }

    // 9. 批量删除
    @DeleteMapping("/batch")
    public Result<Void> batchDeleteUser(@RequestBody List<Long> ids) {
        userService.batchDelete(ids);
        return Result.success();
    }

    // 10. 逻辑删除（改状态）
    @PatchMapping("/{id}/status")
    public Result<Void> updateUserStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success();
    }
}


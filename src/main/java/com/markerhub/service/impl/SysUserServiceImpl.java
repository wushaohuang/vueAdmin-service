package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.SysMenu;
import com.markerhub.entity.SysRole;
import com.markerhub.entity.SysUser;
import com.markerhub.mapper.SysUserMapper;
import com.markerhub.service.SysMenuService;
import com.markerhub.service.SysRoleService;
import com.markerhub.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-01-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysMenuService sysMenuService;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        //  ROLE_admin,ROLE_normal,sys:user:list
        String authority = "";


        //获取角色编码
        List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>().inSql("id", "select role_id from sys_user_role where user_id = " + userId));

        if (roles.size() > 0) {
            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
            //  ROLE_admin,ROLE_normal
            authority = roleCodes.concat(",");
        }

        //获取菜单编码
        List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
        if (menuIds.size() > 0) {
            List<SysMenu> menus = sysMenuService.listByIds(menuIds);
            String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
            authority = authority.concat(menuPerms);
        }

        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {

    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {

    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {

    }
}

package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.SysUser;
import com.markerhub.mapper.SysUserMapper;
import com.markerhub.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-01-31
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        return null;
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

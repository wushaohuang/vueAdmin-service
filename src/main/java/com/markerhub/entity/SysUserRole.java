package com.markerhub.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long roleId;


}

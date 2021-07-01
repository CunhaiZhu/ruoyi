package com.ruoyi.common.core.cms.exception;

/**
 * 用户不存在异常类
 *
 * @author ruoyi
 */
public class UserNotExistsException extends UserException
{
    private static final long serialVersionUID = 1L;

    public UserNotExistsException()
    {
        super("user.not.exists", null);
    }
}

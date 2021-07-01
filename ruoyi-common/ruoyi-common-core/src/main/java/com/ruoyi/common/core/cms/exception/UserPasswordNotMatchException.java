package com.ruoyi.common.core.cms.exception;

/**
 * 用户密码不正确或不符合规范异常类
 *
 * @author ruoyi
 */
public class UserPasswordNotMatchException extends com.ruoyi.common.exception.user.UserException
{
    private static final long serialVersionUID = 1L;

    public UserPasswordNotMatchException()
    {
        super("user.password.not.match", null);
    }
}

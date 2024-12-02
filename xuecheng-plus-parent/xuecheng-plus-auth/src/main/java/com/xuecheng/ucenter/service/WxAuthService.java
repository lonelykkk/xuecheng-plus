package com.xuecheng.ucenter.service;

import com.xuecheng.ucenter.model.po.XcUser;

/**
 * @author Mr.M
 * @version 1.0
 * @description 微信扫码接入
 * @date 2023/2/24 15:42
 */
public interface WxAuthService {

 /**
  *  微信扫码认证，申请令牌，携带令牌查询用户信息、保存用户信息到数据库
  * @param code 授权码
  * @return
  */
 public XcUser wxAuth(String code);
}

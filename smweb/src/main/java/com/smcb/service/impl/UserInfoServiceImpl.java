package com.smcb.service.impl;

import com.smcb.entity.UserInfo;
import com.smcb.mapper.UserInfoMapper;
import com.smcb.service.IUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zqh
 * @since 2017-04-12
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
	
}

package com.road.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.NationMapper;
import com.road.pojo.Nation;
import com.road.service.INationService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhouc
 * @since 2021-09-06
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}

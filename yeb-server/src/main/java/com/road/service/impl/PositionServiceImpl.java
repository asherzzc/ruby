package com.road.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.PositionMapper;
import com.road.pojo.Position;
import com.road.service.IPositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}

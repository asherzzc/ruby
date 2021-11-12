package com.road.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.road.mapper.SalaryMapper;
import com.road.pojo.Salary;
import com.road.service.ISalaryService;
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
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

}

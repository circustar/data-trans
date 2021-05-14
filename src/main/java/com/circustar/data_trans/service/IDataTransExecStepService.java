package com.circustar.data_trans.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.circustar.data_trans.entity.DataTransExecStep;

public interface IDataTransExecStepService extends IService<DataTransExecStep> {
    void updateExecResult(int dataTransExecId, String dataTransId, int error);
}

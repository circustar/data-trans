package com.circustar.data_trans.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.circustar.data_trans.entity.DataTransExecStep;
import com.circustar.data_trans.executor.init.DataTransTableDefinition;
import com.circustar.data_trans.mapper.DataTransExecStepMapper;
import com.circustar.data_trans.service.IDataTransExecStepService;

import java.util.Date;

public class DataTransExecStepService extends ServiceImpl<DataTransExecStepMapper, DataTransExecStep> implements IDataTransExecStepService {
    public void updateExecResult(Long dataTransExecId, String dataTransId, int error) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_EXEC_ID, dataTransExecId);
        qw.eq(DataTransTableDefinition.COLUMN_NAME_DATA_TRANS_ID, dataTransId);
        DataTransExecStep dataTransExecStep = this.getOne(qw);
        if(dataTransExecStep != null) {
            dataTransExecStep.setExecuteError(error);
            dataTransExecStep.setExecuteTime(new Date());
            this.updateById(dataTransExecStep);
        } else {
            dataTransExecStep = DataTransExecStep.builder()
                    .dataTransExecId(dataTransExecId)
                    .dataTransId(dataTransId)
                    .executed(1)
                    .executeError(error)
                    .executeTime(new Date())
                    .build();
            this.save(dataTransExecStep);
        }
    }
}

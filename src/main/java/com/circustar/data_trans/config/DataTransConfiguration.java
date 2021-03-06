package com.circustar.data_trans.config;

import com.circustar.data_trans.service.*;
import com.circustar.data_trans.service.impl.*;
import com.circustar.data_trans.executor.DataTransExecutorManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
@MapperScan("com.circustar.data_trans.mapper")
public class DataTransConfiguration {
    private DataSource dataSource;
    private DataTransExecutorManager dataTransExecutorManager;
    private IDataTransService dataTransService;
    private IDataTransGroupService dataTransGroupService;
    private IDataTransSourceService dataTransSourceService;
    private IDataTransColumnService dataTransColumnService;
    private IDataTransExecService dataTransExecService;
    private IDataTransExecStepService dataTransExecStepService;
    private IDataTransExecParamService dataTransExecParamService;
    public DataTransConfiguration(DataSource dataSource) throws Exception {
        assert(dataSource != null);
        this.dataSource = dataSource;
        this.dataTransGroupService = new DataTransGroupService();
        this.dataTransService = new DataTransService();
        this.dataTransSourceService = new DataTransSourceService();
        this.dataTransColumnService = new DataTransColumnService();
        this.dataTransExecService = new DataTransExecService();
        this.dataTransExecStepService = new DataTransExecStepService();
        this.dataTransExecParamService = new DataTransExecParamService();
        this.dataTransExecutorManager = new DataTransExecutorManager(dataSource
                ,dataTransGroupService
                ,dataTransService
                ,dataTransSourceService
                ,dataTransColumnService
                ,dataTransExecService
                ,dataTransExecStepService
                ,dataTransExecParamService);
        this.dataTransExecutorManager.execInit();
    }

    @Bean
    public IDataTransService getDataTransService() {
        return this.dataTransService;
    }

    @Bean
    public IDataTransGroupService getDataTransGroupService() {
        return this.dataTransGroupService;
    }

    @Bean
    public IDataTransSourceService getDataTransSourceService() {
        return this.dataTransSourceService;
    }

    @Bean
    public IDataTransColumnService getDataTransColumnService() {
        return this.dataTransColumnService;
    }

    @Bean
    public IDataTransExecService getDataTransExecService() {
        return this.dataTransExecService;
    }

    @Bean
    public IDataTransExecStepService getDataTransExecStepService() {
        return this.dataTransExecStepService;
    }

    @Bean
    public IDataTransExecParamService getDataTransExecParamService() {
        return this.dataTransExecParamService;
    }

    @Bean
    public DataTransExecutorManager getDataTransExecutorManager() {
        return this.dataTransExecutorManager;
    }
}

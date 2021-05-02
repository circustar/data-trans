package com.circustar.data_trans.config;

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
    private DataTransService dataTransService;
    private DataTransGroupService dataTransGroupService;
    private DataTransSourceService dataTransSourceService;
    private DataTransColumnService dataTransColumnService;
    private DataTransExecService dataTransExecService;
    private DataTransExecParamService dataTransExecParamService;
    public DataTransConfiguration(DataSource dataSource) throws Exception {
        this.dataSource = dataSource;
        this.dataTransGroupService = new DataTransGroupService();
        this.dataTransService = new DataTransService();
        this.dataTransSourceService = new DataTransSourceService();
        this.dataTransColumnService = new DataTransColumnService();
        this.dataTransExecService = new DataTransExecService();
        this.dataTransExecParamService = new DataTransExecParamService();
        this.dataTransExecutorManager = new DataTransExecutorManager(dataSource
                ,dataTransGroupService
                ,dataTransService
                ,dataTransSourceService
                ,dataTransColumnService
                ,dataTransExecService
                ,dataTransExecParamService);
        Connection connection = this.dataSource.getConnection();
        this.dataTransExecutorManager.execInit(connection);
        connection.close();
    }

    @Bean
    public DataTransService getDataTransService() {
        return this.dataTransService;
    }

    @Bean
    public DataTransGroupService getDataTransGroupService() {
        return this.dataTransGroupService;
    }

    @Bean
    public DataTransSourceService getDataTransSourceService() {
        return this.dataTransSourceService;
    }

    @Bean
    public DataTransColumnService getDataTransColumnService() {
        return this.dataTransColumnService;
    }

    @Bean
    public DataTransExecService getDataTransExecService() {
        return this.dataTransExecService;
    }

    @Bean
    public DataTransExecParamService getDataTransExecParamService() {
        return this.dataTransExecParamService;
    }

    @Bean
    public DataTransExecutorManager getDataTransExecutorManager() {
        return this.dataTransExecutorManager;
    }
}

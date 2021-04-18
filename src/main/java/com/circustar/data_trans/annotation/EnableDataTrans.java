package com.circustar.data_trans.annotation;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.circustar.data_trans.config.DataTransConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import(value = DataTransConfiguration.class)
@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
public @interface EnableDataTrans {
}

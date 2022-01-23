### 1.特性
* 数据抽取工具
* 自动管理临时表生命周期
* 自动管理索引
* 支持多种数据库，包括Oracle、Mysql、DB2、SQLServer

### 2.依赖
* mybatis-plus

### 3.引入依赖
```xml
    <dependency>
        <groupId>com.circustar</groupId>
        <artifactId>data-trans</artifactId>
        <version>1.0.0</version>
    </dependency>
```

### 4.使用方法
#### 4.1.定义一个数据转换组
##### 4.1.1.DATA_TRANS_GROUP中定义数据转换组名称
|  字段名   | 名称  | 类型  | 备注  |
|  ----  | ----  | ----  | ----  |
| DATA_TRANS_GROUP_NAME  | 分组名 | 文字 |  |
| RECOVERABLE  | 是否可恢复 | 数字 | 1表示上次执行报错后，下次仍可继续执行 |
| REMARK  | 备注 | 文字 |  |

##### 4.1.2.DATA_TRANS表中定义分步步骤
|  字段名   | 名称  | 类型  | 备注  |
|  ----  | ----  | ----  | ----  |
| DATA_TRANS_GROUP_NAME  | 分组名 | 文字 |  |
| DATA_TRANS_ID  | 分步ID | 文字 |  |
| DEPEND_DATA_TRANS_ID  | 分步依赖ID | 文字 | 定义执行顺序，表示在依赖的分步ID后执行 |
| TABLE_NAME  | 处理数据的表名 | 文字 | 表示在那个表中处理数据 |
| DISABLED  | 是否禁用 | 数字 | 0表示未禁用，1表示禁用 |
| DROP_TABLE_FLAG  | 执行前是否需要Drop表 | 数字 | 0表示不会Drop，1表示Drop |
| CREATE_TABLE_FLAG  | 执行前是否需要创建表 | 数字 | 0表示不会创建，1表示创建 |
| TRUNCATE_TABLE_FLAG  | 执行前是否需要清空表数据 | 数字 | 0表示不会清空，1表示清空 |
| UPDATE_TYPE  | 更新类型 | 文字 | 可选择INSERT/UPDATE/DELETE |
| SELECT_PREFIX  | 执行Select时的前缀 | 文字 | 比如SqlServer的前缀：Top 1 |
| SELECT_SUFFIX  | 执行Select时的后缀 | 文字 | 比如MySQL的后缀：Limit 1 |
| SKIP_EXPRESSION  | 跳过条件 | 文字 | 支持SPEL表达式，表示什么情况下跳过该步骤 |
| ADD_TO_PARAM_MAP  | 是否加入参数Map中 | 数字 | 1时回把更新结果放到参数Map中 |
| REMARK  | 备注 | 文字 |  |

##### 4.1.3.DATA_TRANS_SOURCE表中数据来源
|  字段名   | 名称  | 类型  | 备注  |
|  ----  | ----  | ----  | ----  |
| DATA_TRANS_SOURCE_ID  | 表ID | 文字 |  |
| DATA_TRANS_ID  | 分步ID | 文字 |  |
| SOURCE_TABLE  | 数据来源表 | 文字 |  |
| ALIAS  | 别名 | 文字 |  |
| JOIN_TYPE  | 表连接类型 | 文字 | 可选inner join、left join等|
| ON_STATEMENT  | 表连接条件 | 文字 |  |
| WHERE_STATEMENT  | 过滤条件 | 文字 |  |

##### 4.1.4.DATA_TRANS_COLUMN表中定义来源表、目标表字段对应关系
|  字段名   | 名称  | 类型  | 备注  |
|  ----  | ----  | ----  | ----  |
| DATA_TRANS_COLUMN_ID  | 表ID | 文字 |  |
| DATA_TRANS_ID  | 分步ID | 文字 |  |
| COLUMN_NAME  | 目标列名 | 文字 |  |
| COLUMN_TYPE  | 目标列列类型 | 文字 |  |
| COLUMN_VALUE  | 目标列值 | 文字 |  |
| GROUP_FLAG  | 分组标志 | 数字 |  |
| PRIMARY_KEY  | 是否是主键 | 数字 |  |
| INDEX_NAME  | 索引名称 | 文字 |  |
| INDEX_ORDER  | 所在索引的顺序 | 数字 |  |

#### 4.2.执行
```java
public class TestDataTrans {
    @Autowired
    private DataTransExecutorManager executorManager;

    @Test
    public void Test01(){
        // 创建执行参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("param1", "test");
        Long execId = executorManager.addExecInfo("test", paramMap);

        // 执行
        executorManager.exec(execId);
    }
}
```

#### 4.3.DATA_TRANS_EXEC表中查看执行结果

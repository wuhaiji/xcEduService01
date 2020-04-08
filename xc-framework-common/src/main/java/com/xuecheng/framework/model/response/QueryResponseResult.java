package com.xuecheng.framework.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author whj
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class QueryResponseResult<T> extends ResponseResult {

    QueryResult<T> queryResult;

    public QueryResponseResult(ResultCode resultCode,QueryResult<T> queryResult){
        super(resultCode);
       this.queryResult = queryResult;
    }

}

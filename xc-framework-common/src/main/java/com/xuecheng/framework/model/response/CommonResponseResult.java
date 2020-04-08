package com.xuecheng.framework.model.response;

import lombok.Data;

/**
 * @author whj
 * @createTime 2020-02-10 18:20
 * @description 通用接口返回对象
 **/
@Data
public class CommonResponseResult extends ResponseResult{
    /**
     * 需要返回的数据对象
     */
    Object data;

    public CommonResponseResult(ResultCode resultCode,Object data){
        super(resultCode);
        this.data=data;
    }

    /**
     * 返回操作成功的数据对象
     * @param data
     * @return
     */
    public static CommonResponseResult Ok(Object data){
        return new CommonResponseResult(CommonCode.SUCCESS,data);
    }

}

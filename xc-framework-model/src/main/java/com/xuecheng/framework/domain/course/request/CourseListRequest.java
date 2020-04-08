package com.xuecheng.framework.domain.course.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author mrt
 * @date 2018/4/13
 */
@Data
@ToString
@ApiModel
public class CourseListRequest extends RequestData {

    @ApiModelProperty(name = "companyId",value = "公司ID")
    private String companyId;
}

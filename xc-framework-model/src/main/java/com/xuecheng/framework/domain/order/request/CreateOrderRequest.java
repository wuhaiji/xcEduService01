package com.xuecheng.framework.domain.order.request;

import com.xuecheng.framework.model.request.RequestData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Created by mrt on 2018/3/26.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class CreateOrderRequest extends RequestData {

    String courseId;

}

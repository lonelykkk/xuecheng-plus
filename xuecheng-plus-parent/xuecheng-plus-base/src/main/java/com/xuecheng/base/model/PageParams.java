package com.xuecheng.base.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lonelykkk
 * @email 2765314967@qq.com
 * @date 2024/7/26 8:42
 * @Version V1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    @ApiModelProperty("页码")
    private Long pageNo = 1L;

    @ApiModelProperty("记录数")
    private Long pageSize = 30L;
}

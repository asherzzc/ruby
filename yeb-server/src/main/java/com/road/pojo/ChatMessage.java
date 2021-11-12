package com.road.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author zhouc
 * @date 2021/11/10 20:27
 * @description
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ChatMessage {
    @ApiModelProperty(value = "发送人")
    private String from;

    @ApiModelProperty(value = "发给谁")
    private String to;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "发送时间")
    private LocalDateTime date;

    @ApiModelProperty(value = "发送人的昵称(管理员，系统管理员，培训管理员)")
    private String formNickName;
}

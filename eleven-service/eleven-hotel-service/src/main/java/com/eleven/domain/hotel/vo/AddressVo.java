package com.eleven.domain.hotel.vo;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Address")
public class AddressVo extends Vo {

    @Schema(example = "中国大陆")
    private String country;

    @Schema(example = "辽宁")
    private String province;

    @Schema(example = "大连")
    private String city;

    @Schema(example = "开发区")
    private String location;

    @Schema(example = "大连开发区海青岛街道东行300米")
    private String address;

}

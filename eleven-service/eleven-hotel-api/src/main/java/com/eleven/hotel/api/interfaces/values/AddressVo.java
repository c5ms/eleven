package com.eleven.hotel.api.interfaces.values;

import com.eleven.hotel.api.interfaces.model.core.AbstractVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(name = "Address")
public class AddressVo extends AbstractVo {

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

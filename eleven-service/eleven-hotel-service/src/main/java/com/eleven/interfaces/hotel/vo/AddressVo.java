package com.eleven.interfaces.hotel.vo;

import com.eleven.common.layer.Vo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Schema(name = "Address")
public class AddressVo extends Vo {

    @NotEmpty
    @Schema(example = "中国大陆")
    private String country;

    @NotEmpty
    @Schema(example = "辽宁")
    private String province;

    @NotEmpty
    @Schema(example = "大连")
    private String city;

    @NotEmpty
    @Schema(example = "开发区")
    private String location;

    @NotEmpty
    @Schema(example = "大连开发区海青岛街道东行300米")
    private String address;

}

package com.motiveschina.erp.application.model;

import java.time.LocalDate;
import com.motiveschina.core.layer.ApplicationDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Material")
public class MaterialDto implements ApplicationDto {

	private Long materialId;
	private String materialCode;
	private String materialName;
	private String materialCategory;
	private String unit;
	private double safetyStock;
	private String mainSupplierId;
	private LocalDate createDate;
	private MaterialDetailDto detail;

	@Data
	@Schema(name = "MaterialDetail")
	public static class MaterialDetailDto implements ApplicationDto {
		private String materialComposition;
		private String colorFastnessGrade;
		private String environmentalCertification;
		private double fabricWeight;
		private double shrinkageRate;
		private double tensileStrength;
	}
}

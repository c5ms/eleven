package com.motiveschina.erp.domain.inventory;

import java.util.ArrayList;
import java.util.List;
import com.eleven.framework.utils.ImmutableValues;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
public class StockInManifest {

    private Long produceId;
    private int quantity;

}

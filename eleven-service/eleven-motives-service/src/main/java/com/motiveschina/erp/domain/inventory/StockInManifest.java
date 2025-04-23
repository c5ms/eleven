package com.motiveschina.erp.domain.inventory;

import java.util.ArrayList;
import java.util.List;
import com.eleven.framework.utils.ImmutableValues;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StockInManifest {

    private final List<Item> items = new ArrayList<>();

    public static StockInManifest empty() {
        return new StockInManifest();
    }

    public void add(Long productId, int quantity) {
        this.items.add(new Item(productId, quantity));
    }

    public ImmutableValues<Item> getItems() {
        return ImmutableValues.of(items);
    }

    public record Item(Long produceId, int quantity) {
    }
}

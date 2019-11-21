package databuilder;

import domain.Item;

public class ItemDataBuilder {
    public static Item getItemTeste() {
        return Item.builder().nome("Item de Teste").build();
    }
}

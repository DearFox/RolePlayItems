package su.paw.dearfox.roleplayitems;

import eu.pb4.polymer.core.api.item.PolymerItemGroupUtils;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Roleplayitems implements ModInitializer  {

    public static final String MOD_ID = "roleplayitems";
    public static final String MOD_NAME = "Role Play items";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    //public static final Item OAK_ROD = register(new SimpleModeledPolymerItem(new Item.Settings(), Items.PAPER,"oak_rod"), "oak_rod");
    public static final Item UMBRELLA_OPEN = register(new UmbrellaItem(new Item.Settings().maxCount(1), Items.RABBIT_HIDE), "umbrella_open");
    public static final Item UMBRELLA_CLOSE = register(new UmbrellaItem(new Item.Settings().maxCount(1), Items.RABBIT_HIDE), "umbrella_close");

    //public static final ItemGroup ITEM_GROUP = new ItemGroup.Builder(null, -1)
    //        .displayName(Text.translatable("roleplayitems.itemgroup").formatted(Formatting.AQUA))
    //        .icon(()->new ItemStack(Roleplayitems.ITEM))
    //        .entries(new ItemGroup.EntryCollector() {
    //            @Override
    //            public void accept(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
    //                entries.add(Items.DAMAGED_ANVIL.getDefaultStack());
    //                var items = REG_CACHE.get(Registries.ITEM);
    //                for (var pair : items) {
    //                    entries.add((ItemConvertible) pair.getRight());
    //                }
    //            }
    //        })
    //        .build();

    //public static SimplePolymerItem ITEM = new SimplePolymerItem(new Item.Settings().fireproof().maxCount(1), Items.IRON_HOE);
    //public static SimplePolymerItem ITEM_2 = new SimplePolymerItem(new Item.Settings().fireproof().maxCount(99), Items.DIAMOND_BLOCK);


    private static final Map<Registry<?>, List<Pair<Identifier, ?>>> REG_CACHE = new HashMap<>();

    @Override
    public void onInitialize() {

        PolymerResourcePackUtils.addModAssets(MOD_ID);
        PolymerResourcePackUtils.markAsRequired();

        LOGGER.info("Hello Fabric world!");

        //register(Registries.ITEM, new Identifier("roleplayitems","item"),ITEM);
        //register(Registries.ITEM, new Identifier("roleplayitems","item_2"),ITEM_2);
        //Registry.register(Registries.ITEM, new Identifier(MOD_ID,"item"),new SimplePolymerItem(new Item.Settings().fireproof().maxCount(1), Items.IRON_HOE));

        PolymerItemGroupUtils.registerPolymerItemGroup(Roleplayitems.id("group"), ItemGroup.create(ItemGroup.Row.BOTTOM, -1)
                .icon(UMBRELLA_OPEN::getDefaultStack)
                .displayName(Text.translatable("itemgroup.roleplayitems"))
                .entries(((context, entries) -> {

                    //// Rods
                    entries.add(UMBRELLA_CLOSE);
                    entries.add(UMBRELLA_OPEN);
                }))
                .build());
    }


    //public ActionResult useItem(ItemUsageContext context) {
    //    LOGGER.info("Hello Fabric world!");
    //    return null;
    //}

    public static <B, T extends B> T register(Registry<B> registry, Identifier id, T obj) {
        REG_CACHE.computeIfAbsent(registry, (r) -> new ArrayList<>()).add(new Pair<>(id, obj));
        return obj;
    }

    public static <T extends Item> T register(T item, String id) {
        if (item instanceof UmbrellaItem) {
            ((UmbrellaItem) item).registerModel(id);
        }
        //if (item instanceof ModeledPolymerBlockItem) {
        //    ((ModeledPolymerBlockItem) item).registerModel(id);
        //}
        return Registry.register(Registries.ITEM, Roleplayitems.id(id), item);
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }
}

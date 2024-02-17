package su.paw.dearfox.roleplayitems;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
//import net.minecraft.util.Identifier;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public class UmbrellaItem extends Item implements PolymerItem {
    public HashMap<UmbrellaItem, PolymerModelData> modelData = new HashMap<>();
    public Item polymerItem;

    public UmbrellaItem(Settings settings, Item polymerItem) {
        super(settings);
        this.polymerItem = polymerItem;
        //this.polymerModel = PolymerResourcePackUtils.requestModel(polymerItem, new Identifier(Roleplayitems.MOD_ID,modelId));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world,PlayerEntity user, Hand hand){
        var stack = user.getStackInHand(hand);
        if (Objects.equals(String.valueOf(stack.getItem()), "umbrella_close")){
            user.setStackInHand(hand,new ItemStack(Roleplayitems.UMBRELLA_OPEN));
            world.playSound(null, user.getX(),user.getY(),user.getZ(),SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,SoundCategory.PLAYERS,1,1.2f);
        } else if (Objects.equals(String.valueOf(stack.getItem()), "umbrella_open")){
            user.setStackInHand(hand,new ItemStack(Roleplayitems.UMBRELLA_CLOSE));
            world.playSound(null, user.getX(),user.getY(),user.getZ(),SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,SoundCategory.PLAYERS,1,1.2f);
        }
        //if (!user.isCreative()) {
        //    stack.decrement(1);
        //}
        //Roleplayitems.LOGGER.info(String.valueOf(stack.getItem()));
        return TypedActionResult.success(stack);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return polymerItem;
    }

    @Override
    public int getPolymerCustomModelData(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        return modelData.get(this).value();
    }

    public void registerModel(String id) {
        modelData.put(this, PolymerResourcePackUtils.requestModel(polymerItem, Roleplayitems.id("item/%s".formatted(id))));
    }
}
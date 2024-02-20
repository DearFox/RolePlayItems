package su.paw.dearfox.roleplayitems;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.resourcepack.api.PolymerModelData;
import eu.pb4.polymer.resourcepack.api.PolymerResourcePackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
    public boolean SafeFallDistancePassed = false;

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
        if (Objects.equals(String.valueOf(stack.getItem()), "mary_poppins_umbrella_close")){
            user.setStackInHand(hand,new ItemStack(Roleplayitems.MARY_POPPINS_UMBRELLA_OPEN));
            world.playSound(null, user.getX(),user.getY(),user.getZ(),SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,SoundCategory.PLAYERS,1,1.2f);
        } else if (Objects.equals(String.valueOf(stack.getItem()), "mary_poppins_umbrella_open")){
            user.setStackInHand(hand,new ItemStack(Roleplayitems.MARY_POPPINS_UMBRELLA_CLOSE));
            world.playSound(null, user.getX(),user.getY(),user.getZ(),SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,SoundCategory.PLAYERS,1,1.2f);
        }
        //if (!user.isCreative()) {
        //    stack.decrement(1);
        //}
        //Roleplayitems.LOGGER.info(String.valueOf(stack.getItem()));
        return TypedActionResult.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected){
        //Roleplayitems.LOGGER.info(String.valueOf(stack.getItem()));
        if (Objects.equals(String.valueOf(stack.getItem()), "mary_poppins_umbrella_open") && (entity instanceof ServerPlayerEntity player && (selected || player.getEquippedStack(EquipmentSlot.HEAD)==stack || player.getEquippedStack(EquipmentSlot.OFFHAND)==stack))){
            if (player.fallDistance > player.getSafeFallDistance()-0.5){
                SafeFallDistancePassed = true;
            }
            if (player.isOnGround() || player.isTouchingWater()){
                SafeFallDistancePassed = false;
            }

            if (SafeFallDistancePassed && !player.isFallFlying() && !player.isTouchingWater()){
                player.setStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING,1,0,false,false),null);
            }
            //if (player.getStatusEffect(StatusEffects.SLOW_FALLING) != null){
            //    Roleplayitems.LOGGER.info(String.valueOf(Objects.requireNonNull(player.getStatusEffect(StatusEffects.SLOW_FALLING)).getDuration()));
            //}
            //player.limitFallDistance();
            //Roleplayitems.LOGGER.info(String.valueOf(player.isFallFlying()));
            //Roleplayitems.LOGGER.info(String.valueOf(player.isOnGround()));

        }
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
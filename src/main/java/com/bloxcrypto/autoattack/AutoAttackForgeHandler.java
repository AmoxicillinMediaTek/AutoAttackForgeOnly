package com.bloxcrypto.autoattack;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(AutoAttackForgeHandler.MOD_ID)
@Mod.EventBusSubscriber(modid = AutoAttackForgeHandler.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AutoAttackForgeHandler {
    public static final String MOD_ID = "autoattack";
    private static final KeyMapping TOGGLE_KEY = new KeyMapping(
            "key.autoattack.toggle",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_J,
            "key.categories.misc"
    );
    private static boolean enabled = true;

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(TOGGLE_KEY);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null) {
            return;
        }

        if (TOGGLE_KEY.consumeClick()) {
            enabled = !enabled;
            minecraft.player.displayClientMessage(
                    Component.literal("AutoAttack: " + (enabled ? "On" : "Off")).withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED),
                    true
            );
        }

        if (!enabled) {
            return;
        }

        if (shouldAutoAttack(minecraft) && minecraft.player.getAttackStrengthScale(0.0F) >= 1.0F) {
            KeyMapping attackKey = minecraft.options.keyAttack;
            attackKey.click(attackKey.getKey());
        }
    }

    private static boolean shouldAutoAttack(Minecraft minecraft) {
        if (minecraft.screen != null || minecraft.isPaused()) {
            return false;
        }

        HitResult hitResult = minecraft.hitResult;
        if (hitResult instanceof EntityHitResult entityHitResult) {
            return entityHitResult.getEntity().isAlive();
        }
        return false;
    }
}

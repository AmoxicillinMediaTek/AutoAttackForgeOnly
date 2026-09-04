import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;

@Mod.EventBusSubscriber(modid = "autoattack", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AutoAttackForgeHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        KeyMapping attackKey = mc.options.keyAttack;
        if (shouldAutoAttack(mc)) {
            attackKey.setDown(true);
            attackKey.click();
        } else {
            attackKey.setDown(false);
        }
    }

    private static boolean shouldAutoAttack(Minecraft mc) {
        if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.ENTITY) {
            EntityHitResult hit = (EntityHitResult) mc.hitResult;
            return hit.getEntity().isAlive();
        }
        return false;
    }
}

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventcb.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "autoattack", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class AutoAttackForgeHandler {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        // Condition check
        if (shouldAutoAttack(mc)) {
            KeyMapping attackKey = mc.options.keyAttack;
            
            // Set it down
            attackKey.setDown(true);
            
            // Force the game to register the click event next frame
            ((AccessorKeyMapping) attackKey).setClickCount(((AccessorKeyMapping) attackKey).getClickCount() + 1);
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

package me.clumsycat.furnitureexpanded.mixin;


import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class BlockBreakEvent {
    @Shadow
    public ServerWorld world;
    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;onBroken(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"), method = "tryBreakBlock", locals = LocalCapture.CAPTURE_FAILHARD)
    private void onBlockBroken(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState state, BlockEntity entity, Block block, boolean b1) {
        PlayerBlockBreakEvents.AFTER.invoker().afterBlockBreak(this.world, this.player, pos, state, entity);
        if(!world.isClient()) {
            SeatEntity mobentity = SeatHandler.getSeatEntity(world, pos);
            if(mobentity != null) {
                SeatHandler.removeSeatEntity(world, pos);
                mobentity.kill();
            }
        }
    }
}

package me.clumsycat.furnitureexpanded.fabric.mixin;

import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import me.clumsycat.furnitureexpanded.util.SeatHandler;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerGameMode.class)
public class BlockBreakEvent {
    @Shadow
    public ServerLevel level;

    @Shadow
    public ServerPlayer player;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;destroy(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"), method = "destroyBlock", locals = LocalCapture.CAPTURE_FAILHARD)
    private void onBreakEvent(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState state, BlockEntity entity, Block block, boolean b1) {
        PlayerBlockBreakEvents.AFTER.invoker().afterBlockBreak(this.level, this.player, pos, state, entity);
        if(!this.level.isClientSide()) {
            SeatEntity mobentity = SeatHandler.getSeatEntity(this.level, pos);
            SeatHandler.removeSeatEntity(this.level, pos);
            if(mobentity != null)
                mobentity.kill();
        }
    }

}

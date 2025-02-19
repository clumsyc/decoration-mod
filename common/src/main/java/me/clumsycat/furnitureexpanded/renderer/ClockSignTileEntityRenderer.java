package me.clumsycat.furnitureexpanded.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import me.clumsycat.furnitureexpanded.util.TimeUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

@Environment(EnvType.CLIENT)
public class ClockSignTileEntityRenderer implements BlockEntityRenderer<ClockSignTileEntity> {
    private final Font font;

    public ClockSignTileEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super();
        this.font = rendererDispatcherIn.getFont();
    }

    @Override
    public void render(ClockSignTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = tileEntityIn.getBlockState();
        int i1 = 98255;
        FormattedCharSequence irp = Component.literal(TimeUtils.convertDaytimeToHHmm(tileEntityIn.getLevel() != null ? tileEntityIn.getLevel().getDayTime() : 0)).getVisualOrderText();

        float f4 = -state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
        renderTask(f4, -.925D, irp, i1, matrixStackIn, bufferIn);

        float f5 = -state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().toYRot();
        renderTask(f5, .075D, irp, i1, matrixStackIn, bufferIn);
    }

    private void renderTask(float rotation, double startingPoint, FormattedCharSequence irp, int i1, PoseStack matrixStackIn, MultiBufferSource bufferIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.mulPose(Axis.YP.rotationDegrees(rotation));
        matrixStackIn.mulPose(Direction.DOWN.getRotation());
        matrixStackIn.translate(startingPoint, .27D, -.13D);
        matrixStackIn.scale(0.05f, 0.05f, 0.05f);
        this.font.drawInBatch(irp, -8, -8, i1, false, matrixStackIn.last().pose(), bufferIn, Font.DisplayMode.NORMAL, 0, 225);
        matrixStackIn.popPose();
    }


}

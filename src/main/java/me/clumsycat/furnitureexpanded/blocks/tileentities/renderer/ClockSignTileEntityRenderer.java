package me.clumsycat.furnitureexpanded.blocks.tileentities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClockSignTileEntityRenderer extends TileEntityRenderer<ClockSignTileEntity> {
    public ClockSignTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ClockSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = tileEntityIn.getBlockState();

        //int i = DyeColor.BLUE.getTextColor();
        //double d0 = 0.4D;
        //int j = (int)((double) NativeImage.getR(i) * 0.4D);
        //int k = (int)((double) NativeImage.getG(i) * 0.4D);
        //int l = (int)((double) NativeImage.getB(i) * 0.4D);
        //int i1 = NativeImage.combine(0, l, k, j);
        int i1 = 98255; // TODO: Add support to custom colors

        FontRenderer fontRenderer = this.renderer.font;
        IReorderingProcessor irp = new StringTextComponent((tileEntityIn.hour < 10 ? "0" + tileEntityIn.hour : tileEntityIn.hour) +" : "
                + (tileEntityIn.minutes < 10 ? "0" + tileEntityIn.minutes : tileEntityIn.minutes)).getVisualOrderText();

        float f4 = -state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f4));
        matrixStackIn.mulPose(Direction.DOWN.getRotation());
        matrixStackIn.translate(-.925, .27, -.13);
        matrixStackIn.scale(0.05f, 0.05f, 0.05f);
        fontRenderer.drawInBatch(irp, -8, -8, i1, false, matrixStackIn.last().pose(), bufferIn, false, 0, 225);
        matrixStackIn.popPose();

        float f5 = -state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite().toYRot();
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f5));
        matrixStackIn.mulPose(Direction.DOWN.getRotation());
        matrixStackIn.translate(.075D, .27D, -.13D);
        matrixStackIn.scale(0.05f, 0.05f, 0.05f);
        fontRenderer.drawInBatch(irp, -8, -8, i1, false, matrixStackIn.last().pose(), bufferIn, false, 0, 225 /*combinedLightIn*/);
        matrixStackIn.popPose();
    }
}

package me.clumsycat.furnitureexpanded.renderer;

import me.clumsycat.furnitureexpanded.blocks.tileentities.ClockSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClockSignTileEntityRenderer implements BlockEntityRenderer<ClockSignTileEntity> {
    private final TextRenderer font;

    public ClockSignTileEntityRenderer(BlockEntityRendererFactory.Context rendererDispatcherIn) {
        super();
        this.font = rendererDispatcherIn.getTextRenderer();
    }

    @Override
    public void render(ClockSignTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = tileEntityIn.getCachedState();
        int i1 = 98255;
        OrderedText irp = Text.of(getTime(tileEntityIn.getWorld() != null ? tileEntityIn.getWorld().getTimeOfDay() : 0)).asOrderedText();

        float f4 = -state.get(HorizontalFacingBlock.FACING).asRotation();
        renderTask(f4, -.925D, irp, i1, matrixStackIn, bufferIn);

        float f5 = -state.get(HorizontalFacingBlock.FACING).getOpposite().asRotation();
        renderTask(f5, .075D, irp, i1, matrixStackIn, bufferIn);
    }

    private void renderTask(float rotation, double startingPoint, OrderedText irp, int i1, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(rotation));
        matrixStackIn.multiply(Direction.DOWN.getRotationQuaternion());
        matrixStackIn.translate(startingPoint, .27D, -.13D);
        matrixStackIn.scale(0.05f, 0.05f, 0.05f);
        this.font.draw(irp, -8, -8, i1, false, matrixStackIn.peek().getPositionMatrix(), bufferIn, TextRenderer.TextLayerType.NORMAL, 0, 225 /*combinedLightIn*/);
        matrixStackIn.pop();
    }

    private String getTime(long dayTime) {
        Calendar c = Calendar.getInstance();
        double ftime = (dayTime+6000)%24000;
        double ftd = ftime/1000;
        double mnt = ((ftd-Math.floor(ftd))*60);
        c.set(Calendar.HOUR_OF_DAY, (int) ftd);
        c.set(Calendar.MINUTE, (int) mnt);

        SimpleDateFormat sdf = new SimpleDateFormat("HH : mm");
        return sdf.format(c.getTime());
    }
}

package me.clumsycat.furnitureexpanded.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import me.clumsycat.furnitureexpanded.blocks.AlarmClock;
import me.clumsycat.furnitureexpanded.blocks.tileentities.AlarmClockTileEntity;
import me.clumsycat.furnitureexpanded.util.TimeUtils;
import me.clumsycat.furnitureexpanded.util.enums.FloorDepth;
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
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class AlarmClockTileEntityRenderer implements BlockEntityRenderer<AlarmClockTileEntity> {
    private final Font font;
    private static final double[][][] posIndex = {
            {{0.505D, 0.095D}, {0.625D, -0.2D}, {0.565D, -0.0525D}},
            {{0.465D, 0.095D}, {0.585D, -0.2D}, {0.525D, -0.0525D}},
            {{0.475D, 0.095D}, {0.595D, -0.2D}, {0.535D, -0.0525D}}
    };


    public AlarmClockTileEntityRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super();
        this.font = rendererDispatcherIn.getFont();
    }

    @Override
    public void render(AlarmClockTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState state = tileEntityIn.getBlockState();
        FormattedCharSequence fcs_hhmm = Component.literal(TimeUtils.convertDaytimeToHHmm(tileEntityIn.getLevel() != null ? tileEntityIn.getLevel().getDayTime() : 0)).getVisualOrderText();
        FormattedCharSequence fcs_alarm = Component.literal("\uD83D\uDD14").getVisualOrderText();
        FormattedCharSequence fcs_noise = Component.literal("\uD83D\uDD0A").getVisualOrderText();
        boolean powered = state.getValue(AlarmClock.isPowered);
        boolean alarmEnabled = false;
        boolean isNoisy = false;
        FloorDepth d = state.getValue(AlarmClock.depth);
        int depth = d == FloorDepth.FAR ? 0 : d == FloorDepth.NEAR ? 1 : 2;
        if (powered) {
            int i1 = 11546150;
            int brightness_alarm = alarmEnabled ? 255 : 70;
            int brightness_noise = isNoisy ? 255 : 70;

            float f4 = -state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot();
            Quaternionf axisYP = Axis.YP.rotationDegrees(f4);
            Quaternionf axisXP = Axis.XP.rotationDegrees(-22.5f);


            renderTask(matrixStackIn, bufferIn, axisYP, axisXP, fcs_hhmm, i1, 255,
                    new Vector3d(-0.925D, posIndex[0][depth][0], posIndex[0][depth][1]), new Vector3f(0.01f));

            renderTask(matrixStackIn, bufferIn, axisYP, axisXP, fcs_alarm, i1, brightness_alarm,
                    new Vector3d(-0.15D, posIndex[1][depth][0], posIndex[1][depth][1]), new Vector3f(0.005f));

            renderTask(matrixStackIn, bufferIn, axisYP, axisXP, fcs_noise, i1, brightness_noise,
                    new Vector3d(-0.28D, posIndex[2][depth][0], posIndex[2][depth][1]), new Vector3f(0.006f));
        }

    }

    private void renderTask(PoseStack matrixStackIn, MultiBufferSource bufferIn,
                            Quaternionf axisYP, Quaternionf axisXP,
                            FormattedCharSequence fcs, int i1, int brightness,
                            Vector3d translation, Vector3f scale) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        matrixStackIn.mulPose(axisYP);
        matrixStackIn.mulPose(axisXP);
        matrixStackIn.mulPose(Direction.DOWN.getRotation());
        matrixStackIn.translate(translation.x, translation.y, translation.z);
        matrixStackIn.scale(scale.x, scale.y, scale.z);
        this.font.drawInBatch(fcs, 70, -15, i1, false, matrixStackIn.last().pose(), bufferIn, Font.DisplayMode.NORMAL, 0, brightness);
        matrixStackIn.popPose();
    }
}

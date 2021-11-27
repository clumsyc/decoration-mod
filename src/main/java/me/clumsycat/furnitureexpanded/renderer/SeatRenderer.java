package me.clumsycat.furnitureexpanded.renderer;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class SeatRenderer extends EntityRenderer<SeatEntity> {
    private static final ResourceLocation _texture = new ResourceLocation(Expanded.MOD_ID, "");
    public SeatRenderer(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(SeatEntity entity) {
        return _texture;
    }

    @Override
    public boolean shouldRender(SeatEntity p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return false;
    }

    @Override
    protected boolean shouldShowName(SeatEntity p_177070_1_) {
        return false;
    }
}

package me.clumsycat.furnitureexpanded.renderer;

import me.clumsycat.furnitureexpanded.Expanded;
import me.clumsycat.furnitureexpanded.entities.SeatEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class SeatRenderer extends EntityRenderer<SeatEntity> {
    private static final Identifier _texture = new Identifier(Expanded.MOD_ID, "");
    public SeatRenderer(EntityRendererFactory.Context p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public Identifier getTexture(SeatEntity entity) {
        return _texture;
    }

    @Override
    public boolean shouldRender(SeatEntity p_225626_1_, Frustum p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return false;
    }

    @Override
    protected boolean hasLabel(SeatEntity p_177070_1_) {
        return false;
    }
}

package me.clumsycat.furnitureexpanded.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShowerParticles extends TextureSheetParticle {
    protected ShowerParticles(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);

        this.xd = 0.0F;
        this.yd = -0.1F;
        this.zd = 0.0F;
        this.setSize(0.1F, 0.1F);
        this.gravity = 0.3F;
        this.lifetime = 60;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.lifetime-- <= 0) this.remove();
        if (!this.removed) {
            this.yd *= 1.03F;
            if (Mth.randomBetweenInclusive(this.random, 0, 200) < 1) {
                double xoffset = 0.0D + Math.random() < 0.5 ? Mth.randomBetween(this.random, 0.1f, 0.4f) : Mth.randomBetween(this.random, -0.1f, -0.4f);
                double zoffset = 0.0D + Math.random() < 0.5 ? Mth.randomBetween(this.random, 0.1f, 0.4f) : Mth.randomBetween(this.random, -0.1f, -0.4f);
                this.level.addParticle(ParticleTypes.CLOUD, this.x + xoffset, this.y, this.z + zoffset, 0.0D, 0.0D, 0.0D);
            }
            if (this.onGround) {
                this.remove();
                this.level.addParticle(ParticleTypes.RAIN, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
            ShowerParticles showerParticles = new ShowerParticles(level, x, y, z);
            showerParticles.pickSprite(this.sprites);
            return showerParticles;
        }
    }
}

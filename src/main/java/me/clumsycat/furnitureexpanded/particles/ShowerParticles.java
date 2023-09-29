package me.clumsycat.furnitureexpanded.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ShowerParticles extends SpriteBillboardParticle {
    protected ShowerParticles(ClientWorld pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);

        this.velocityX = 0.0F;
        this.velocityY = -0.1F;
        this.velocityZ = 0.0F;
        this.setBoundingBoxSpacing(0.1F, 0.1F);
        this.gravityStrength = 0.3F;
        this.maxAge = 60;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.maxAge-- <= 0) this.markDead();
        if (!this.dead) {
            this.velocityY *= 1.03F;
            if (MathHelper.nextBetween(this.random, 0, 200) < 1) {
                double xoffset = 0.0D + Math.random() < 0.5 ? MathHelper.nextBetween(this.random, 0.1f, 0.4f) : MathHelper.nextBetween(this.random, -0.1f, -0.4f);
                double zoffset = 0.0D + Math.random() < 0.5 ? MathHelper.nextBetween(this.random, 0.1f, 0.4f) : MathHelper.nextBetween(this.random, -0.1f, -0.4f);
                this.world.addParticle(ParticleTypes.CLOUD, this.x + xoffset, this.y, this.z + zoffset, 0.0D, 0.0D, 0.0D);
            }
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(ParticleTypes.RAIN, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Provider implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;
        public Provider(SpriteProvider spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(DefaultParticleType particleType, ClientWorld level, double x, double y, double z, double dx, double dy, double dz) {
            ShowerParticles showerParticles = new ShowerParticles(level, x, y, z);
            showerParticles.setSprite(this.sprites);
            return showerParticles;
        }
    }
}

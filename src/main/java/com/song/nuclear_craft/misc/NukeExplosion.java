package com.song.nuclear_craft.misc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NukeExplosion extends Explosion {
    private static final ExplosionContext field_242407_a = new ExplosionContext();
    private final boolean causesFire;
    private final Explosion.Mode mode;
    private final Random random = new Random();
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity exploder;
    private final float size;
    private final List<BlockPos> affectedBlockPositions = Lists.newArrayList();
    private final Map<PlayerEntity, Vector3d> playerKnockbackMap = Maps.newHashMap();
    private final Vector3d position;

    public NukeExplosion(World worldIn, @Nullable Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, affectedPositions);
        this.world = worldIn;
        this.exploder = entityIn;
        this.size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        this.causesFire = true;
        this.mode = Mode.BREAK;
        this.position = new Vector3d(this.x, this.y, this.z);
    }

    public void addAffected(BlockPos blockPos){
        this.affectedBlockPositions.add(blockPos);
    }

    @Override
    public void doExplosionA() {
        float f2 = this.size * 2.0F;
        int k1 = MathHelper.floor(this.x - (double)f2 - 1.0D);
        int l1 = MathHelper.floor(this.x + (double)f2 + 1.0D);
        int i2 = MathHelper.floor(this.y - (double)f2 - 1.0D);
        int i1 = MathHelper.floor(this.y + (double)f2 + 1.0D);
        int j2 = MathHelper.floor(this.z - (double)f2 - 1.0D);
        int j1 = MathHelper.floor(this.z + (double)f2 + 1.0D);
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.world, this, list, f2);
        Vector3d vector3d = new Vector3d(this.x, this.y, this.z);

        for(int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = list.get(k2);
            if (!entity.isImmuneToExplosions()) {
                double d12 = (double)(MathHelper.sqrt(entity.getDistanceSq(vector3d)) / f2);
                if (d12 <= 1.0D) {
                    double d5 = entity.getPosX() - this.x;
                    double d7 = (entity instanceof TNTEntity ? entity.getPosY() : entity.getPosYEye()) - this.y;
                    double d9 = entity.getPosZ() - this.z;
                    double d13 = (double)MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d13 != 0.0D) {
                        d5 = d5 / d13;
                        d7 = d7 / d13;
                        d9 = d9 / d13;
                        double d14 = (double)getBlockDensity(vector3d, entity);
                        double d10 = (1.0D - d12) * d14;
                        entity.attackEntityFrom(this.getDamageSource(), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f2 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof LivingEntity) {
                            d11 = ProtectionEnchantment.getBlastDamageReduction((LivingEntity)entity, d10);
                        }

                        entity.setMotion(entity.getMotion().add(d5 * d11, d7 * d11, d9 * d11));
                        if (entity instanceof PlayerEntity) {
                            PlayerEntity playerentity = (PlayerEntity)entity;
                            if (!playerentity.isSpectator() && (!playerentity.isCreative() || !playerentity.abilities.isFlying)) {
                                this.playerKnockbackMap.put(playerentity, new Vector3d(d5 * d10, d7 * d10, d9 * d10));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void doExplosionB(boolean spawnParticles) {
        super.doExplosionB(spawnParticles);
    }
}

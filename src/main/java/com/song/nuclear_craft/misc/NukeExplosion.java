package com.song.nuclear_craft.misc;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NukeExplosion extends Explosion {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    private final boolean causesFire;
    private final Explosion.BlockInteraction mode;
    private final Random random = new Random();
    private final Level world;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity exploder;
    private final float size;
    private final List<BlockPos> affectedBlockPositions = Lists.newArrayList();
    private final Map<Player, Vec3> playerKnockbackMap = Maps.newHashMap();
    private final Vec3 position;

    public NukeExplosion(Level worldIn, @Nullable Entity entityIn, double x, double y, double z, float size, List<BlockPos> affectedPositions) {
        super(worldIn, entityIn,null,null,  x, y, z,  size,false, BlockInteraction.DESTROY);
        this.world = worldIn;
        this.exploder = entityIn;
        this.size = size;
        this.x = x;
        this.y = y;
        this.z = z;
        this.causesFire = false;
        this.mode = BlockInteraction.DESTROY;
        this.position = new Vec3(this.x, this.y, this.z);
        this.affectedBlockPositions.addAll(affectedPositions);
    }

    public void addAffected(BlockPos blockPos){
        this.affectedBlockPositions.add(blockPos);
    }

    @Override
    public void explode() {
        float f2 = this.size * 2.0F;
        int k1 = Mth.floor(this.x - (double)f2 - 1.0D);
        int l1 = Mth.floor(this.x + (double)f2 + 1.0D);
        int i2 = Mth.floor(this.y - (double)f2 - 1.0D);
        int i1 = Mth.floor(this.y + (double)f2 + 1.0D);
        int j2 = Mth.floor(this.z - (double)f2 - 1.0D);
        int j1 = Mth.floor(this.z + (double)f2 + 1.0D);
        List<Entity> list = this.world.getEntities(this.exploder, new AABB((double)k1, (double)i2, (double)j2, (double)l1, (double)i1, (double)j1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.world, this, list, f2);
        Vec3 vector3d = new Vec3(this.x, this.y, this.z);

        for(int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = list.get(k2);
            if (!entity.ignoreExplosion()) {
                double d12 = (double)(Mth.sqrt((float) entity.distanceToSqr(vector3d)) / f2);
                if (d12 <= 1.0D) {
                    double d5 = entity.getX() - this.x;
                    double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
                    double d9 = entity.getZ() - this.z;
                    double d13 = (double)Mth.sqrt((float) (d5 * d5 + d7 * d7 + d9 * d9));
                    if (d13 != 0.0D) {
                        d5 = d5 / d13;
                        d7 = d7 / d13;
                        d9 = d9 / d13;
                        double d14 = (double)getSeenPercent(vector3d, entity);
                        double d10 = (1.0D - d12) * d14;
                        entity.hurt(this.getDamageSource(), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f2 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof LivingEntity) {
                            d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity)entity, d10);
                        }

                        entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                        if (entity instanceof Player) {
                            Player playerentity = (Player)entity;
                            if (!playerentity.isSpectator() && (!playerentity.isCreative() || !playerentity.getAbilities().flying)) {
                                this.playerKnockbackMap.put(playerentity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void finalizeExplosion(boolean spawnParticles) {
        if (this.world.isClientSide) {
            this.world.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean flag = this.mode != Explosion.BlockInteraction.NONE;
        if (spawnParticles) {
            if (!(this.size < 2.0F) && flag) {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            } else {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
            }
        }

        if (flag) {
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
            Collections.shuffle(this.affectedBlockPositions, this.world.random);

            for(BlockPos blockpos : this.affectedBlockPositions) {
                BlockState blockstate = this.world.getBlockState(blockpos);
                Block block = blockstate.getBlock();
                if (!blockstate.isAir()) {
                    BlockPos blockpos1 = blockpos.immutable();
                    this.world.getProfiler().push("explosion_blocks");
                    if (blockstate.canDropFromExplosion(this.world, blockpos, this) && this.world instanceof ServerLevel) {
                        BlockEntity tileentity = blockstate.hasBlockEntity() ? this.world.getBlockEntity(blockpos) : null;
                        LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.world)).withRandom(this.world.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, tileentity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.exploder);
                        if (this.mode == Explosion.BlockInteraction.DESTROY) {
                            lootcontext$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.size);
                        }

                        blockstate.getDrops(lootcontext$builder).forEach((p_229977_2_) -> {
                            addBlockDrops(objectarraylist, p_229977_2_, blockpos1);
                        });
                    }

                    blockstate.onBlockExploded(this.world, blockpos, this);
                    this.world.getProfiler().pop();
                }
            }

            for(Pair<ItemStack, BlockPos> pair : objectarraylist) {
                Block.popResource(this.world, pair.getSecond(), pair.getFirst());
            }
        }

        if (this.causesFire) {
            for(BlockPos blockpos2 : this.affectedBlockPositions) {
                if (this.random.nextInt(3) == 0 && this.world.getBlockState(blockpos2).isAir() && this.world.getBlockState(blockpos2.below()).isSolidRender(this.world, blockpos2.below())) {
                    this.world.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.world, blockpos2));
                }
            }
        }

    }

    private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> p_229976_0_, ItemStack p_229976_1_, BlockPos p_229976_2_) {
        int i = p_229976_0_.size();

        for(int j = 0; j < i; ++j) {
            Pair<ItemStack, BlockPos> pair = p_229976_0_.get(j);
            ItemStack itemstack = pair.getFirst();
            if (ItemEntity.areMergable(itemstack, p_229976_1_)) {
                ItemStack itemstack1 = ItemEntity.merge(itemstack, p_229976_1_, 16);
                p_229976_0_.set(j, Pair.of(itemstack1, pair.getSecond()));
                if (p_229976_1_.isEmpty()) {
                    return;
                }
            }
        }

        p_229976_0_.add(Pair.of(p_229976_1_, p_229976_2_));
    }
}



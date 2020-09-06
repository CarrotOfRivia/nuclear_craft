package com.song.nuclear_craft.event;

import com.song.nuclear_craft.entities.AtomicBombEntity;
import com.song.nuclear_craft.misc.NukeExplosion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MyEventHandler {
    @SubscribeEvent
    public void explosion(ExplosionEvent explosionEvent){
    }

}

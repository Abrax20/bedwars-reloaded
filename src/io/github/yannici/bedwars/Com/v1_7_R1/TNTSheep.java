package io.github.yannici.bedwars.Com.v1_7_R1;

import java.lang.reflect.Field;
import java.util.ArrayList;

import io.github.yannici.bedwars.Main;
import io.github.yannici.bedwars.Shop.Specials.ITNTSheep;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftTNTPrimed;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import net.minecraft.server.v1_7_R1.AttributeInstance;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntitySheep;
import net.minecraft.server.v1_7_R1.GenericAttributes;
import net.minecraft.server.v1_7_R1.Navigation;
import net.minecraft.server.v1_7_R1.EntityTNTPrimed;

public class TNTSheep extends EntitySheep implements ITNTSheep {
	
	private World world = null;
	private TNTPrimed primedTnt = null;
	
	public TNTSheep(net.minecraft.server.v1_7_R1.World world) {
        super(world);
    }
	
	public TNTSheep(Location location, Player target) {
		super(((CraftWorld) location.getWorld()).getHandle());
		
		this.world = location.getWorld();
		this.locX = location.getX();
		this.locY = location.getY();
		this.locZ = location.getZ();
		
		try {
			Field b = this.goalSelector.getClass().getDeclaredField("b");
			b.setAccessible(true);
			b.set(this.goalSelector, new ArrayList<>());
			Field field = Navigation.class.getDeclaredField("e");
			field.setAccessible(true);
			AttributeInstance ai = (AttributeInstance) field.get(this.getNavigation());
			ai.setValue(128);
			this.getAttributeInstance(GenericAttributes.b).setValue(128D);
			this.getAttributeInstance(GenericAttributes.d).setValue(Main.getInstance().getConfig().getDouble("specials.tntsheep.speed", 0.4D));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.goalSelector.a(0, new PathfinderGoalBedwarsPlayer(this, EntityHuman.class, 1D, false));
		this.setTarget(((CraftEntity) target).getHandle());
		((Creature) this.getBukkitEntity()).setTarget((LivingEntity) target);
	}

	@Override
	public Location getLocation() {
		return new Location(this.world, this.locX, this.locY, this.locZ);
	}

	@Override
	public void setTNT(TNTPrimed tnt) {
		this.primedTnt = tnt;
	}

	@Override
	public TNTPrimed getTNT() {
		return this.primedTnt;
	}
	
	@Override
    public void setPassenger(TNTPrimed tnt) {
        this.getBukkitEntity().setPassenger(tnt);
    }
	
	@Override
    public void remove() {
        this.getBukkitEntity().remove();
    }
	
	@Override
    public void setTNTSource(Entity source) {
    	if(source == null) {
    		return;
    	}
    	
        try {
            Field sourceField = EntityTNTPrimed.class.getDeclaredField("source");
            sourceField.setAccessible(true);
            sourceField.set(((CraftTNTPrimed) this.primedTnt).getHandle(), ((CraftEntity) source).getHandle());
        } catch (Exception ex) {
            // didn't work
        }
    }

}

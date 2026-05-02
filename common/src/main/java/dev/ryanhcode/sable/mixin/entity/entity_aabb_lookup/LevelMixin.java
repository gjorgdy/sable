package dev.ryanhcode.sable.mixin.entity.entity_aabb_lookup;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.ryanhcode.sable.Sable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.function.Predicate;

@Mixin(Level.class)
public class LevelMixin {

	@Unique
	private Level sable$level = (Level) (Object) this;

	@WrapMethod(method = "getEntities(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;)Ljava/util/List;")
	public List<Entity> getEntities(final Entity entity, final AABB boundingBox, final Predicate<? super Entity> predicate, final Operation<List<Entity>> original) {
		final Vector3d minCorner = Sable.HELPER.projectOutOfSubLevel(this.sable$level, new Vector3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ));
		final Vector3d maxCorner = Sable.HELPER.projectOutOfSubLevel(this.sable$level, new Vector3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ));
		final AABB outOfSubLevelBoundingBox = new AABB(minCorner.x, minCorner.y, minCorner.z, maxCorner.x, maxCorner.y, maxCorner.z);
		return original.call(entity, outOfSubLevelBoundingBox, predicate);
	}

}

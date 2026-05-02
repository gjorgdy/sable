package dev.ryanhcode.sable.neoforge.mixin.compatibility.irons_spellbooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.ryanhcode.sable.Sable;
import io.redspace.ironsspellbooks.spells.nature.TouchDigSpell;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TouchDigSpell.class)
public class TouchDigSpellMixin {

	@WrapOperation(method = "onCast", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/BlockHitResult;getLocation()Lnet/minecraft/world/phys/Vec3;"))
	public Vec3 particleVec(final BlockHitResult instance, final Operation<Vec3> original, @Local(argsOnly = true) final Level level) {
		return Sable.HELPER.projectOutOfSubLevel(level, original.call(instance));
	}

}

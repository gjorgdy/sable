package dev.ryanhcode.sable.neoforge.mixin.compatibility.irons_spellbooks;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import dev.ryanhcode.sable.Sable;
import io.redspace.ironsspellbooks.block.portal_frame.PortalFrameBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PortalFrameBlock.class)
public class PortalFrameBlockMixin {

	@WrapOperation(method = "entityInside", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/VoxelShape;move(DDD)Lnet/minecraft/world/phys/shapes/VoxelShape;"))
	public VoxelShape onEntityInside(final VoxelShape instance, final double xOffset, final double yOffset, final double zOffset, final Operation<VoxelShape> original, @Local(argsOnly = true) final Level pLevel, @Local(argsOnly = true) final BlockPos pPos) {
		final var newPosVec = Sable.HELPER.projectOutOfSubLevel(pLevel, pPos.getBottomCenter());
		return original.call(instance, newPosVec.x, newPosVec.y, newPosVec.z);
	}

}

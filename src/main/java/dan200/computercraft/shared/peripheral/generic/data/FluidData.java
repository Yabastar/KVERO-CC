/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.shared.peripheral.generic.data;

import dan200.computercraft.shared.platform.Registries;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

public class FluidData {
    public static void fillBasic(Map<? super String, Object> data, FluidStack stack) {
        data.put("name", DataHelpers.getId(Registries.FLUIDS, stack.getFluid()));
        data.put("amount", stack.getAmount());
    }

    public static void fill(Map<? super String, Object> data, FluidStack stack) {
        // FluidStack doesn't have a getTags method, so we need to use the deprecated builtInRegistryHolder.
        @SuppressWarnings("deprecation")
        var holder = stack.getFluid().builtInRegistryHolder();
        data.put("tags", DataHelpers.getTags(holder));
    }
}

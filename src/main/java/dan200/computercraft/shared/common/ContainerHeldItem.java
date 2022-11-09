/*
 * This file is part of ComputerCraft - http://www.computercraft.info
 * Copyright Daniel Ratcliffe, 2011-2022. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */
package dan200.computercraft.shared.common;

import dan200.computercraft.shared.ModRegistry;
import dan200.computercraft.shared.network.container.HeldItemContainerData;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class ContainerHeldItem extends AbstractContainerMenu {
    private final ItemStack stack;
    private final InteractionHand hand;

    public ContainerHeldItem(MenuType<? extends ContainerHeldItem> type, int id, Player player, InteractionHand hand) {
        super(type, id);

        this.hand = hand;
        stack = player.getItemInHand(hand).copy();
    }

    public static ContainerHeldItem createPrintout(int id, Inventory inventory, HeldItemContainerData data) {
        return new ContainerHeldItem(ModRegistry.Menus.PRINTOUT.get(), id, inventory.player, data.getHand());
    }

    public ItemStack getStack() {
        return stack;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        if (!player.isAlive()) return false;

        var stack = player.getItemInHand(hand);
        return stack == this.stack || !stack.isEmpty() && !this.stack.isEmpty() && stack.getItem() == this.stack.getItem();
    }

    public static class Factory implements MenuProvider {
        private final MenuType<ContainerHeldItem> type;
        private final Component name;
        private final InteractionHand hand;

        public Factory(MenuType<ContainerHeldItem> type, ItemStack stack, InteractionHand hand) {
            this.type = type;
            name = stack.getHoverName();
            this.hand = hand;
        }

        @Override
        public Component getDisplayName() {
            return name;
        }

        @Nullable
        @Override
        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
            return new ContainerHeldItem(type, id, player, hand);
        }
    }
}

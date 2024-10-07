package ic2.advancedmachines.common;

import ic2.api.item.Items;
import ic2.api.recipe.Recipes;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileEntityRotaryMacerator extends TileEntityAdvancedMachine {
    public int supplementedItemsLeft = 0;
    private int currentResultCount;

    private int idIronDust;
    private int idCopperDust;
    private int idTinDust;
    private int idCoalDust;
    private int idWaterCell;
    private ItemStack bronzeDust;
    private ItemStack hydratedCoalDust;

    public TileEntityRotaryMacerator() {
        super("Rotary Macerator", "%5d RPM", 1, new int[]{1}, new int[]{2, 3});

        idIronDust = Items.getItem("ironDust").itemID;
        idCopperDust = Items.getItem("copperDust").itemID;
        idTinDust = Items.getItem("tinDust").itemID;
        idCoalDust = Items.getItem("coalDust").itemID;
        idWaterCell = Items.getItem("waterCell").itemID;
        bronzeDust = Items.getItem("bronzeDust");
        hydratedCoalDust = Items.getItem("hydratedCoalDust");
        this.upgradeSlots = new int[] {4, 5, 6, 7};
    }

    @Override
    protected boolean isStackValidForSlot(int slotSize, ItemStack itemstack, int blockSide) {
        if (blockSide == 1) {
            return getResultFor(itemstack, false) != null;
        }
        return isStackValidForSlot(slotSize, itemstack);
    }

    @Override
    public Container getGuiContainer(InventoryPlayer var1) {
        return new ContainerRotaryMacerator(var1, this);
    }

    @Override
    public ItemStack getResultFor(ItemStack macerated, boolean adjustOutput) {
        ItemStack result = (ItemStack) Recipes.macerator.getOutputFor(macerated, adjustOutput);
        ItemStack supplement = (inventory[8] != null) ? inventory[8].copy() : null;

        if (supplement != null) {
            if (supplementedItemsLeft > 0) {
                result = getSpecialResultFor(macerated, result, supplement, adjustOutput);
            } else {
                if (getSpecialResultFor(macerated, result, supplement, adjustOutput) != null) {
                    result = getSpecialResultFor(macerated, result, supplement, adjustOutput);
                    supplementedItemsLeft = currentResultCount;
                }
            }
        }

        return result;
    }

    @Override
    public void onFinishedProcessingItem() {
        if (supplementedItemsLeft != 0) {
            if (supplementedItemsLeft == 1) {
                inventory[8].stackSize--;
                if (inventory[8].stackSize == 0) {
                    inventory[8] = null;
                }
            }
            supplementedItemsLeft--;
        }

        super.onFinishedProcessingItem();
    }

    private ItemStack getSpecialResultFor(ItemStack original, ItemStack result, ItemStack supplement, boolean bool) {
        if (result != null && supplement != null) {
            ItemStack supplementOutput = (ItemStack) Recipes.macerator.getOutputFor(supplement, bool);

            if (result.itemID == this.idIronDust && supplement.itemID == Item.coal.itemID) {
                currentResultCount = 128;
                return new ItemStack(AdvancedMachines.refinedIronDust, result.stackSize);
            } else if (result.itemID == this.idCopperDust && supplementOutput != null && supplementOutput.itemID == idTinDust) {
                currentResultCount = 4;
                return new ItemStack(bronzeDust.getItem(), result.stackSize);
            } else if (result.itemID == this.idCoalDust && supplement.itemID == this.idWaterCell) {
                currentResultCount = 8;
                return hydratedCoalDust;
            }
        }

        return null;
    }

    @Override
    public int getUpgradeSlotsStartSlot() {
        return 4;
    }

    @Override
    public String getStartSoundFile() {
        return AdvancedMachines.advMaceSound;
    }

    @Override
    public String getInterruptSoundFile() {
        return AdvancedMachines.interruptSound;
    }
}

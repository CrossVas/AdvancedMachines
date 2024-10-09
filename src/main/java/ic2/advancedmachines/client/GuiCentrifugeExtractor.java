package ic2.advancedmachines.client;

import ic2.advancedmachines.common.AdvancedMachines;
import ic2.advancedmachines.common.ContainerCentrifugeExtractor;
import ic2.advancedmachines.common.TileEntityCentrifugeExtractor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import org.lwjgl.opengl.GL11;

public class GuiCentrifugeExtractor extends GuiContainer {
    public TileEntityCentrifugeExtractor tileentity;

    public GuiCentrifugeExtractor(InventoryPlayer var1, TileEntityCentrifugeExtractor var2) {
        super(new ContainerCentrifugeExtractor(var1, var2));
        this.tileentity = var2;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(AdvancedMachines.advExtcName, this.xSize / 2 - this.fontRenderer.getStringWidth(AdvancedMachines.advExtcName) / 2, 4, 4210752);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 4210752);
        this.fontRenderer.drawString("Speed:", 6, 36, 4210752);
        this.fontRenderer.drawString(this.tileentity.printFormattedData(), 10, 44, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        int var4 = this.mc.renderEngine.getTexture("/ic2/advancedmachines/client/sprites/GUICenterfuge.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(var4);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.ySize);
        int var7;
        if (this.tileentity.energy > 0) {
            var7 = this.tileentity.gaugeFuelScaled(14);
            this.drawTexturedModalRect(var5 + 56, var6 + 36 + 14 - var7, 176, 14 - var7, 14, var7);
        }

        var7 = this.tileentity.gaugeProgressScaled(24);
        this.drawTexturedModalRect(var5 + 79, var6 + 34, 176, 14, var7 + 1, 16);
    }
}

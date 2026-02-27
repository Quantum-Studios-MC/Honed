package quantumstudios.honed.te;

import com.cleanroommc.modularui.api.IGuiHolder;
import com.cleanroommc.modularui.api.IThemeApi;
import com.cleanroommc.modularui.api.drawable.IKey;
import com.cleanroommc.modularui.api.widget.IWidget;
import com.cleanroommc.modularui.drawable.GuiTextures;
import com.cleanroommc.modularui.factory.PosGuiData;
import com.cleanroommc.modularui.screen.ModularPanel;
import com.cleanroommc.modularui.screen.ModularScreen;
import com.cleanroommc.modularui.screen.UISettings;
import com.cleanroommc.modularui.value.sync.PanelSyncManager;
import com.cleanroommc.modularui.widgets.ButtonWidget;
import com.cleanroommc.modularui.widgets.TextWidget;
import com.cleanroommc.modularui.widgets.slot.ItemSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import quantumstudios.honed.Tags;
import quantumstudios.honed.gui.widget.WidgetForgingMinigame;

public class TileEntityForgingAnvil extends TileEntity implements ITickable, IGuiHolder<PosGuiData>, IItemHandlerModifiable {
    public ItemStack currentlyModifying = ItemStack.EMPTY;
    public MinigameContext minigame = null;
    public TileEntityForgingAnvil() {

    }

    public MinigameContext getMinigame() {
        return this.minigame;
    }
    public void acceptMinigameScore(float score) {

    }

    @Override
    public void update() {
        this.checkMinigameContextuality();
        if (this.minigame != null) {
            this.minigame.tick();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        if (!this.currentlyModifying.isEmpty()) {
            nbt.setTag("CurrentlyModifying", this.currentlyModifying.serializeNBT());
        }

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        if (nbt.hasKey("CurrentlyModifying", Constants.NBT.TAG_COMPOUND)) {
            this.currentlyModifying = new ItemStack(nbt.getCompoundTag("CurrentlyModifying"));
        } else {
            this.currentlyModifying = ItemStack.EMPTY;
        }
    }

    @Override
    public ModularPanel buildUI(PosGuiData posGuiData, PanelSyncManager panelSyncManager, UISettings uiSettings) {
        ModularPanel panel = ModularPanel.defaultPanel("forging_anvil");
        panel.bindPlayerInventory()
                .child(IKey.lang("tile.honed.forging_anvil.name").asWidget().top(4).left(7))
                .child(ItemSlot.create(false).slot(this, 0).leftRel(0.5F).topRelAnchor(0.25F, 0.5F))
                .child(new WidgetForgingMinigame<>(this::getMinigame).leftRel(0.5F).topRelAnchor(0.35F, 0.5F))
                .child(new ButtonWidget<>().onMouseTapped(button -> {
                    if (this.minigame != null) {
                        float result = this.minigame.moused();
                        return true;
                    }
                    return false;
                }).setEnabledIf(button -> this.minigame != null).leftRel(0.5F).topRelAnchor(0.44F, 0.5F));
        return panel;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModularScreen createScreen(PosGuiData data, ModularPanel mainPanel) {
        return new ModularScreen(Tags.MOD_ID, mainPanel);
    }

    private void checkMinigameContextuality() {
        if (this.minigame == null) {
            this.minigame = new MinigameContext();
            this.minigame.isPlaying = true;
            this.minigame.min = 25;
            this.minigame.max = 40;
            this.minigame.speed = 1;
        }
    }
    public static class MinigameContext {
        public boolean isPlaying = false;
        public int min, max;
        public int cursor; // 0 - 100
        public boolean direction = false;
        public int speed;
        public boolean wrap = false;

        public void tick() {
            if (this.direction) this.cursor -= this.speed;
            else this.cursor += this.speed;

            if (this.cursor >= 100) {
                if (this.wrap) this.cursor = 0;
                else {
                    this.cursor = 100;
                    this.direction = true;
                }
            }
            if (this.cursor < 0) {
                if (this.wrap) this.cursor = 100;
                else {
                    this.cursor = 0;
                    this.direction = false;
                }
            }
        }

        public float moused() {
            float middle = (this.min+this.max) * 0.5F;
            if (this.cursor == (int)middle) return 1.0F;
            float distMax = this.max - middle;
            float dist = this.max - this.cursor;
            if (dist > distMax) return 0.0F;
            return 1.0F-(dist / distMax);
        }
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        this.currentlyModifying = stack;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.currentlyModifying;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!this.currentlyModifying.isEmpty()) return stack;
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack copy = stack.copy();
        ItemStack split = copy.splitStack(1);
        if (simulate) return copy;
        this.currentlyModifying = split;
        this.markDirty();
        return copy;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) return ItemStack.EMPTY;
        ItemStack copy = this.currentlyModifying.copy();
        if (simulate) return copy;
        this.currentlyModifying = ItemStack.EMPTY;
        this.markDirty();
        return copy;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}

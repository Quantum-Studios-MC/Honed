package quantumstudios.honed.gui.widget;

import com.cleanroommc.modularui.drawable.GuiDraw;
import com.cleanroommc.modularui.screen.viewport.ModularGuiContext;
import com.cleanroommc.modularui.theme.WidgetThemeEntry;
import com.cleanroommc.modularui.widget.Widget;
import quantumstudios.honed.te.TileEntityForgingAnvil;

import java.util.function.Supplier;

public class WidgetForgingMinigame<W extends WidgetForgingMinigame<W>> extends Widget<W> {
    private final Supplier<TileEntityForgingAnvil.MinigameContext> minigameGetter;

    public WidgetForgingMinigame(Supplier<TileEntityForgingAnvil.MinigameContext> minigameGetter) {
        this.minigameGetter = minigameGetter;
    }

    @Override
    public void draw(ModularGuiContext context, WidgetThemeEntry<?> widgetTheme) {
        TileEntityForgingAnvil.MinigameContext ctx = this.minigameGetter.get();
        if (ctx != null && ctx.currentPass >= 0) {
            int w = this.getArea().width;
            int h = this.getArea().height;
            int zoneMin = (int) ((ctx.targetCenter - ctx.targetHalfWidth) * w);
            int zoneMax = (int) ((ctx.targetCenter + ctx.targetHalfWidth) * w);
            int cursor  = (int) (ctx.getClientBarPos() * w);
            GuiDraw.drawBorderOutsideXYWH(0, 0, w, h, 0xFF000000);
            GuiDraw.drawRect(0, 0, w, h, -1);
            GuiDraw.drawRect(zoneMin, 0, zoneMax - zoneMin, h, 0xFF88FF88);
            GuiDraw.drawRect(cursor, 0, 1, h, 0xFF000000);
        }
    }

    @Override
    public int getDefaultHeight() {
        return 8;
    }

    @Override
    public int getDefaultWidth() {
        return 100;
    }
}

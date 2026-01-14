package me.dw1e.ff.gui;

import me.dw1e.ff.FairFight;
import me.dw1e.ff.check.Check;
import me.dw1e.ff.check.api.Category;
import me.dw1e.ff.gui.impl.CheckGui;
import me.dw1e.ff.gui.impl.MainGui;
import me.dw1e.ff.gui.impl.TypeGui;
import org.bukkit.entity.HumanEntity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class GuiManager {

    private final Map<Category, Gui> typeGuis = new ConcurrentHashMap<>();
    private final Set<Gui> guis = new HashSet<>();
    private Gui mainGui, checkGui;

    public void enable() {
        List<Check> checks = FairFight.INSTANCE.getCheckManager().loadChecks(null);

        guis.add(mainGui = new MainGui());
        guis.add(checkGui = new CheckGui(checks));

        for (Category category : Category.values())
            typeGuis.put(category, new TypeGui(category.getName(), checks.stream()
                    .filter(check -> check.getCategory().equals(category))
                    .collect(Collectors.toList())
            ));

        guis.addAll(typeGuis.values());
    }

    public void disable() {
        guis.forEach(gui -> new ArrayList<>(gui.getInventory().getViewers()).forEach(HumanEntity::closeInventory));
        guis.clear();

        typeGuis.clear();

        mainGui = checkGui = null;
    }

    public Gui getMainGui() {
        return mainGui;
    }

    public Gui getCheckGui() {
        return checkGui;
    }

    public Gui getTypeGui(Category category) {
        return typeGuis.get(category);
    }
}
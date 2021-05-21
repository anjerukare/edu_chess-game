package edu.anjerukare.screens.utils;

import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import edu.anjerukare.screens.views.SideMenuView;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class SideMenuManager {

    private final Map<String, SideMenuView> sideMenuViews = new HashMap<>();
    private Cell<SideMenuView> cell;

    public void setViewsCell(Cell<SideMenuView> cell) {
        this.cell = cell;
    }

    public void addView(String name, SideMenuView sideMenuView) {
        sideMenuViews.put(name, sideMenuView);
    }

    public void pushView(String name) {
        if (cell == null)
            throw new RuntimeException("Cell isn't set");
        SideMenuView sideMenuView = sideMenuViews.get(name);
        if (sideMenuView == null)
            throw new NoSuchElementException("No view with name '" + name + "'");

        cell.setActor(sideMenuView);
    }

    public <T extends SideMenuView> T getView(String name) {
        SideMenuView sideMenuView = sideMenuViews.get(name);
        if (sideMenuView == null)
            throw new NoSuchElementException("No view with name '" + name + "'");
        return (T) sideMenuView;
    }
}

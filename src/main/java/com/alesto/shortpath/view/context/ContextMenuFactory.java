package com.alesto.shortpath.view.context;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;


/**
 * Created on 16.02.2016.
 */
public abstract class ContextMenuFactory {
    private static ContextMenu cellContext = new ContextMenu();
    private static MenuItem option1 = new MenuItem("Option1");
    private static MenuItem option2 = new MenuItem("Option2");
    private static MenuItem option3 = new MenuItem("Option33");


    public static ContextMenu getCellContext() {
        return cellContext;
    }
}

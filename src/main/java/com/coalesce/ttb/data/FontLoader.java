package com.coalesce.ttb.data;

import com.coalesce.plugin.CoModule;
import com.coalesce.plugin.CoPlugin;
import org.jetbrains.annotations.NotNull;

public class FontLoader extends CoModule {

    /**
     * Create a new module
     *
     * @param plugin The plugin that's creating this module
     * @param name   The name of this module
     */
    public FontLoader(@NotNull CoPlugin plugin, @NotNull String name) {
        super(plugin, name);
    }

    @Override
    protected void onEnable() throws Exception {

    }

    @Override
    protected void onDisable() throws Exception {
        //Do anything we need to do to save font data
    }

}

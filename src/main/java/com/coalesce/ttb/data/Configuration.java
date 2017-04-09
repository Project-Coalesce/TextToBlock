package com.coalesce.ttb.data;

import com.coalesce.plugin.CoPlugin;
import com.moandjiezana.toml.Toml;

import java.io.File;

/**
 * Created by NJDaeger on 4/9/2017.
 */
public class Configuration {
    
    private File fontfolder, path;
    
    public Configuration(CoPlugin plugin) {
        Toml toml = new Toml().
        this.fontfolder = new File("plugins" + File.separator + "TextToBlock" + File.separator + "Fonts");
        
    }
    
    public File[] getFonts() {
        return fontfolder.listFiles();
    }
    
}

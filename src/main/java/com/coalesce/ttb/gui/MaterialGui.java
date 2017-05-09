package com.coalesce.ttb.gui;

import com.coalesce.gui.PlayerGui;
import com.coalesce.plugin.CoPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.bukkit.ChatColor.*;

public class MaterialGui extends PlayerGui {

    private Material selection;
    private int currentPage;

    private static final int GUI_ROWS = 6;
    private static final int MATERIAL_ROWS = 5;
    private static ArrayList<Material> validBuildMaterials;

    public MaterialGui(CoPlugin plugin, Material selection) {
        super(plugin, GUI_ROWS * 9, DARK_GRAY + "Material Menu");

        this.selection = selection;
        this.currentPage = 0; //Page numbers start at 0
    }

    private void setMaterials(){

        if (validBuildMaterials.size() < MATERIAL_ROWS * 9 * currentPage){
            //Something is wrong, this should never happen
            return;
        }

        validBuildMaterials.stream().skip(MATERIAL_ROWS * 9 * currentPage).limit(MATERIAL_ROWS * 9)

    }

    private static List<Material> getValidBuildMaterials(){

        if (validBuildMaterials == null){

            validBuildMaterials = new ArrayList<>();

            //Add all materials that are blocks to the array
            final Inventory temp = Bukkit.createInventory(null, 9);
            Stream.of(org.bukkit.Material.values()).filter(material -> {

                if (material.isBlock()){

                    temp.setItem(0, new ItemStack(material));
                    if (temp.getItem(0) == null){
                        return false;
                    }
                }

                return true;

            }).forEach(validBuildMaterials::add);
        }

        return validBuildMaterials;
    }

}

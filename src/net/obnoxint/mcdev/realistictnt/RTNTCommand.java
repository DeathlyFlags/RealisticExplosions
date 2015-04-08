package net.obnoxint.mcdev.realistictnt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;






final class RTNTCommand
  implements TabExecutor
{
  private static final String C_WILDCARD = "*";
  private static final String C_ENABLE = "enable";
  private static final String C_DISABLE = "disable";
  private static final String C_LOAD = "loadConfig";
  private static final String C_SAVE = "saveConfig";
  private static final String C_MFB = "maxFallingBlocks";
  private static final String C_RLB = "removeLandedBlocks";
  private static final String C_ADDEB = "addExcludedBlock";
  private static final String C_ADDET = "addExcludedType";
  private static final String C_REMEB = "removeExcludedBlock";
  private static final String C_REMET = "removeExcludedType";
  private static final String NL = "\n";
  private static final Set<Material> BLOCK_MATERIALS = new HashSet();
  private static final Set<EntityType> EXPLODABLE_TYPES = new HashSet();
  private static final Map<String, EntityType> TYPE_NAMES = new HashMap();
  private final RealisticTNT plugin;
  
  static { for (Material m : Material.values()) {
      if (m.isBlock()) {
        BLOCK_MATERIALS.add(m);
      }
    }
    
    EXPLODABLE_TYPES.add(EntityType.CREEPER);
    EXPLODABLE_TYPES.add(EntityType.FIREBALL);
    EXPLODABLE_TYPES.add(EntityType.MINECART_TNT);
    EXPLODABLE_TYPES.add(EntityType.PLAYER);
    EXPLODABLE_TYPES.add(EntityType.PRIMED_TNT);
    EXPLODABLE_TYPES.add(EntityType.SMALL_FIREBALL);
    EXPLODABLE_TYPES.add(EntityType.UNKNOWN);
    EXPLODABLE_TYPES.add(EntityType.WEATHER);
    EXPLODABLE_TYPES.add(EntityType.WITHER);
    EXPLODABLE_TYPES.add(EntityType.WITHER_SKULL);
    
    for (EntityType t : EntityType.values()) {
      TYPE_NAMES.put(t.name(), t);
    }
  }
  
  private static final void addIfStartsWith(List<String> list, String start, String... strings) {
    for (int i = 0; i < strings.length; i++) {
      addIfStartsWith(list, strings[i], start);
    }
  }
  
  private static final void addIfStartsWith(List<String> list, String string, String start) {
    if ((string.startsWith(start)) && (!list.contains(string))) {
      list.add(string);
    }
  }
  
  private static SortedSet<String> getWorldNames() {
    SortedSet<String> r = new TreeSet();
    for (World w : Bukkit.getWorlds()) {
      r.add(w.getName());
    }
    r.add("*");
    return r;
  }
  

  private final RealisticTNTConfiguration config;
  RTNTCommand(RealisticTNT plugin)
  {
    this.plugin = plugin;
    this.config = plugin.getConfiguration();
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (((sender instanceof Player)) && (!sender.isOp()) && (!sender.hasPermission(this.plugin.getName()))) {
      return false;
    }
    
    int l = args.length;
    
    if ((l == 0) || (l == 1)) {
      StringBuilder sb = new StringBuilder();
      
      List<String> ew = this.config.getEnabledWorlds();
      if (l == 0)
      {
        sb.append(ChatColor.BOLD).append(this.plugin.getDescription().getFullName()).append(ChatColor.RESET).append("\n").append("\n");
        

        if (ew.isEmpty()) {
          sb.append("No worlds enabled. Type ").append(ChatColor.ITALIC).append("/rtnt worldName enable").append(ChatColor.RESET).append(" to enable a world.").append("\n").append("\n");
        }
        else
        {
          StringBuilder wb = new StringBuilder();
          for (int i = 0; i < ew.size(); i++) {
            wb.append((String)ew.get(i));
            if (i != ew.size() - 1) {
              wb.append(", ");
            }
          }
          sb.append("Enabled worlds: ").append(ChatColor.ITALIC).append(wb).append(ChatColor.RESET).append("\n").append("\n");
        }
        

        worldConfig(sb, null);

      }
      else if (args[0].equalsIgnoreCase("loadConfig")) {
        this.config.reload();
        sb.append("Loaded configuration from file.");
      } else if (args[0].equalsIgnoreCase("saveConfig")) {
        this.config.writeConfig();
        sb.append("Saved configuration to file.");
        this.config.save();
      } else if (this.config.getEnabledWorlds().contains(args[0])) {
        worldConfig(sb, args[0]);
      } else if (args[0].equals("*")) {
        worldConfig(sb, null);
      } else {
        sb.append("The world ").append(ChatColor.ITALIC).append(args[0]).append(ChatColor.RESET).append(" is not enabledor doesn't exist.");
      }
      


      sender.sendMessage(sb.toString().split("\n"));
      
      return true;
    }
    
    World w = Bukkit.getWorld(args[0]);
    
    if ((w == null) && (!args[0].equals("*"))) {
      sender.sendMessage("This world does not exist: " + args[0]);
      return true;
    }
    
    if (l == 2)
    {
      if ((args[1].equalsIgnoreCase("enable")) || (args[1].equalsIgnoreCase("disable"))) {
        this.config.setWorldEnabled(w, args[1].equalsIgnoreCase("enable"));
        return true;
      }
    }
    

    if (l == 3)
    {
      Integer p = null;
      try {
        p = Integer.valueOf(Integer.parseInt(args[2]));
      }
      catch (NumberFormatException e) {}
      String n = args[0].equals("*") ? null : args[0];
      Material m = Material.getMaterial(args[2].toUpperCase());
      EntityType e = (EntityType)TYPE_NAMES.get(args[2].toUpperCase());
      if ((args[1].equalsIgnoreCase("maxFallingBlocks")) && (p != null)) {
        this.config.setMaxFallingBlocks(n, p.intValue());
        sender.sendMessage("Spawn not more than " + ChatColor.ITALIC + this.config.getMaxFallingBlocks(n) + ChatColor.RESET + " falling blocks per explosion.");
        return true; }
      if ((args[1].equalsIgnoreCase("removeLandedBlocks")) && (p != null)) {
        this.config.setRemoveLandedBlocks(n, p.intValue());
        sender.sendMessage("Try to remove landed blocks each " + ChatColor.ITALIC + this.config.getRemoveLandedBlocks(n) + ChatColor.RESET + " seconds.");
        return true; }
      if ((args[1].equalsIgnoreCase("addExcludedBlock")) && (m != null)) {
        sender.sendMessage("Adding " + ChatColor.ITALIC + args[2] + ChatColor.RESET + " to list of excluded block types.");
        this.config.addExcludedBlock(n, m);
        return true; }
      if ((args[1].equalsIgnoreCase("addExcludedType")) && (e != null)) {
        sender.sendMessage("Adding " + ChatColor.ITALIC + args[2] + ChatColor.RESET + " to list of excluded explosion causes.");
        this.config.addExcludedType(n, e);
        return true; }
      if ((args[1].equalsIgnoreCase("removeExcludedBlock")) && (m != null)) {
        sender.sendMessage("Removing " + ChatColor.ITALIC + args[2] + ChatColor.RESET + " from list of excluded block types.");
        this.config.removeExcludedBlock(n, m);
        return true; }
      if ((args[1].equalsIgnoreCase("removeExcludedType")) && (e != null)) {
        sender.sendMessage("Removing " + ChatColor.ITALIC + args[2] + ChatColor.RESET + " from list of excluded explosion causes.");
        this.config.removeExcludedType(n, e);
        return true;
      }
    }
    

    return false;
  }
  
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
  {
    List<String> r = new ArrayList();
    int l = args.length;
    
    if (l == 0)
    {
      r = new ArrayList(getWorldNames());
    }
    else if (l == 1)
    {
      addIfStartsWith(r, args[0], new String[] { "loadConfig", "saveConfig" });
      
      Set<String> wn = getWorldNames();
      addIfStartsWith(r, args[0], (String[])wn.toArray(new String[wn.size()]));
    }
    else if (l == 2)
    {
      if (!args[0].equals("*")) {
        addIfStartsWith(r, args[1], new String[] { "enable", "disable" });
      }
      

      addIfStartsWith(r, args[1], new String[] { "maxFallingBlocks", "removeLandedBlocks", "addExcludedBlock", "addExcludedType", "removeExcludedBlock", "removeExcludedType" });
    }
    else if (l == 3)
    {
      if ((args[1].equals("maxFallingBlocks")) || (args[1].equals("removeLandedBlocks")))
      {
        r.add("0");
      }
      else if ((args[1].equals("addExcludedBlock")) || (args[1].equals("removeExcludedBlock")))
      {
        List<Material> ml = this.config.getExcludedBlocks(args[0].equals("*") ? null : args[0]);
        List<String> mn = new ArrayList();
        
        if (ml == null) {
          ml = new ArrayList();
        }
        
        if (args[1].equals("addExcludedBlock")) {
          for (Material m : BLOCK_MATERIALS) {
            if (!ml.contains(m)) {
              addIfStartsWith(r, m.name(), args[2].toUpperCase());
            }
          }
        } else if ((args[1].equals("removeExcludedBlock")) && (ml != null)) {
          for (Material m : ml) {
            addIfStartsWith(r, m.name(), args[2].toUpperCase());
          }
        }
        
        Collections.sort(mn);
        r.addAll(mn);
      }
      else if ((args[1].equals("addExcludedType")) || (args[1].equals("removeExcludedType")))
      {
        List<EntityType> tl = this.config.getExcludedTypes(args[0].equals("*") ? null : args[0]);
        List<String> tn = new ArrayList();
        
        if (tl == null) {
          tl = new ArrayList();
        }
        
        if (args[1].equals("addExcludedType")) {
          for (EntityType t : EXPLODABLE_TYPES) {
            if (!tl.contains(t.name())) {
              addIfStartsWith(tn, t.name(), args[2].toUpperCase());
            }
          }
        } else if ((args[1].equals("removeExcludedType")) && (tl != null)) {
          for (EntityType t : tl) {
            addIfStartsWith(tn, t.name(), args[2].toUpperCase());
          }
        }
        
        Collections.sort(tn);
        r.addAll(tn);
      }
    }
    

    return r;
  }
  
  private void worldConfig(StringBuilder sb, String worldName) {
    StringBuilder eb = new StringBuilder();
    for (Material m : this.config.getExcludedBlocks(worldName)) {
      eb.append(m.name()).append(" ");
    }
    
    StringBuilder et = new StringBuilder();
    for (EntityType t : this.config.getExcludedTypes(worldName)) {
      et.append(t.name()).append(" ");
    }
    
    sb.append(ChatColor.BOLD).append("Configuration of " + worldName + ":").append(ChatColor.RESET).append("\n").append(ChatColor.ITALIC).append("Maximum number of falling blocks per explosion: ").append(ChatColor.RESET).append(String.valueOf(this.config.getMaxFallingBlocks(worldName))).append("\n").append(ChatColor.ITALIC).append("Try to remove debris at this interval (seconds): ").append(ChatColor.RESET).append(String.valueOf(this.config.getRemoveLandedBlocks(worldName))).append("\n");
    








    if (eb.toString().isEmpty()) {
      sb.append(ChatColor.ITALIC).append("The list of excluded block types is empty.").append(ChatColor.RESET).append("\n");
    } else {
      sb.append(ChatColor.ITALIC).append("These blocks will not be affected:").append(ChatColor.RESET).append("\n").append(eb.toString()).append("\n");
    }
    

    if (et.toString().isEmpty()) {
      sb.append(ChatColor.ITALIC).append("The  list of excluded explosion causes is empty.").append(ChatColor.RESET).append("\n");
    } else {
      sb.append(ChatColor.ITALIC).append("Explosions caused by these entity types will be ignored:").append(ChatColor.RESET).append("\n").append(et.toString()).append("\n");
    }
  }
}


/* Location:              C:\Users\Lihtron\Downloads\RealisticTNT.jar!\net\obnoxint\mcdev\realistictnt\RTNTCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */
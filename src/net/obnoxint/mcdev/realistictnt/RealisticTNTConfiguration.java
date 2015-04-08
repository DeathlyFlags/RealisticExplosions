package net.obnoxint.mcdev.realistictnt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;



public final class RealisticTNTConfiguration
{
  private static final String CONFIG_KEY_ENABLEDWORLDS = "enabledWorlds";
  private static final String CONFIG_KEY_EXCLUDEDBLOCKS = "excludedBlocks";
  private static final String CONFIG_KEY_EXCLUDEDTYPES = "excludedTypes";
  private static final String CONFIG_KEY_MAXFALLINGBLOCKS = "maxFallingBlocks";
  private static final String CONFIG_KEY_REMOVELANDEDBLOCKS = "removeLandedBlocks";
  private static final Material[] EXCLUSION_HARD = { Material.AIR, Material.ANVIL, Material.BEACON, Material.BED_BLOCK, Material.BREWING_STAND, Material.BURNING_FURNACE, Material.CAKE_BLOCK, Material.CAULDRON, Material.CHEST, Material.COMMAND, Material.DISPENSER, Material.DOUBLE_PLANT, Material.DROPPER, Material.ENCHANTMENT_TABLE, Material.ENDER_CHEST, Material.ENDER_PORTAL, Material.ENDER_PORTAL_FRAME, Material.FLOWER_POT, Material.FURNACE, Material.HOPPER, Material.IRON_DOOR, Material.IRON_DOOR_BLOCK, Material.JUKEBOX, Material.LADDER, Material.LAVA, Material.LEVER, Material.LOCKED_CHEST, Material.MOB_SPAWNER, Material.MONSTER_EGGS, Material.NOTE_BLOCK, Material.PISTON_BASE, Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE, Material.PISTON_STICKY_BASE, Material.PORTAL, Material.SIGN_POST, Material.SKULL, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.STONE_BUTTON, Material.TNT, Material.TRAPPED_CHEST, Material.TRIPWIRE_HOOK, Material.WALL_SIGN, Material.WATER, Material.WOOD_BUTTON, Material.WOODEN_DOOR, Material.WORKBENCH };
  

















































  private static final Set<Material> mapHard = new HashSet();
  
  static {
    for (Material m : EXCLUSION_HARD) {
      mapHard.add(m);
    }
  }
  
  private static final String CONFIG_FILE = "plugins" + File.separator + "RealisticTNT" + File.separator + "config.yml";
  
  public static Set<Material> getHardExclusions() {
    return new HashSet(mapHard);
  }
  
  static List<String> fromMaterialList(List<Material> materials) {
    List<String> r = new ArrayList();
    for (Material m : materials) {
      if ((m != null) && (m.isBlock())) {
        r.add(m.name());
      }
    }
    return r;
  }
  
  static List<String> fromTypeList(List<EntityType> types) {
    List<String> r = new ArrayList();
    for (EntityType t : types) {
      if (t != null) {
        r.add(t.name());
      }
    }
    return r;
  }
  
  private static boolean isBlockMaterial(Material material) {
    return (material != null) && (material.isBlock());
  }
  
  private final List<String> enabledWorlds = new ArrayList();
  private final Map<String, List<Material>> excludedBlocks = new HashMap();
  private final Map<String, List<EntityType>> excludedTypes = new HashMap();
  private final Map<String, Integer> maxFallingBlocks = new HashMap();
  private final Map<String, Integer> removeLandedBlocks = new HashMap();
  private final FileConfiguration config;
  
  RealisticTNTConfiguration(FileConfiguration config)
  {
    this.config = config;
    

    this.config.setDefaults(new MemoryConfiguration());
    
    this.maxFallingBlocks.put(null, Integer.valueOf(0));
    this.removeLandedBlocks.put(null, Integer.valueOf(0));
  }
  
  public void addExcludedBlock(Material material) {
    addExcludedBlock((String)null, material);
  }
  
  public void addExcludedBlock(World world, Material material) {
    addExcludedBlock(world.getName(), material);
  }
  
  public void addExcludedBlocks(List<Material> materials) {
    addExcludedBlocks((String)null, materials);
  }
  
  public void addExcludedBlocks(World world, List<Material> materials) {
    addExcludedBlocks(world.getName(), materials);
  }
  
  public void addExcludedType(EntityType type) {
    addExcludedType((String)null, type);
  }
  
  public void addExcludedTypes(List<EntityType> types) {
    addExcludedTypes((String)null, types);
  }
  
  public void addExcludedTypes(World world, List<EntityType> types) {
    addExcludedTypes(world.getName(), types);
  }
  
  public List<String> getEnabledWorlds() {
    return new ArrayList(this.enabledWorlds);
  }
  
  public List<Material> getExcludedBlocks() {
    return getExcludedBlocks(null);
  }
  
  public int getMaxFallingBlocks() {
    return getMaxFallingBlocks((String)null);
  }
  
  public int getMaxFallingBlocks(World w) {
    return getMaxFallingBlocks(w.getName());
  }
  
  public int getRemoveLandedBlocks() {
    return getRemoveLandedBlocks((String)null);
  }
  
  public int getRemoveLandedBlocks(World w) {
    return getRemoveLandedBlocks(w.getName());
  }
  
  public void removeExcludedBlock(World world, Material material) {
    removeExcludedBlock(world.getName(), material);
  }
  
  public void removeExcludedBlocks(List<Material> materials) {
    removeExcludedBlocks((String)null, materials);
  }
  
  public void removeExcludedBlocks(World world, List<Material> materials) {
    removeExcludedBlocks(world.getName(), materials);
  }
  
  public void removeExcludedType(EntityType type) {
    removeExcludedType((String)null, type);
  }
  
  public void removeExcludedType(World world, EntityType type) {
    removeExcludedType(world.getName(), type);
  }
  
  public void removeExcludedTypes(List<EntityType> types) {
    removeExcludedTypes((String)null, types);
  }
  
  public void removeExcludedTypes(World world, List<EntityType> types) {
    removeExcludedTypes(world.getName(), types);
  }
  
  public void setExcludedBlocks(List<Material> materials) {
    setExcludedBlocks((String)null, materials);
  }
  
  public void setExcludedBlocks(World world, List<Material> materials) {
    setExcludedBlocks(world.getName(), materials);
  }
  
  public void setExcludedTypes(List<EntityType> types) {
    setExcludedTypes((String)null, types);
  }
  
  public void setExcludedTypes(World world, List<EntityType> types) {
    setExcludedTypes(world.getName(), types);
  }
  
  public void setMaxFallingBlocks(int maxFallingBlocks) {
    setMaxFallingBlocks(null, maxFallingBlocks);
  }
  
  public void setMaxFallingBlocks(String worldName, int blocks) {
    if ((blocks < 0) && (worldName != null)) {
      this.maxFallingBlocks.remove(worldName);
    } else {
      this.maxFallingBlocks.put(worldName, Integer.valueOf(blocks < 0 ? 0 : blocks));
    }
  }
  
  public void setRemoveLandedBlocks(int delay) {
    setRemoveLandedBlocks((String)null, delay);
  }
  
  public void setRemoveLandedBlocks(String worldName, int delay) {
    if ((delay < 0) && (worldName != null)) {
      this.removeLandedBlocks.remove(worldName);
    } else {
      this.removeLandedBlocks.put(worldName, Integer.valueOf(delay < 0 ? 0 : delay));
    }
  }
  
  public void setRemoveLandedBlocks(World world, int delay) {
    setRemoveLandedBlocks(world.getName(), delay);
  }
  
  public void setWorldEnabled(World world, boolean enable) {
    if (world != null) {
      setWorldEnabled(world.getName(), enable);
    }
  }
  
  void addExcludedBlock(String worldName, Material material) {
    if ((material != null) && (material.isBlock())) {
      List<Material> l = getExcludedBlocks(worldName);
      if (!l.contains(material)) {
        l.add(material);
        this.excludedBlocks.put(worldName, l);
      }
    }
  }
  
  void addExcludedBlocks(String worldName, List<Material> materials) {
    if ((materials != null) && (!materials.isEmpty())) {
      for (Material m : materials) {
        addExcludedBlock(worldName, m);
      }
    }
  }
  
  void addExcludedType(String worldName, EntityType type) {
    if (type != null) {
      List<EntityType> l = getExcludedTypes(worldName);
      if (!l.contains(type)) {
        l.add(type);
        this.excludedTypes.put(worldName, l);
      }
    }
  }
  
  void addExcludedTypes(String worldName, List<EntityType> types) {
    if ((types != null) && (!types.isEmpty())) {
      for (EntityType t : types) {
        addExcludedType(worldName, t);
      }
    }
  }
  
  List<Material> getExcludedBlocks(String worldName) {
    List<Material> r = (List)this.excludedBlocks.get(worldName);
    return r == null ? new ArrayList() : new ArrayList(r);
  }
  
  List<EntityType> getExcludedTypes(String worldName) {
    List<EntityType> r = (List)this.excludedTypes.get(worldName);
    return r == null ? new ArrayList() : new ArrayList(r);
  }
  
  int getMaxFallingBlocks(String worldName) {
    Integer r = (Integer)this.maxFallingBlocks.get(worldName);
    return (r == null) && (worldName != null) ? getMaxFallingBlocks() : r.intValue();
  }
  
  int getRemoveLandedBlocks(String worldName) {
    Integer r = (Integer)this.removeLandedBlocks.get(worldName);
    return (r == null) && (worldName != null) ? getRemoveLandedBlocks() : r.intValue();
  }
  
  boolean isExcluded(String worldName, Material material) {
    return (getHardExclusions().contains(material)) || (getExcludedBlocks().contains(material)) || (getExcludedBlocks(worldName).contains(material));
  }
  
  void load()
  {
    try
    {
      this.config.load(CONFIG_FILE);
    } catch (FileNotFoundException e) {
      RealisticTNT.getInstance().saveDefaultConfig();
      RealisticTNT.getInstance().getLogger().info("Hello and thanks for using Realistic TNT. Type \"rtnt <space> <tab>\" to get started.");
      load();
    } catch (Exception e) {
      RealisticTNT.getInstance().getLogger().severe("An error occured while loading the configuration file.");
      e.printStackTrace();
    }
  }
  
  void readConfig()
  {
    List<String> loc_ew = new ArrayList();
    Map<String, List<Material>> loc_eb = new HashMap();
    Map<String, List<EntityType>> loc_et = new HashMap();
    Map<String, Integer> loc_mfb = new HashMap();
    Map<String, Integer> loc_rlb = new HashMap();
    

    Map<String, ConfigurationSection> rem_s_ew = new HashMap();
    

    if ((this.config.get("enabledWorlds") instanceof List)) {
      RealisticTNT.getInstance().getLogger().info("RealisticTNT will update your configuration file.");
      List<String> l = this.config.getStringList("enabledWorlds");
      for (String s : l) {
        rem_s_ew.put(s, null);
      }
    } else {
      for (String s : this.config.getConfigurationSection("enabledWorlds").getKeys(false)) {
        rem_s_ew.put(s, this.config.getConfigurationSection("enabledWorlds").getConfigurationSection(s));
      }
    }
    
    List<String> rem_eb = this.config.getStringList("excludedBlocks");
    List<String> rem_et = this.config.getStringList("excludedTypes");
    Integer rem_mfb = Integer.valueOf(this.config.getInt("maxFallingBlocks", 0));
    Integer rem_rlb = Integer.valueOf(this.config.getInt("removeLandedBlocks", 0));
    

    for (String s : rem_s_ew.keySet()) {
      loc_ew.add(s);
      
      ConfigurationSection rem_s_ew_wc = (ConfigurationSection)rem_s_ew.get(s);
      if (rem_s_ew_wc != null)
      {
        if (rem_s_ew_wc.contains("excludedBlocks")) {
          loc_eb.put(s, new ArrayList());
          List<String> rem_s_ew_wc_eb = rem_s_ew_wc.getList("excludedBlocks");
          if (rem_s_ew_wc_eb != null) {
            for (String s1 : rem_s_ew_wc_eb) {
              Material m = Material.getMaterial(s1);
              if (isBlockMaterial(m)) {
                ((List)loc_eb.get(s)).add(m);
              }
            }
          }
        }
        
        if (rem_s_ew_wc.contains("excludedTypes")) {
          loc_et.put(s, new ArrayList());
          List<String> rem_s_ew_wc_et = rem_s_ew_wc.getList("excludedTypes");
          if (rem_s_ew_wc_et != null) {
            for (String s1 : rem_s_ew_wc_et) {
              EntityType t = EntityType.fromName(s1);
              if (t != null) {
                ((List)loc_et.get(s)).add(t);
              }
            }
          }
        }
        
        if (rem_s_ew_wc.contains("maxFallingBlocks")) {
          Integer v = (Integer)rem_s_ew_wc.get("maxFallingBlocks");
          loc_mfb.put(s, Integer.valueOf(v.intValue() < 0 ? 0 : v == null ? 0 : v.intValue()));
        }
        
        if (rem_s_ew_wc.contains("removeLandedBlocks")) {
          Integer v = (Integer)rem_s_ew_wc.get("removeLandedBlocks");
          loc_rlb.put(s, Integer.valueOf(v.intValue() < 0 ? 0 : v == null ? 0 : v.intValue()));
        }
      }
    }
    


    if (rem_eb != null) {
      loc_eb.put(null, new ArrayList());
      for (String s : rem_eb) {
        Material m = Material.getMaterial(s);
        if (isBlockMaterial(m)) {
          ((List)loc_eb.get(null)).add(m);
        }
      }
    }
    
    if (rem_et != null) {
      loc_et.put(null, new ArrayList());
      for (String s : rem_et) {
        EntityType t = EntityType.fromName(s);
        if (t != null) {
          ((List)loc_et.get(null)).add(t);
        }
      }
    }
    
    if (rem_mfb != null) {
      loc_mfb.put(null, Integer.valueOf(rem_mfb.intValue() < 0 ? 0 : rem_mfb.intValue()));
    }
    
    if (rem_rlb != null) {
      loc_rlb.put(null, Integer.valueOf(rem_rlb.intValue() < 0 ? 0 : rem_rlb.intValue()));
    }
    
    this.enabledWorlds.clear();
    this.enabledWorlds.addAll(loc_ew);
    
    this.excludedBlocks.clear();
    this.excludedBlocks.putAll(loc_eb);
    
    this.excludedTypes.clear();
    this.excludedTypes.putAll(loc_et);
    
    this.maxFallingBlocks.clear();
    this.maxFallingBlocks.putAll(loc_mfb);
    
    this.removeLandedBlocks.clear();
    this.removeLandedBlocks.putAll(loc_rlb);
  }
  
  void reload()
  {
    clear();
    load();
    readConfig();
  }
  
  void removeExcludedBlock(Material material) {
    removeExcludedBlock((String)null, material);
  }
  
  void removeExcludedBlock(String worldName, Material material) {
    if (material != null) {
      ((List)this.excludedBlocks.get(worldName)).remove(material);
    }
  }
  
  void removeExcludedBlocks(String worldName, List<Material> materials) {
    if (materials != null) {
      for (Material m : materials) {
        removeExcludedBlock(worldName, m);
      }
    }
  }
  
  void removeExcludedType(String worldName, EntityType type) {
    if (type != null) {
      ((List)this.excludedTypes.get(worldName)).remove(type);
    }
  }
  
  void removeExcludedTypes(String worldName, List<EntityType> types) {
    if (types != null) {
      for (EntityType t : types) {
        removeExcludedType(worldName, t);
      }
    }
  }
  
  void save() {
    try {
      this.config.save(CONFIG_FILE);
    } catch (IOException e) {
      RealisticTNT.getInstance().getLogger().warning("An error occured while saving the configuration file.");
      e.printStackTrace();
    }
  }
  
  void setExcludedBlocks(String worldName, List<Material> materials) {
    if (this.excludedBlocks.get(worldName) == null) {
      this.excludedBlocks.put(worldName, new ArrayList());
    }
    ((List)this.excludedBlocks.get(worldName)).clear();
    if (materials != null) {
      addExcludedBlocks(worldName, materials);
    }
  }
  
  void setExcludedTypes(String worldName, List<EntityType> types) {
    if (this.excludedTypes.get(worldName) == null) {
      this.excludedTypes.put(worldName, new ArrayList());
    }
    ((List)this.excludedTypes.get(worldName)).clear();
    if (types != null) {
      addExcludedTypes(worldName, types);
    }
  }
  
  void setWorldEnabled(String worldName, boolean enable)
  {
    if ((enable) && (!this.enabledWorlds.contains(worldName))) {
      this.enabledWorlds.add(worldName);
    } else if ((!enable) && (this.enabledWorlds.contains(worldName))) {
      this.enabledWorlds.remove(worldName);
    }
  }
  
  void writeConfig() {
    clear();
    
    Map<String, Map<String, Object>> rem_ew = new HashMap();
    
    for (String s : this.enabledWorlds) {
      Map<String, Object> rem_s_ew = new HashMap();
      if (this.excludedBlocks.containsKey(s)) {
        rem_s_ew.put("excludedBlocks", fromMaterialList((List)this.excludedBlocks.get(s)));
      }
      if (this.excludedTypes.containsKey(s)) {
        rem_s_ew.put("excludedTypes", fromTypeList((List)this.excludedTypes.get(s)));
      }
      if (this.maxFallingBlocks.containsKey(s)) {
        rem_s_ew.put("maxFallingBlocks", this.maxFallingBlocks.get(s));
      }
      if (this.removeLandedBlocks.containsKey(s)) {
        rem_s_ew.put("removeLandedBlocks", this.removeLandedBlocks.get(s));
      }
      rem_ew.put(s, rem_s_ew);
    }
    
    this.config.createSection("enabledWorlds", rem_ew);
    this.config.set("excludedBlocks", fromMaterialList((List)this.excludedBlocks.get(null)));
    this.config.set("excludedTypes", fromTypeList((List)this.excludedTypes.get(null)));
    this.config.set("maxFallingBlocks", this.maxFallingBlocks.get(null));
    this.config.set("removeLandedBlocks", this.removeLandedBlocks.get(null));
  }
  
  private void clear() {
    this.config.set("enabledWorlds", null);
    this.config.set("excludedBlocks", null);
    this.config.set("excludedTypes", null);
    this.config.set("maxFallingBlocks", null);
    this.config.set("removeLandedBlocks", null);
  }
}


/* Location:              C:\Users\Lihtron\Downloads\RealisticTNT.jar!\net\obnoxint\mcdev\realistictnt\RealisticTNTConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */
package net.obnoxint.mcdev.realistictnt;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public final class RealisticTNT extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener
{
  private static RealisticTNT instance = null;
  private RealisticTNTConfiguration configuration;
  
  public static RealisticTNT getInstance() { return instance; }
  
  private static void setInstance(RealisticTNT instance)
  {
    if ((instance == null) && (instance != null)) {
      instance = instance;
    }
  }
  

  public RealisticTNTConfiguration getConfiguration()
  {
    return this.configuration;
  }
  
  public void onDisable()
  {
    EntityExplodeEvent.getHandlerList().unregister(this);
    this.configuration.writeConfig();
    this.configuration.save();
    instance = null;
  }
  
  public void onEnable()
  {
    setInstance(this);
    this.configuration = new RealisticTNTConfiguration(getConfig());
    this.configuration.reload();
    getServer().getPluginManager().registerEvents(this, this);
    getCommand("rtnt").setExecutor(new RTNTCommand(this));
  }
  
  public void onLoad()
  {
    getDataFolder().mkdirs();
  }
  
  @org.bukkit.event.EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
  void onEntityExplode(EntityExplodeEvent event) {
    World w = event.getLocation().getWorld();
    if ((this.configuration.getEnabledWorlds().contains(w.getName())) && (!this.configuration.getExcludedTypes(w.getName()).contains(event.getEntityType())))
    {
      int maxFallingBlocks = this.configuration.getMaxFallingBlocks(w);
      int fallingBlocks = 0;
      List<Block> blocks = event.blockList();
      List<FallingBlock> falling = new ArrayList();
      for (Block b : blocks) {
        if ((!this.configuration.isExcluded(w.getName(), b.getType())) && ((maxFallingBlocks == 0) || (fallingBlocks <= maxFallingBlocks))) {
          FallingBlock fb = w.spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
          Vector v = new Vector().add(b.getLocation().toVector()).subtract(event.getEntity().getLocation().toVector()).multiply(1.0D / b.getLocation().distance(event.getEntity().getLocation()));
          


          fb.setVelocity(v);
          fb.setDropItem(false);
          if (!b.getType().equals(Material.AIR)) {
            fallingBlocks++;
            falling.add(fb);
          }
        }
        if (!b.getType().equals(Material.TNT)) {
          b.setType(Material.AIR);
        }
      }
      RLBTask.scheduleRLBTask(w, falling);
    }
  }
}


/* Location:              C:\Users\Lihtron\Downloads\RealisticTNT.jar!\net\obnoxint\mcdev\realistictnt\RealisticTNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */
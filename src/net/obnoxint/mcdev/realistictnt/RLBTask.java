package net.obnoxint.mcdev.realistictnt;

import java.util.List;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;

final class RLBTask extends org.bukkit.scheduler.BukkitRunnable
{
  private static final int MAX_ITERATIONS = 19;
  private static final int MAX_TRIES = 199;
  private static final long MAX_DELAY = 500L;
  private final World world;
  private final List<FallingBlock> blocks;
  private final int iteration;
  
  static void scheduleRLBTask(World world, List<FallingBlock> blocks)
  {
    int rlb = RealisticTNT.getInstance().getConfiguration().getRemoveLandedBlocks(world) * 20;
    if (rlb > 0) {
      new RLBTask(world, blocks, 0).runTaskLater(RealisticTNT.getInstance(), rlb);
    }
  }
  



  private RLBTask(World world, List<FallingBlock> blocks, int iteration)
  {
    this.world = world;
    this.blocks = blocks;
    this.iteration = iteration;
  }
  
  public void run()
  {
    List<FallingBlock> rm = new java.util.ArrayList();
    long ctm = System.currentTimeMillis();
    
    for (int i = 0; i <= 199; i++) {
      if (System.currentTimeMillis() - ctm > 500L) {
        break;
      }
      FallingBlock fb;
      try
      {
        fb = (FallingBlock)this.blocks.get(i++);
      }
      catch (IndexOutOfBoundsException e) {
        break;
      }
      if ((fb.getFallDistance() == 0.0F) && (fb.isOnGround()) && (fb.getMaterial().equals(fb.getLocation().getBlock().getType()))) {
        fb.getLocation().getBlock().setType(org.bukkit.Material.AIR);
        rm.add(fb);
      }
      
      if (fb.isDead()) {
        rm.add(fb);
      }
    }
    
    this.blocks.removeAll(rm);
    
    if ((!this.blocks.isEmpty()) && (this.iteration < 19)) {
      new RLBTask(this.world, this.blocks, this.iteration + 1).runTaskLater(RealisticTNT.getInstance(), RealisticTNT.getInstance().getConfiguration().getRemoveLandedBlocks(this.world) * 20);
    }
  }
}


/* Location:              C:\Users\Lihtron\Downloads\RealisticTNT.jar!\net\obnoxint\mcdev\realistictnt\RLBTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1-SNAPSHOT-20140817
 */
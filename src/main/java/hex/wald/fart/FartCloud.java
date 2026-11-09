package hex.wald.fart;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

final class FartCloud {

    private static final int PUFFS = 4;
    private static final Particle.DustTransition BROWN = new Particle.DustTransition(
            Color.fromRGB(120, 74, 44), Color.fromRGB(179, 125, 82), 0.95f);

    private final FartPlugin plugin;
    private final Map<UUID, BukkitTask> active = new HashMap<>();

    FartCloud(FartPlugin plugin) {
        this.plugin = plugin;
    }

    void show(Player player, List<Player> listeners, double radiusSquared) {
        stop(player);
        World world = player.getWorld();

        BukkitTask task = new BukkitRunnable() {
            private int puffs = PUFFS;

            @Override
            public void run() {
                if (!player.isOnline() || player.isDead() || !player.getWorld().equals(world)) {
                    stop(player);
                    return;
                }

                Location origin = player.getLocation();
                Location smoke = origin.clone();
                smoke.setPitch(0);
                double distance = player.getWidth() * 0.7 + (PUFFS - puffs) * 0.07;
                smoke.subtract(smoke.getDirection().multiply(distance)).add(0, player.getHeight() * 0.5, 0);

                for (Player listener : listeners) {
                    if (!listener.isOnline() || !listener.getWorld().equals(world) || !plugin.canReceiveFart(listener, player)
                            || listener.getLocation().distanceSquared(origin) > radiusSquared) {
                        continue;
                    }

                    listener.spawnParticle(Particle.DUST_COLOR_TRANSITION, smoke, 3,
                            0.08, 0.06, 0.08, 0, BROWN);
                }

                if (--puffs == 0) {
                    stop(player);
                }
            }
        }.runTaskTimer(plugin, 0, 2);
        active.put(player.getUniqueId(), task);
    }

    void stop(Player player) {
        BukkitTask task = active.remove(player.getUniqueId());
        if (task != null) {
            task.cancel();
        }
    }
}

package me.sky.boss.skills;

import com.google.common.collect.Iterables;
import me.sky.boss.IBossPlugin;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.mineacademy.boss.api.*;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.event.BossDeathEvent;
import org.mineacademy.boss.api.event.BossSpawnEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SkillListener implements Listener {
    private final IBossPlugin plugin;

    public SkillListener(IBossPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void entitySpawn(EntitySpawnEvent event) {
        if (BossAPI.isBoss(event.getEntity())) {
            return;
        }
        SkillTypes skillType = SkillTypes.getSkillType(event.getEntityType());
        if (skillType == null) {
            return;
        }
        Boss boss = BossAPI.getBoss(skillType.name().toLowerCase());
        if (boss == null) {
            return;
        }
        event.setCancelled(true);
        SpawnedBoss spawnedBoss = boss.spawn(event.getLocation(), BossSpawnReason.CUSTOM);
        spawnedBoss.getEntity().setCustomName(null);
        spawnedBoss.getEntity().setCustomNameVisible(false);
    }

    @EventHandler
    public void bossSpawn(BossSpawnEvent event) {
        Boss boss = event.getBoss();
        if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_SKELETON))) {
            plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_SKELETON).executeSkill(boss, event.getEntity());
        } else if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_ZOMBIE))) {
            double rand = Math.random() * 100;
            if (rand <= 4) {
                event.getEntity().getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
                event.getEntity().getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            } else if (rand <= 50) {
                event.getEntity().getEquipment().setItemInMainHand(new ItemStack(Material.STICK));
            } else if (rand <= 70) {
                event.getEntity().getEquipment().setItemInMainHand(new ItemStack(Material.WOODEN_SWORD));
                event.getEntity().getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0));
            }
        } else if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_CREEPER)) && event.getEntity() instanceof Creeper) {
            if (Math.random() * 100 <= 15) {
                Creeper creeper = (Creeper) event.getEntity();
                creeper.setPowered(true);
                event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
            }
        } else if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_SPIDER_JOCKEY)) && event.getEntity() instanceof Spider) {
            Boss skelBoss = null;
            for (Boss b : BossAPI.getBosses()) {
                if (b.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_SKELETON))) {
                    skelBoss = b;
                    break;
                }
            }
            if (skelBoss == null) {
                plugin.getLogger().info("Couldn't find a boss with the following skill -> 'Custom Skeleton Boss' for Spider Jockey.");
                return;
            }
            SpawnedBoss spawnedBoss = skelBoss.spawn(event.getEntity().getLocation(), BossSpawnReason.CUSTOM);
            event.getEntity().addPassenger(spawnedBoss.getEntity());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void pigDeath(BossDeathEvent event) {
        Boss boss = event.getBoss();
        if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_PIG))) {
            event.getDrops().add(new BossDrop(new ItemStack(Material.REDSTONE), 100));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void customDropDeath(EntityDeathEvent event) {
        if (!BossAPI.isBoss(event.getEntity())) {
            return;
        }
        Boss boss = BossAPI.getBoss(event.getEntity());
        if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_PIG))) {
            event.setDroppedExp((int) Math.round(event.getDroppedExp() * 2.5D));
            event.getDrops().forEach(itemStack -> {
                if (itemStack.getType() == Material.GOLD_INGOT) {
                    itemStack.setAmount((int) Math.round(itemStack.getAmount() * 1.4));
                }
            });
        } else if (boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_WITHER_SKELETON))) {
            for (ItemStack itemStack : event.getDrops()) {
                if (itemStack.getType() == Material.WITHER_SKELETON_SKULL) {
                    return;
                }
            }
            if (Math.random() * 100 <= 10) {
                event.getDrops().add(new ItemStack(Material.WITHER_SKELETON_SKULL));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void customEnderman(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        if (!BossAPI.isBoss(event.getDamager())) {
            return;
        }
        Boss boss = BossAPI.getBoss(event.getDamager());
        if (!boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_ENDERMAN)) || event.getDamager() instanceof Enderman) {
            return;
        }
        Player player = (Player) event.getEntity();
        Enderman entity = (Enderman) event.getDamager();
        if (Math.random() * 100 <= 5 + Math.random() * 10) {
            List<ItemStack> items = new ArrayList<>();
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null && itemStack.getType() != Material.AIR) {
                    items.add(itemStack);
                }
            }
            Collections.shuffle(items);
            ItemStack item = Iterables.getFirst(items, null);
            if (item != null) {
                entity.getWorld().dropItemNaturally(entity.getLocation(), item.clone());
                item.setAmount(0);
                player.sendMessage(plugin.getMessages().getMessage("EndermanSteal"));
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void customZombieStickHit(EntityDamageByEntityEvent event) {
        if (!BossAPI.isBoss(event.getDamager())) {
            return;
        }
        Boss boss = BossAPI.getBoss(event.getDamager());
        if (!boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_ZOMBIE))) {
            return;
        }
        LivingEntity entity = (LivingEntity) event.getDamager();
        if (entity.getEquipment().getItemInMainHand().getType() != Material.STICK) {
            return;
        }
        event.getEntity().setVelocity(event.getEntity().getVelocity().add(new Vector(0, 2 + (Math.random() * 3), 0)));
    }

    @EventHandler(ignoreCancelled = true)
    public void customZombieSkill(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity) || !(event.getDamager() instanceof LivingEntity)) {
            return;
        }
        if (!BossAPI.isBoss(event.getDamager()) && !BossAPI.isBoss(event.getEntity())) {
            return;
        }
        LivingEntity target = (LivingEntity) (BossAPI.isBoss(event.getDamager()) ? event.getEntity() : event.getDamager());
        LivingEntity entity = (LivingEntity) (target.equals(event.getEntity()) ? event.getDamager() : event.getEntity());
        Boss boss = BossAPI.getBoss(entity);
        if (!boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_ZOMBIE))) {
            return;
        }
        double chance = target.equals(event.getDamager()) ? 70 : 30;
        executeFoodPoisoning(boss, target, entity, chance);
    }

    private void executeFoodPoisoning(Boss boss, LivingEntity target, LivingEntity entity, double chance) {
        if (!boss.getSkills().contains(plugin.getSkillManager().getSkillSet().get(SkillTypes.CUSTOM_ZOMBIE))) {
            return;
        }
        if (entity.getHealth() >=
                entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
            return;
        }
        if (Math.random() * 100 <= chance) {
            target.addPotionEffect(
                    new PotionEffect(PotionEffectType.HUNGER, 20 * 4, 0));
        }
    }
}

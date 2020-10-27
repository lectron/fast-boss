package me.sky.boss.skills.types;

import me.sky.boss.IBossPlugin;
import me.sky.boss.skills.CustomBossSkill;
import me.sky.boss.skills.utils.ChanceObject;
import me.sky.boss.utils.DropSet;
import me.sky.boss.utils.SkillUtils;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.boss.api.Boss;
import org.mineacademy.boss.api.BossSkillDelay;
import org.mineacademy.boss.api.SpawnedBoss;

import java.util.*;

public class AdvancedSkeleton extends CustomBossSkill {

    private final IBossPlugin plugin;
    private final List<DropSet> dropSets = new ArrayList<>();

    public AdvancedSkeleton(IBossPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "Advanced Armor Set";
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Material.DIAMOND_CHESTPLATE);
    }

    @Override
    public BossSkillDelay getDefaultDelay() {
        return new BossSkillDelay(Integer.MAX_VALUE);
    }

    @Override
    public String[] getDefaultMessage() {
        return new String[0];
    }

    @Override
    public boolean execute(SpawnedBoss spawnedBoss) {
        return false;
    }

    @Override
    public void executeSkill(Boss boss, LivingEntity livingEntity) {
        if (dropSets.isEmpty()) {
            return;
        }
        Collections.shuffle(dropSets);
        DropSet dropSet = dropSets.get(0);
        if (dropSet.getMainHand() != null && Math.random() * 100 <= dropSet.getMainHand().getChance()) {
            livingEntity.getEquipment().setItemInMainHand(new ItemStack(dropSet.getMainHand().getObject()));
        }
        if (dropSet.getOffHand() != null && Math.random() * 100 <= dropSet.getOffHand().getChance()) {
            livingEntity.getEquipment().setItemInOffHand(new ItemStack(dropSet.getOffHand().getObject()));
        }
        if (dropSet.getArmorContents() != null) {
            int i = 0;
            for (ChanceObject<Material> armor : dropSet.getArmorContents()) {
                if (armor != null && Math.random() * 100 <= armor.getChance()) {
                    livingEntity.getEquipment().getArmorContents()[i] = new ItemStack(armor.getObject());
                }
                i++;
            }
        }
    }

    @Override
    public void readSettings(Map<String, Object> map1) {
        for (String d : map1.keySet()) {
            try {
                Map<String, Object> map = (Map<String, Object>) map1.get(d);
                ChanceObject<Material> mainHand = null;
                ChanceObject<Material> offHand = null;
                ChanceObject<Material>[] armorContents = null;
                if (map.containsKey("MainHand")) {
                    String[] data = ((String) map.get("MainHand")).split(":");
                    mainHand = SkillUtils.readChanceObject(Material.valueOf(data[0]), data[1]);
                }
                if (map.containsKey("OffHand")) {
                    String[] data = ((String) map.get("OffHand")).split(":");
                    offHand = SkillUtils.readChanceObject(Material.valueOf(data[0]), data[1]);
                }
                if (map.containsKey("ArmorContents")) {
                    armorContents = new ChanceObject[4];
                    int i = 0;
                    for (String s : (List<String>) map.get("ArmorContents")) {
                        if (s.equalsIgnoreCase("none")) {
                            continue;
                        }
                        String[] data = s.split(":");
                        armorContents[i] = SkillUtils.readChanceObject(Material.valueOf(data[0]), data[1]);
                        i++;
                    }
                }
                dropSets.add(new DropSet(mainHand, offHand, armorContents));
            } catch (Exception e) {
            }
        }
    }

    @Override
    public Map<String, Object> writeSettings() {
        final Map<String, Object> map1 = new HashMap<>();
        final Map<String, Object> map = new HashMap<>();
        map.put("MainHand", String.format("%s:%s", Material.DIAMOND_SWORD.name(), "4%"));
        map.put("OffHand", String.format("%s:%s", Material.SHIELD.name(), "2%"));
        map.put("ArmorContents", new String[]{
                String.format("%s:%s", Material.DIAMOND_HELMET.name(), "2%"),
                String.format("%s:%s", Material.DIAMOND_CHESTPLATE.name(), "2%"),
                "none",
                "none"
        });
        map1.put("DropSet-1", map);
        return map1;
    }
}

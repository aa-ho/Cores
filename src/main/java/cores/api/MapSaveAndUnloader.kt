package cores.api

import cores.config.Paths.GAME_CONFIG
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.EntityType
import java.io.File

class MapSaveAndUnloader {

    data class BlockData(val x: Int, val y: Int, val z: Int, val material: Material)
    data class EntityData(val location: Location, val type: EntityType)

    private var loc1: Location = Location(Bukkit.getWorld("Cores"), -32.0, 0.0, -24.0)
    private var loc2: Location = Location(Bukkit.getWorld("Cores"), 25.0, 350.0, 16.0)
    private val savedBlocks = mutableListOf<BlockData>()
    private val savedEntities = mutableListOf<EntityData>()

    fun save() {
        Messages.sendConsole("§aSaving  map..", true)
            val world = loc1.world ?: return

            savedBlocks.clear()
            savedEntities.clear()

            val minX = minOf(loc1.blockX, loc2.blockX)
            val maxX = maxOf(loc1.blockX, loc2.blockX)
            val minY = minOf(loc1.blockY, loc2.blockY)
            val maxY = maxOf(loc1.blockY, loc2.blockY)
            val minZ = minOf(loc1.blockZ, loc2.blockZ)
            val maxZ = maxOf(loc1.blockZ, loc2.blockZ)

            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    for (z in minZ..maxZ) {
                        val block = world.getBlockAt(x, y, z)
                        if (block.type != Material.AIR) {
                            savedBlocks.add(BlockData(x, y, z, block.type))
                        }
                    }
                }
            }

            val entities = world.getNearbyEntities(
                loc1,
                (maxX - minX).toDouble(),
                (maxY - minY).toDouble(),
                (maxZ - minZ).toDouble()
            )
            entities.forEach { entity ->
                savedEntities.add(EntityData(entity.location, entity.type))
                entity.remove()
            }

            saveToFile()
            Messages.sendConsole("§aSave map", true)
    }

    fun reset() {
        val world = loc1.world ?: return

        // Reset blocks
        val minX = minOf(loc1.blockX, loc2.blockX)
        val maxX = maxOf(loc1.blockX, loc2.blockX)
        val minY = minOf(loc1.blockY, loc2.blockY)
        val maxY = maxOf(loc1.blockY, loc2.blockY)
        val minZ = minOf(loc1.blockZ, loc2.blockZ)
        val maxZ = maxOf(loc1.blockZ, loc2.blockZ)

        for (x in minX..maxX) {
            for (y in minY..maxY) {
                for (z in minZ..maxZ) {
                    world.getBlockAt(x, y, z).type = Material.AIR
                }
            }
        }

        savedBlocks.forEach { data ->
            world.getBlockAt(data.x, data.y, data.z).type = data.material
        }

        // Reset entities
        savedEntities.forEach { data ->
            world.spawnEntity(data.location, data.type)
        }
        Messages.sendConsole("§cMap reset", true)
    }

    private fun saveToFile() {
        val mapsFile = File(GAME_CONFIG, "maps.yml")
        val mapsYml: FileConfiguration = YamlConfiguration.loadConfiguration(mapsFile)

        val blockList = savedBlocks.map { mapOf("x" to it.x, "y" to it.y, "z" to it.z, "type" to it.material.name) }
        mapsYml.set("blocks", blockList)

        val entityList = savedEntities.map {
            mapOf("location" to mapOf("world" to it.location.world?.name, "x" to it.location.x, "y" to it.location.y, "z" to it.location.z), "type" to it.type.name)
        }
        mapsYml.set("entities", entityList)

        mapsYml.save(mapsFile)
        Messages.sendConsole("§aMap saved!", true)
    }

}

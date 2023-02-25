package cores.api

import cores.Main.Companion.gameStateManager
import cores.api.GlobalConst.LEAVE_GAME_ITEM
import cores.api.GlobalConst.PERMISSION_BYPASS
import cores.api.GlobalConst.STAR_GAME_ITEM
import cores.api.GlobalConst.TEAM_SELECTOR_ITEM
import cores.api.GlobalConst.arrowItems
import cores.api.GlobalConst.bowItem
import cores.api.GlobalConst.goldenAppleItem
import cores.api.GlobalConst.iron_axe
import cores.api.GlobalConst.iron_pickage
import cores.api.GlobalConst.swordItem
import cores.api.GlobalConst.woodItems
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.KICK_LEAVE_ITEM
import cores.gameStates.GameStates
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit
import kotlin.math.cos
import kotlin.math.sin

object ImportantFunctions {

    fun playSoundForAll(sound: Sound, volume: Float = 1.0F, pitch: Float = 1.0F) {
        Bukkit.getOnlinePlayers().forEach {
            it.playSound(it.location, sound, volume, pitch)
        }
    }

    fun sendTitleForAll(title: String, fadeIn: Int, stay: Int, fadeOut: Int, subTitle: String = "") {
        Bukkit.getOnlinePlayers().forEach {
            it.sendTitle(title, subTitle, fadeIn, stay, fadeOut)
        }
    }

    fun setLevelAll(level: Int) {
        Bukkit.getOnlinePlayers().forEach {
            it.level = level
        }
    }

    fun playTimeReminderSoundToAll() {
        playSoundForAll(Sound.ITEM_GOAT_HORN_SOUND_3)
    }

    fun teleportAll(loc: Location) {
        Bukkit.getOnlinePlayers().forEach {
            it.teleport(loc)
        }
    }

    fun clearAll() {
        Bukkit.getOnlinePlayers().forEach {
            it.inventory.clear()
        }
    }

    fun setLobbyInventoryAndPrivileges(p: Player) {
        p.inventory.setItem(8, LEAVE_GAME_ITEM)
        p.inventory.setItem(4, TEAM_SELECTOR_ITEM)
        if (p.hasPermission(PERMISSION_BYPASS)) {
            p.inventory.setItem(0, STAR_GAME_ITEM)
            //val laser = Laser.GuardianLaser(GlobalConst.LOBBY_SPAWN_LOCATION, GlobalConst.LOBBY_SPAWN_LOCATION, 5, 10)
            //laser.attachEndEntity(p)
        }
    }

    fun enchantStartItem() {
        Bukkit.getOnlinePlayers().forEach {
            if (it.hasPermission(PERMISSION_BYPASS)) it.inventory.getItem(0)?.addUnsafeEnchantment(Enchantment.LUCK, 1)
        }
    }

    fun disEnchantStartItem() {
        Bukkit.getOnlinePlayers().forEach {
            if (it.hasPermission(PERMISSION_BYPASS)) it.inventory.getItem(0)?.removeEnchantment(Enchantment.LUCK)
        }
    }

    fun skipCountdown(p: Player) {
        if (gameStateManager.getCurrentGameState() == GameStates.LOBBY_STATE) {
            if (gameStateManager.lobbyState.lobbyCountdown.isIdling && gameStateManager.lobbyState.lobbyCountdown.seconds <= GlobalConst.LOBBY_COUNTDOWN_SKIP_SECONDS) {
                Messages.sendPlayerLobbyCountdownNotSkippable(p)
            } else if (PLAYERS.size < GlobalConst.MIN_PLAYERS) {
                Messages.sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers(p)
            } else {
                gameStateManager.lobbyState.lobbyCountdown.seconds =
                    GlobalConst.LOBBY_COUNTDOWN_SKIP_SECONDS
                Messages.sendAllLobbyCountdownSkipped(p)
                p.playSound(p.location, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F)
                disEnchantStartItem()
            }
        } else {
            Messages.sendPlayerOnlyLobbyStateStart(p)
        }
    }

    fun kickPlayerLeave(p: Player) {
        p.kickPlayer(KICK_LEAVE_ITEM)
    }

    fun setGameModeAll(gameMode: GameMode) {
        Bukkit.getOnlinePlayers().forEach {
            it.gameMode = gameMode
        }
    }
    fun setIngamePlayerItems(p: Player) {
        p.inventory.setItem(0, swordItem)
        p.inventory.setItem(1, bowItem)
        p.inventory.setItem(2, goldenAppleItem)
        p.inventory.setItem(3, iron_pickage)
        p.inventory.setItem(4, iron_axe)
        p.inventory.setItem(5, woodItems)
        p.inventory.setItem(8, arrowItems)
    }
    fun setSpectatorPlayerItems(p: Player) {

    }
/*    fun createSmokeCircle(player: Player) {
        val particle = Particle.SMOKE_NORMAL
        val startY = player.location.y - 1.0
        val endY = player.location.y + 2.0
        val numParticles = 30
        val radius = 1.5
        val deltaTheta = 2.0 * Math.PI / numParticles
        for (i in 0 until numParticles) {
            val theta = i * deltaTheta
            val x = player.location.x + radius * cos(theta)
            val z = player.location.z + radius * sin(theta)
            player.world.spawnParticle(particle, x, startY, z, 1, 0.0, 0.0, 0.0, 0.0)
            player.world.spawnParticle(particle, x, endY, z, 1, 0.0, 0.0, 0.0, 0.0)
            TimeUnit.MILLISECONDS.sleep(100)
        }
    }*/
    fun closeAllInventories() {
        Bukkit.getOnlinePlayers().forEach {
            it.openInventory.close()
        }
    }
    fun setIngameItemsAll() {
        PLAYERS.forEach {
            setIngamePlayerItems(it.key)
        }
    }
    fun sendPlayerFailedSound(p: Player) {
        p.playSound(p.location, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F)
    }
}
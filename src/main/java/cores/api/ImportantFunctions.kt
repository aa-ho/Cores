package cores.api

import cores.Main.Companion.plugin
import cores.api.GlobalConst.LEAVE_GAME_ITEM
import cores.api.GlobalConst.PERMISSION_BYPASS
import cores.api.GlobalConst.SPECTATOR_SPAWN_LOCATION
import cores.api.GlobalConst.STAR_GAME_ITEM
import cores.api.GlobalConst.TEAM_SELECTOR_ITEM
import cores.api.GlobalConst.TEAM_SPAWN_BLUE_LOCATION
import cores.api.GlobalConst.TEAM_SPAWN_RED_LOCATION
import cores.api.GlobalConst.arrowItems
import cores.api.GlobalConst.bowItem
import cores.api.GlobalConst.goldenAppleItems
import cores.api.GlobalConst.ironAxe
import cores.api.GlobalConst.ironPickage
import cores.api.GlobalConst.steakItems
import cores.api.GlobalConst.swordItem
import cores.api.GlobalConst.woodItems
import cores.api.GlobalVars.PLAYERS
import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.KICK_LEAVE_ITEM
import cores.api.Messages.KICK_RESTART
import cores.api.Messages.RANDOM_TEAM_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.SPECTATOR_COLORED
import cores.gameStates.GameStates
import net.md_5.bungee.api.ChatMessageType
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player

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

    fun resetPlayer(p: Player) {
        p.level = 0
        p.health = 20.0
        p.foodLevel = 20
        p.inventory.heldItemSlot
        p.inventory.clear()
        p.openInventory.close()
        p.activePotionEffects.forEach{p.removePotionEffect(it.type)}
        //TODO respawn...
    }

    fun resetAllPlayers() {
        Bukkit.getOnlinePlayers().forEach {
            resetPlayer(it)
        }
    }

    fun playSoundLevelSuccess(p: Player) {
        p.playSound(p.location, Sound.ENTITY_VILLAGER_YES, 1.0F, 1.0F)
    }

    fun playTimeReminderSoundToAll() {
        playSoundForAll(Sound.ITEM_GOAT_HORN_SOUND_3)
    }

    fun teleportAll(loc: Location) {
        Bukkit.getOnlinePlayers().forEach {
            it.teleport(loc)
        }
    }

    fun teleportAllTeams() {
        Bukkit.getOnlinePlayers().forEach {
            if (!PLAYERS.containsKey(it))
                it.teleport(SPECTATOR_SPAWN_LOCATION)
            else if (plugin.teamHelper.getPlayerTeam(it) == Team.RED) {
                it.bedSpawnLocation = TEAM_SPAWN_RED_LOCATION
                it.teleport(TEAM_SPAWN_RED_LOCATION)
            } else {
                it.bedSpawnLocation = TEAM_SPAWN_BLUE_LOCATION
                it.teleport(TEAM_SPAWN_BLUE_LOCATION)
            }
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
    //TODO setMOTD!

    fun skipCountdown(p: Player) {
        if (plugin.gameStateManager.getCurrentGameState() == GameStates.LOBBY_STATE) {
            if (plugin.gameStateManager.lobbyState.lobbyCountdown.isIdling && plugin.gameStateManager.lobbyState.lobbyCountdown.seconds <= GlobalConst.LOBBY_COUNTDOWN_SKIP_SECONDS) {
                Messages.sendPlayerLobbyCountdownNotSkippable(p)
            } else if (PLAYERS.size < GlobalConst.MIN_PLAYERS) {
                Messages.sendPlayerNoLobbyCountdownSkipBecauseNotEnoughPlayers(p)
                sendPlayerFailedSound(p)
            } else {
                plugin.gameStateManager.lobbyState.lobbyCountdown.seconds =
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
    fun kickAll(message: String) {
        Bukkit.getOnlinePlayers().forEach {
            it.kickPlayer(message)
        }
    }

    fun setGameModeAll(gameMode: GameMode) {
        Bukkit.getOnlinePlayers().forEach {
            it.gameMode = gameMode
        }
    }

    fun setPlayersSurvival() {
        PLAYERS.forEach {
            it.key.gameMode = GameMode.SURVIVAL
        }
    }

    fun setIngamePlayerItems(p: Player) {
        p.inventory.setItem(0, swordItem)
        p.inventory.setItem(1, bowItem)
        p.inventory.setItem(2, goldenAppleItems)
        p.inventory.setItem(3, ironPickage)
        p.inventory.setItem(4, ironAxe)
        p.inventory.setItem(5, woodItems)
        p.inventory.setItem(6, steakItems)
        p.inventory.setItem(8, arrowItems)
    }

    fun setSpectatorPlayerItems(p: Player) {
        //TODO...
    }

    fun updateLobbyScoreboardAll() {
        Bukkit.getOnlinePlayers().forEach {
            plugin.scoreboard.updateLobbyScoreboard(it)
        }
    }

    fun updateInGameScoreboardAll() {
        Bukkit.getOnlinePlayers().forEach {
            plugin.scoreboard.updateInGameScoreboard(it)
        }
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

    fun setIngameItemsForPlayers() {
        PLAYERS.forEach {
            setIngamePlayerItems(it.key)
        }
    }

    fun sendPlayerFailedSound(p: Player) {
        p.playSound(p.location, Sound.BLOCK_ANVIL_LAND, 1.0F, 1.0F)
    }

    fun sendPlayerKillSound(p: Player) {
        p.playSound(p.location, Sound.ITEM_HONEY_BOTTLE_DRINK, 2.0F, 2.0F)
    }

    fun setPlayerTeamActionBar(p: Player) {
        if (PLAYERS.containsKey(p)) {
            val team: Team?
            if (!plugin.teamHelper.isPlayerInTeam(p)) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(RANDOM_TEAM_COLORED))
                return
            }
            team = plugin.teamHelper.getPlayerTeam(p)
            p.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                TextComponent(if (team == Team.RED) RED_COLORED else BLUE_COLORED)
            )
        } else {
            p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent(SPECTATOR_COLORED))
        }
    }

    fun setAllPlayerTeamActionBar() {
        Bukkit.getOnlinePlayers().forEach {
            setPlayerTeamActionBar(it)
        }
    }
}
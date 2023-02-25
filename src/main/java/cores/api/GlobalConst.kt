package cores.api

import cores.api.Messages.BLUE_COLORED
import cores.api.Messages.RED_COLORED
import cores.api.Messages.teamSelectItems
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound

object GlobalConst {
    var MIN_PLAYERS = 2
    var MAX_PLAYERS = 10
    var LOBBY_COUNTDOWN_SECONDS = 60 //TODO Eigentlich 60
    var INGAME_TOTAL_SECONDS = 60 * 240
    var END_COUNTDOWN_SECONDS = 20
    var DATE_TEXT_FORMAT = "dd.MM.yyyy HH:mm"
    var LOBBY_COUNTDOWN_SKIP_SECONDS = 10

    var LOBBY_SPAWN_LOCATION = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var TEAM_SPAWN_RED_LOCATION = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var TEAM_SPAWN_BLUE_LOCATION = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)

    var RED_CORE_FRONT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var RED_CORE_BACK = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var RED_CORE_LEFT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var RED_CORE_RIGHT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)

    var BLUE_CORE_FRONT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var BLUE_CORE_BACK = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var BLUE_CORE_LEFT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)
    var BLUE_CORE_RIGHT = Location(Bukkit.getWorld("world"), 0.0, 300.0, 0.0)

    val swordItem = ItemBuilder(Material.STONE_SWORD).build()
    val iron_pickage = ItemBuilder(Material.IRON_PICKAXE).build()
    val iron_axe = ItemBuilder(Material.IRON_AXE).build()
    val bowItem = ItemBuilder(Material.BOW).build()
    val goldenAppleItem = ItemBuilder(Material.GOLDEN_APPLE).build()
    val woodItems = ItemBuilder(Material.SPRUCE_LOG).setAmount(64).build()
    val arrowItems = ItemBuilder(Material.ARROW).setAmount(16).build()

    val LOBBY_COUNTDOWN_SOUND = Sound.BLOCK_NOTE_BLOCK_BANJO
    val GAME_START_SOUND = Sound.ITEM_GOAT_HORN_SOUND_3
    val START_GAME_ITEM_NAME = "§5Countdown verkürzen"
    val STAR_GAME_ITEM = ItemBuilder(Material.FEATHER).setDisplayName(START_GAME_ITEM_NAME).build()

    val LEAVE_GAME_ITEM_NAME = "§cSpiel verlassen"
    val LEAVE_GAME_ITEM = ItemBuilder(Material.STRUCTURE_VOID).setDisplayName(LEAVE_GAME_ITEM_NAME).build()

    val TEAM_SELECTOR_ITEM_NAME = "§7Team Auswahl"
    val TEAM_SELECTOR_ITEM = ItemBuilder(Material.RED_BED).setDisplayName(TEAM_SELECTOR_ITEM_NAME).build()

    val TEAMSELECTORINVENTORYTITLE = TEAM_SELECTOR_ITEM_NAME
    val TEAM_SELECTOR_INVENTORY=  Bukkit.createInventory(null, 54, TEAMSELECTORINVENTORYTITLE)
    val redTeamItem = ItemBuilder(Material.RED_CANDLE).setDisplayName(RED_COLORED).setLore(teamSelectItems(Teams.RED)).build()
    val blueTeamItem = ItemBuilder(Material.BLUE_CANDLE).setDisplayName(BLUE_COLORED).setLore(teamSelectItems(Teams.BLUE)).build()


    var PERMISSION_BYPASS = "cores.bypass"
    val CORES_COMMAND = "cores"
    val START_COMMAND = "start"
    val SET_COMMAND = "set"
    val SET_LOBBY_COMMAND = "lobby"
    val SET_TEAM_SPAWN = "spawn"
    val RED_COMMAND = "red"
    val BLUE_COMMAND = "blue"
    val HELP_COMMAND = "help"
    val SET_BEACON_COMMAND = "beacon"
    val BEACON_FRONT = "front"
    val BEACON_BACK = "back"
    val BEACON_LEFT = "left"
    val BEACON_RIGHT = "right"
}
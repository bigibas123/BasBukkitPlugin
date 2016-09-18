# ServerChangeGui
A plugin utilising the bungee-API to create an inventory server list

![Build Status](http://ci.dingemans.mooo.com:8080/buildStatus/icon?job=BigiBukkitLib&style=plastic)

 [Normal Releases](https://github.com/bigibas123/ServerChangeGui/releases "Normal Releases")



feel free to contact me about anything
help/merge requests greatly appreciated


##Commands:

  `/SCG` opens the interface, permission: SCG.use


##Example config
```YAML
SCG:
  items:
    lobby:
      item: STAINED_CLAY
      durability: 6
      location: 0
      lore:
      - Connects you to the lobby server
      customName: To hub
    skywars:
      item: GRASS
      durability: 0
      location: 1
      lore:
      - Connects you to the skywars server
      customName: Skywars
  general:
    menuName: Servers

```

for the item names see https://gist.githubusercontent.com/bigibas123/aea44ec5eea0104cb062/raw/7e07891cccf97c6672c1ecd4cf7af47ef6c27f3e/BukkitMaterials and durability's http://minecraft-ids.grahamedgecombe.com/


##Credits
nisovin:[Icon Menu class](http://bukkit.org/threads/icon-menu.108342/) [Bukkit Profile](http://bukkit.org/members/nisovin.2980/)

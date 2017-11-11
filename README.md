# ServerChangeGui
A plugin utilising the bungee-API to create an inventory server list

[![Build Status](https://travis-ci.org/bigibas123/ServerChangeGui.svg?branch=master)](https://travis-ci.org/bigibas123/ServerChangeGui)

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
      item: GRASS
      durability: 0
      location: 0
      customName: §bLobby Server Pls
      lore:
      - the fishes stay at sea
      visible: true
    skywars:
      item: IRON_SWORD
      durability: 11
      location: 1
      customName: §bskywars
      lore:
      - brings you to the skywars server
      - kill everybody!
      visible: true
  general:
    menuName: SpaceMarine
    paragraphForCopy: §

```

for the item names see https://gist.githubusercontent.com/bigibas123/aea44ec5eea0104cb062/raw/7e07891cccf97c6672c1ecd4cf7af47ef6c27f3e/BukkitMaterials and durability's http://minecraft-ids.grahamedgecombe.com/


##Credits
luko: [helper](https://github.com/lucko/helper)

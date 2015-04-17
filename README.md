#RandomItemDrop
 * Version: 1.3.0 (compatible with 1.7.x, 1.8.x)
 * Version: 1.2.4 (tested with 1.7.2)
 * Plugin needed: Vault
 * Author: [Gerviba]
 * License: Creative Commons 4.0 (BY-NC-SA)

##Description:
The plugin drops items to the configured coordinates and when the player picks up the item, he/she gets money. Recommend this plugin for SkyPVP, KitPVP, Prison or any SMP servers.

##Commands:
 * /rid - Random Item Drop
 * /rid reload - Reloads the configuration (for OPs only)
 * /rid redrop - Drops the items again (for OPs only)

##How to configure?
Put the jar file into the plugins folder and start your server. After it loaded, it will generate the config files and disable the plugin. You need to open the 'Lang.yml' in the 'plugins/RandomItemDrop' folder and set the values. After that, you should set the coordinates of the items in the 'ItemInfos.yml' at the same location. You will see something like that:

```
	- world, 0.5, 0.1, 0.5, GOLD_INGOT, 35.5
	- world, 0.5, 0.1, 0.5, #266, 35.5
```

The parameters are:
 * The name of the World
 * The X coordinate (Use .5 ending to align the item to the center of the coordinate.)
 * The Y coordinate
 * The Z coordinate (Same as X coordinates.)
 * The Material of the droped item OR # < id of the item >
 * The Money what the picker gets

If you've finished, you can start your server and enjoy the plugin! Have fun!

##Donate: 
This plugin is free but you can donate if you wish! :) 
[Donate] here!

##Disclaimer
I wrote this plugin a long time ago. There might be a problem with the new versions, though not in principle. 

* This project is under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International license (CC BY-NC-SA 4.0)

[Gerviba]:https://github.com/Gerviba
[Donate]:https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=64K9CU3CX3FV4

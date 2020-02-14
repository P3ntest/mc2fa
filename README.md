# mc2fa
A Minecraft [Spigot](https://spigotmc.org) ([Bukkit](https://bukkit.org)) plugin for bringing [Two Factor Authentication](https://en.wikipedia.org/wiki/Multi-factor_authentication) (2FA) to Minecraft servers.

## How it works
You can set a List of permissions, which only get granted if a player is authenticated. After Permissions are granted to players, mcf2a removes the ones requiring a 2f Authendication.

One can verify himself, by using __/2fa <6-digit-code>__, after which he will be granted all missing permissions.

When a player enters it the first time, or the custom event is triggered, a virtual world will be generated for the player, to scan the QR-Code. The Materials for the QR-Code can be customized, the standart is black and white wool.

execute as @a[scores={boulder_timer=..36000}] if entity @s[nbt={Inventory:[{id:"callofequestria:boulder"}]}] run scoreboard players remove @s boulder_timer 1

execute as @a[nbt=!{Inventory:[{id:"callofequestria:boulder"}]}] run scoreboard players set @s boulder_timer 36000

execute as @a[scores={boulder_timer=0}] run advancement grant @s only callofequestria:boulder
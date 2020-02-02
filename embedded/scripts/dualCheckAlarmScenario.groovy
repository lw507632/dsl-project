//2. Dual-check alarm: Pushing a button will trigger a buzzer if and only if two buttons are pushed at
//the very same time. Releasing at least one of the button stop the sound


sensor "buttonLeft" onPin 8
sensor "buttonRight" onPin 9
actuator "buzzer" pin 12
state "on" means "buzzer" becomes "high"
state "off" means "buzzer" becomes "low"
initial "off"
from "off" to "on" when "buttonLeft" becomes "high" and "buttonRight" becomes "high"
from "on" to "off" when "buttonLeft" becomes "low" and "buttonRight" becomes "high"
from "on" to "off" when "buttonLeft" becomes "high" and "buttonRight" becomes "low"
from "on" to "off" when "buttonLeft" becomes "low" and "buttonRight" becomes "low"
export "Dual-Check alarm!"
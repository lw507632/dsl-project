//2. Dual-check alarm: Pushing a button will trigger a buzzer if and only if two buttons are pushed at
//the very same time. Releasing at least one of the button stop the sound


sensor "buttonLeft" onPin 8
sensor "buttonRight" onPin 9
actuator "buzzer" pin 11
state "on" means "buzzer" becomes "HIGH"
state "off" means "buzzer" becomes "LOW"
initial "off"
from "off" to "on" when "buttonLeft" becomes "HIGH" and "buttonRight" becomes "HIGH"
from "on" to "off" when "buttonLeft" becomes "LOW" and "buttonRight" becomes "HIGH"
from "on" to "off" when "buttonLeft" becomes "HIGH" and "buttonRight" becomes "LOW"
from "on" to "off" when "buttonLeft" becomes "LOW" and "buttonRight" becomes "LOW"
export "Dual-Check alarm!"
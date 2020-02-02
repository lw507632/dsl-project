//Very simple alarm: Pushing a button activates a LED and a buzzer. Releasing the button switches
//the actuators off

sensor "button" onPin 9
actuator "led" pin 12
actuator "buzzer" pin 11
state "on" means "led" becomes "high" and "buzzer" becomes "high"
state "off" means "led" becomes "low" and "buzzer" becomes "low"
initial "off"
from "on" to "off" when "button" becomes "high"
from "off" to "on" when "button" becomes "low"
export "Alarm!"
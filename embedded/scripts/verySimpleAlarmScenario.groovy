//Very simple alarm: Pushing a button activates a LED and a buzzer. Releasing the button switches
//the actuators off

sensor "button" onPin 9
actuator "led" pin 12
actuator "buzzer" pin 11
state "on" means "led" becomes "HIGH" and "buzzer" becomes "HIGH"
state "off" means "led" becomes "LOW" and "buzzer" becomes "LOW"
initial "off"
from "on" to "off" when "button" becomes "LOW"
from "off" to "on" when "button" becomes "HIGH"
export "Alarm!"
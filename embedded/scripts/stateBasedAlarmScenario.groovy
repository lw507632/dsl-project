//. State-based alarm: Pushing the button once switch the system in a mode where the LED is switched
//on. Pushing it again switches it off.

sensor "button" onPin 9
actuator "led" pin 12
state "on" means "led" becomes "high"
state "off" means "led" becomes "low"
initial "off"
from "on" to "off" when "button" becomes "high"
from "off" to "on" when "button" becomes "low"
export "State based Alarm!"
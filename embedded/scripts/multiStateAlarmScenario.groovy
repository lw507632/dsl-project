//Multi-state alarm: Pushing the button starts the buzz noise. Pushing it again stop the buzzer and
//switch the LED on. Pushing it again switch the LED off, and makes the system ready to make noise
//again after one push, and so on.

sensor "button" onPin 9
actuator "led" pin 12
actuator "buzzer" pin 13
state "noise" means "led" becomes "low" and "buzzer" becomes "high"
state "light" means "led" becomes "high" and "buzzer" becomes "low"
state "initialState" means "led" becomes "low" and "buzzer" becomes "low"
initial "initialState"
from "initialState" to "noise" when "button" becomes "high"
from "noise" to "light" when "button" becomes "low"
from "light" to "noise" when "button" becomes "high"
export "Multi state Alarm!"
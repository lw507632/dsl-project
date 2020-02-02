//Multi-state alarm: Pushing the button starts the buzz noise. Pushing it again stop the buzzer and
//switch the LED on. Pushing it again switch the LED off, and makes the system ready to make noise
//again after one push, and so on.

sensor "button" onPin 9
actuator "led" pin 12
actuator "buzzer" pin 11
state "noise" means "led" becomes "LOW" and "buzzer" becomes "HIGH"
state "light" means "led" becomes "HIGH" and "buzzer" becomes "LOW"
state "initialState" means "led" becomes "LOW" and "buzzer" becomes "LOW"
initial "initialState"
from "initialState" to "noise" when "button" becomes "HIGH"
from "noise" to "light" when "button" becomes "HIGH"
from "light" to "initialState" when "button" becomes "HIGH"
export "Multi state Alarm!"
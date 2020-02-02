# Embedded

Le dossier scripts contient les différents scripts correspondants aux scénarios implémentés.

Le script build est utilisé pour construire le module embedded. Il est utilisé dans le script principal test.sh

## Le script test.sh est utilisé pour construire le module et exécuté un scénario.
#### La commande pour lancer un scénario :  ./test.sh [scénario]
Example : ./test.sh scripts/verySimpleAlarmScenario.groovy

Dans le cas d'un problème de compilation, veuillez compiler le kernel et ré-essayez : 

--> cd ../kernels/jvm

--> mvn clean install

--> cd ../../embedded

--> ./test.sh [scénario]

#### Toutes les commandes possibles :

very simple alarm scenario :  ./test.sh scripts/verySimpleAlarmScenario.groovy

multi state alarm scenario :  ./test.sh scripts/multiStateAlarmScenario.groovy

dual check alarm scenario :   ./test.sh scripts/dualCheckAlarmScenario.groovy

state based alarm scenario :  ./test.sh scripts/stateBasedAlarmScenario.groovy

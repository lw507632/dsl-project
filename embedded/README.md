# Embedded

Le dossier scripts contient les différents scripts correspondants aux scénarios implémentés.

Le script build est utilisé pour construire le module embedded. Il est utilisé dans le script principal test.sh

## Le script test.sh est utilisé pour construire le module et exécuté un scénario.
#### La commande pour lancer un scénario :  ./test.sh [scénario]
Example :```sh  ./test.sh scripts/verySimpleAlarmScenario.groovy ```

Dans le cas d'un problème de compilation, veuillez compiler le kernel et ré-essayez : 

```sh 
  cd ../kernels/jvm
```

```sh
  mvn clean install
```

```sh
  cd ../../embedded
```

```sh
  ./test.sh [scénario]
```

#### Toutes les commandes possibles :

very simple alarm scenario :  ```sh ./test.sh scripts/verySimpleAlarmScenario.groovy ```

multi state alarm scenario :  ```sh ./test.sh scripts/multiStateAlarmScenario.groovy ```

dual check alarm scenario :   ```sh ./test.sh scripts/dualCheckAlarmScenario.groovy ```

state based alarm scenario :  ```sh ./test.sh scripts/stateBasedAlarmScenario.groovy ```

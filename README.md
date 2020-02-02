# dsl-project

Le projet contient trois modules principaux : 

embedded pour le SDL internal.

external pour le SDL external.

kernel pour le coeur du SDL.

Chaque module contient un fichier README expliquant le module et comment l'éxecuter.

## Execution de Embedded: 

```sh 
	cd embedded
```

```sh 
	./test.sh [scénario]  
```

example : ./test.sh scripts/verySimpleAlarmScenario.groovy

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

## Execution external

> Go to the exernal/ folder.

To convert .aml code to native arduino code run :

```sh
	java -jar AML_Runnable.jar example.aml

```
/!\ However this do not verify the synthax of the .aml, to write valid .aml files, we need to use eclipse generated editor with xtext.

(There is samples files in ```samples/```)

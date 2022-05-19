# mercadolibre
Se desarrolla una api Rest la cual recibe los IDs de los items y el amount de un bono con 
el objetivo de calcular la cantidad items que se le permitirá al usuario comprar sin exceder
y optimizando la cantidad del bono.

Para el desarrollo de la api se utilizó lenguaje "Java 1.8" con el entorno de desarrollo "Intellij", el framework Springboot(el cual tiene embebido un servidor de aplicación "Tomcat").
Se realizaron pruebas con la herramienta "ARC" para verificar el request y el response salidas de esta api.
Además se construyó la imagen de la aplicacion con "Docker" y se publicó a traves de "AWS"

## Ejecución del programa

primer paso: Descargar el proyecto desde el repositorio Git con el siguiente comando: git clone https://github.com/alejoer900820/mercadolibre.git

segundo paso: Se importa en el entorno de desarrollo IntelliJ(a traves del cual se actualizan dependencias de maven)

Tercer paso: Buscar el archivo MercadolibreApplication.java y dar click en Run o en la parte superior derecha también se puede ejecutar dando Run

{Cabe resaltar que es necesario tener instalado Java8 en el ambiente donde se probará de manera local}

### Pruebas locales
Para estas pruebas se realizó con una aplicación llamada ARC(Advanced rest client), pero también se puede realizar
con Postman, ambas se ddeben instalar.

En ambas se debe seleccionar el método POST y en la Url ingresar: http://localhost:8080/coupon/

Se debe seleccionar el content type: application/json.
Se debe seleccionar en editor view: Raw input

y por último ingresar en el body el siguiente Json:

```
{
  "item_ids": [
    "MLA844702270",
    "MLA844702274",
    "MLA844702281",
    "MLA844702289",
    "MLA844702297"
  ],
  "amount": 700
}
```
Con el Json anterior devuelve la siguiente respuesta, con el código 200:

```
{
  "item_ids":
  ["MLA844702281",
  "MLA844702274"],
  "amount":700.0
}
```

En caso de que se exceda el amount de bono, no se ingresen los items o que el mínimo precio
de un producto supera la cantidad del bono, se regresará lo siguiente:

```
{   
  "message":"Item prices exceed bonus amount",
  "code":404
}
```


### Pruebas con IP pública


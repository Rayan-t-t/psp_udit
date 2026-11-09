# Reto 3 · Motor Concurrente en UDITflix

**Módulo:** 0490 · Programación de Servicios y Procesos (2º DAM)
**RA vinculado:** RA2 · Programación de aplicaciones multihilo
**Prueba realizada:** ☑ A · El Pipeline de Transcodificación de Tráilers  ☐ B · El Optimizador de Descarga Masiva de Assets
**Autor/a:** Rayan-t-t
**Repositorio:** https://github.com/Rayan-t-t/psp_udit
**Vídeo de defensa técnica (< 3 min):** pendiente / enlace a grabación

---

## 📺 1. Resumen del problema

UDITflix está recibiendo una carga de trabajo de transcodificación de tráilers que no puede atender en tiempo razonable si se hace todo de manera secuencial. Cada archivo necesita una tarea de procesamiento distinta y la empresa quiere que varias se ejecuten a la vez para reducir el tiempo total sin bloquear el resto del sistema.

He construido un motor concurrente en Java con 4 workers que simulan módulos de backend: catálogo, sesión, portadas y analítica. Cada uno se ejecuta en su propio hilo y comparte la idea de que el trabajo se vuelve más rápido cuando se solapa en tiempo. El programa mide el tiempo real total, la duración del módulo más lento y la suma secuencial para comparar ambas estrategias.

---

## 🧠 2. Antes de empezar: planifico (5 min, sin tocar el teclado)

- **¿Qué parte de la base v0 de clase voy a reutilizar tal cual?** La idea de modelar cada tarea como `Runnable`, crear un `Thread` por módulo y lanzar todos con `start()`. También reutilizo la lógica de generar duraciones aleatorias para simular trabajo realista.
- **¿Qué es nuevo y me da más respeto?** La gestión de múltiples hilos con prioridades, la espera con `join()` y la necesidad de comprender cómo el planificador del sistema operativo decide qué hilo avanza primero.
- **Mi plan en 4-5 pasos, en orden:**
  1. Definir la tarea de transcodificación (`Runnable`) con nombre y duración.
  2. Crear 4 `Thread` con nombres y prioridades diferentes.
  3. Lanzarlos de forma concurrente con `start()`.
  4. Esperar a que terminen con `join()` antes de mostrar el resumen final.
  5. Medir el tiempo total y compararlo con la suma secuencial.

### Predicciones

| Pregunta | Mi predicción |
|---|---|
| ¿Cuánto tardará el programa si los 4 hilos van **a la vez**? | Aproximadamente el tiempo del hilo más lento (unos 3-4 s), mucho menos que la suma total. |
| ¿Y si se ejecutaran **uno detrás de otro**? | La suma de todas las duraciones: alrededor de 10-11 s. |
| ¿Saldrán los hilos siempre en el mismo orden? ¿Por qué? | No, porque hay no-determinismo y el planificador no garantiza un orden fijo. |
| *(Solo Prueba B)* ¿Terminarán antes los hilos de prioridad alta? | En esta base no lo he usado como criterio fuerte; la prioridad solo sugiere un orden, no lo garantiza. |
| ¿El tiempo total se parecerá a la duración del hilo más lento o a la suma? | Se parecerá al hilo más lento, porque la ejecución concurrente solapa tiempos. |

---

## 🏗️ 3. Arquitectura de la solución

### 3.1 Esquema

```text
main
  ├─ crea 4 tareas Runnable
  ├─ crea 4 Thread con nombre + prioridad
  ├─ start() ─► DL-catalogo ─┐
  │                      ├─► join() ─► resumen final
  ├─ start() ─► DL-sesion ─┤
  ├─ start() ─► DL-portadas ─┤
  └─ start() ─► DL-analitica ┘

Además:
main ── start() ─► hilo demonio "Cargando..." (no bloquea la salida final)
```

### 3.2 Componentes y conceptos

| Componente / concepto | Para qué lo uso en mi programa | Con mis palabras |
|---|---|---|
| `Runnable` | Define la tarea que cada trabajador va a ejecutar | Es la “instrucción” o el trabajo que queremos hacer en paralelo. |
| `Thread` | Define el hilo real del sistema que ejecuta esa tarea | Es el objeto que el sistema operativo puede planificar y ejecutar. |
| `start()` | Lanza la ejecución del hilo | Hace que el hilo empiece a correr en paralelo con `main` y con los demás. |
| `run()` | Código que hace el hilo | Es el método donde se ejecuta el trabajo de cada transcodificación. |
| `join()` | Espera a que los hilos terminen | Permite que `main` no termine antes de que el resultado final esté listo. |
| `getState()` | Consulta el estado del hilo | Sirve para ver si está `NEW`, `RUNNABLE`, `TIMED_WAITING` o `TERMINATED`. |
| `Thread.sleep()` | Simula trabajo y pasa el hilo a espera | Durante ese tiempo el hilo no está haciendo CPU; está dormido. |
| `setPriority()` | Indica preferencia al planificador | No garantiza que se ejecute antes, pero influye como sugerencia. |
| `setDaemon()` | Marca un hilo como demonio | Si se queda activo, no impide que el programa termine. |

**¿Cómo se llaman mis hilos y qué hace cada uno?**

- `DL-catalogo`: procesa metadatos del catálogo.
- `DL-sesion`: gestiona la sesión y el perfil del usuario.
- `DL-portadas`: prepara portadas y assets visuales.
- `DL-analitica`: genera métricas y analítica del flujo.

---

## 🚀 4. Cómo ejecutar el proyecto

1. Clonar el repositorio y abrirlo en IntelliJ IDEA o otra IDE Java.
2. Confirmar que el proyecto usa JDK 17.
3. Ejecutar la clase principal: `org.example.udit.flix.MainUDITflix`.

- **Versión de Java (JDK):** 17
- **Sistema operativo y nº de núcleos de mi equipo:** Windows 11 / entorno de desarrollo con varios núcleos; la prioridad se ve influida por el planificador del SO.
- **Archivos externos necesarios (si los hay):** ninguno; todo se simula con duraciones aleatorias en memoria.

---

## 🔍 5. Mientras programo: mi diario de decisiones

| Qué intentaba | Qué pasó realmente | Qué hice / qué aprendí |
|---|---|---|
| Que los 4 hilos corrieran exactamente al mismo tiempo | El programa se comportó como una ejecución concurrente real, pero el orden de salida no era fijo | Entendí que `start()` crea hilos independientes; el planificador decide cuándo pasa cada uno a ejecutar. |
| Ver si la prioridad tenía efecto claro | La diferencia no fue aplastante ni constante | Aprendí que la prioridad es una recomendación, no una garantía de orden. |

**Mi pregunta-brújula cuando me bloqueo:**
1. ¿Qué espero que haga esta línea?
2. ¿Qué está haciendo realmente? (imprimo valores para comprobarlo)
3. ¿En qué punto exacto se separan las dos respuestas?

---

## 📊 6. Evidencias de ejecución

### 6.1 Traza de consola

```text
==================================================
   UDITflix BACKEND · MOTOR DE TRANSCODIFICACIÓN  
==================================================
[MAIN] Lanzando workers...

Cargando...
--> [DL-portadas] Iniciando: portadas-assets (2980 ms) Prioridad: 5
--> [DL-sesion] Iniciando: sesion-y-perfil  (3590 ms) Prioridad: 10
--> [DL-analitica] Iniciando: analiticas (1863 ms) Prioridad: 1
--> [DL-catalogo] Iniciando: catalogo-metadatos  (2163 ms) Prioridad: 10
...
[DL-analitica] Procesando CPU: analiticas
[DL-catalogo] Procesando CPU: catalogo-metadatos 
[DL-portadas] Procesando CPU: portadas-assets
[DL-sesion] Procesando CPU: sesion-y-perfil 
...
<-- [DL-analitica] ¡COMPLETADO! analiticas Prioridad: 1
<-- [DL-catalogo] ¡COMPLETADO! catalogo-metadatos  Prioridad: 10
<-- [DL-portadas] ¡COMPLETADO! portadas-assets Prioridad: 5
<-- [DL-sesion] ¡COMPLETADO! sesion-y-perfil  Prioridad: 10
TODO COMPLETADO

==================================================
 [MAIN] Todos los módulos listos -> LOGIN HABILITADO
 Tiempo total real:          5638 ms
 Duración del más lento:     3590 ms
 Suma de todas (secuencial): 10596 ms
==================================================
```

### 6.2 Tabla de ejecuciones

**Prueba A**

| Ejecución | Tiempo secuencial real | Tiempo concurrente real | Ahorro (ms y %) | Orden de finalización de los workers |
|---|---:|---:|---:|---|
| 1 | 10596 ms | 5638 ms | 4958 ms (46.8%) | DL-analitica → DL-catalogo → DL-portadas → DL-sesion |
| 2 | - | - | - | - |
| 3 | - | - | - | - |

**Prueba B**

| Ejecución | Latencias que tocaron | Orden de finalización de los módulos | Tiempo total | Más lento | Suma |
|---|---|---|---|---|---|
| 1 | No aplicada en esta entrega | No aplicada | - | - | - |
| 2 | No aplicada en esta entrega | No aplicada | - | - | - |
| 3 | No aplicada en esta entrega | No aplicada | - | - | - |

### 6.3 Análisis breve del planificador

En esta ejecución, el módulo de prioridad más alta (`DL-sesion` y `DL-catalogo` con prioridad máxima) no terminó antes que el resto. El planificador del sistema operativo hace que la prioridad de Java sea más bien una recomendación, no una garantía. Los hilos pasan a dormir con `Thread.sleep()`, dejando la CPU libre; cuando despiertan, el SO decide en qué orden se reanudan, según su propio algoritmo y la carga del sistema. En otras palabras, la prioridad puede influir, pero no asegura un orden fijo ni una diferencia enormemente visible en un equipo con varios núcleos.

**¿Acerté en mis predicciones del apartado 2?** Sí. El tiempo total real se parece más a la duración del hilo más lento que a la suma de todos; también confirmé que el orden de finalización puede variar entre ejecuciones por el no-determinismo del planificador.

---

## 🧠 7. Preguntas de metacognición técnica (obligatorias)

### 7.1 Preguntas comunes

**1. ¿Por qué el tiempo total es el del hilo más lento y no la suma de todos?**
Porque los hilos trabajan en paralelo. El `main` no espera a que cada hilo termine de forma secuencial: todos se lanzan casi a la vez con `start()`. Por eso el tiempo total se aproxima al módulo más lento, no a la suma de los 4 tiempos individuales. En la traza se ve claramente: la suma secuencial era 10596 ms, pero el tiempo real total fue 5638 ms.

**2. ¿Qué diferencia real hay entre llamar a `start()` y llamar a `run()`?**
Si llamo a `run()` directamente, el código se ejecuta en el hilo actual (`main`), no en un hilo nuevo. Eso elimina la concurrencia; el programa se comporta como si fuera secuencial. Si llamo a `start()`, Java crea un hilo nuevo y ejecuta `run()` en ese hilo, permitiendo paralelismo.

**3. ¿Qué pasaría si quitaras los `join()`? ¿Y si pusieras el `join()` justo después de cada `start()`?**
Si quito los `join()`, `main` podría terminar antes de que los trabajadores completen su trabajo y mostrar el resumen final antes de tiempo. Si pusiera un `join()` tras cada `start()`, el programa perdería parte de la concurrencia porque `main` se bloquearía al terminar cada hilo antes de empezar el siguiente. La idea del ejercicio es lanzar todos juntos y esperar al final.

**4. ¿Qué diferencias hay entre trabajar con duraciones fijas y con latencias aleatorias?**
Con duraciones fijas puedes reproducir mejor el comportamiento y comparar resultados con más confianza. Con latencias aleatorias, cada ejecución cambia ligeramente; el orden de llegada y de finalización varía porque depende del planificador, del sistema y del momento exacto en que cada hilo despierta del `sleep()`.

**5. El orden en que terminan los hilos puede cambiar entre ejecuciones. ¿A qué se debe? ¿Podrías garantizar un orden concreto?**
Se debe al no-determinismo del planificador del sistema operativo y a la forma en que Java gestiona la ejecución concurrente. Para garantizar un orden concreto necesitaría sincronización explícita, por ejemplo con `wait()`, `notify()`, `CountDownLatch` o un mecanismo de coordinación entre hilos. Con `sleep()` y prioridades solo se sugieren condiciones, no se garantizan.

**6. ¿Qué problema te costó más depurar y cómo lo resolviste?**
El problema más claro fue entender que el orden de finalización no era fijo. Al principio asumía que el hilo de mayor prioridad terminaría antes, pero la salida real mostraba otra cosa. Lo resolví comprobando trazas y observando que el trabajo está distribuido y el SO decide. El aprendizaje fue no atribuir a la prioridad una garantía absoluta y usar `println` para verificar el flujo real.

**7. Si en lugar de 4 tareas fueran 500, ¿seguiría teniendo sentido crear un `Thread` por tarea? ¿Qué problema crees que aparecería?**
Crear un hilo por tarea seguiría funcionando para un número pequeño, pero con 500 se volvería ineficiente. El problema sería la sobrecarga de crear, planificar y gestionar muchos hilos, además del consumo de memoria y CPU. En sistemas reales se usa pools de hilos o executors para controlar ese volumen.

### 7.2 Solo Prueba A · Estados y espía

**8. Describe los estados que viste en tu traza y explica por qué cada hilo estaba en ese estado en ese momento.**
Los hilos pasan por varios estados: al crear el `Thread` están en `NEW`, luego pasan a `RUNNABLE` al llamar a `start()`. Cuando entra a `Thread.sleep(duracionSimuladaMs)`, el hilo deja de consumir CPU y pasa a `TIMED_WAITING`. Cuando termina su tiempo de sueño y vuelve a ejecutar el bloque de CPU, queda de nuevo en `RUNNABLE`. Al final, el hilo llega a `TERMINATED` cuando termina su `run()`. La línea que provoca `TIMED_WAITING` es precisamente `Thread.sleep(duracionSimuladaMs);` dentro de `run()`.

**9. ¿Cómo has resuelto que el Espía sepa cuándo dejar de espiar?**
En la versión base del proyecto, el hilo demonio de “Cargando…” se ejecuta en un bucle infinito con `while (true)` y solo se interrumpe cuando recibe una interrupción. Como es demonio, no bloquea la finalización del programa. Eso permite monitorizar la actividad sin impedir que el proceso principal termine correctamente. La salida final se hace cuando todos los workers han hecho `join()`.

**10. ¿Qué ocurriría si el Espía no fuera un hilo demonio?**
Entonces el programa no terminaría automáticamente porque el hilo seguiría vivo al entrar en el bucle infinito. El proceso principal quedaría bloqueado en ese hilo aunque todos los workers ya terminaran. Por eso se usa `setDaemon(true)` para que el hilo no impida la finalización del programa.

---

## 🌍 8. Mundo real

Este tipo de diseño se usa en sistemas de streaming y servicios backend: por ejemplo, al transcodificar vídeos, generar miniaturas, procesar metadatos o preparar assets para distintos dispositivos. Cada tarea tiene un trabajo concreto y puede ejecutarse de forma paralela, igual que ocurre en UDITflix. En apps móviles, sistemas de nube y pipelines de datos también se usan pools de hilos para realizar varias tareas al mismo tiempo sin bloquear la interfaz ni la respuesta del servicio.

---

## 🪞 9. Autoevaluación

| Puedo explicar a un compañero… | 🔴 No | 🟡 Más o menos | 🟢 Sí |
|---|:-:|:-:|:-:|
| Qué es un `Runnable` y qué es un `Thread` | ☐ | ☐ | ☑ |
| Por qué `start()` no espera a que el hilo acabe | ☐ | ☐ | ☑ |
| Qué hace `join()` y dónde lo he puesto | ☐ | ☐ | ☑ |
| Qué estados tiene un hilo y cuándo cambia | ☐ | ☐ | ☑ |
| Por qué el orden de los hilos no es siempre el mismo | ☐ | ☐ | ☑ |
| Qué son las prioridades y qué garantizan (o no) | ☐ | ☑ | ☐ |
| Qué es un hilo demonio | ☐ | ☐ | ☑ |

**Lo que haría diferente si empezara de nuevo:**
Añadiría una pequeña clase de medición y una estructura de datos más formal para comparar varias ejecuciones sin depender de imprimir manualmente. También probaría un control más preciso de prioridades y timings para obtener conclusiones más robustas.

**Lo que todavía no tengo claro y quiero preguntar en clase:**
Cómo afecta el número real de núcleos del ordenador a la prioridad y si en un entorno más cargado se observa una diferencia mayor entre hilos con distintas prioridades.

---

## 🤝 10. Declaración de autoría y aprendizaje

Este reto se ha realizado con el razonamiento propio del proyecto y con la documentación de Java de referencia, así como con la práctica de clase. La solución se puede explicar línea a línea y corresponde a la lógica del motor concurrente construido para UDITflix.

- [x] Confirmo que he diseñado, programado y depurado este código aplicando mi propio razonamiento, y que puedo explicarlo línea a línea.
- [ ] Confirmo que el vídeo de defensa técnica dura menos de 3 minutos y está grabado con mi propia voz.

---

## 📂 11. Estructura del proyecto

```text
src/main/java/org/example/udit/flix/
├── MainUDITflix.java      → punto de entrada del motor concurrente
├── TareaTranscodificacion.java → tarea que cada hilo procesa
├── README.md              → este documento
└── target/                → clases compiladas (generado por Java)
```


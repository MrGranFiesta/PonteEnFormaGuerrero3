package com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource

import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.EjercicioBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Musculo
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.Nivel

class InitialEjercicioDB {
    companion object {
        fun getListDataDefaultEjercicio(): List<EjercicioBean> {
            return listOf(
                EjercicioBean(
                    idEjercicio = 2_000_000,
                    idUser = 1,
                    nombre = "Correr",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.FACIL,
                    descripcion = "Para una técnica adecuada, mantén la espalda recta, la mirada al frente y el cuerpo ligeramente inclinado. Los brazos deben moverse de forma relajada, sincronizados con las piernas. Aterriza con el medio del pie para reducir el impacto y mantén una respiración rítmica. Comienza a un ritmo cómodo y aumenta progresivamente.",
                    photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_999,
                    idUser = 1,
                    nombre = "Abdominales",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES),
                    nivel = Nivel.FACIL,
                    descripcion = "Acuéstate boca arriba, contrae el abdomen y eleva el torso hacia arriba lo máximo que puedas sin levantar la espalda del suelo. Consejos. A Algunos les ayuda poner las manos en el suelo o en el cuello, no tiene importancia. Al hacer Abdominales no hay que levantar el cuello, puede ser lesivo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_999".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_998,
                    idUser = 1,
                    nombre = "Plancha",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate en el suelo apoyando las puntas de los pies y todo el antebrazo. Mantén esta posición por un tiempo determinado. Mantén siempre la espalda recta respecto a las piernas y el cuello en posición neutra.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_998".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_997,
                    idUser = 1,
                    nombre = "Dead Hang",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.ANTEBRAZO),
                    nivel = Nivel.FACIL,
                    descripcion = "Quédate colgado en agarre prono y aguanta en esa posición un tiempo determinado. Este ejercicio mejora la fuerza de agarre. Recuerda colocar los dedos pulgares por debajo de la barra.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_997".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_996,
                    idUser = 1,
                    nombre = "Zancadas",
                    isSimetria = true,
                    musculoSet = mutableSetOf(Musculo.PIERNA, Musculo.GLUTEOS),
                    nivel = Nivel.FACIL,
                    descripcion = "Adelantar una pierna, dejando la otra atrás y flexionar la rodilla hasta un ángulo de 90º, después hay que volver a la posición inicial.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_996".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_995,
                    idUser = 1,
                    nombre = "Puentes femorales",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.PIERNA, Musculo.GLUTEOS),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate tumbado en el suelo boca arriba, flexiona rodillas y levanta la cabeza hasta que la espalda quede alineada con los muslos, vuelve a la posición inicial para completar una repetición.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_995".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_994,
                    idUser = 1,
                    nombre = "Elevaciones de gemelos",
                    isSimetria = true,
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate en el borde de un escalón o a la pata coja, contrae el gemelo para ponerte de puntillas y luego volver a bajar.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_994".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_993,
                    idUser = 1,
                    nombre = "Bandera",
                    isSimetria = true,
                    musculoSet = mutableSetOf(
                        Musculo.HOMBRO, Musculo.TRICEPS, Musculo.BICEPS,
                        Musculo.OBLICUOS, Musculo.ESPALDA, Musculo.ABDOMINALES
                    ),
                    nivel = Nivel.DIFICIL,
                    descripcion = "Colócate al lado de una barra vertical, agarra con los 2 brazos abiertos en forma de 'Y' la barra, sujétate y túmbate horizontalmente. Aguanta en esta postura todo el tiempo que puedas.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_993".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_992,
                    idUser = 1,
                    nombre = "Plancha lateral",
                    isSimetria = true,
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES, Musculo.OBLICUOS),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate en el suelo de lado, apoyando un antebrazo y el lateral de los pies. Mantén la cadera firme todo el tiempo que puedas.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_992".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_991,
                    idUser = 1,
                    nombre = "Plancha lateral de brazos extendidos",
                    isSimetria = true,
                    musculoSet = mutableSetOf(
                        Musculo.ABDOMINALES,
                        Musculo.OBLICUOS,
                        Musculo.HOMBRO
                    ),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate en el suelo de lado, con el brazo lateral extendido, la cadera elevada y el tronco y las piernas alineadas. Mantente por un tiempo determinado en esa posición sin perder la línea.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_991".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_990,
                    idUser = 1,
                    nombre = "Sentadilla con mancuerna",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.PIERNA, Musculo.GLUTEOS),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate con los pies separados y coge una mancuerna. Empieza a flexionar las rodillas y caderas como si te fueras a sentar, mantén la espalda siempre recta y baja hasta que los músculos estén paralelos al suelo y sube de nuevo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_990".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_989,
                    idUser = 1,
                    nombre = "Sentadilla de sumo",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.ESPALDA, Musculo.PIERNA, Musculo.GLUTEOS),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate con una apertura de piernas superior a lo normal, con la punta de los pies mirando hacia afuera y empieza a flexionar las rodillas y caderas como si te fueras a sentar, mantén la espalda siempre recta y baja hasta que los músculos estén paralelos al suelo y sube de nuevo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_989".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_988,
                    idUser = 1,
                    nombre = "Remo con mancuerna",
                    isSimetria = true,
                    musculoSet = mutableSetOf(Musculo.BICEPS, Musculo.TRAPECIO, Musculo.ESPALDA),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate de pie con las piernas semi flexionadas y la espalda inclinada hacia delante. Agarra la mancuerna con la mano y levántala hasta llevarla hacia tu abdomen. Después vuelve a la posición inicial para completar el ejercicio.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_988".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_987,
                    idUser = 1,
                    nombre = "Press de pectoral con barra",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate en el suelo boca arriba y con la barra cogida con ambas manos, extiende los brazos hasta arriba y acaba bajando a su posición original.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_987".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_986,
                    idUser = 1,
                    nombre = "Correr en el sitio",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.FACIL,
                    descripcion = "Para una técnica adecuada, mantén la espalda recta, la mirada al frente y el cuerpo ligeramente inclinado. Los brazos deben moverse de forma relajada, sincronizados con las piernas. Aterriza con el medio del pie para reducir el impacto y mantén una respiración rítmica. Comienza a un ritmo cómodo y aumenta progresivamente.",
                    photoUri = "${DRAWABLE_URI}ejer2_000_000".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_985,
                    idUser = 1,
                    nombre = "Peso muerto",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.ESPALDA, Musculo.GLUTEOS, Musculo.PIERNA),
                    nivel = Nivel.FACIL,
                    descripcion = "Coloca la barra en el suelo, agarra mientras flexionas las piernas y mantienes la espalda recta, levanta del suelo hasta que quedes con las piernas y la cadera extendidas, vuelve a la posición inicial para completar una repetición.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_985".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_984,
                    idUser = 1,
                    nombre = "Curl de biceps con mancuerna",
                    isSimetria = true,
                    musculoSet = mutableSetOf(Musculo.BICEPS),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate de pie, coge la mancuerna con una mano con las palmas hacia delante. Flexiona el codo para llevar la mancuerna a la altura de los hombros. Después baja el bíceps.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_984".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_983,
                    idUser = 1,
                    nombre = "Dead Hang con una mano",
                    isSimetria = true,
                    musculoSet = mutableSetOf(Musculo.BICEPS),
                    nivel = Nivel.MEDIO,
                    descripcion = "Quédate colgado en agarre prono con una mano y aguanta en esa posición un tiempo determinado. Este ejercicio mejora la fuerza de agarre. Recuerda colocar los dedos pulgares por debajo de la barra.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_983".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_982,
                    idUser = 1,
                    nombre = "Zancada lateral con mancuerna",
                    isSimetria = true,
                    musculoSet = mutableSetOf(
                        Musculo.ANTEBRAZO,
                        Musculo.BICEPS,
                        Musculo.ESPALDA,
                        Musculo.PIERNA
                    ),
                    nivel = Nivel.MEDIO,
                    descripcion = "Comienza con los pies juntos y una mancuerna a ambos lados del cuerpo. Mantén la espalda recta y el pecho hacia fuera mirando al frente. Luego da un paso grande con una pierna manteniendo la otra en su lugar. Al mismo tiempo flexiona la rodilla del pie que se ha movido hasta que el muslo esté paralelo al suelo y baja las mancuernas a cada lado del pie adelantado. Asegura de mantener el torso erguido y de no permitir que la rodilla adelantada sobrepase la punta del pie. Finalmente, empuja con la pierna que ha dado el paso para volver a la posición inicial.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_982".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_981,
                    idUser = 1,
                    nombre = "Sentadilla",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.PIERNA),
                    nivel = Nivel.FACIL,
                    descripcion = "Colócate con los pies separados. Empieza a flexionar las rodillas y caderas como si te fueras a sentar, mantén la espalda siempre recta y baja hasta que los músculos estén paralelos al suelo y sube de nuevo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_981".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_980,
                    idUser = 1,
                    nombre = "Muscle Up",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS,
                        Musculo.TRICEPS,
                        Musculo.PECHO,
                        Musculo.ESPALDA
                    ),
                    nivel = Nivel.MEDIO,
                    descripcion = "Empieza colgado, inclínate hacia atrás y adelante para coger un mejor impulso y sube de manera explosiva hasta superar el abdomen la barra y quédate en posición de fondo en la barra. Puede que necesites mejorar la flexibilidad de los hombros para mejorar en este ejercicio.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_980".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_979,
                    idUser = 1,
                    nombre = "Muscle Up con salto",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS,
                        Musculo.TRICEPS,
                        Musculo.PECHO,
                        Musculo.ESPALDA
                    ),
                    nivel = Nivel.FACIL,
                    descripcion = "Empieza dando un salto desde el suelo hasta la barra, inclínate hacia atrás y adelante para coger un mejor impulso y sube de manera explosiva hasta superar el abdomen la barra y quédate en posición de fondo en la barra. Para hacer mejor el ejercicio se puede usar una barra de altura media o baja.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_979".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_978,
                    idUser = 1,
                    nombre = "Flexión en pica",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO, Musculo.HOMBRO),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate con los pies separados y manos casi paralelas con los hombros. Flexiona los codos como si fueras a hacer una flexión hasta que la cabeza se acerque al suelo y después sube.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_978".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_977,
                    idUser = 1,
                    nombre = "Flexión con puños",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO),
                    nivel = Nivel.MEDIO,
                    descripcion = "Realiza flexiones normales pero apoyándote con los puños en lugar de las manos, para que suba la dificultad aumenta la distancia recorrida. Para personas que tengan problemas de muñecas puede ser más cómodo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_977".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_976,
                    idUser = 1,
                    nombre = "Flexión diamante",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate en el suelo con las manos unidas formando una figura de diamante. Realiza una flexión en esta posición. Esta variante aumenta la carga de trabajo en el tríceps.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_976".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_975,
                    idUser = 1,
                    nombre = "Fondos en barra",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.TRICEPS, Musculo.PECHO),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate con las dos manos en una barra, acerca el pectoral a la barra lo máximo posible y elévate con tus brazos y desciende para hacer una repetición. Puedes flexionar un poco la cadera para facilitar la colocación.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_975".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_974,
                    idUser = 1,
                    nombre = "Twists ruso con mancuerna",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.ABDOMINALES, Musculo.OBLICUOS),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate en el suelo con las rodillas elevadas a la altura del torso sin tocar el suelo, lo único que debe apoyarse en el suelo son los gluteos. Con una mancuerna realiza torsiones de un lado a otro. Realiza este movimiento de forma controlada y con un peso controlable, estas en una posición que puede ser comprometida para la espalda.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_974".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_973,
                    idUser = 1,
                    nombre = "Flexiones con una mano",
                    isSimetria = true,
                    musculoSet = mutableSetOf(
                        Musculo.TRICEPS,
                        Musculo.PECHO,
                        Musculo.ABDOMINALES,
                        Musculo.OBLICUOS
                    ),
                    nivel = Nivel.DIFICIL,
                    descripcion = "Abre las piernas para mantener mejor el equilibrio, coloca un brazo en la parte trasera del muslo y realiza flexiones con el otro brazo. Al principio puede costar realizar el rango de movimiento completo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_973".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_972,
                    idUser = 1,
                    nombre = "Apertura de brazos",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.PECHO,
                        Musculo.ESPALDA,
                        Musculo.TRAPECIO,
                        Musculo.HOMBRO
                    ),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate mirando al frente con las mancuernas, con las piernas abiertas y la espalda inclinada hacia delante. En ambas manos sujeta las mancuernas y empieza a abrir hasta llegar el brazo a la altura de los hombros. El movimiento más allá de la altura del hombro es lesivo, no debe superar la altura del hombro.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_972".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_971,
                    idUser = 1,
                    nombre = "Curl de biceps con barra",
                    isSimetria = false,
                    musculoSet = mutableSetOf(Musculo.BICEPS),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate con los pies abiertos y con la barra en las manos. Empieza a levantar la barra hacia arriba flexionando los codos.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_971".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_970,
                    idUser = 1,
                    nombre = "Lanzadas a bandera",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.OBLICUOS,
                        Musculo.ESPALDA,
                        Musculo.BICEPS,
                        Musculo.TRICEPS,
                        Musculo.HOMBRO,
                        Musculo.ABDOMINALES,
                        Musculo.TRAPECIO
                    ),
                    nivel = Nivel.MEDIO,
                    descripcion = "Utiliza el impulso de las piernas para lanzarte en posición de bandera. Cuando más controlado realices el ejercicio, más efectivo será. No hace falta que mantengas la posición final, solo márcala y vuelve a bajar.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_970".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_969,
                    idUser = 1,
                    nombre = "Bandera caida",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.OBLICUOS,
                        Musculo.ESPALDA,
                        Musculo.BICEPS,
                        Musculo.TRICEPS,
                        Musculo.HOMBRO,
                        Musculo.PECHO,
                        Musculo.ABDOMINALES,
                        Musculo.TRAPECIO
                    ),
                    nivel = Nivel.MEDIO,
                    descripcion = "Colócate con una mano a la altura superior de la cabeza y otra a la altura de la cintura. Estira la pierna unos 45º grados con respecto al suelo. Mantén esa posición durante un tiempo determinado.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_969".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_968,
                    idUser = 1,
                    nombre = "Dominada supina en L",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ABDOMINALES, Musculo.PIERNA, Musculo.ESPALDA
                    ),
                    nivel = Nivel.DIFICIL,
                    descripcion = "Manteniendo las piernas rectas en posición paralela al suelo realiza repeticiones de dominadas supinas.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_968".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_967,
                    idUser = 1,
                    nombre = "Vuelta al mundo",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.PIERNA, Musculo.ABDOMINALES, Musculo.OBLICUOS
                    ),
                    nivel = Nivel.DIFICIL,
                    descripcion = "Colócate colgando de la barra, mantén en todo momento las piernas rectas. Lleva los pies a un lado y comienza a dibujar un circulo completo en el aire.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_967".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_966,
                    idUser = 1,
                    nombre = "Dominadas a una mano",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.PIERNA, Musculo.ABDOMINALES, Musculo.OBLICUOS
                    ),
                    nivel = Nivel.DIFICIL,
                    descripcion = "Estando completamente colgado, suelta un brazo y realiza una dominada. El movimiento termina cuando el hombro llega a la altura de la barra. Con el brazo que no se utiliza lo mejor es pegarlo lo más posible al cuerpo.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_966".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_965,
                    idUser = 1,
                    nombre = "Flexión arquea",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.TRICEPS, Musculo.ABDOMINALES, Musculo.PECHO
                    ),
                    nivel = Nivel.DIFICIL,
                    descripcion = "Colócate en posición de flexiones con un agarre más amplio de lo normal, aproximadamente el doble del ancho de los hombros. En cada repetición estira un brazo a la vez que flexionas el otro, llevando el cuerpo hacia un lado.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_965".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_964,
                    idUser = 1,
                    nombre = "Flexión",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.TRICEPS, Musculo.PECHO
                    ),
                    nivel = Nivel.FACIL,
                    descripcion = "Coloca las manos en el suelo, con una apertura un poco amplia que la de los hombros y espalda recta. Flexiona los codos hasta que el pecho llegue hasta el suelo, intentando que los mismos no se abran demasiado hacia fuera. Vuelve a la posición inicial para completar la repetición.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_964".toUri()
                ),
                EjercicioBean(
                    idEjercicio = 1_999_963,
                    idUser = 1,
                    nombre = "Dominada supina",
                    isSimetria = false,
                    musculoSet = mutableSetOf(
                        Musculo.BICEPS, Musculo.ESPALDA
                    ),
                    nivel = Nivel.FACIL,
                    descripcion = "Elevate hasta colocar la barbilla por encima de la barra en agarre supino. Intenta bajar un poquito y volver a subir, realizando repeticiones cortas, de hasta unos 45º.",
                    photoUri = "${DRAWABLE_URI}ejer1_999_963".toUri()
                )
            )
        }
    }
}
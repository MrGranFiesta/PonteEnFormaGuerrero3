package com.mrgranfiesta.ponteenformaguerrero3.data.db.datasource

import androidx.core.net.toUri
import com.mrgranfiesta.ponteenformaguerrero3.domain.bean.MaterialBean
import com.mrgranfiesta.ponteenformaguerrero3.domain.constans.DRAWABLE_URI

class InitialMaterialDB {
    companion object {
        fun getListDataDefaultMaterial(): List<MaterialBean> {
            return listOf(
                MaterialBean(
                    idMaterial = 1_000_000,
                    idUser = 1,
                    nombre = "Comba",
                    isMaterialWeight = false,
                    descripcion = "La comba es ideal para entrenamientos de saltos y mejorar la resistencia cardiovascular. Ajusta la longitud según tus necesidades y utilízala en superficies planas.",
                    photoUri = "${DRAWABLE_URI}material1_000_000".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_999,
                    idUser = 1,
                    nombre = "Conos",
                    isMaterialWeight = false,
                    descripcion = "Los conos son utilizados para marcar zonas de entrenamiento y mejorar la agilidad. Colócalos en el suelo de forma estratégica para diseñar tu circuito de ejercicios.",
                    photoUri = "${DRAWABLE_URI}material999_999".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_998,
                    idUser = 1,
                    nombre = "Escala de velocidad",
                    isMaterialWeight = false,
                    descripcion = "La escala de velocidad es perfecta para entrenamientos de velocidad y agilidad. Colócala en el suelo y realiza ejercicios de desplazamiento y cambio de dirección.",
                    photoUri = "${DRAWABLE_URI}material999_998".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_997,
                    idUser = 1,
                    nombre = "Mancuerna",
                    isMaterialWeight = true,
                    descripcion = "Las mancuernas permiten ejercicios de fuerza y resistencia para diferentes grupos musculares. Asegúrate de seleccionar el peso adecuado para tus necesidades.",
                    photoUri = "${DRAWABLE_URI}material999_997".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_996,
                    idUser = 1,
                    nombre = "Barra de mancuerna, sin pesos",
                    isMaterialWeight = false,
                    descripcion = "La barra de mancuerna se usa con pesas para entrenamientos de fuerza. Añade discos de peso en los extremos de la barra según tus requerimientos.",
                    photoUri = "${DRAWABLE_URI}material999_996".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_995,
                    idUser = 1,
                    nombre = "Banda de resistencia",
                    isMaterialWeight = false,
                    descripcion = "Las bandas de resistencia son versátiles y ayudan en ejercicios de fuerza y flexibilidad. Asegúrate de elegir la resistencia adecuada para tu nivel de condición física.",
                    photoUri = "${DRAWABLE_URI}material999_995".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_994,
                    idUser = 1,
                    nombre = "Balón Medicinal",
                    isMaterialWeight = true,
                    descripcion = "El balón medicinal se usa en ejercicios de lanzamiento y entrenamientos funcionales. Verifica el peso del balón antes de usarlo y realiza ejercicios en áreas adecuadas.",
                    photoUri = "${DRAWABLE_URI}material999_994".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_993,
                    idUser = 1,
                    nombre = "Kettlebell",
                    isMaterialWeight = true,
                    descripcion = "El kettlebell es excelente para ejercicios de fuerza y acondicionamiento físico. Asegúrate de seleccionar el peso correcto y utiliza una técnica adecuada para evitar lesiones.",
                    photoUri = "${DRAWABLE_URI}material999_993".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_992,
                    idUser = 1,
                    nombre = "Pesa (Barra larga)",
                    isMaterialWeight = true,
                    descripcion = "Se compone de una barra metálica con discos de peso en ambos extremos.  la barra proporciona estabilidad y resistencia durante los movimientos, puede servir para entrenar ambos lados del cuerpo, asegurarse que los discos estén bien sujetos antes de su levantamiento",
                    photoUri = "${DRAWABLE_URI}material999_992".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_991,
                    idUser = 1,
                    nombre = "Silla",
                    isMaterialWeight = false,
                    descripcion = "La silla se usa para ejercicios de tríceps y como apoyo en diversos ejercicios. Asegúrate de que la silla sea estable y segura para su uso.",
                    photoUri = "${DRAWABLE_URI}material999_991".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_990,
                    idUser = 1,
                    nombre = "Banco",
                    isMaterialWeight = false,
                    descripcion = "El banco es esencial para ejercicios de banco plano, inclinado y declinado. Verifica que esté bien ensamblado y ajustado antes de usarlo.",
                    photoUri = "${DRAWABLE_URI}material999_990".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_989,
                    idUser = 1,
                    nombre = "Esterilla",
                    isMaterialWeight = false,
                    descripcion = "La esterilla proporciona comodidad y soporte en ejercicios de suelo y estiramientos. Asegúrate de que esté limpia y en buen estado antes de usarla.",
                    photoUri = "${DRAWABLE_URI}material999_989".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_988,
                    idUser = 1,
                    nombre = "Barra de dominadas",
                    isMaterialWeight = false,
                    descripcion = "La barra de dominadas se usa para entrenar la parte superior del cuerpo, como la espalda y los brazos. Asegúrate de que esté correctamente instalada y que puedas realizar los ejercicios de forma segura.",
                    photoUri = "${DRAWABLE_URI}material999_988".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_987,
                    idUser = 1,
                    nombre = "Colchoneta",
                    isMaterialWeight = false,
                    descripcion = "La colchoneta es esencial para ejercicios de yoga, pilates y entrenamientos en el suelo. Utilízala en superficies limpias y niveladas para mayor comodidad.",
                    photoUri = "${DRAWABLE_URI}material999_987".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_986,
                    idUser = 1,
                    nombre = "Paracaídas de resistencia",
                    isMaterialWeight = false,
                    descripcion = "El paracaídas de resistencia agrega resistencia al correr para mejorar la velocidad. Asegúrate de usarlo en un espacio seguro y con la suficiente distancia para evitar obstáculos.",
                    photoUri = "${DRAWABLE_URI}material999_986".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_985,
                    idUser = 1,
                    nombre = "Bolsa de arena",
                    isMaterialWeight = false,
                    descripcion = "La bolsa de arena se utiliza en ejercicios de levantamiento y entrenamientos funcionales. Asegúrate de que la bolsa esté bien cerrada y que su peso sea apropiado para tus ejercicios.",
                    photoUri = "${DRAWABLE_URI}material999_985".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_984,
                    idUser = 1,
                    nombre = "Chaleco lastrado",
                    isMaterialWeight = true,
                    descripcion = "El chaleco lastrado se usa para añadir peso adicional a tus entrenamientos de resistencia. Asegúrate de ajustar el peso de manera equilibrada y cómoda para evitar tensiones innecesarias.",
                    photoUri = "${DRAWABLE_URI}material999_984".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_983,
                    idUser = 1,
                    nombre = "Hula Hoops",
                    isMaterialWeight = false,
                    descripcion = "Los hula hoops son ideales para ejercicios de cintura y coordinación. Úsalos en áreas con suficiente espacio y asegúrate de que estén en buenas condiciones.",
                    photoUri = "${DRAWABLE_URI}material999_983".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_982,
                    idUser = 1,
                    nombre = "Disco de mancuerna",
                    isMaterialWeight = false,
                    descripcion = "Los discos de mancuerna se utilizan para ajustar el peso en ejercicios de fuerza. Asegúrate de que los discos estén bien sujetos a las mancuernas antes de su uso.",
                    photoUri = "${DRAWABLE_URI}material999_982".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_981,
                    idUser = 1,
                    nombre = "Barra de dominadas vertical",
                    isMaterialWeight = false,
                    descripcion = "Barra fija en el suelo, diseñada para soportar el peso corporal completo. Se utiliza en ejercicios especiales de dominadas.",
                    photoUri = "${DRAWABLE_URI}material999_981".toUri(),
                    confValue = 0.0
                ),
                MaterialBean(
                    idMaterial = 999_980,
                    idUser = 1,
                    nombre = "Pared",
                    isMaterialWeight = false,
                    descripcion = "Sirve como punto de apoyo para practicar algunos ejercicios que requieran un mejor equilibrio.",
                    photoUri = "${DRAWABLE_URI}material999_980".toUri(),
                    confValue = 0.0
                )
            )
        }
    }
}
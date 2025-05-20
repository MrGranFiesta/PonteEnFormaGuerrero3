package com.mrgranfiesta.ponteenformaguerrero3.domain.constans

import com.mrgranfiesta.ponteenformaguerrero3.domain.utils.StringUtils

//SPLASH SCREEM
const val SPLASH_CREATED_TXT_INITIAL = "Iniciando App"
const val SPLASH_CREATED_TXT_PHASE_1 = "Creando materiales, rutinas y ejercicios..."
const val SPLASH_CREATED_TXT_PHASE_2 = "Creando configuraciones..."

//DIALOG EDIT_STEP
const val LABEL_EDIT_STEP_REPETICION = "Nº de repeticiones"
const val LABEL_EDIT_STEP_CRONO = "Segundos"

//SubScreem END_CRONO
const val LABEL_ENHORABUENA = "¡¡¡Enhorabuena!!!"
const val LABEL_RUTINA_DONE = "¡Rutina completada!"

//CRONO - COMMONS
const val LABEL_LADO_IZQUIERDO = "Lado Izq."
const val LABEL_LADO_DERECHO = "Lado Drch."

// CRONO - CALENTAMINETO
const val LABEL_CALENTAMIENTO_CORRER = "Correr"

//START QUESTION RUTINA
const val LABEL_MUSICA = "Música"

//COMPONENT DATE_RANGE_FIELDS
const val LABEL_FECHA_INICIO_CAP = "Fecha Inicio"
const val LABEL_FECHA_INICIO = "Fecha Inicio"
const val LABEL_FECHA_FIN_CAP = "Fecha Fin"
const val LABEL_FECHA_FIN = "Fecha fin"

//COMMONS
const val LABEL_CERRAR = "Cerrar"

const val LABEL_CANCELAR_UPPER = "CANCELAR"
const val LABEL_ACEPTAR_UPPER = "ACEPTAR"

const val LABEL_OPT_NO = "No"
const val LABEL_OPT_SI = "Si"

const val LABEL_OPT_ACTIVO = "Activo"
const val LABEL_OPT_INACTIVO = "Inactivo"

const val LABEL_SELECIONAR = "Seleccionar"
const val LABEL_ATRAS = "Atrás"

//SNACKBAR HOST
class SnackbarHostText {
    companion object {
        fun snackbarHostErrorEmpty(label: String): String {
            return "Error, campo $label no puede estar vacío."
        }

        fun snackbarHostErrorMaxLength(label: String, maxLegth: Int) =
            "Error, campo $label no puede superar $maxLegth carácteres."

        fun snackbarHostErrorMinLength(label: String, maxLegth: Int) =
            "Error, campo $label debe superar los $maxLegth carácteres."

        fun snackbarHostErrorIlegalChar(label: String) =
            "Error, campo $label contiene carácteres no permitidos."

        fun snackbarHostErrorMalformetEmail(label: String) =
            "Error, campo $label contiene un formato erróneo."
    }
}

const val LABEL_SNACKBAR_ERROR_RUTINA_PREMIUM =
    "Esta rutina es exclusiva para usuarios premium. Suscríbete para desbloquear todo el contenido."

//COMPONENT CHARACTER COUNT LABEL
class CharacterCountLabelText {
    companion object {
        fun characterCountLabelMaxLength(maxCount: Int) =
            "El campo solo puede contener máximo $maxCount carácteres."
    }
}

//GENERIC NAME ATRIBUTES
const val LABEL_NOMBRE = "Nombre"
const val LABEL_EMAIL = "Email"
const val LABEL_PASSWORD = "Contraseña"
const val LABEL_NEW_PASSWORD = "Contraseña"
const val LABEL_OLD_PASSWORD = "Anterior contraseña"
const val LABEL_CONFIRM_PASSWORD = "Confirmar contraseña"
const val LABEL_CONFIRM_NEW_PASSWORD = "Confirmar nueva contraseña"
const val LABEL_MUSCULOS = "Músculos"
const val LABEL_NIVEL = "Nivel"
const val LABEL_SERIE = "Serie"
const val LABEL_UNIDAD = "Unidad"
const val LABEL_MATERIALES = "Materiales"
const val LABEL_SIMETRIA = "Simetría"
const val LABEL_DESCRIPCION = "Descripción"
const val LABEL_GRUPO_MUSCULAR = "Grupo Muscular"
const val LABEL_GRUPOS_MUSCULARES = "Grupos Musculares"
const val LABEL_EJERCICIO = "Ejercicio"
const val LABEL_TIEMPO_TOTAL = "Tiempo total"
const val LABEL_COMPLETADO = "Completado"
const val LABEL_FALLIDO = "Fallido"
const val LABEL_OMITIR = "Omitir"

//COLON
const val LABEL_NOMBRE_COLON = "Nombre:"
const val LABEL_YOU_NOMBRE_COLON = "Tu nombre:"
const val LABEL_EMAIL_COLON = "Email:"
const val LABEL_NIVEL_COLON = "Nivel:"
const val LABEL_SERIE_COLON = "Serie:"
const val LABEL_GRUPO_MUSCULAR_COLON = "Grupo Muscular:"
const val LABEL_SIMETRIA_COLON = "Simetría: "
const val LABEL_DESCRIPCION_COLON = "Descripción:"
const val LABEL_CALENTAMIENTO_COLON = "Calentamiento: "
const val LABEL_MOVILIDAD_ARTICULAR_COLON = "Movilidad articular: "
const val LABEL_ESTIRAMIENTOS_COLON = "Estiramientos: "
const val LABEL_EJERCICIO_COLON = "Ejercicios: "
const val LABEL_DESCANSO_COLON = "Descanso:"
const val LABEL_MATERIALES_COLON = "Materiales:"
const val LABEL_STAT_COLON = "Estadísticas:"
const val LABEL_ENFOCADOS_COLON = "Enfocados:"
const val LABEL_EJERCICOS_REALIZADOS = "Ejercicios realizados:"
const val LABEL_MUSCULOS_ENTRENADOS_COLON = "Músculos entrenados:"
const val LABEL_OTROS_MUSCULOS_COLON = "Otros músculos:"
const val LABEL_EJERCICIOS_COLON = "Ejercicios:"

//Unidades
const val LABEL_REPS = "reps"
const val LABEL_KG = "Kg"
const val LABEL_MINUTOS = "Minutos"
const val LABEL_SEGUNDOS = "Segundos"

//TIMMER AND DATA
class TimerFormmertText {
    companion object {
        fun getDescansoFormatter(timer: Int) =
            "$LABEL_DESCANSO_COLON ${StringUtils.segToMinForrmatter(timer)}"

        fun getSeriesFormatter(
            serie: Int,
            cantidad: Int,
            tipo: TipoEsfuerzo
        ) = "$serie series de ${
            StringUtils.toTipoEsfuerzoForrmatter(
                cantidad = cantidad,
                tipo = tipo
            )
        }"
    }
}

//TITLES - TopBar
const val LABEL_EJERCICIO_FORM_TITLE = "Edición - ejercicio"
const val LABEL_EJERCICIO_INFO_TITLE = "Detalle del ejercicio"
const val LABEL_LOGGIN = "Iniciar Sesión"
const val LABEL_CREATE_ACCOUNT = "Crear cuenta"
const val LABEL_EJERCICIO_LIST_TITLE = "Ejercicios"
const val LABEL_FAQ_LIST_TITLE = "Preguntas frecuentes"
const val LABEL_MATERIAL_FORM_TITLE = "Edición - material"
const val LABEL_MATERIAL_INFO_TITLE = "Detalle del material"
const val LABEL_USER_INFO_TITLE = "Perfil"
const val LABEL_MATERIAL_LIST_TITLE = "Materiales"
const val LABEL_RUTINA_FORM_TITLE = "Edición - rutina"
const val LABEL_RUTINA_INFO_TITLE = "Detalle de la rutina"
const val LABEL_STAT_INFO_TITLE = "Detalle de la estadística"
const val LABEL_RUTINA_LIST_TITLE = "Rutinas"
const val LABEL_SELECT_MATERIAL_TITLE = "Selecciona un material"
const val LABEL_START_RUTINA_QUESTION_TITLE = "¿Preparado?"
const val LABEL_STAT_LIST_TITLE = "Estadísticas"

//TITLES
const val LABEL_FILTRO = "Filtro"
const val LABEL_SELECT_NIVEL = "Selecciona un nivel"
const val LABEL_SELECT_MUSCULOS = "Selecciona grupos musculares"
const val LABEL_CONF_EJERCICIO = "Configuración del ejercicio"
const val LABEL_SELECT_MATERIAL = "Selecciona un material"
const val LABEL_SELECT_EJERCICIO = "Selecciona un ejercicio"
const val LABEL_MATERIAL_LIST_DIALOG = "Materiales necesarios"
const val LABEL_SELECT_OPTION_DIALOG = "Selecciona una opción"
const val LABEL_CHANGE_USERNAME_DIALOG = "Escribe tu nombre"

//BT
const val LABEL_REGISTRARSE_BT = "Registrarse"
const val LABEL_INIT_SESION_BT = "Iniciar sesión"
const val LABEL_CLOSE_SESION_BT = "Cerrar Sesión"
const val LABEL_CREATE_ACCOUNT_BT = "Crear cuenta"
const val LABEL_START_RUTINA_BT = "Empezar rutina"
const val LABEL_ADD_EJERCICIO_BT = "Agregar ejercicio"
const val LABEL_SELECT_NIVEL_BT = "Selecciona nivel"
const val LABEL_SELECIONAR_MUSCULOS_BT = "Seleccionar músculos"
const val LABEL_SELECT_MATERIAL_BT = "Seleccionar materiales"
const val LABEL_COMPLETADO_BT = "¡Completado!"
const val LABEL_FINALIZAR_BT = "Finalizar"
const val LABEL_EMPEZAR_BT = "Empezar"
const val LABEL_DELETE_USER_BT = "Borrar usuario"
const val LABEL_USER_PREMIUM_BT = "Probar premium gratis por 7 días"
const val LABEL_CANCEL_PREMIUM_BT = "Cancelar suscripción premium"

//QUESTIONS GENERIC
const val LABEL_QUESTION_START_MATERIAL = "¿Todos los materiales preparados?"
const val LABEL_QUESTION_START_MUSIC = "¿Tienes la música preparada para entrenar?"
const val LABEL_QUESTION_USA_PESO = "¿Se usa con peso ajustable?"
const val LABEL_QUESTION_ESTIRAMIENTOS = "¿Quieres hacer estiramientos?"
const val LABEL_QUESTION_CALENTAMIENTO = "¿Quieres omitir el calentamiento?"
const val LABEL_QUESTION_MOV_ARTICULAR = "¿Quieres omitir la movilidad articular?"

//QUESTIONS DELETE
const val LABEL_DELETE_EJERCICIO = "¿Quieres eliminar el ejercicio?"
const val LABEL_DELETE_RUTINA = "¿Quieres eliminar la rutina?"
const val LABEL_DELETE_MATERIAL = "¿Quieres eliminar el material?"
const val LABEL_DELETE_USER = "¿Quieres eliminar el usuario?"
const val LABEL_SUSCRIPCTION_PREMIUM = "¿Quieres suscribirte al plan premium?"
const val LABEL_CANCEL_PREMIUM = "¿Quieres desuscribirte al plan premium?"
const val LABEL_DELETE_USER_WARNING = "(esta acción es importante y no reversible)"
const val LABEL_TRY_FREE_PREMIUM_WARNING = "(La prueba es gratis durante 7 dias)"

const val LABEL_DELETE_STAT = "¿Quieres eliminar las estadísticas?"

//QUESTIONS CONFIRM
const val LABEL_CONFIRM_CHANGE = "¿Quieres confirmar los cambios realizados?"
const val LABEL_CONFIRM_CLOSE_SESION = "¿Estás seguro que quieres cerrar sesión?"
const val LABAL_QUESTION_WARNING_EXIT = "¿Desea salir sin guardar?"
const val LABAL_QUESTION_WARNING_EXIT_RUTINA = "¿Desea salir de la rutina?"

//ERROR GENERIC
const val LABEL_NOT_EMPTY = "El campo no puede estar vacío."
const val LABEL_ERROR_CHARACTER_FIELD = "Error: Se han ingresado caracteres inválidos."
const val LABEL_ERROR_CHARACTER =
    "Error: Ingresado caracteres inválidos. Por favor, utilice solo caracteres permitidos."
const val LABEL_ERROR_INIT_DATE_UPPER_THAN_END_DATE_FIELD =
    "Error: La fecha de inicio es superior a la fecha fin, no se va a tener en cuenta el filtro."
const val LABEL_ERROR_EMPTY_RUTINA_STEP = "Error: La rutina no tiene ejercicios."
const val LABEL_ERROR_NOT_PERMISSION = "Las estadísticas solamente están disponibles con la suscripción premium. \n\n¡Suscríbete ahora y desbloquea todas las funciones exclusivas!"
const val LABEL_ERROR_GENERIC = "Se ha produccido un error"

//ERROR NOT FIND
const val LABEL_NOT_FIND_EJERCICIOS = "No se han podido encontrar ejercicios."
const val LABEL_NOT_FIND_MATERIAL = "No se han podido encontrar materiales."
const val LABEL_NOT_FIND_RUTINAS = "No se han podido encontrar rutinas."

//EMPTY DATA
const val LABEL_MATERIAL_PESO = "El material se usa con peso ajustable."
const val LABEL_MATERIAL_FOR_EJERCICIO = "Aún no se han agregado materiales al ejercicio."
const val LABEL_EMPTY_STEP_LIST = "Aún no se han agregado ejercicios a la rutina."
const val LABEL_EMPTY_MATERIAL = "Esta rutina no requiere de ningún material."
const val LABEL_EMPTY_GRUPO_MUSCULAR = "No seleccionados."
const val LABEL_EMPTY_DESCRIPCION = "Sin descripción."
const val LABEL_PHOTO_URI = "No hay imagen seleccionada."

//TOOLTIPS
const val LABEL_UPLOAD_IMG_TOOLSTIP = "Subir imagen"
const val LABEL_DELETE_IMG_TOOLSTIP = "Eliminar imagen"
const val LABEL_VIEW_MATERIAL_TOOLSTIP = "Ver materiales"
const val LABEL_EDITS_MATERIAL_TOOLSTIP = "Editar material"
const val LABEL_DELETE_MATERIAL_TOOLSTIP = "Eliminar material"
const val LABEL_FILTER_TOOLSTIP = "Filtrar"
const val LABEL_MODO_TEXTO_TOOLSTIP = "Modo texto"
const val LABEL_MODO_CALENDARIO_TOOLSTIP = "Modo calendario"
const val LABEL_DELETE_EJERCICIO_TOOLSTIP = "Eliminar ejercicio"
const val LABEL_EDITS_EJERCICIO_TOOLSTIP = "Editar ejercicio"
const val LABEL_SAVE_MATERIAL_TOOLSTIP = "Guardar material"
const val LABEL_SAVE_RUTINA_TOOLSTIP = "Guardar rutina"
const val LABEL_DELETE_RUTINA_TOOLSTIP = "Eliminar rutina"
const val LABEL_EDIT_RUTINA_TOOLSTIP = "Editar rutina"
const val LABEL_STAT_RUTINA_TOOLSTIP = "Eliminar estadísticas"
const val LABEL_ADD_EJERCICIO_TOOLSTIP = "Añadir ejercicio"
const val LABEL_ADD_MATERIAL_TOOLSTIP = "Añadir material"
const val LABEL_ADD_RUTINA_TOOLSTIP = "Añadir rutina"
const val LABEL_SAVE_EJERCICIO_TOOLSTIP = "Guardar ejercicio"
const val LABEL_TUTORIAL_STEP_REORDER_TOOLSTIP =
    "Presiona y arrastra los elementos para reordenarlos"
const val LABEL_RUTINA_PREMIUN_TOOLSTIP = "Rutina premium"
const val LABEL_FAQ_RESPONSE_TOOLSTIP = "Ver respuesta"

//OTHER
const val LABEL_BORRAR = "Borrar"
const val LABEL_GALERY = "Galería"
const val LABEL_CHANGE_PASSWORD = "Cambiar contraseña"
const val LABEL_TITLE_APP = "Ponte en forma"
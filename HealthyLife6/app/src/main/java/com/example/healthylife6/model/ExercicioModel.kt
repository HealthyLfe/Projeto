package com.example.healthylife6.model

data class ExercicioModel(
    var exerId: String,
    var titulo: String,
    var tempo: String,
    var desc:String,
    var img: String

) {

    constructor() : this("", "","", "", "")

    constructor(
        exerId: String,
        titulo: String,
        tempo: String,
        desc: String

    ) : this(exerId, titulo, tempo, desc, "")
}
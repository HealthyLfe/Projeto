package com.example.healthylife6.model

data class ReceitaModel(
    var recId: String,
    var titulo: String,
    var ingrediente: String,
    var desc:String,
    var categoria: String,
    var img: String

) {

    constructor() : this("", "", "", "", "", "")

    constructor(
        recId: String,
        titulo: String,
        ingrediente: String,
        desc: String,
        categoria: String

    ) : this(recId, titulo, ingrediente, desc, categoria, "")
}
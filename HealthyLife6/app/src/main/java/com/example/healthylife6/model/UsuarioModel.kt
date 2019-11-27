package com.example.healthylife6.model

class UsuarioModel {

    var userId: String? = null
    var nome: String? = null
    var sexo: String? = null
    var idade:String? = null
    var peso:String? = null
    var altura:String? = null
    var email:String? = null
    var senha: String? = null

    constructor() {}

    constructor(
        userId: String,
        nome: String,
        sexo: String,
        idade: String,
        peso: String,
        altura: String,
        email: String,
        senha: String
    ) {
        this.userId = userId
        this.nome = nome
        this.sexo = sexo
        this.idade = idade
        this.peso = peso
        this.altura = altura
        this.email = email
        this.senha = senha
    }
}
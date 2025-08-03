package com.keepcoding.agdragonball.domain.entities

import kotlin.random.Random

data class Hero(
    val id: String,
    val foto: String,
    val name: String,
    val life: Int = 100,
){
    fun isAlive() : Boolean{
        return this.life > 0
    }
    fun heal(){
        if(this.isAlive()){
            this.life + 10
        }
    }
    fun fight(){
        if(this.isAlive()){
            this.life - Random.nextInt()
        }
    }
}

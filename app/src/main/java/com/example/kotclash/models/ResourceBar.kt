package com.example.kotclash.models


class ResourceBar {

    //TODO
    var resources = 0f    //à faire en pourcent => s'accorde bien avec view
    val speedFill = 0f

    fun checkResourceBar(): Float{
        return resources
    }


    fun updateResourceBar(elapsedTimeMS: Long){
        resources += elapsedTimeMS*speedFill
    }
}
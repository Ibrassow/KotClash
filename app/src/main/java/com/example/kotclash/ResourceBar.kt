package com.example.kotclash

class ResourceBar {

    //TODO
    var resources = 0f
    val speedFill = 0f

    fun checkResourceBar(): Float{
        return resources
    }


    fun updateResourceBar(elapsedTimeMS: Long){
        resources += elapsedTimeMS*speedFill
    }
}

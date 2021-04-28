package com.example.kotclash.models


class ResourceBar {

    //TODO
    var resources = 200000000000000f
    val speedFill = 1f

    fun checkResourceBar(): Float{
        return resources
    }


    fun updateResourceBar(elapsedTimeMS: Long){
        resources += elapsedTimeMS*speedFill
    }
}
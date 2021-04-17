package com.example.kotclash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.view.View



class MapLoader(var context : Context, mapChoice : String, val view: View) {

    var grass : Bitmap //Don't join yet
    var soil : Bitmap

    var paint = Paint()

    lateinit var map : Map

    init{
        // Don't touch if you don't want to die
        grass = BitmapFactory.decodeResource(context.resources, R.drawable.grass)
        soil = BitmapFactory.decodeResource(context.resources, R.drawable.soil)
        map = loadMap(mapChoice)
    }

    fun returnMap() : Map{
        return map
    }



    fun loadMap(mapName : String) : Map{

        var grid = Map() //temp fix

        when (mapName){
            "spring" -> grid = parseFile("spring")
        }

        return grid
    }


    fun parseFile(filename : String) : Map {

        var map = Map()
        val inputStream = context.assets.open("$filename.txt")
        var lineList = mutableListOf<String>()


        var xi = 0f
        var yi = 0f
        var i = 0
        var j = 0

        inputStream.bufferedReader().use(){
            it.forEachLine {
                line -> lineList = line.split(" ") as MutableList<String>

                map.grid.add(mutableListOf()) //Add a new row
                for (tileElement in lineList){
                    when(tileElement){
                        "G" -> map.grid[i].add(Tile(xi, yi, "grass"))
                        "S" -> map.grid[i].add(Tile(xi, yi, "soil"))
                    }
                    j++
                    //xi += Renderable.RENDERABLE_WIDTH
                    xi++
                }
                //yi += Renderable.RENDERABLE_HEIGHT
                yi++
                i++
                xi = 0f
            }


                return map
            }

    }



}

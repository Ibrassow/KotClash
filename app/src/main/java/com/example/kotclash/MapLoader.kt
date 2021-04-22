package com.example.kotclash

import android.content.Context
import android.graphics.Paint
import android.util.Log


class MapLoader() {

    var paint = Paint()
    var map = Map()


    fun returnMap() : Map{
        return map
    }


    fun loadMap(mapName : String) : Map{

        if (map.grid.isNotEmpty()){
            map.clearMap()
        }

        //TODO Add other maps
        when (mapName){
            "spring" -> map = parseFile("spring")
        }
        return map
    }


    fun parseFile(filename : String) : Map {

        //val inputStream = context.assets.open("$filename.txt")
        val inputStream = App.getContext().resources.assets.open("$filename.txt")
        var lineList = mutableListOf<String>()
        val posBases = mutableMapOf<String, Pair<Float, Float>>()
        //Temporary
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
                        "*" -> map.grid[i].add(Tile(xi, yi, "soil"))
                        "A" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            posBases.put("ally", Pair(xi, yi))
                            Log.d("allyPosBase", "$xi $yi")
                        }
                        "E" -> {
                            map.grid[i].add(Tile(xi, yi, "soil"))
                            posBases.put("enemy", Pair(xi, yi))
                            Log.d("enemyPosBase", "$xi $yi")
                        }
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

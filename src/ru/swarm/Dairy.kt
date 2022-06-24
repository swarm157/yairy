package ru.swarm

class Dairy(var pages: ArrayList<String>) : java.io.Serializable {

    override fun toString(): String {
        return "Dairy(pages=$pages)"
    }

}
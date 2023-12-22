package com.itis.android_homework.utils

object ItemsDataGenerator {
    fun generateItemsList(numberOfElements: Int): MutableList<Int> {
        val itemsList = mutableListOf<Int>()
        var elementCount = numberOfElements
        // Generate items based on the given logic
        while (elementCount > 5) {
            // First block: 6 elements
            val list = listOf(2,0,1,1,0,2)
            itemsList.addAll(list)
            elementCount -= 6
        }
        when (elementCount) {
            5 -> {
                val list = listOf(2,0,1,1,0)
                itemsList.addAll(list)
            }
            4 -> {
                val list = listOf(2,1,1,2)
                itemsList.addAll(list)
            }
            3 -> {
                val list = listOf(2,1,1)
                itemsList.addAll(list)
            }
            2 -> {
                val list = listOf(0,0)
                itemsList.addAll(list)
            }
            1 -> {
                val list = listOf(2)
                itemsList.addAll(list)
            }
        }

        return itemsList
    }
}
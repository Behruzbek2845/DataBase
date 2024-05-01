package com.example.database.db

import com.example.database.models.Users

interface MyDbInterface {
    fun addUser(user: Users)
    fun showUsers():ArrayList<Users>
    fun editUser(users: Users)
    fun deleteUser(users: Users)
}
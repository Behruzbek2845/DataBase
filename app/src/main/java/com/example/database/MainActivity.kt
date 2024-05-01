package com.example.database

import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.database.adapters.RvAdapter
import com.example.database.databinding.ActivityMainBinding
import com.example.database.databinding.ItemDialogBinding
import com.example.database.db.MyDbHelper
import com.example.database.db.MyDbInterface
import com.example.database.models.Users

class MainActivity : AppCompatActivity(), RvAdapter.RvAction {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    lateinit var rvAdapter: RvAdapter
    lateinit var myDbHelper: MyDbInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        myDbHelper = MyDbHelper(this)


        binding.btnAdd.setOnClickListener {
            val dialog = AlertDialog.Builder(this).create()
            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)

            itemDialogBinding.btnSave.setOnClickListener {
                val user = Users(
                    1,
                    itemDialogBinding.edtName.text.toString(),
                    itemDialogBinding.edtNumber.text.toString()
                )
                myDbHelper.addUser(user)
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                onResume()
                dialog.cancel()
            }
            dialog.setView(itemDialogBinding.root)
            dialog.show()
        }

    }

    override fun onResume() {
        super.onResume()
        val list = myDbHelper.showUsers()
        rvAdapter = RvAdapter(this, list)
        binding.rv.adapter = rvAdapter
    }

    override fun moreClick(user: Users, imageView: ImageView) {
        val popupMenu = PopupMenu(this@MainActivity, imageView)
        popupMenu.inflate(R.menu.popup_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> {
                    val dialog = AlertDialog.Builder(this).create()
                    val customDialog = ItemDialogBinding.inflate(layoutInflater)
                    dialog.setView(customDialog.root)
                    customDialog.edtName.setText(user.name)
                    customDialog.edtNumber.setText(user.number)
                    customDialog.btnSave.setOnClickListener {
                        user.name = customDialog.edtName.text.toString()
                        user.number = customDialog.edtNumber.text.toString()
                        myDbHelper.editUser(user)
                        onResume()
                        dialog.cancel()
                    }
                    dialog.show()
                }
                R.id.delete -> {
                    myDbHelper.deleteUser(user)
                    onResume()
                }
            }
            true
        }
        popupMenu.show()
    }
}
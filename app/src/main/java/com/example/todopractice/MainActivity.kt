package com.example.todopractice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todopractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 사후처리 위한 ActivityResultLauncher
        val requestLauncher = registerForActivityResult(
            // Contract
            ActivityResultContracts.StartActivityForResult())
        {
            // Callback
            it.data!!.getStringExtra("result")?.let {
                datas?.add(it)
                adapter.notifyDataSetChanged()
            }
        }
        // launch
        binding.mainFab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            requestLauncher.launch(intent)
        }

        datas = mutableListOf<String>()

        // DB 데이터 불러오기
        val db = DBHelper(this).readableDatabase
        val cursor = db.rawQuery("select * from TODO_TB", null)

        cursor.run {
            while(moveToNext()){
                datas?.add(cursor.getString(1))
            }
        }
        db.close()

        // 화면 구성
        val layoutManager = LinearLayoutManager(this)
        binding.mainRecyclerView.layoutManager=layoutManager
        adapter=MyAdapter(datas)
        binding.mainRecyclerView.adapter=adapter
        binding.mainRecyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemDeleteClick(position: Int) {
                val itemToRemove = datas?.get(position)
                val db = DBHelper(this@MainActivity).writableDatabase
                db.execSQL("DELETE FROM TODO_TB WHERE todo = ?", arrayOf(itemToRemove))
                db.close()
                adapter.removeItem(position)
            }

            override fun onItemEditClick(position: Int) {
                val itemToEdit = datas?.get(position)
                val intent = Intent(this@MainActivity, EditActivity::class.java).apply {
                    putExtra("todo", itemToEdit)
                    putExtra("position", position)
                }
                startActivity(intent)
            }
        }
    }
}